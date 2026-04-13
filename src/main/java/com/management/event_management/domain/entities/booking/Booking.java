package com.management.event_management.domain.entities.booking;

import com.management.event_management.domain.entities.BaseEntity;
import com.management.event_management.domain.entities.event.Event;
import com.management.event_management.domain.entities.user.User;
import com.management.event_management.domain.enums.BookingStatus;
import com.management.event_management.domain.valueobjects.Money;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "bookings")
@Getter
@Setter
public class Booking extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


    @ElementCollection
    @CollectionTable(name = "booking_reserved_seats", joinColumns = @JoinColumn(name = "booking_id"))
    @Column(name = "seat_id")
    private List<UUID> reservedSeatIds = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;

    @Column(nullable = false )
    private int quantity = 1;

    @OneToMany(mappedBy = "booking", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Ticket> tickets;

    @Enumerated(EnumType.STRING)
    private BookingStatus status;

    public void setQuantity(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero");
        }
        this.quantity = quantity;
    }


    public Money calculateTotalAmount() {
        if (event == null) {
            throw new IllegalStateException("Booking must have an associated event");
        }

        if (quantity <= 0) {
            throw new IllegalStateException("Booking quantity must be greater than zero");
        }

        Double ticketPrice = event.getTicketPrice();
        if (ticketPrice == null || ticketPrice <= 0) {
            throw new IllegalStateException("Event ticket price is not set or invalid");
        }

        BigDecimal total = BigDecimal.valueOf(ticketPrice)
                .multiply(BigDecimal.valueOf(quantity));

        return new Money(total);
    }}