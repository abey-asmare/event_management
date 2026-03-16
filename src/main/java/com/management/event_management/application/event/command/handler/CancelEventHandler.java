package com.management.event_management.application.event.command.handler;

import com.management.event_management.application.event.command.CancelEventCommand;
import com.management.event_management.domain.entities.event.Event;
import com.management.event_management.domain.exceptions.event.EventNotFoundException;
import com.management.event_management.infrastructure.persistence.repositories.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CancelEventHandler {

    private final EventRepository eventRepository;

    @Transactional
    public Event handle(CancelEventCommand command) {

        Event event = eventRepository.findById(command.getEventId())
                .orElseThrow(() -> new EventNotFoundException("Event not found"));

        event.cancel();

        return eventRepository.save(event);
    }
}