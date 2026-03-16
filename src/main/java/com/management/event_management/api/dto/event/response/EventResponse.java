package com.management.event_management.api.dto.response;

import com.management.event_management.domain.enums.EventStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class EventResponse {

    private UUID id;
    private String title;
    private String description;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Double ticketPrice;
    private boolean seatBased;
    private int capacity;
    private EventStatus status;
    private UUID organizerId;
    private UUID venueId;
}