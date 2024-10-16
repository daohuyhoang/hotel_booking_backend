package com.daisy.daisy_hotel_backend.service.client.impl;

import com.daisy.daisy_hotel_backend.dto.request.BookingRequest;
import com.daisy.daisy_hotel_backend.model.Booking;
import com.daisy.daisy_hotel_backend.model.CustomUserDetail;
import com.daisy.daisy_hotel_backend.model.Room;
import com.daisy.daisy_hotel_backend.model.User;
import com.daisy.daisy_hotel_backend.model.enums.BookingPaymentStatus;
import com.daisy.daisy_hotel_backend.model.enums.BookingStatus;
import com.daisy.daisy_hotel_backend.model.enums.RoomStatus;
import com.daisy.daisy_hotel_backend.repository.BookingRepository;
import com.daisy.daisy_hotel_backend.repository.RoomRepository;
import com.daisy.daisy_hotel_backend.repository.UserRepository;
import com.daisy.daisy_hotel_backend.service.client.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookingServiceImpl implements BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<Booking> createBooking(BookingRequest bookingRequest) {

        List<Booking> savedBookings = new ArrayList<>();

        User user = getUserByCustomUserDetail();

        for (Long roomId : bookingRequest.getRoomId()) {
            Room room = roomRepository.findById(roomId)
                    .orElseThrow(() -> new RuntimeException("Room not found"));
            if (room.getAvailabilityStatus() != RoomStatus.AVAILABLE) {
                throw new IllegalStateException("Room is not available for booking");
            }

            Booking booking = new Booking();
            booking.setUser(user);
            booking.setRoom(room);
            booking.setCheckInDate(bookingRequest.getCheckinDate());
            booking.setCheckOutDate(bookingRequest.getCheckoutDate());
            booking.setStatus(BookingStatus.PENDING);
            booking.setPaymentStatus(BookingPaymentStatus.NOT_PAID);

            Booking savedBooking = bookingRepository.save(booking);
            savedBookings.add(savedBooking);

            room.setAvailabilityStatus(RoomStatus.BOOKED);
            roomRepository.save(room);
        }

        return savedBookings;
    }

    private User getUserByCustomUserDetail() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetail userDetail = (CustomUserDetail) auth.getPrincipal();
        Long userId = userDetail.getUserId();
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}
