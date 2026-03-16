package com.management.event_management.api.dto.booking.response;

import com.management.event_management.domain.enums.BookingStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class BookingResponse {
    private UUID id;
    private LocalDateTime createdAt;
    private EventSummary event;
    private UserSummary user;
    private BookingStatus status;
}