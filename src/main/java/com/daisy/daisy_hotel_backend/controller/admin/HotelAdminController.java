package com.daisy.daisy_hotel_backend.controller.admin;

import com.daisy.daisy_hotel_backend.dto.request.HotelCreateDTO;
import com.daisy.daisy_hotel_backend.dto.response.HotelResponseDTO;
import com.daisy.daisy_hotel_backend.model.Hotel;
import com.daisy.daisy_hotel_backend.service.admin.HotelAdminService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
        return new ResponseEntity<>(hotels, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<Hotel> createHotel(
            @RequestPart(value = "hotel") @Valid HotelCreateDTO hotelCreateDTO,
            @RequestPart(value = "hotelImages", required = false) List<MultipartFile> images) {
        Hotel createdHotel = hotelAdminService.createHotel(hotelCreateDTO, images);

        return new ResponseEntity<>(createdHotel, HttpStatus.CREATED);
    }
}
