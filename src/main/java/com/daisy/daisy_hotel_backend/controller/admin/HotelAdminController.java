package com.daisy.daisy_hotel_backend.controller.admin;

import com.daisy.daisy_hotel_backend.dto.response.HotelResponseDTO;
import com.daisy.daisy_hotel_backend.service.admin.HotelAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/admin/hotels")
public class HotelAdminController {

    @Autowired
    private HotelAdminService hotelAdminService;

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping
    public ResponseEntity<List<HotelResponseDTO>> getAllHotels() {
        List<HotelResponseDTO> hotels = hotelAdminService.getAllHotels();
        return ResponseEntity.ok(hotels);
    }
}
