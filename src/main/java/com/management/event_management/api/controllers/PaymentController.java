package com.management.event_management.api.controllers;

import com.management.event_management.api.dto.payment.request.CompletePaymentRequest;
import com.management.event_management.api.dto.payment.request.CreatePaymentRequest;
import com.management.event_management.api.dto.payment.response.PaymentInitializationResponse;
import com.management.event_management.api.dto.payment.response.PaymentResponse;
import com.management.event_management.application.payment.command.CompletePaymentCommand;
import com.management.event_management.application.payment.command.CreatePaymentCommand;
import com.management.event_management.application.payment.command.FailPaymentCommand;
import com.management.event_management.application.payment.command.handler.CompletePaymentHandler;
import com.management.event_management.application.payment.command.handler.CreatePaymentHandler;
import com.management.event_management.application.payment.command.handler.FailPaymentHandler;
import com.management.event_management.application.payment.query.GetPayments;
import com.management.event_management.domain.entities.payment.Payment;
import com.management.event_management.domain.enums.PaymentStatus;
import com.management.event_management.domain.valueobjects.Money;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final CreatePaymentHandler createPaymentHandler;
    private final CompletePaymentHandler completePaymentHandler;
    private final FailPaymentHandler failPaymentHandler;
    private final GetPayments getPayments;

    @PostMapping
    public PaymentInitializationResponse createPayment(
            @Valid @RequestBody CreatePaymentRequest request
    ) {

        Money money = new Money(request.getAmount());

        String checkoutUrl = createPaymentHandler.handle(
                new CreatePaymentCommand(request.getBookingId(), money)
        );

        return new PaymentInitializationResponse(checkoutUrl);
    }



//    @PostMapping
//    public PaymentResponse createPayment(@Valid @RequestBody CreatePaymentRequest request) {
//
//        Money money = new Money(request.getAmount());
//
//        Payment payment = createPaymentHandler.handle(
//                new CreatePaymentCommand(request.getBookingId(), money)
//        );
//
//        return PaymentResponse.from(payment);
//    }

    @PostMapping("/{paymentId}/complete")
    public PaymentResponse completePayment(
            @PathVariable UUID paymentId,
            @Valid  @RequestBody CompletePaymentRequest request
    ) {

        Payment payment = completePaymentHandler.handle(
                new CompletePaymentCommand(paymentId, request.getReceiptUrl())
        );

        return PaymentResponse.from(payment);
    }

    @PostMapping("/{paymentId}/fail")
    public PaymentResponse failPayment(@PathVariable UUID paymentId) {

        Payment payment = failPaymentHandler.handle(
                new FailPaymentCommand(paymentId)
        );

        return PaymentResponse.from(payment);
    }

    @GetMapping
    public List<PaymentResponse> getAllPayments() {
        return getPayments.getAllPayments()
                .stream()
                .map(PaymentResponse::from)
                .toList();
    }

    @GetMapping("/{paymentId}")
    public PaymentResponse getById(@PathVariable UUID paymentId) {
        return PaymentResponse.from(getPayments.getPaymentById(paymentId));
    }

    @GetMapping("/booking/{bookingId}")
    public PaymentResponse getByBooking(@PathVariable UUID bookingId) {
        return PaymentResponse.from(getPayments.getPaymentByBookingId(bookingId));
    }

    @GetMapping("/status/{status}")
    public List<PaymentResponse> getByStatus(@PathVariable PaymentStatus status) {
        return getPayments.getPaymentsByStatus(status)
                .stream()
                .map(PaymentResponse::from)
                .toList();
    }
}