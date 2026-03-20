package com.management.event_management.application.payment.command.handler;

import com.management.event_management.application.payment.command.FailPaymentCommand;
import com.management.event_management.domain.entities.payment.Payment;
import com.management.event_management.infrastructure.persistence.repositories.payment.PaymentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FailPaymentHandler {

    private final PaymentRepository paymentRepository;

    public FailPaymentHandler(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @Transactional
    public Payment handle(FailPaymentCommand command) {
        Payment payment = paymentRepository.findById(command.getPaymentId())
                .orElseThrow(() -> new IllegalArgumentException("Payment not found"));

        payment.fail();
        paymentRepository.save(payment);

        return payment;
    }
}