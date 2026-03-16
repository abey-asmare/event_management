package com.management.event_management.infrastructure.persistence.repositories.venue;

import com.management.event_management.domain.entities.venue.Venue;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VenueRepository extends JpaRepository<Venue, Long> {
}