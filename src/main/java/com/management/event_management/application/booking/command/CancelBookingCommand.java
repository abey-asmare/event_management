package com.management.event_management.application.booking.command;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class CancelBookingCommand {

    private UUID bookingId;
}