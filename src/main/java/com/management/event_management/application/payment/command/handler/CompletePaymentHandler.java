package com.management.event_management.application.payment.command.handler;

import com.management.event_management.application.payment.command.CompletePaymentCommand;
import com.management.event_management.domain.entities.booking.Booking;
import com.management.event_management.domain.entities.booking.Ticket;
import com.management.event_management.domain.entities.event.Seat;
import com.management.event_management.domain.entities.payment.Payment;
import com.management.event_management.domain.enums.BookingStatus;
import com.management.event_management.domain.enums.PaymentStatus;
import com.management.event_management.domain.enums.TicketStatus;
import com.management.event_management.domain.exceptions.payment.PaymentNotFoundException;
import com.management.event_management.infrastructure.persistence.repositories.booking.BookingRepository;
import com.management.event_management.infrastructure.persistence.repositories.payment.PaymentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class CompletePaymentHandler {

    private final PaymentRepository paymentRepository;
    private final BookingRepository bookingRepository;

    @Transactional
    public Payment handle(CompletePaymentCommand command) {
        log.info("Processing payment completion for txRef: {}", command.getTransactionRef());

        Payment payment = paymentRepository.findByTransactionRef(command.getTransactionRef())
                .orElseThrow(() -> new PaymentNotFoundException("Payment not found: " + command.getTransactionRef()));

        if (payment.getStatus() == PaymentStatus.COMPLETED) {
            log.info("Payment already completed: {}", command.getTransactionRef());
            return payment;
        }

        payment.complete(command.getReceiptUrl());
        payment = paymentRepository.save(payment);
        log.info("Payment completed. Receipt URL: {}", command.getReceiptUrl());

        Booking booking = payment.getBooking();
        booking.setStatus(BookingStatus.CONFIRMED);
        log.info("Booking {} marked as CONFIRMED", booking.getId());

        if (booking.getEvent().isSeatBased() && booking.getReservedSeatIds() != null) {
            List<Seat> eventSeats = booking.getEvent().getSeats();
            for (UUID seatId : booking.getReservedSeatIds()) {
                Seat seat = eventSeats.stream()
                        .filter(s -> s.getId().equals(seatId))
                        .findFirst()
                        .orElseThrow(() -> new IllegalStateException("Seat not found: " + seatId));

                Ticket ticket = new Ticket();
                ticket.setBooking(booking);
                ticket.setEvent(booking.getEvent());
                ticket.setSeat(seat);
                ticket.setHolderUser(booking.getUser());
                ticket.setHolderName(booking.getUser().getName());
                ticket.setClaimToken(generateClaimToken());
                ticket.setStatus(TicketStatus.CLAIMED);

                booking.getTickets().add(ticket);
            }
            log.info("{} tickets generated", booking.getReservedSeatIds().size());
        }

        bookingRepository.save(booking);
        return payment;
    }

    private String generateClaimToken() {
        return UUID.randomUUID().toString().replace("-", "").substring(0, 12).toUpperCase();
    }
}