package com.daisy.daisy_hotel_backend.controller.client;

import com.daisy.daisy_hotel_backend.model.Hotel;
import com.daisy.daisy_hotel_backend.service.client.HotelService;
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
@RequestMapping("/api/user/hotels")
public class HotelController {

    @Autowired
    private HotelService hotelService;

    @PreAuthorize("hasAuthority('ROLE_USER')")
    @GetMapping("/search")
    public ResponseEntity<List<Hotel>> searchRooms(
            @RequestParam(required = false) String cityId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime checkinDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime checkoutDate) {
        List<Hotel> hotels = hotelService.searchHotels(cityId, checkinDate, checkoutDate);
        return new ResponseEntity<>(hotels, HttpStatus.OK);
    }
}
