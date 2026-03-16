package com.management.event_management.domain.exceptions.booking;

import com.management.event_management.domain.exceptions.DomainException;

public class EventNotAvailableForBookingException extends DomainException {

    public EventNotAvailableForBookingException() {
        super("Event is not available for booking");
    }
}