package com.daisy.daisy_hotel_backend.repository;

import com.daisy.daisy_hotel_backend.model.Booking;
import com.daisy.daisy_hotel_backend.model.enums.BookingStatus;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
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

    @Modifying
    @Query("UPDATE Booking b SET b.status = :bookingStatus WHERE b.bookingId = :bookingId")
    void updateBookingStatus(@Param("bookingId") Long bookingId, @Param("bookingStatus") BookingStatus bookingStatus);
}
