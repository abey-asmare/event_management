package com.management.event_management.application.payment.command.handler;

import com.management.event_management.api.dto.payment.response.PaymentInitiationResponse;
import com.management.event_management.application.payment.command.CreatePaymentCommand;
import com.management.event_management.domain.entities.booking.Booking;
import com.management.event_management.domain.entities.payment.Payment;
import com.management.event_management.domain.services.payment.PaymentGateway;
import com.management.event_management.domain.valueobjects.Money;
import com.management.event_management.infrastructure.persistence.repositories.booking.BookingRepository;
import com.management.event_management.infrastructure.persistence.repositories.payment.PaymentRepository;
import com.management.event_management.infrastructure.services.payment.PaymentGatewayRegistry;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class CreatePaymentHandler {

    private final PaymentRepository paymentRepository;
    private final BookingRepository bookingRepository;
    private final PaymentGatewayRegistry gatewayRegistry;

    @Transactional
    public PaymentInitiationResponse handle(CreatePaymentCommand command) {
        Booking booking = bookingRepository.findById(command.getBookingId())
                .orElseThrow(() -> new IllegalArgumentException("Booking not found"));
        Money amount = booking.calculateTotalAmount();

        Payment payment = Payment.create(booking);
        payment.setPaymentMethod(command.getPaymentMethod());

        payment.markAsPending();

        PaymentGateway gateway = gatewayRegistry.getGateway(command.getPaymentMethod());

        PaymentInitiationResponse domainResponse = gateway.initiatePayment(payment);

        payment.setTransactionRef(domainResponse.getTransactionRef());
        paymentRepository.save(payment);

        return new PaymentInitiationResponse(domainResponse.getCheckoutUrl(), domainResponse.getTransactionRef());
    }
}