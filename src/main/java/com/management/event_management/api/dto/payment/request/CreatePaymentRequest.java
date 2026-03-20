package com.management.event_management.api.dto.payment.request;

import com.management.event_management.domain.enums.PaymentMethod;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CreatePaymentRequest {

    @NotNull(message = "Booking ID is required")
    private UUID bookingId;

    @NotNull(message = "Payment method is required")
    private PaymentMethod paymentMethod;
}