package com.daisy.daisy_hotel_backend.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HotelDTO {

    private Long hotelId;
    private String name;
    private String address;
    private String phoneNumber;
    private String email;
}
