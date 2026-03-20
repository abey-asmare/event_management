package com.management.event_management.domain.entities.payment;

import com.management.event_management.domain.entities.BaseEntity;
import com.management.event_management.domain.entities.booking.Booking;
import com.management.event_management.domain.enums.PaymentStatus;
import com.management.event_management.domain.exceptions.payment.InvalidPaymentStateException;
import com.management.event_management.domain.exceptions.payment.PaymentAlreadyCompletedException;
import com.management.event_management.domain.valueobjects.Money;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "payments")
@Getter
@Setter
@NoArgsConstructor
public class Payment extends BaseEntity {

    @OneToOne
    @JoinColumn(name = "booking_id", nullable = false)
    private Booking booking;

    @Embedded
    private Money amount;

    private String receiptUrl;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    private Payment(Booking booking, Money amount) {
        if (booking == null) {
            throw new IllegalArgumentException("Booking cannot be null");
        }
        if (amount == null || amount.isZero()) {
            throw new IllegalArgumentException("Payment amount must be greater than zero");
        }
        this.booking = booking;
        this.amount = amount;
        this.status = PaymentStatus.INITIATED;
    }

    public static Payment create(Booking booking, Money amount) {
        return new Payment(booking, amount);
    }


    public void markAsPending() {
        if (this.status != PaymentStatus.INITIATED) {
            throw new InvalidPaymentStateException(
                    "Payment must be INITIATED to move to PENDING"
            );
        }

        this.status = PaymentStatus.PENDING;
    }

    public void complete(String receiptUrl) {
        if (this.status == PaymentStatus.COMPLETED) {
            throw new PaymentAlreadyCompletedException();
        }

        if (this.status != PaymentStatus.PENDING) {
            throw new InvalidPaymentStateException(
                    "Payment must be PENDING to complete"
            );
        }

        if (receiptUrl == null || receiptUrl.isBlank()) {
            throw new IllegalArgumentException("Receipt URL is required");
        }

        this.status = PaymentStatus.COMPLETED;
        this.receiptUrl = receiptUrl;
    }

    public void fail() {
        if (this.status == PaymentStatus.COMPLETED) {
            throw new PaymentAlreadyCompletedException();
        }

        this.status = PaymentStatus.FAILED;
    }
}