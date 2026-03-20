package com.management.event_management.domain.exceptions.organier;

import com.management.event_management.domain.exceptions.DomainException;

public class OrganizerNotFoundException extends DomainException {

    public OrganizerNotFoundException() {
        super("Organizer not found");
    }
}