package com.management.event_management.application.event.factory;

import com.management.event_management.domain.entities.event.Event;
import com.management.event_management.domain.entities.event.Seat;
import com.management.event_management.domain.entities.organizer.Organizer;
import com.management.event_management.domain.entities.venue.Venue;
import com.management.event_management.domain.enums.EventStatus;

import java.util.ArrayList;
import java.util.List;

public class EventFactory {

    public static Event createEvent(
            Organizer organizer,
            Venue venue,
            String title,
            String description,
            boolean seatBased,
            int capacity,
            Double ticketPrice,
            int seatRows,
            int seatsPerRow
    ) {
        Event event = new Event();

        event.setOrganizer(organizer);
        event.setVenue(venue);
        event.setTitle(title);
        event.setDescription(description);
        event.setSeatBased(seatBased);
        event.setCapacity(capacity);
        event.setTicketPrice(ticketPrice);
        event.setStatus(EventStatus.DRAFT);

        if (seatBased) {
            event.generateSeats(seatRows, seatsPerRow);
        }

        return event;
    }
}