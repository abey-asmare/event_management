package com.management.event_management.domain.entities.event;

import com.management.event_management.domain.entities.BaseEntity;
import com.management.event_management.domain.entities.booking.Booking;
import com.management.event_management.domain.entities.booking.Ticket;
import com.management.event_management.domain.entities.user.User;
import com.management.event_management.domain.entities.venue.Venue;
import com.management.event_management.domain.enums.EventStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "events")
@Getter
@Setter
public class Event extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "organizer_id")
    private User organizer;

    @ManyToOne
    @JoinColumn(name = "venue_id")
    private Venue venue;

    private String title;

    @Column(columnDefinition = "text")
    private String description;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private Integer capacity;

    private boolean seatBased;

    private Double ticketPrice;

    @Enumerated(EnumType.STRING)
    private EventStatus status;


    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Seat> seats;

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL)
    private List<Booking> bookings;

    @OneToMany(mappedBy = "event")
    private List<Ticket> tickets;
}