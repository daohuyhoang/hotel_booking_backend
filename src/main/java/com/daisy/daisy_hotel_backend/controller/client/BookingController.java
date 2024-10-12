package com.daisy.daisy_hotel_backend.controller.client;

import com.daisy.daisy_hotel_backend.dto.request.BookingDTO;
import com.daisy.daisy_hotel_backend.model.Booking;
import com.daisy.daisy_hotel_backend.service.client.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/user/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @PreAuthorize("hasAuthority('ROLE_UESR')")
    @PostMapping
    public ResponseEntity<Booking> createBooking(@RequestBody BookingDTO bookingDTO) {
        Booking newBooking = bookingService.createBooking(bookingDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(newBooking);
    }
}
