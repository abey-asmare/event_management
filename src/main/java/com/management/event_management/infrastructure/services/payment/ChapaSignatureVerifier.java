package com.management.event_management.infrastructure.services.payment;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Component
@Slf4j
public class ChapaSignatureVerifier {

    @Value("${chapa_secret_key}")
    private String webhookSecret;

    public boolean isValidSignature(HttpServletRequest request, String payload) {
        String signatureHeader = request.getHeader("Chapa-Signature");
        if (signatureHeader == null || signatureHeader.isBlank()) {
            log.warn("Missing Chapa-Signature header");
            return false;
        }

        try {
            Mac sha256Hmac = Mac.getInstance("HmacSHA256");
            SecretKeySpec secretKey = new SecretKeySpec(
                    webhookSecret.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
            sha256Hmac.init(secretKey);

            byte[] signedBytes = sha256Hmac.doFinal(payload.getBytes(StandardCharsets.UTF_8));
            String computedSignature = Base64.getEncoder().encodeToString(signedBytes);

            return computedSignature.equals(signatureHeader);
        } catch (Exception e) {
            log.error("Error verifying webhook signature", e);
            return false;
        }
    }
}