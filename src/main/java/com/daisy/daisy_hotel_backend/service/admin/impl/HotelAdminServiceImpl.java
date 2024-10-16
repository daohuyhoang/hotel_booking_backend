package com.daisy.daisy_hotel_backend.service.admin.impl;

import com.daisy.daisy_hotel_backend.dto.request.HotelCreateDTO;
import com.daisy.daisy_hotel_backend.dto.response.HotelResponseDTO;
import com.daisy.daisy_hotel_backend.exception.ResourceNotFoundException;
import com.daisy.daisy_hotel_backend.model.City;
import com.daisy.daisy_hotel_backend.model.Hotel;
import com.daisy.daisy_hotel_backend.model.HotelImage;
import com.daisy.daisy_hotel_backend.repository.CityRepository;
import com.daisy.daisy_hotel_backend.repository.HotelImageRepository;
import com.daisy.daisy_hotel_backend.repository.HotelRepository;
import com.daisy.daisy_hotel_backend.service.admin.CloudinaryService;
import com.daisy.daisy_hotel_backend.service.admin.HotelAdminService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class HotelAdminServiceImpl implements HotelAdminService {

    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private HotelImageRepository hotelImageRepository;

    @Autowired
    private CloudinaryService cloudinaryService;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<HotelResponseDTO> getAllHotels() {
        List<Hotel> hotels = hotelRepository.findHotelWithImages();
        return hotels.stream().map(hotel -> {
            HotelResponseDTO dto = modelMapper.map(hotel, HotelResponseDTO.class);

            List<String> imageUrls = hotel.getHotelImages()
                    .stream()
                    .map(HotelImage::getImageUrl)
                    .collect(Collectors.toList());

            dto.setImageUrls(imageUrls);

            return dto;
        }).collect(Collectors.toList());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Hotel createHotel(HotelCreateDTO hotelCreateDTO, List<MultipartFile> images) {
        City city = cityRepository.findById(hotelCreateDTO.getCityId())
                .orElseThrow(() -> new ResourceNotFoundException("City not found"));

        Hotel hotel = modelMapper.map(hotelCreateDTO, Hotel.class);
        hotel.setCity(city);

        Hotel savedHotel = hotelRepository.save(hotel);

        if (images != null && !images.isEmpty()) {
            for (MultipartFile imageFile : images) {

                String uploadedImagePath = cloudinaryService.uploadImage(imageFile);

                HotelImage hotelImage = new HotelImage();
                hotelImage.setHotel(savedHotel);
                hotelImage.setImageUrl(uploadedImagePath);

                hotelImageRepository.save(hotelImage);
            }
        }
        return savedHotel;
    }
}
