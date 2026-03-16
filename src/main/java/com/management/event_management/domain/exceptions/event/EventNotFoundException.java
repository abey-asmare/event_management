package com.management.event_management.domain.exceptions.event;

import com.management.event_management.domain.exceptions.DomainException;

public class EventNotFoundException extends DomainException {
    public EventNotFoundException(String message) {
        super(message);
    }

    public EventNotFoundException() {
        super("Event not found");
    }
}