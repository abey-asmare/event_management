package com.management.event_management.application.payment.factory;

import com.management.event_management.domain.entities.booking.Booking;
import com.management.event_management.domain.entities.payment.Payment;
import com.management.event_management.domain.valueobjects.Money;
import org.springframework.stereotype.Component;

@Component
public class PaymentFactory {

    public Payment createPayment(Booking booking, Money amount) {
        return Payment.create(booking, amount);
    }
}