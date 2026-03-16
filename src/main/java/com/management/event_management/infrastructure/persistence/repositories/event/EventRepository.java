package com.management.event_management.infrastructure.persistence.repositories.event;

import com.management.event_management.domain.entities.event.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {

    List<Event> findByOrganizerId(Long organizerId);

}