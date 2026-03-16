package com.management.event_management.domain.entities.booking;

import com.management.event_management.domain.entities.BaseEntity;
import com.management.event_management.domain.entities.event.Event;
import com.management.event_management.domain.entities.user.User;
import com.management.event_management.domain.enums.BookingStatus;
import jakarta.persistence.*;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "bookings")
public class Booking extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;

    @OneToMany(mappedBy = "booking", cascade = CascadeType.ALL)
    private List<Ticket> tickets;

    @Enumerated(EnumType.STRING)
    private BookingStatus status;
}