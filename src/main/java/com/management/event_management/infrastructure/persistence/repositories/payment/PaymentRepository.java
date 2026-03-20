package com.management.event_management.infrastructure.persistence.repositories.payment;

import com.management.event_management.domain.entities.payment.Payment;
import com.management.event_management.domain.enums.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PaymentRepository extends JpaRepository<Payment, UUID> {
    @Override
    <S extends Payment> S saveAndFlush(S entity);
    Optional<Payment> findById(UUID id);

    Optional<Payment> findByBookingId(UUID bookingId);
    List<Payment> findByStatus(PaymentStatus status);

}
