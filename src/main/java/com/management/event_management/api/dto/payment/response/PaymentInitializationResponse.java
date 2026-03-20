package com.management.event_management.api.dto.payment.response;

public class PaymentInitializationResponse {

    private String checkoutUrl;

    public PaymentInitializationResponse(String checkoutUrl) {
        this.checkoutUrl = checkoutUrl;
    }

    public String getCheckoutUrl() {
        return checkoutUrl;
    }
}