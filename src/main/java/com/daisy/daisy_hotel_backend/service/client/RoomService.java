package com.daisy.daisy_hotel_backend.service.client;

import com.daisy.daisy_hotel_backend.model.Room;

import java.time.LocalDateTime;
import java.util.List;

public interface RoomService {
    List<Room> findAvailableRooms(Long hotelId, LocalDateTime checkInDate, LocalDateTime checkOutDate);
}
