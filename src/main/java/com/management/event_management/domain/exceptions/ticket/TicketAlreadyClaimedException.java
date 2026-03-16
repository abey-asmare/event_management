package com.management.event_management.domain.exceptions.ticket;

import com.management.event_management.domain.exceptions.DomainException;

public class TicketAlreadyClaimedException extends DomainException {

    public TicketAlreadyClaimedException() {
        super("Ticket has already been claimed");
    }

}