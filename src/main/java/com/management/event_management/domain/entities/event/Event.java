package com.management.event_management.domain.entities.event;

import com.management.event_management.domain.entities.BaseEntity;
import com.management.event_management.domain.entities.venue.Venue;
import com.management.event_management.domain.enums.EventStatus;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "events")
public class Event extends BaseEntity {

    private String title;
    private String description;
    private boolean seatBased;

    @Enumerated(EnumType.STRING)
    private EventStatus status;

    private LocalDateTime startTime;
    private LocalDateTime endTime;

    @ManyToOne
    @JoinColumn(name = "venue_id")
    private Venue venue;

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL)
    private List<Seat> seats;

}