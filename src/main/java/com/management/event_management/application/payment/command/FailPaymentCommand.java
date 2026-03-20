package com.management.event_management.application.payment.command;

import java.util.UUID;

public class FailPaymentCommand {
    private final UUID paymentId;

    public FailPaymentCommand(UUID paymentId) {
        this.paymentId = paymentId;
    }

    public UUID getPaymentId() { return paymentId; }
}