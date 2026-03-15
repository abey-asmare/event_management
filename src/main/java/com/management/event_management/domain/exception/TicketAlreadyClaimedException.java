package com.management.event_management.domain.exception;

public class TicketAlreadyClaimedException extends DomainException {

    public TicketAlreadyClaimedException() {
        super("Ticket has already been claimed");
    }

}