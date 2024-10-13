package com.daisy.daisy_hotel_backend.dto.request;

import com.daisy.daisy_hotel_backend.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookingRequest {
    private Long roomId;
    private User user;
    private LocalDateTime checkinDate;
    private LocalDateTime checkoutDate;
}
