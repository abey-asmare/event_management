package com.management.event_management.infrastructure.services.payment;

import com.management.event_management.domain.entities.payment.Payment;
import com.management.event_management.domain.exceptions.payment.PaymentInitializationException;
import com.management.event_management.domain.exceptions.payment.PaymentVerificationException;
import com.management.event_management.domain.services.payment.PaymentGateway;
import com.yaphet.chapa.Chapa;
import com.yaphet.chapa.model.Customization;
import com.yaphet.chapa.model.InitializeResponseData;
import com.yaphet.chapa.model.PostData;
import com.yaphet.chapa.model.VerifyResponseData;
import com.yaphet.chapa.utility.Util;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ChapaPaymentGateway implements PaymentGateway {

    private final String secretKey = "YOUR_SECRET_KEY";


    @Override
    public String initiatePayment(Payment payment) {
        try {
            Chapa chapa = new Chapa(secretKey);

            String txRef = "tx-" + UUID.randomUUID();

            Customization customization = new Customization()
                    .setTitle("E-commerce")
                    .setDescription("Time to pay")
                    .setLogo("https://mylogo.com/log.png");

            PostData postData = new PostData()
                    .setAmount(payment.getAmount().getAmount())
                    .setCurrency("ETB")
                    .setEmail("customer@email.com")
                    .setFirstName("First")
                    .setLastName("Last")
                    .setTxRef(txRef)
                    .setCallbackUrl("http://localhost:8080/api/payments/webhook")
                    .setReturnUrl("http://localhost:3000/payment-success")
                    .setCustomization(customization);

            InitializeResponseData response = chapa.initialize(postData);

            if (response == null || response.getData() == null) {
                throw new PaymentInitializationException("Invalid response from Chapa");
            }

            return response.getData().getCheckOutUrl();

        } catch (Throwable e) {
            throw new PaymentInitializationException("Failed to initialize Chapa payment", e);
        }
    }

    @Override
    public boolean verifyPayment(String transactionRef) {
        try {
            Chapa chapa = new Chapa(secretKey);

            VerifyResponseData response = chapa.verify(transactionRef);

            if (response == null) {
                throw new PaymentVerificationException("Empty response from Chapa");
            }

            Object status = response.getStatus();

            return status != null && status.toString().equalsIgnoreCase("success");

        } catch (Throwable e) {
            throw new PaymentVerificationException("Failed to verify Chapa payment", e);
        }
    }
}