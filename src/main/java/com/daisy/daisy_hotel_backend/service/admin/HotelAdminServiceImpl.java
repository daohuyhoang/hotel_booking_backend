package com.daisy.daisy_hotel_backend.service.admin;

import com.daisy.daisy_hotel_backend.dto.response.HotelResponseDTO;
import com.daisy.daisy_hotel_backend.model.Hotel;
import com.daisy.daisy_hotel_backend.model.HotelImage;
import com.daisy.daisy_hotel_backend.repository.HotelImageRepository;
import com.daisy.daisy_hotel_backend.repository.HotelRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class HotelAdminServiceImpl implements HotelAdminService {

    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private HotelImageRepository hotelImageRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<HotelResponseDTO> getAllHotels() {
        List<Hotel> hotels = hotelRepository.findAll();
        return hotels.stream().map(hotel -> {
            HotelResponseDTO dto = modelMapper.map(hotel, HotelResponseDTO.class);

            List<String> imageUrls = hotelImageRepository.findByHotel(hotel)
                    .stream()
                    .map(HotelImage::getImageUrl)
                    .collect(Collectors.toList());

            dto.setImageUrls(imageUrls);

            return dto;
        }).collect(Collectors.toList());
    }
}
