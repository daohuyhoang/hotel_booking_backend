package com.daisy.daisy_hotel_backend.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HotelCreateDTO implements BaseHotelDTO{
    private Long hotelId;

    @NotBlank(message = "Hotel name cannot be blank")
    @Size(max = 100, message = "Hotel name must not exceed 100 characters")
    private String name;

    @Size(max = 255, message = "Address must not exceed 255 characters")
    private String address;

    @Size(min = 10, max = 10, message = "Phone number must be exactly 10 digits")
    private String phoneNumber;

    @Email(message = "Email should be valid")
    @Size(max = 100, message = "Email must not exceed 100 characters")
    private String email;

    @NotNull(message = "City is required")
    private Long cityId;

    private List<MultipartFile> hotelImages;

    private List<RoomDTO> rooms;
}