package com.daisy.daisy_hotel_backend.service.client.impl;

import com.daisy.daisy_hotel_backend.dto.request.BookingDTO;
import com.daisy.daisy_hotel_backend.model.Booking;
import com.daisy.daisy_hotel_backend.model.Room;
import com.daisy.daisy_hotel_backend.model.enums.BookingPaymentStatus;
import com.daisy.daisy_hotel_backend.model.enums.BookingStatus;
import com.daisy.daisy_hotel_backend.model.enums.RoomStatus;
import com.daisy.daisy_hotel_backend.repository.BookingRepository;
import com.daisy.daisy_hotel_backend.repository.RoomRepository;
import com.daisy.daisy_hotel_backend.service.client.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookingServiceImpl implements BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Override
    public Booking createBooking(BookingDTO bookingDTO) {
        Room room = roomRepository.findById(bookingDTO.getRoomId())
                .orElseThrow(() -> new RuntimeException("Room not found"));
        if (room.getAvailabilityStatus() != RoomStatus.AVAILABLE) {
            throw new IllegalStateException("Room is not available for booking");
        }

        Booking booking = new Booking();
        booking.setUser(bookingDTO.getUser());
        booking.setRoom(room);
        booking.setCheckInDate(bookingDTO.getCheckinDate());
        booking.setCheckOutDate(bookingDTO.getCheckoutDate());
        booking.setStatus(BookingStatus.PENDING);
        booking.setPaymentStatus(BookingPaymentStatus.NOT_PAID);

        Booking savedBooking = bookingRepository.save(booking);

        room.setAvailabilityStatus(RoomStatus.BOOKED);
        roomRepository.save(room);
        return savedBooking;
    }
}
