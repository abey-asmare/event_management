package com.management.event_management.infrastructure.services.payment;

import com.management.event_management.api.dto.payment.response.PaymentInitiationResponse;
import com.management.event_management.domain.entities.payment.Payment;
import com.management.event_management.domain.enums.PaymentMethod;
import com.management.event_management.domain.services.payment.PaymentGateway;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class TelebirrPaymentGateway implements PaymentGateway {
    @Override
    public PaymentInitiationResponse initiatePayment(Payment payment) {
        return new PaymentInitiationResponse("https://telebirr.com/pay/" + payment.getId(), "tx-" + UUID.randomUUID());
    }
    @Override
    public boolean verifyPayment(String transactionRef) { return false; }
    @Override
    public PaymentMethod getSupportedMethod() { return PaymentMethod.TELEBIRR; }
}