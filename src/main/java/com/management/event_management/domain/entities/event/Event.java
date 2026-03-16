package com.management.event_management.domain.entities.event;

import com.management.event_management.domain.entities.BaseEntity;
import com.management.event_management.domain.entities.booking.Booking;
import com.management.event_management.domain.entities.booking.Ticket;
import com.management.event_management.domain.entities.user.User;
import com.management.event_management.domain.entities.venue.Venue;
import com.management.event_management.domain.enums.EventStatus;
import com.management.event_management.domain.exceptions.event.InvalidEventScheduleException;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
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

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Seat> seats = new ArrayList<>();

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL)
    private List<Booking> bookings = new ArrayList<>();

    @OneToMany(mappedBy = "event")
    private List<Ticket> tickets = new ArrayList<>();

    // =========================
    // DOMAIN METHODS
    // =========================

    public void updateDetails(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public void reschedule(LocalDateTime start, LocalDateTime end) {
        if (start.isAfter(end)) {
            throw new InvalidEventScheduleException();
        }
        this.startTime = start;
        this.endTime = end;
    }

    public void changeTicketPrice(Double price) {
        this.ticketPrice = price;
    }

    public void cancel() {
        if (this.status == EventStatus.CANCELLED) {
            return;
        }
        this.status = EventStatus.CANCELLED;
    }

    public void generateSeats(int rows, int seatsPerRow) {
        if (!seatBased) return;

        for (int r = 0; r < rows; r++) {
            String rowLabel = String.valueOf((char) ('A' + r));
            for (int i = 1; i <= seatsPerRow; i++) {
                Seat seat = new Seat();
                seat.setEvent(this);
                seat.setRowLabel(rowLabel);
                seat.setSeatNumber(i);
                seat.setReserved(false);
                this.seats.add(seat);
            }
        }
    }
}