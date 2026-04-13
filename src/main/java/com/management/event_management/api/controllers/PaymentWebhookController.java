package com.management.event_management.api.controllers;

import com.management.event_management.api.dto.payment.webhook.ChapaWebhookPayload;
import com.management.event_management.application.payment.command.CompletePaymentCommand;
import com.management.event_management.application.payment.command.FailPaymentCommand;
import com.management.event_management.application.payment.command.handler.CompletePaymentHandler;
import com.management.event_management.application.payment.command.handler.FailPaymentHandler;
import com.management.event_management.domain.entities.payment.Payment;
import com.management.event_management.domain.enums.PaymentStatus;
import com.management.event_management.domain.exceptions.payment.PaymentNotFoundException;
import com.management.event_management.infrastructure.persistence.repositories.payment.PaymentRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/payments/webhook")
@RequiredArgsConstructor
@Slf4j
public class PaymentWebhookController {

    private final PaymentRepository paymentRepository;
    private final CompletePaymentHandler completePaymentHandler;
    private final FailPaymentHandler failPaymentHandler;

    private static final String RECEIPT_URL_TEMPLATE = "https://chapa.link/payment-receipt/%s";

     // Return URL
    @GetMapping("/chapa/success")
    public ResponseEntity<String> success(@RequestParam String tx_ref) {
        Payment payment = paymentRepository.findByTransactionRef(tx_ref)
                .orElseThrow(() -> new IllegalArgumentException("Payment not found"));

        return switch (payment.getStatus()) {
            case COMPLETED -> ResponseEntity.ok("Payment successful! Transaction: " + tx_ref);
            case PENDING, INITIATED -> ResponseEntity.ok("Payment is still processing. Transaction: " + tx_ref);
            case FAILED -> ResponseEntity.ok("Payment failed. Transaction: " + tx_ref);
            case CANCELLED -> ResponseEntity.ok("Payment was cancelled. Transaction: " + tx_ref);
            default -> ResponseEntity.ok("Unknown payment status. Transaction: " + tx_ref);
        };
    }

        //  Webhook endpoint
    @RequestMapping(value = "/chapa", method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseEntity<Void> handleChapaWebhook(
            HttpServletRequest request,
            @RequestBody(required = false) ChapaWebhookPayload payload,
            @RequestParam(required = false) String trx_ref,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String ref_id) {

        String txRef = null;
        String paymentStatus = null;
        String receiptUrl = null;

        // Handle POST with JSON body
        if ("POST".equalsIgnoreCase(request.getMethod()) && payload != null) {
            txRef = payload.getTransactionRef();
            paymentStatus = payload.getStatus();
            receiptUrl = payload.getReceiptUrl();
            log.info("POST webhook received: txRef={}, status={}", txRef, paymentStatus);
        }
        // Handle GET with query parameters
        else if ("GET".equalsIgnoreCase(request.getMethod())) {
            txRef = trx_ref;
            paymentStatus = status;
            if (ref_id != null && !ref_id.isBlank()) {
                receiptUrl = String.format(RECEIPT_URL_TEMPLATE, ref_id);
            }
            log.info("GET webhook received: txRef={}, status={}, ref_id={}", txRef, paymentStatus, ref_id);
        }

        // Validate transaction reference
        if (txRef == null || txRef.isBlank()) {
            log.error("Webhook missing transaction reference");
            return ResponseEntity.badRequest().build();
        }

        // Find payment
        Payment payment = paymentRepository.findByTransactionRef(txRef)
                .orElseThrow(() -> new PaymentNotFoundException("Payment not found: "));

        // Idempotency check
        if (payment.getStatus() == PaymentStatus.COMPLETED) {
            log.info("Payment already completed: {}", txRef);
            return ResponseEntity.ok().build();
        }

        // Process based on status
        try {
            if ("success".equalsIgnoreCase(paymentStatus)) {
                completePaymentHandler.handle(new CompletePaymentCommand(txRef, receiptUrl));
            } else {
                failPaymentHandler.handle(new FailPaymentCommand(payment.getId()));
            }
        } catch (Exception e) {
            log.error("Webhook processing failed for txRef={}", txRef, e);
            return ResponseEntity.internalServerError().build();
        }

        return ResponseEntity.ok().build();
    }
}