package com.management.event_management.application.booking.factory;

import com.management.event_management.domain.entities.booking.Booking;
import com.management.event_management.domain.entities.event.Event;
import com.management.event_management.domain.entities.user.User;

import java.util.UUID;

public class BookingFactory {

    public static Booking create(Event event, User user) {

        Booking booking = new Booking();
        booking.setId(null);
        booking.setEvent(event);
        booking.setUser(user);

        return booking;
    }
}