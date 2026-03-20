package com.management.event_management.application.payment.command.handler;

import com.management.event_management.application.payment.command.CompletePaymentCommand;
import com.management.event_management.domain.entities.payment.Payment;
import com.management.event_management.infrastructure.persistence.repositories.payment.PaymentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CompletePaymentHandler {

    private final PaymentRepository paymentRepository;

    public CompletePaymentHandler(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @Transactional
    public Payment handle(CompletePaymentCommand command) {
        Payment payment = paymentRepository.findById(command.getPaymentId())
                .orElseThrow(() -> new IllegalArgumentException("Payment not found"));

        payment.complete(command.getReceiptUrl());
        paymentRepository.save(payment);

        return payment;
    }
}