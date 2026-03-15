package com.management.event_management.domain.exception;

public class SeatAlreadyReservedException extends DomainException {

    public SeatAlreadyReservedException() {
        super("Seat is already reserved");
    }

}