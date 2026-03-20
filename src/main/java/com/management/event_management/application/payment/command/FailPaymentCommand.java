package com.management.event_management.application.payment.command;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class FailPaymentCommand {
    private final UUID paymentId;
}