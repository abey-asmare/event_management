package com.management.event_management.api.dto.booking.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class EventSummary {
    private UUID id;
    private String title;
}
