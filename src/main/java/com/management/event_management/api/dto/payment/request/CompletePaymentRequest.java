package com.management.event_management.api.dto.payment.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class CompletePaymentRequest {

    @NotBlank(message = "Receipt URL is required")
    private String receiptUrl;

}