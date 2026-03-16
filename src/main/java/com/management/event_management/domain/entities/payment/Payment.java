package com.management.event_management.domain.entities.payment;

import com.management.event_management.domain.entities.BaseEntity;
import com.management.event_management.domain.entities.booking.Booking;
import com.management.event_management.domain.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "payments")
@Getter
@Setter
public class Payment extends BaseEntity {

    @OneToOne
    @JoinColumn(name = "booking_id")
    private Booking booking;

    private double amount;

    private String receiptUrl;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status;
}