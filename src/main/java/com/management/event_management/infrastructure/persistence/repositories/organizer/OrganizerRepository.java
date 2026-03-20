package com.management.event_management.infrastructure.persistence.repositories.organizer;

import com.management.event_management.domain.entities.organizer.Organizer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrganizerRepository extends JpaRepository<Organizer, UUID> {
}