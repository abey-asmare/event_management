package com.management.event_management.domain.exceptions.booking;

import com.management.event_management.domain.exceptions.DomainException;

public class NotEnoughSeatsException extends DomainException {
    public NotEnoughSeatsException() {
        super("Not enough seats available for booking");
    }
}