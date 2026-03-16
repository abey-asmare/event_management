package com.management.event_management.domain.exceptions.ticket;

import com.management.event_management.domain.exceptions.DomainException;

public class TicketNotFoundException extends DomainException {
    public TicketNotFoundException() {
        super("Ticket not found");
    }
}