package com.daisy.daisy_hotel_backend.service.client.impl;

import com.daisy.daisy_hotel_backend.model.Hotel;
import com.daisy.daisy_hotel_backend.repository.HotelRepository;
import com.daisy.daisy_hotel_backend.service.client.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class HotelServiceImpl implements HotelService {

    @Autowired
    private HotelRepository hotelRepository;

    @Override
    public List<Hotel> searchHotels(String cityId, LocalDateTime checkinDate, LocalDateTime checkoutDate) {
        return hotelRepository.searchHotels(cityId, checkinDate, checkoutDate);
    }
}
