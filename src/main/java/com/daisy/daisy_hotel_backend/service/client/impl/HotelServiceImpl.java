package com.daisy.daisy_hotel_backend.service.client.impl;

import com.daisy.daisy_hotel_backend.dto.request.HotelDTO;
import com.daisy.daisy_hotel_backend.exception.InvalidCheckinDateException;
import com.daisy.daisy_hotel_backend.model.Hotel;
import com.daisy.daisy_hotel_backend.model.HotelImage;
import com.daisy.daisy_hotel_backend.repository.HotelRepository;
import com.daisy.daisy_hotel_backend.service.client.HotelService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HotelServiceImpl implements HotelService {

    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private ModelMapper modelMapper;

    private static final Logger logger = LoggerFactory.getLogger(HotelServiceImpl.class);

    @Override
    @Cacheable(value = "hotels", key = "#cityId + '_' + #capacity + '_' + #checkinDate + '_' + #checkoutDate", unless = "#result.isEmpty()")
    public List<HotelDTO> searchHotels(String cityId, Integer capacity, LocalDateTime checkinDate, LocalDateTime checkoutDate) {
        logger.info("Querying database for hotels...");
        if (checkinDate != null && checkinDate.isBefore(LocalDateTime.now())) {
            throw new InvalidCheckinDateException("Check-in date must be today or after the current date");
        }
        List<Hotel> hotels = hotelRepository.searchHotels(cityId, capacity, checkinDate, checkoutDate);

        return hotels.stream().map(hotel -> {
            HotelDTO hotelDTO = modelMapper.map(hotel, HotelDTO.class);

            List<String> imageUrls = hotel.getHotelImages()
                    .stream()
                    .map(HotelImage::getImageUrl)
                    .collect(Collectors.toList());

            hotelDTO.setImageUrls(imageUrls);
            return hotelDTO;
        }).collect(Collectors.toList());
    }
}
