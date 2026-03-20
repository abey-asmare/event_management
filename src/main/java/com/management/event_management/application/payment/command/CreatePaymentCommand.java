package com.management.event_management.application.payment.command;

import com.management.event_management.domain.enums.PaymentMethod;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class CreatePaymentCommand {
    private final UUID bookingId;
    private final PaymentMethod paymentMethod;
}