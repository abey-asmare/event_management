package com.management.event_management.application.booking.query.handler;

import com.management.event_management.application.booking.query.BookingQuery;
import com.management.event_management.domain.entities.booking.Booking;
import com.management.event_management.domain.exceptions.booking.BookingNotFoundException;

import com.management.event_management.infrastructure.persistence.repositories.booking.BookingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BookingQueryHandler implements BookingQuery {

    private final BookingRepository bookingRepository;

    @Override
    public List<Booking> getBookings() {
        return bookingRepository.findAll();
    }

    @Override
    public Booking getBookingById(UUID bookingId) {
        return bookingRepository.findById(bookingId)
                .orElseThrow(BookingNotFoundException::new);
    }

    @Override
    public List<Booking> getBookingsForUser(UUID userId) {
        return bookingRepository.findByUserId(userId);
    }

    @Override
    public List<Booking> getBookingsForEvent(UUID eventId) {
        return bookingRepository.findByEventId(eventId);
    }
}