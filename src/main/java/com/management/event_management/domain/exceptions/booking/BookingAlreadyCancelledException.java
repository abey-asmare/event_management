package com.management.event_management.domain.exceptions.booking;

import com.management.event_management.domain.exceptions.DomainException;

public class BookingAlreadyCancelledException extends DomainException {

    public BookingAlreadyCancelledException() {
        super("Booking is already cancelled");
    }
}