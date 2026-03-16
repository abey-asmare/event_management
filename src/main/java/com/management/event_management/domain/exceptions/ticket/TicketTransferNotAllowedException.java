package com.management.event_management.domain.exceptions.ticket;

import com.management.event_management.domain.exceptions.DomainException;

public class TicketTransferNotAllowedException extends DomainException {
    public TicketTransferNotAllowedException() {
        super("Ticket transfer is not allowed");
    }
}