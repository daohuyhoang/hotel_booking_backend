package com.daisy.daisy_hotel_backend.service.client;

import com.daisy.daisy_hotel_backend.dto.request.RoomDTO;

import java.time.LocalDateTime;
import java.util.List;

public interface RoomService {
    List<RoomDTO> findAvailableRooms(Long hotelId, Integer capacity, LocalDateTime checkInDate, LocalDateTime checkOutDate);
}
