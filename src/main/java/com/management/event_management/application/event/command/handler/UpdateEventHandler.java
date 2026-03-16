package com.management.event_management.application.event.command.handler;

import com.management.event_management.application.event.command.UpdateEventCommand;
import com.management.event_management.domain.entities.event.Event;
import com.management.event_management.domain.exceptions.event.EventNotFoundException;
import com.management.event_management.infrastructure.persistence.repositories.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Service
@RequiredArgsConstructor
public class UpdateEventHandler {

    private final EventRepository eventRepository;

    @Transactional
    public Event handle(UpdateEventCommand command) {

        Event event = eventRepository.findById(command.getEventId())
                .orElseThrow(() -> new EventNotFoundException("Event not found"));

        event.updateDetails(command.getTitle(), command.getDescription());

        event.reschedule(command.getStartTime(), command.getEndTime());

        event.changeTicketPrice(command.getTicketPrice());

        return eventRepository.save(event);
    }
}