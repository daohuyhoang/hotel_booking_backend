package com.daisy.daisy_hotel_backend.repository;

import com.daisy.daisy_hotel_backend.model.Booking;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    @Query("SELECT COUNT(b) > 0 FROM Booking b JOIN b.rooms r " +
            "WHERE r.roomId = :roomId " +
            "AND b.checkInDate <= :checkoutDate " +
            "AND b.checkOutDate >= :checkinDate")
    boolean isRoomBooked(Long roomId, LocalDateTime checkinDate, LocalDateTime checkoutDate);

    @Query("SELECT b FROM Booking b JOIN FETCH b.rooms WHERE b.bookingId = :bookingId")
    Optional<Booking> findByIdWithRooms(@Param("bookingId") Long bookingId);
}
