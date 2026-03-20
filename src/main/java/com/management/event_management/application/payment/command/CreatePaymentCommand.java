package com.management.event_management.application.payment.command;

import com.management.event_management.domain.valueobjects.Money;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class CreatePaymentCommand {
    private final UUID bookingId;
    private final Money amount;

}