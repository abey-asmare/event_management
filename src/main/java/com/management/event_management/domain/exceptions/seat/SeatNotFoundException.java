package com.management.event_management.domain.exceptions.seat;

import com.management.event_management.domain.exceptions.DomainException;

public class SeatNotFoundException extends DomainException {
    public SeatNotFoundException() {
        super("Seat not found");
    }
}