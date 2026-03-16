package com.management.event_management.api.dto.booking.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class UserSummary {
    private UUID id;
    private String name;
}