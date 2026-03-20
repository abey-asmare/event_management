package com.management.event_management.domain.services.payment;

import com.management.event_management.api.dto.payment.response.PaymentInitiationResponse;
import com.management.event_management.domain.entities.payment.Payment;
import com.management.event_management.domain.enums.PaymentMethod;

public interface PaymentGateway {
    PaymentInitiationResponse initiatePayment(Payment payment);


    boolean verifyPayment(String transactionRef);
    PaymentMethod getSupportedMethod();
}