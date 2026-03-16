package com.management.event_management.application.booking.command;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class CreateBookingCommand {

    private UUID eventId;
    private UUID userId;
    private List<UUID> seatIds;
    private Integer ticketQuantity;
}