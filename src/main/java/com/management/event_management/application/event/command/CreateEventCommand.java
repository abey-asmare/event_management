package com.management.event_management.application.event.command;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateEventCommand {

    private Long organizerId;
    private Long venueId;
    private String title;
    private String description;
    private boolean seatBased;
    private int capacity;
    private Double ticketPrice;
    private int seatRows;
    private int seatsPerRow;
}