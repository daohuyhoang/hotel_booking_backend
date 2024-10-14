package com.daisy.daisy_hotel_backend.service.admin;

import com.daisy.daisy_hotel_backend.dto.response.HotelResponseDTO;

import java.util.List;

public interface HotelAdminService {
    List<HotelResponseDTO> getAllHotels();
}
