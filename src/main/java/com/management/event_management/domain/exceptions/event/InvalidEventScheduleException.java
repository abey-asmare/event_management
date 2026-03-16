package com.management.event_management.domain.exceptions.event;

import com.management.event_management.domain.exceptions.DomainException;

public class InvalidEventScheduleException extends DomainException {
    public InvalidEventScheduleException() {
        super("Event start time must be before end time");
    }
}