package com.daisy.daisy_hotel_backend.controller.client;

import com.daisy.daisy_hotel_backend.dto.request.HotelDTO;
import com.daisy.daisy_hotel_backend.model.Hotel;
import com.daisy.daisy_hotel_backend.service.client.HotelService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/user/hotels")
public class HotelController {

    @Autowired
    private HotelService hotelService;

    @Autowired
    private ModelMapper modelMapper;

    @PreAuthorize("hasAuthority('ROLE_USER')")
    @GetMapping("/search")
    public ResponseEntity<List<HotelDTO>> searchRooms(
            @RequestParam(required = false) String cityId,
            @RequestParam(required = false) Integer capacity,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime checkinDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime checkoutDate) {
        List<Hotel> hotels = hotelService.searchHotels(cityId, capacity, checkinDate, checkoutDate);
        List<HotelDTO> hotelDTOs = hotels.stream()
                .map(hotel -> modelMapper.map(hotel, HotelDTO.class))
                .collect(Collectors.toList());
        return new ResponseEntity<>(hotelDTOs, HttpStatus.OK);
    }
}
