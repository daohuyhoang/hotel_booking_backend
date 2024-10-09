package com.daisy.daisy_hotel_backend.repository;

import com.daisy.daisy_hotel_backend.model.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Long> {
    @Query("SELECT DISTINCT r.hotel FROM Room r " +
            "WHERE (:cityId IS NULL OR r.hotel.city.cityId = :cityId) AND " +
            "r.availabilityStatus = 'AVAILABLE' AND " +
            "(:capacity IS NULL OR r.capacity >= :capacity) AND " +
            "NOT EXISTS (SELECT 1 FROM Booking b " +
            "            WHERE b.room.roomId = r.roomId AND " +
            "                  b.checkInDate <= :checkoutDate AND b.checkOutDate >= :checkinDate)")
    List<Hotel> searchHotels(String cityId, Integer capacity, LocalDateTime checkinDate, LocalDateTime checkoutDate);
}
