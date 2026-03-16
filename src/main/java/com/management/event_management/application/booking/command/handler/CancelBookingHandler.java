package com.management.event_management.application.booking.command.handler;

import com.management.event_management.application.booking.command.CancelBookingCommand;
import com.management.event_management.domain.entities.booking.Booking;
import com.management.event_management.domain.enums.BookingStatus;
import com.management.event_management.domain.exceptions.booking.BookingAlreadyCancelledException;
import com.management.event_management.domain.exceptions.booking.BookingNotFoundException;

import com.management.event_management.infrastructure.persistence.repositories.booking.BookingRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CancelBookingHandler {

    private final BookingRepository bookingRepository;

    @Transactional
    public void handle(CancelBookingCommand command) {

        Booking booking = bookingRepository.findById(command.getBookingId())
                .orElseThrow(BookingNotFoundException::new);

        if (booking.getStatus() == BookingStatus.CANCELLED) {
            throw new BookingAlreadyCancelledException();
        }

        booking.setStatus(BookingStatus.CANCELLED);

        bookingRepository.save(booking);
    }
}