package com.management.event_management.application.payment.query;

import com.management.event_management.domain.entities.payment.Payment;
import com.management.event_management.domain.enums.PaymentStatus;
import com.management.event_management.infrastructure.persistence.repositories.payment.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GetPayments {

    private final PaymentRepository paymentRepository;

    @Transactional(readOnly = true)
    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Payment getPaymentById(UUID paymentId) {
        return paymentRepository.findById(paymentId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Payment with ID " + paymentId + " not found"));
    }

    @Transactional(readOnly = true)
    public Payment getPaymentByBookingId(UUID bookingId) {
        return paymentRepository.findByBookingId(bookingId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Payment for booking ID " + bookingId + " not found"));
    }

    @Transactional(readOnly = true)
    public List<Payment> getPaymentsByStatus(PaymentStatus status) {
        List<Payment> payments = paymentRepository.findByStatus(status);
        if (payments.isEmpty()) {
            throw new IllegalArgumentException("No payments found with status " + status);
        }

        return payments;
    }
}