package com.daisy.daisy_hotel_backend.repository;

import com.daisy.daisy_hotel_backend.model.Payment;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    @Query("SELECT p FROM Payment p WHERE p.booking.bookingId = :bookingId")
    Optional<Payment> findByBookingId(@Param("bookingId") Long bookingId);

}
