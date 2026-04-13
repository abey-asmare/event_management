package com.management.event_management.application.payment.command.handler;

import com.management.event_management.api.dto.payment.response.PaymentInitiationResponse;
import com.management.event_management.application.payment.command.CreatePaymentCommand;
import com.management.event_management.domain.entities.booking.Booking;
import com.management.event_management.domain.entities.payment.Payment;
import com.management.event_management.domain.exceptions.payment.PaymentInitializationException;
import com.management.event_management.domain.services.payment.PaymentGateway;
import com.management.event_management.infrastructure.persistence.repositories.booking.BookingRepository;
import com.management.event_management.infrastructure.persistence.repositories.payment.PaymentRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CreatePaymentHandler {

    private final BookingRepository bookingRepository;
    private final PaymentRepository paymentRepository;
    private final PaymentGateway paymentGateway;

    @Transactional
    public PaymentInitiationResponse handle(CreatePaymentCommand command) {
        Booking booking = bookingRepository.findById(command.getBookingId())
                .orElseThrow(() -> new EntityNotFoundException("Booking not found"));

        Payment payment = Payment.create(booking);
        payment.setPaymentMethod(command.getPaymentMethod());
        payment = paymentRepository.save(payment);
        log.info("Payment created with id: {}", payment.getId());

        PaymentInitiationResponse response;
        try {
            response = paymentGateway.initiatePayment(payment);
        } catch (Exception e) {
            log.error("Failed to initiate payment with gateway", e);
            throw new PaymentInitializationException("Payment gateway error", e);
        }

        payment.setTransactionRef(response.getTransactionRef());
        payment.markAsPending();   // <-- critical: status becomes PENDING
        paymentRepository.save(payment);
        log.info("Payment {} marked as PENDING with tx_ref: {}", payment.getId(), response.getTransactionRef());

        return response;
    }
}