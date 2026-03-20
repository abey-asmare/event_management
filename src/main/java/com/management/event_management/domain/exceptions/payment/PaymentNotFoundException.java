package com.management.event_management.domain.exceptions.payment;

import com.management.event_management.domain.exceptions.DomainException;

public class PaymentNotFoundException extends DomainException {
    public PaymentNotFoundException(String message) {
        super(message);
    }

    public PaymentNotFoundException() {
        super("Event not found");
    }
}