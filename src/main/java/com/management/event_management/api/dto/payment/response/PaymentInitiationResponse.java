package com.management.event_management.api.dto.payment.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PaymentInitiationResponse {
    private final String checkoutUrl;
    private final String transactionRef;
}