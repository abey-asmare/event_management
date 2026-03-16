package com.management.event_management.infrastructure.persistence.repositories;

import com.management.event_management.domain.entities.event.Event;
import com.management.event_management.domain.enums.EventStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface EventRepository extends JpaRepository<Event, UUID> {

    List<Event> findByOrganizerId(UUID organizerId);

    List<Event> findByStartTimeAfter(LocalDateTime time);

    List<Event> findByStatus(EventStatus status);
}