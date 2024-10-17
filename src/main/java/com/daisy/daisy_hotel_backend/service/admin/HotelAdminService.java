package com.daisy.daisy_hotel_backend.service.admin;

import com.daisy.daisy_hotel_backend.dto.request.HotelCreateDTO;
import com.daisy.daisy_hotel_backend.dto.response.HotelResponseDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface HotelAdminService {
    List<HotelResponseDTO> getAllHotels();

    void createHotel(HotelCreateDTO hotelCreateDTO, List<MultipartFile> images);
}
