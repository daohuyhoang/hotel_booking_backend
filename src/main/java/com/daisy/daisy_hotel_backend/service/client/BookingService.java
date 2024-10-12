package com.daisy.daisy_hotel_backend.service.client;

import com.daisy.daisy_hotel_backend.dto.request.BookingDTO;
import com.daisy.daisy_hotel_backend.model.Booking;

public interface BookingService {
    Booking createBooking(BookingDTO bookingDTO);
}
