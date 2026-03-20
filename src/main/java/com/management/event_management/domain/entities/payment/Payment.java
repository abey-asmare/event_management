package com.management.event_management.domain.entities.payment;

import com.management.event_management.domain.entities.BaseEntity;
import com.management.event_management.domain.entities.booking.Booking;
import com.management.event_management.domain.enums.PaymentMethod;
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

    private String transactionRef;

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    // private constructor – amount is derived from booking
    private Payment(Booking booking) {
        if (booking == null) {
            throw new IllegalArgumentException("Booking cannot be null");
        }
        this.booking = booking;
        this.amount = booking.calculateTotalAmount();
        System.out.println("total amount " + this.amount.getAmount() );
        if (this.amount == null || this.amount.isZero()) {
            throw new IllegalArgumentException("Payment amount must be greater than zero");
        }
        this.status = PaymentStatus.INITIATED;
    }

    public static Payment create(Booking booking) {
        return new Payment(booking);
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

    public void setTransactionRef(String transactionRef) {
        this.transactionRef = transactionRef;
    }
}