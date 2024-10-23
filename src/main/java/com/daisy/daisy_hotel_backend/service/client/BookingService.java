package com.daisy.daisy_hotel_backend.service.client;

import com.daisy.daisy_hotel_backend.dto.request.BookingRequest;
import com.daisy.daisy_hotel_backend.dto.response.BookingResponse;


public interface BookingService {
    BookingResponse createBooking(BookingRequest bookingRequest);
}
