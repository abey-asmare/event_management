package com.management.event_management.infrastructure.persistence.repositories.venue;

import com.management.event_management.domain.entities.venue.Venue;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface VenueRepository extends JpaRepository<Venue, UUID> {
}