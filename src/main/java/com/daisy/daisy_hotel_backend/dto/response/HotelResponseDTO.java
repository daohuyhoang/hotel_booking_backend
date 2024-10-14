package com.daisy.daisy_hotel_backend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HotelResponseDTO {
    private int hotelId;
    private String name;
    private String address;
    private String phoneNumber;
    private List<String> imageUrls;
}
