package com.management.event_management.domain.exceptions.payment;

public class PaymentInitializationException extends PaymentGatewayException {

    public PaymentInitializationException(String message) {
        super(message);
    }

    public PaymentInitializationException(String message, Throwable cause) {
        super(message, cause);
    }
}