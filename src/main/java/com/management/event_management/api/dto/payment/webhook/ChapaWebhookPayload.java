package com.management.event_management.api.dto.payment.webhook;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ChapaWebhookPayload {
    @JsonProperty("tx_ref")
    private String transactionRef;

    private String status;

    @JsonProperty("receipt_url")
    private String receiptUrl;

}