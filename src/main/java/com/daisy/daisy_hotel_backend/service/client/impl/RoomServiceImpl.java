package com.daisy.daisy_hotel_backend.service.client.impl;

import com.daisy.daisy_hotel_backend.dto.request.RoomDTO;
import com.daisy.daisy_hotel_backend.exception.InvalidCheckinDateException;
import com.daisy.daisy_hotel_backend.model.Room;
import com.daisy.daisy_hotel_backend.repository.RoomRepository;
import com.daisy.daisy_hotel_backend.service.client.RoomService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoomServiceImpl implements RoomService {

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<RoomDTO> findAvailableRooms(Long hotelId, Integer capacity, LocalDateTime checkInDate, LocalDateTime checkOutDate) {
        if (checkInDate != null && checkInDate.isBefore(LocalDateTime.now())) {
            throw new InvalidCheckinDateException("Check-in date must be today or after the current date");
        }
        List<Room> availableRooms = roomRepository.findAvailableRooms(hotelId, capacity, checkInDate, checkOutDate);
        return availableRooms.stream()
                .map(room -> modelMapper.map(room, RoomDTO.class))
                .collect(Collectors.toList());
    }
}
