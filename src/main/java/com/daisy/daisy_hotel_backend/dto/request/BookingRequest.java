package com.daisy.daisy_hotel_backend.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookingRequest implements Serializable {
    private List<Long> roomIds;
    private LocalDateTime checkinDate;
    private LocalDateTime checkoutDate;
}
