package com.management.event_management.infrastructure.persistence.repositories.booking;

import com.management.event_management.domain.entities.booking.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface BookingRepository extends JpaRepository<Booking, UUID> {

    List<Booking> findByUserId(UUID userId);

    List<Booking> findByEventId(UUID eventId);

}