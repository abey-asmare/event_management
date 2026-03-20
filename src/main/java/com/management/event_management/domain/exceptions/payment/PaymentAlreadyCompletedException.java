package com.management.event_management.domain.exceptions.payment;

public class PaymentAlreadyCompletedException extends RuntimeException {
    public PaymentAlreadyCompletedException() {
        super("Payment is already completed");
    }
}