package com.management.event_management.api.dto.booking.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.Mapping;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class CreateBookingRequest {


    @NotNull(message = "Event ID is required")
    private UUID eventId;

    @NotNull(message = "User ID is required")
    private UUID userId;

    private List<UUID> seatIds;

    @Min(value = 1, message = "Ticket quantity must be at least 1")
    private Integer ticketQuantity;
}