package com.management.event_management.application.booking.query;

import java.util.List;
import java.util.UUID;

public interface BookingQuery {

    List<?> getBookings();

    Object getBookingById(UUID bookingId);

    List<?> getBookingsForUser(UUID userId);

    List<?> getBookingsForEvent(UUID eventId);
}