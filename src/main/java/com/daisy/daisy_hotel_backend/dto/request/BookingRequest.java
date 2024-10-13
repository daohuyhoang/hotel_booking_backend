package com.daisy.daisy_hotel_backend.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookingRequest {
    private List<Long> roomId;
    private LocalDateTime checkinDate;
    private LocalDateTime checkoutDate;
}
