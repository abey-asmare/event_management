package com.management.event_management.application.event.command.handler;

import com.management.event_management.application.event.command.CreateEventCommand;
import com.management.event_management.application.event.factory.EventFactory;
import com.management.event_management.domain.entities.event.Event;
import com.management.event_management.domain.entities.user.User;
import com.management.event_management.domain.entities.venue.Venue;
import com.management.event_management.domain.exceptions.event.EventNotFoundException;
import com.management.event_management.infrastructure.persistence.repositories.EventRepository;
import com.management.event_management.infrastructure.persistence.repositories.user.UserRepository;
import com.management.event_management.infrastructure.persistence.repositories.venue.VenueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CreateEventHandler {

    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final VenueRepository venueRepository;

    @Transactional
    public Event handle(CreateEventCommand command) {

        User organizer = userRepository.findById(command.getOrganizerId())
                .orElseThrow(() -> new EventNotFoundException("Organizer not found"));

        Venue venue = venueRepository.findById(command.getVenueId())
                .orElseThrow(() -> new EventNotFoundException("Venue not found"));

        Event event = EventFactory.createEvent(
                organizer,
                venue,
                command.getTitle(),
                command.getDescription(),
                command.isSeatBased(),
                command.getCapacity(),
                command.getTicketPrice(),
                command.getSeatRows(),
                command.getSeatsPerRow()
        );

        return eventRepository.save(event);
    }
}