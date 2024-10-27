package com.daisy.daisy_hotel_backend.dto.response;

import com.daisy.daisy_hotel_backend.model.enums.BookingPaymentStatus;
import com.daisy.daisy_hotel_backend.model.enums.BookingStatus;
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
public class BookingResponse {
    private Long bookingId;
    private LocalDateTime checkInDate;
    private LocalDateTime checkOutDate;
    private BookingStatus status;
    private List<Long> roomIds;
    private BookingPaymentStatus paymentStatus;
}
