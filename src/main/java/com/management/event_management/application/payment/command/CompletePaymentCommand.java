package com.management.event_management.application.payment.command;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CompletePaymentCommand {
    private final String transactionRef;
    private final String receiptUrl;
}