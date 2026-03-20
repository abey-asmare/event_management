package com.management.event_management.domain.exceptions.payment;

public class PaymentVerificationException extends PaymentGatewayException {

    public PaymentVerificationException(String message) {
        super(message);
    }

    public PaymentVerificationException(String message, Throwable cause) {
        super(message, cause);
    }
}