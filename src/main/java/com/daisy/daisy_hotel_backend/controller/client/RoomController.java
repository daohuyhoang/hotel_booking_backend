package com.daisy.daisy_hotel_backend.controller.client;

import com.daisy.daisy_hotel_backend.dto.request.RoomDTO;
import com.daisy.daisy_hotel_backend.service.client.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/user/rooms")
public class RoomController {

    @Autowired
    private RoomService roomService;

    @PreAuthorize("hasAuthority('ROLE_USER')")
    @GetMapping("/available")
    public ResponseEntity<List<RoomDTO>> getAvailableRooms(
            @RequestParam(required = false) Long hotelId,
            @RequestParam(required = false) Integer capacity,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime checkInDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime checkOutDate) {
        List<RoomDTO> roomDTOs = roomService.findAvailableRooms(hotelId, capacity, checkInDate, checkOutDate);
        return new ResponseEntity<>(roomDTOs, HttpStatus.OK);
    }
}
