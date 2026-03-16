package com.management.event_management.domain.exceptions.booking;

import com.management.event_management.domain.exceptions.DomainException;

public class BookingNotFoundException extends DomainException {
    public BookingNotFoundException() {
        super("Booking not found");
    }
}