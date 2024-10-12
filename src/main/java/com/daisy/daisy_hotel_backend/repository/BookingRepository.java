package com.daisy.daisy_hotel_backend.repository;

import com.daisy.daisy_hotel_backend.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
}
