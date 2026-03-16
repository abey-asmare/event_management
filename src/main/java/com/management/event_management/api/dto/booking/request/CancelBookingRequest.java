package com.management.event_management.api.dto.booking.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CancelBookingRequest {

    @NotNull(message = "Booking ID is required")
    private UUID bookingId;
}