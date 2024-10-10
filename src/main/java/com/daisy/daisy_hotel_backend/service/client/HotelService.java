package com.daisy.daisy_hotel_backend.service.client;

import com.daisy.daisy_hotel_backend.dto.request.HotelDTO;
import java.time.LocalDateTime;
import java.util.List;

public interface HotelService{
    List<HotelDTO> searchHotels(String cityId, Integer capacity, LocalDateTime checkinDate, LocalDateTime checkoutDate);
}
