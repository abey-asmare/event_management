package com.management.event_management.api.dto.event.request;

import jakarta.validation.constraints.*;
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

    @NotNull(message = "SeatBased flag must be provided")
    private Boolean seatBased;

    @NotNull(message = "Capacity is required")
    @Min(value = 1, message = "Capacity must be at least 1")
    private Integer capacity;

    @NotNull(message = "Ticket price is required")
    @DecimalMin(value = "0.0", message = "Ticket price cannot be negative")
    private Double ticketPrice;

    @Min(value = 1, message = "Seat rows must be at least 1")
    private Integer seatRows;

    @Min(value = 1, message = "Seats per row must be at least 1")
    private Integer seatsPerRow;
}