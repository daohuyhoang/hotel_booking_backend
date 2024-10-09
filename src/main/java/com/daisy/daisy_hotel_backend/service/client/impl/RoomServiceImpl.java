package com.daisy.daisy_hotel_backend.service.client.impl;

import com.daisy.daisy_hotel_backend.model.Room;
import com.daisy.daisy_hotel_backend.repository.RoomRepository;
import com.daisy.daisy_hotel_backend.service.client.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RoomServiceImpl implements RoomService {

    @Autowired
    private RoomRepository roomRepository;

    @Override
    public List<Room> findAvailableRooms(Long hotelId, LocalDateTime checkInDate, LocalDateTime checkOutDate) {
        return roomRepository.findAvailableRooms(hotelId, checkInDate, checkOutDate);
    }
}
