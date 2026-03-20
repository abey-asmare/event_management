package com.management.event_management.domain.services.payment;

import com.management.event_management.domain.entities.payment.Payment;

public interface PaymentGateway {

    String initiatePayment(Payment payment);
}