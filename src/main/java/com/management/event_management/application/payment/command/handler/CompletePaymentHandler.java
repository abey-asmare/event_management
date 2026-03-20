package com.management.event_management.application.payment.command.handler;

import com.management.event_management.application.payment.command.CompletePaymentCommand;
import com.management.event_management.domain.entities.payment.Payment;
import com.management.event_management.domain.enums.PaymentStatus;
import com.management.event_management.domain.services.payment.PaymentGateway;
import com.management.event_management.infrastructure.persistence.repositories.payment.PaymentRepository;
import com.management.event_management.infrastructure.services.payment.PaymentGatewayRegistry;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Slf4j
public class CompletePaymentHandler {

    private final PaymentRepository paymentRepository;
    private final PaymentGatewayRegistry gatewayRegistry;

    @Transactional
    public Payment handle(CompletePaymentCommand command) {

        Payment payment = paymentRepository.findByTransactionRef(command.getTransactionRef())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Payment not found for transaction reference: " + command.getTransactionRef()
                ));

        if (payment.getStatus() == PaymentStatus.COMPLETED) {
            log.info("Payment already completed: {}", payment.getTransactionRef());
            return payment;
        }

        PaymentGateway gateway = gatewayRegistry.getGateway(payment.getPaymentMethod());

        boolean verified = gateway.verifyPayment(command.getTransactionRef());

        if (verified) {
            String receiptUrl = "https://chapa.co/transaction/" + command.getTransactionRef();

            payment.complete(receiptUrl);
            log.info("Payment completed successfully: {}", payment.getTransactionRef());

        } else {
            payment.fail();
            log.warn("Payment verification failed: {}", payment.getTransactionRef());
        }

        paymentRepository.save(payment);
        return payment;
    }
}