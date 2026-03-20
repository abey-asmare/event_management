package com.management.event_management.api.dto.payment.response;

import com.management.event_management.domain.entities.payment.Payment;
import com.management.event_management.domain.enums.PaymentStatus;

import java.math.BigDecimal;
import java.util.UUID;

public class PaymentResponse {

    private UUID id;
    private UUID bookingId;
    private BigDecimal amount;
    private PaymentStatus status;
    private String receiptUrl;

    public static PaymentResponse from(Payment payment) {
        PaymentResponse response = new PaymentResponse();
        response.id = payment.getId();
        response.bookingId = payment.getBooking().getId();
        response.amount = payment.getAmount().getAmount();
        response.status = payment.getStatus();
        response.receiptUrl = payment.getReceiptUrl();
        return response;
    }

    // getters
    public UUID getId() { return id; }
    public UUID getBookingId() { return bookingId; }
    public BigDecimal getAmount() { return amount; }
    public PaymentStatus getStatus() { return status; }
    public String getReceiptUrl() { return receiptUrl; }
}