package com.management.event_management.infrastructure.services.payment;

import com.management.event_management.domain.entities.payment.Payment;
import com.management.event_management.domain.services.payment.PaymentGateway;
import org.springframework.stereotype.Service;

@Service
public class TelebirrPaymentGateway implements PaymentGateway {

    @Override
    public String initiatePayment(Payment payment) {
        // TODO: Implement
        return "https://telebirr.com/pay/" + payment.getId();
    }
}