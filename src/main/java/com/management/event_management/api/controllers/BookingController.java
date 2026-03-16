package com.management.event_management.api.controllers;

import com.management.event_management.api.dto.booking.request.CancelBookingRequest;
import com.management.event_management.api.dto.booking.request.CreateBookingRequest;
import com.management.event_management.api.dto.booking.response.BookingResponse;
import com.management.event_management.api.dto.booking.response.EventSummary;
import com.management.event_management.api.dto.booking.response.UserSummary;
import com.management.event_management.application.booking.command.CancelBookingCommand;
import com.management.event_management.application.booking.command.CreateBookingCommand;
import com.management.event_management.application.booking.command.handler.CancelBookingHandler;
import com.management.event_management.application.booking.command.handler.CreateBookingHandler;
import com.management.event_management.application.booking.query.handler.BookingQueryHandler;
import com.management.event_management.domain.entities.booking.Booking;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final CreateBookingHandler createBookingHandler;
    private final CancelBookingHandler cancelBookingHandler;
    private final BookingQueryHandler bookingQueryHandler;

    @PostMapping
    public UUID createBooking(@Valid @RequestBody CreateBookingRequest request) {
        CreateBookingCommand command = new CreateBookingCommand(
                request.getEventId(),
                request.getUserId(),
                request.getSeatIds(),
                request.getTicketQuantity()
        );
        return createBookingHandler.handle(command);
    }

    @PostMapping("/cancel")
    public void cancelBooking(@Valid @RequestBody CancelBookingRequest request) {
        CancelBookingCommand command = new CancelBookingCommand(request.getBookingId());
        cancelBookingHandler.handle(command);
    }

    @GetMapping
    public ResponseEntity<List<BookingResponse>> getBookings() {
        List<Booking> bookings = bookingQueryHandler.getBookings();
        List<BookingResponse> response = bookings.stream()
                .map(this::mapToBookingResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookingResponse> getBookingById(@PathVariable UUID id) {
        Booking booking = bookingQueryHandler.getBookingById(id);
        if (booking == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Booking not found");
        }
        return ResponseEntity.ok(mapToBookingResponse(booking));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<BookingResponse>> getBookingsForUser(@PathVariable UUID userId) {
        List<Booking> bookings = bookingQueryHandler.getBookingsForUser(userId);
        List<BookingResponse> response = bookings.stream()
                .map(this::mapToBookingResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/event/{eventId}")
    public ResponseEntity<List<BookingResponse>> getBookingsForEvent(@PathVariable UUID eventId) {
        List<Booking> bookings = bookingQueryHandler.getBookingsForEvent(eventId);
        List<BookingResponse> response = bookings.stream()
                .map(this::mapToBookingResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    private BookingResponse mapToBookingResponse(Booking booking) {
        return new BookingResponse(
                booking.getId(),
                booking.getCreatedAt(),
                new EventSummary(booking.getEvent().getId(), booking.getEvent().getTitle()),
                new UserSummary(booking.getUser().getId(), booking.getUser().getName()),
                booking.getStatus()
        );
    }
}