package com.management.event_management.api.controllers;

import com.management.event_management.api.dto.event.request.CreateEventRequest;
import com.management.event_management.api.dto.request.UpdateEventRequest;
import com.management.event_management.api.dto.response.EventResponse;
import com.management.event_management.application.event.command.CancelEventCommand;
import com.management.event_management.application.event.command.CreateEventCommand;
import com.management.event_management.application.event.command.UpdateEventCommand;
import com.management.event_management.application.event.command.handler.CancelEventHandler;
import com.management.event_management.application.event.command.handler.CreateEventHandler;
import com.management.event_management.application.event.command.handler.UpdateEventHandler;
import com.management.event_management.application.event.query.GetEvents;
import com.management.event_management.domain.entities.event.Event;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
public class EventController {

    private final CreateEventHandler createHandler;
    private final UpdateEventHandler updateHandler;
    private final CancelEventHandler cancelHandler;
    private final GetEvents getEvents;

    // create
    @PostMapping
    public ResponseEntity<EventResponse> createEvent(@Valid @RequestBody CreateEventRequest request) {
        CreateEventCommand command = new CreateEventCommand(
                request.getOrganizerId(),
                request.getVenueId(),
                request.getTitle(),
                request.getDescription(),
                request.isSeatBased(),
                request.getCapacity(),
                request.getTicketPrice(),
                request.getSeatRows(),
                request.getSeatsPerRow()
        );

        Event event = createHandler.handle(command);
        return new ResponseEntity<>(mapToResponse(event), HttpStatus.CREATED);
    }

    // update
    @PutMapping("/{id}")
    public ResponseEntity<EventResponse> updateEvent(
            @PathVariable("id") Long eventId,
            @Valid @RequestBody UpdateEventRequest request) {

        UpdateEventCommand command = new UpdateEventCommand(
                eventId,
                request.getTitle(),
                request.getDescription(),
                request.getStartTime(),
                request.getEndTime(),
                request.getTicketPrice()
        );

        Event updated = updateHandler.handle(command);
        return ResponseEntity.ok(mapToResponse(updated));
    }

    // cancel
    @PostMapping("/{id}/cancel")
    public ResponseEntity<EventResponse> cancelEvent(@PathVariable("id") Long eventId) {
        CancelEventCommand command = new CancelEventCommand(eventId);
        Event canceled = cancelHandler.handle(command);
        return ResponseEntity.ok(mapToResponse(canceled));
    }

    // getall
    @GetMapping
    public ResponseEntity<List<EventResponse>> getAllEvents() {
        List<Event> events = getEvents.getAllEvents();
        return ResponseEntity.ok(events.stream().map(this::mapToResponse).collect(Collectors.toList()));
    }

    //get
    @GetMapping("/{id}")
    public ResponseEntity<EventResponse> getEventById(@PathVariable("id") Long id) {
        Event event = getEvents.getEventById(id);
        return ResponseEntity.ok(mapToResponse(event));
    }
    @GetMapping("/organizer/{id}")
    public ResponseEntity<List<EventResponse>> getEventsForOrganizer(@PathVariable("id") Long organizerId) {
        List<Event> events = getEvents.getEventsForOrganizer(organizerId);
        return ResponseEntity.ok(events.stream().map(this::mapToResponse).collect(Collectors.toList()));
    }

    @GetMapping("/upcoming")
    public ResponseEntity<List<EventResponse>> getUpcomingEvents() {
        List<Event> events = getEvents.getUpcomingEvents();
        return ResponseEntity.ok(events.stream().map(this::mapToResponse).collect(Collectors.toList()));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<EventResponse>> getEventsByStatus(@PathVariable("status") String status) {
        List<Event> events = getEvents.getEventsByStatus(Enum.valueOf(com.management.event_management.domain.enums.EventStatus.class, status.toUpperCase()));
        return ResponseEntity.ok(events.stream().map(this::mapToResponse).collect(Collectors.toList()));
    }

    private EventResponse mapToResponse(Event event) {
        return new EventResponse(
                event.getId(),
                event.getTitle(),
                event.getDescription(),
                event.getStartTime(),
                event.getEndTime(),
                event.getTicketPrice(),
                event.isSeatBased(),
                event.getCapacity(),
                event.getStatus(),
                event.getOrganizer().getId(),
                event.getVenue().getId()
        );
    }
}