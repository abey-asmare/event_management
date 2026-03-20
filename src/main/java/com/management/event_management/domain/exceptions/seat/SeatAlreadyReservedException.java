package com.management.event_management.domain.exceptions.seat;

import com.management.event_management.domain.exceptions.DomainException;

public class SeatAlreadyReservedException extends DomainException {

    public SeatAlreadyReservedException() {
        super("Seat is already reserved");
    }

}