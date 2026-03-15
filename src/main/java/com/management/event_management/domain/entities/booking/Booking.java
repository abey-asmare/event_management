package com.management.event_management.domain.entities.booking;

import com.management.event_management.domain.entities.BaseEntity;
import com.management.event_management.domain.entities.event.Event;
import com.management.event_management.domain.enums.BookingStatus;
import jakarta.persistence.*;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "bookings")
public class Booking extends BaseEntity {

    @Column(name = "user_id", columnDefinition = "uuid")
    private UUID userId;

    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;

    @Enumerated(EnumType.STRING)
    private BookingStatus status;

    @OneToMany(mappedBy = "booking", cascade = CascadeType.PERSIST)
    private List<Ticket> tickets;

    // Getters and setters
}