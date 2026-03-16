package com.management.event_management.api.dto.event.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CreateEventRequest {

    @NotNull(message = "Organizer ID is required")
    private UUID organizerId;

    @NotNull(message = "Venue ID is required")
    private UUID venueId;

    @NotBlank(message = "Title is required")
    private String title;

    private String description;

    private boolean seatBased;

    @Min(value = 1, message = "Capacity must be at least 1")
    private int capacity;

    @NotNull(message = "Ticket price is required")
    @Min(value = 0, message = "Ticket price cannot be negative")
    private Double ticketPrice;

    @Min(value = 1, message = "Seat rows must be at least 1")
    private int seatRows;

    @Min(value = 1, message = "Seats per row must be at least 1")
    private int seatsPerRow;
}