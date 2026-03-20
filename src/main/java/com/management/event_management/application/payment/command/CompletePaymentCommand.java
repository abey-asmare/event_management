package com.management.event_management.application.payment.command;

import java.util.UUID;

public class CompletePaymentCommand {
    private final UUID paymentId;
    private final String receiptUrl;

    public CompletePaymentCommand(UUID paymentId, String receiptUrl) {
        this.paymentId = paymentId;
        this.receiptUrl = receiptUrl;
    }

    public UUID getPaymentId() { return paymentId; }
    public String getReceiptUrl() { return receiptUrl; }
}