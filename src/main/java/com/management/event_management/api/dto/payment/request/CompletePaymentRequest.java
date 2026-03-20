package com.management.event_management.api.dto.payment.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class CompletePaymentRequest {

    @NotBlank(message = "Transaction reference is required")
    private String transactionRef;
}