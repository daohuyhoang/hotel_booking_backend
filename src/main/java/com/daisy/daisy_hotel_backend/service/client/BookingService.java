package com.daisy.daisy_hotel_backend.service.client;

import com.daisy.daisy_hotel_backend.dto.request.BookingRequest;
import com.daisy.daisy_hotel_backend.dto.response.BookingResponse;
import com.daisy.daisy_hotel_backend.model.enums.BookingStatus;


public interface BookingService {
    BookingResponse createBooking(BookingRequest bookingRequest);
}
