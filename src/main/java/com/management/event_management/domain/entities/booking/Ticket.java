package com.management.event_management.domain.entities.booking;

import com.management.event_management.domain.entities.BaseEntity;
import com.management.event_management.domain.entities.event.Event;
import com.management.event_management.domain.entities.event.Seat;
import com.management.event_management.domain.entities.user.User;
import com.management.event_management.domain.enums.TicketStatus;
import jakarta.persistence.*;

import java.util.UUID;
@Entity
@Table(name = "tickets")
public class Ticket extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "booking_id")
    private Booking booking;

    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;

    @OneToOne
    @JoinColumn(name = "seat_id")
    private Seat seat;

    @ManyToOne
    @JoinColumn(name = "holder_user_id")
    private User holderUser;

    private String holderName;

    private String claimToken;

    @Enumerated(EnumType.STRING)
    private TicketStatus status;
}