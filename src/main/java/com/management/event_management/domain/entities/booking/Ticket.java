package com.management.event_management.domain.entities.booking;

import com.management.event_management.domain.entities.BaseEntity;
import com.management.event_management.domain.enums.TicketStatus;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "tickets")
public class Ticket extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "booking_id")
    private Booking booking;

    @Column(name = "seat_id", columnDefinition = "uuid")
    private UUID seatId;

    private String holderName;
    private String claimToken;

    @Enumerated(EnumType.STRING)
    private TicketStatus status;

    // Getters and setters
}