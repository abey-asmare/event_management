package com.management.event_management.api.dto.payment.response;

import com.management.event_management.domain.entities.payment.Payment;
import com.management.event_management.domain.enums.PaymentMethod;
import com.management.event_management.domain.enums.PaymentStatus;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
public class PaymentResponse {

    private UUID id;
    private UUID bookingId;
    private BigDecimal amount;
    private PaymentStatus status;
    private String receiptUrl;
    private String transactionRef;
    private PaymentMethod paymentMethod;

    public static PaymentResponse from(Payment payment) {
        PaymentResponse response = new PaymentResponse();
        response.id = payment.getId();
        response.bookingId = payment.getBooking().getId();
        response.amount = payment.getAmount().getAmount();
        response.status = payment.getStatus();
        response.receiptUrl = payment.getReceiptUrl();
        response.transactionRef = payment.getTransactionRef();
        response.paymentMethod = payment.getPaymentMethod();
        return response;
    }
}