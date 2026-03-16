package com.management.event_management.application.event.query;

import com.management.event_management.domain.entities.event.Event;
import com.management.event_management.domain.enums.EventStatus;
import com.management.event_management.domain.exceptions.event.EventNotFoundException;
import com.management.event_management.infrastructure.persistence.repositories.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GetEvents {

    private final EventRepository eventRepository;

    @Transactional(readOnly = true)
    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Event getEventById(Long eventId) {
        return eventRepository.findById(eventId)
                .orElseThrow(() -> new EventNotFoundException("Event with ID " + eventId + " not found"));
    }

    @Transactional(readOnly = true)
    public List<Event> getEventsForOrganizer(Long organizerId) {
        List<Event> events = eventRepository.findByOrganizerId(organizerId);
        if (events.isEmpty()) {
            throw new EventNotFoundException("No events found for organizer with ID " + organizerId);
        }
        return events;
    }

    @Transactional(readOnly = true)
    public List<Event> getUpcomingEvents() {
        LocalDateTime now = LocalDateTime.now();
        return eventRepository.findByStartTimeAfter(now);
    }

    @Transactional(readOnly = true)
    public List<Event> getEventsByStatus(EventStatus status) {
        List<Event> events = eventRepository.findByStatus(status);
        if (events.isEmpty()) {
            throw new EventNotFoundException("No events found with status " + status);
        }
        return events;
    }
}