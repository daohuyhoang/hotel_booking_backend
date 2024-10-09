package com.daisy.daisy_hotel_backend.repository;

import com.daisy.daisy_hotel_backend.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

    @Query("SELECT r FROM Room r WHERE r.hotel.hotelId = :hotelId AND " +
            "r.availabilityStatus = 'AVAILABLE' AND " +
            "(:capacity IS NULL OR r.capacity >= :capacity) AND " +
            "NOT EXISTS (" +
            "   SELECT 1 FROM Booking b " +
            "   WHERE b.room.roomId = r.roomId AND b.checkInDate <= :checkOutDate AND b.checkOutDate >= :checkInDate" +
            ")")
    List<Room> findAvailableRooms(Long hotelId, Integer capacity, LocalDateTime checkInDate, LocalDateTime checkOutDate);
}
