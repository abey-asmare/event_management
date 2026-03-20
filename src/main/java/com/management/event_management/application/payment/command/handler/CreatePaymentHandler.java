package com.management.event_management.application.payment.command.handler;

import com.management.event_management.application.payment.command.CreatePaymentCommand;
import com.management.event_management.application.payment.factory.PaymentFactory;
import com.management.event_management.domain.entities.booking.Booking;
import com.management.event_management.domain.entities.payment.Payment;
import com.management.event_management.domain.services.payment.PaymentGateway;
import com.management.event_management.infrastructure.persistence.repositories.booking.BookingRepository;
import com.management.event_management.infrastructure.persistence.repositories.payment.PaymentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CreatePaymentHandler {

    private final PaymentRepository paymentRepository;
    private final BookingRepository bookingRepository;
    private final PaymentGateway paymentGateway;
    private final PaymentFactory paymentFactory = new PaymentFactory();


    public CreatePaymentHandler(
            PaymentRepository paymentRepository,
            BookingRepository bookingRepository,
            PaymentGateway paymentGateway) {
        this.paymentRepository = paymentRepository;
        this.bookingRepository = bookingRepository;
        this.paymentGateway = paymentGateway;
    }

    @Transactional
    public Payment handle(CreatePaymentCommand command) {
        Booking booking = bookingRepository.findById(command.getBookingId())
                .orElseThrow(() -> new IllegalArgumentException("Booking not found"));

        Payment payment = paymentFactory.createPayment(booking, command.getAmount());
        paymentRepository.save(payment);

        String receiptUrl = paymentGateway.initiatePayment(payment);
        payment.markAsPending();
        paymentRepository.save(payment);

        return payment;
    }
}