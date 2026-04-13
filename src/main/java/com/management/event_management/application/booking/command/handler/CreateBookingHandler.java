package com.management.event_management.application.booking.command.handler;

import com.management.event_management.application.booking.command.CreateBookingCommand;
import com.management.event_management.application.booking.factory.BookingFactory;
import com.management.event_management.domain.entities.booking.Booking;
import com.management.event_management.domain.entities.event.Event;
import com.management.event_management.domain.entities.event.Seat;
import com.management.event_management.domain.entities.user.User;
import com.management.event_management.domain.exceptions.event.EventNotFoundException;
import com.management.event_management.domain.exceptions.seat.SeatAlreadyReservedException;
import com.management.event_management.domain.exceptions.seat.SeatNotFoundException;
import com.management.event_management.infrastructure.persistence.repositories.EventRepository;
import com.management.event_management.infrastructure.persistence.repositories.booking.BookingRepository;
import com.management.event_management.infrastructure.persistence.repositories.user.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CreateBookingHandler {

    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final BookingRepository bookingRepository;

    @Transactional
    public UUID handle(CreateBookingCommand command) {

        Event event = eventRepository.findById(command.getEventId())
                .orElseThrow(EventNotFoundException::new);

        User user = userRepository.findById(command.getUserId())
                .orElseThrow();

        List<UUID> reservedSeatIds = new ArrayList<>();

        if (event.isSeatBased()) {
            List<Seat> seats = event.getSeats();

            for (UUID seatId : command.getSeatIds()) {

                Seat seat = seats.stream()
                        .filter(s -> s.getId().equals(seatId))
                        .findFirst()
                        .orElseThrow(SeatNotFoundException::new);

                if (seat.isReserved()) {
                    throw new SeatAlreadyReservedException();
                }

                seat.setReserved(true);
                reservedSeatIds.add(seatId);


            }
        }

        Booking booking = BookingFactory.create(event, user);

        booking.setReservedSeatIds(reservedSeatIds);
        bookingRepository.save(booking);

        return booking.getId();
    }
}