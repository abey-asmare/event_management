package com.management.event_management.domain.exceptions.payment;

public class InvalidPaymentStateException extends RuntimeException {
    public InvalidPaymentStateException(String message) {
        super(message);
    }
}