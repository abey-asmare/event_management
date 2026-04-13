package com.management.event_management.infrastructure.services.payment;

import com.management.event_management.api.dto.payment.response.PaymentInitiationResponse;
import com.management.event_management.domain.entities.payment.Payment;
import com.management.event_management.domain.enums.PaymentMethod;
import com.management.event_management.domain.exceptions.payment.PaymentInitializationException;
import com.management.event_management.domain.exceptions.payment.PaymentVerificationException;
import com.management.event_management.domain.services.payment.PaymentGateway;
import com.yaphet.chapa.Chapa;
import com.yaphet.chapa.model.Customization;
import com.yaphet.chapa.model.InitializeResponseData;
import com.yaphet.chapa.model.PostData;
import com.yaphet.chapa.model.VerifyResponseData;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Primary
public class ChapaPaymentGateway implements PaymentGateway {

    private final String secretKey;
    private final String BASE_URL;

    public ChapaPaymentGateway(@Value("${chapa_secret_key}") String secretKey, @Value("${BASE_URL}") String BASE_URL) {
        this.secretKey = secretKey;
        this.BASE_URL = BASE_URL;
    }

    @Override
    public PaymentInitiationResponse initiatePayment(Payment payment) {
        try {
            Chapa chapa = new Chapa(secretKey);

            String txRef = "tx-" + UUID.randomUUID();


            Customization customization = new Customization()
                    .setTitle("Event Management")
                    .setDescription("Payment for booking")
                    .setLogo("https://mylogo.com/log.png");

            PostData postData = new PostData()
                    .setAmount(payment.getAmount().getAmount())
                            .setCurrency("ETB")
                            .setEmail("customer@email.com")
                            .setFirstName("First")
                            .setLastName("Last")
                            .setTxRef(txRef)
                            .setCallbackUrl(this.BASE_URL + "/payments/webhook/chapa")
                            .setReturnUrl(this.BASE_URL + "/payments/webhook/chapa/success?tx_ref="+txRef)
                    .setCustomization(customization);

            InitializeResponseData response = chapa.initialize(postData);

            if (response == null || response.getData() == null) {
                throw new PaymentInitializationException("Invalid response from Chapa");
            }

            String checkoutUrl = response.getData().getCheckOutUrl();
            return new PaymentInitiationResponse(checkoutUrl, txRef);

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

    @Override
    public PaymentMethod getSupportedMethod() {
        return PaymentMethod.CHAPA;
    }
}