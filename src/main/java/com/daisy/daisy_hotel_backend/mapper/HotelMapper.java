package com.daisy.daisy_hotel_backend.mapper;

import com.daisy.daisy_hotel_backend.dto.response.HotelResponseDTO;
import com.daisy.daisy_hotel_backend.model.Hotel;
import com.daisy.daisy_hotel_backend.model.HotelImage;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface HotelMapper {

    @Mapping(target = "hotelImages", expression = "java(mapHotelImages(hotel.getHotelImages()))")
    HotelResponseDTO hotelToHotelResponseDTO(Hotel hotel);

    default List<String> mapHotelImages(List<HotelImage> hotelImages) {
        return hotelImages.stream()
                .map(HotelImage::getImageUrl)
                .collect(Collectors.toList());
    }
}
