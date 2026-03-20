package com.management.event_management.api.controllers;

import com.management.event_management.api.dto.payment.webhook.ChapaWebhookPayload;
import com.management.event_management.application.payment.command.CompletePaymentCommand;
import com.management.event_management.application.payment.command.FailPaymentCommand;
import com.management.event_management.application.payment.command.handler.CompletePaymentHandler;
import com.management.event_management.application.payment.command.handler.FailPaymentHandler;
import com.management.event_management.domain.entities.payment.Payment;
import com.management.event_management.domain.exceptions.payment.InvalidPaymentStateException;
import com.management.event_management.domain.exceptions.payment.PaymentNotFoundException;
import com.management.event_management.infrastructure.persistence.repositories.payment.PaymentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payments/webhook")
@RequiredArgsConstructor
@Slf4j
public class PaymentWebhookController {

    private final PaymentRepository paymentRepository;
    private final CompletePaymentHandler completePaymentHandler;
    private final FailPaymentHandler failPaymentHandler;


    @GetMapping("/chapa/success")
    public ResponseEntity<String> success(@RequestParam String tx_ref) {
//        this is the return url(frontend): it is here for the testing purposes.
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

    @PostMapping("/chapa")
    public ResponseEntity<Void> handleChapaWebhook(@RequestBody ChapaWebhookPayload payload) {

        String txRef = payload.getTransactionRef();
        String status = payload.getStatus();

        log.info("Received Chapa webhook: txRef={}, status={}", txRef, status);

        if (txRef == null || txRef.isBlank()) {
            log.error("Webhook missing transaction reference");
            return ResponseEntity.badRequest().build();
        }

        Payment payment = paymentRepository.findByTransactionRef(txRef)
                .orElseThrow(()-> new PaymentNotFoundException("Payment doesn't exist"));
        System.out.println(payment);

//        prevent reprocessing
        if (payment.getStatus() == com.management.event_management.domain.enums.PaymentStatus.COMPLETED) {
            log.info("Payment already completed: {}", txRef);
            return ResponseEntity.ok().build();
        }

        try {
            if ("success".equalsIgnoreCase(status)) {
                completePaymentHandler.handle(new CompletePaymentCommand(txRef));
            } else {
                failPaymentHandler.handle(new FailPaymentCommand(payment.getId()));
            }
        } catch (Exception e) {
            log.error("Error processing webhook for txRef={}", txRef, e);
            return ResponseEntity.internalServerError().build();
        }

        return ResponseEntity.ok().build();
    }
    }
