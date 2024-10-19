package com.daisy.daisy_hotel_backend.service.client.impl;

import com.daisy.daisy_hotel_backend.dto.request.BookingRequest;
import com.daisy.daisy_hotel_backend.exception.ResourceNotFoundException;
import com.daisy.daisy_hotel_backend.model.Booking;
import com.daisy.daisy_hotel_backend.model.CustomUserDetail;
import com.daisy.daisy_hotel_backend.model.Room;
import com.daisy.daisy_hotel_backend.model.User;
import com.daisy.daisy_hotel_backend.model.enums.BookingPaymentStatus;
import com.daisy.daisy_hotel_backend.model.enums.BookingStatus;
import com.daisy.daisy_hotel_backend.repository.BookingRepository;
import com.daisy.daisy_hotel_backend.repository.RoomRepository;
import com.daisy.daisy_hotel_backend.repository.UserRepository;
import com.daisy.daisy_hotel_backend.service.client.BookingService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BookingServiceImpl implements BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private UserRepository userRepository;

//    @Autowired
//    private RedisTemplate<String, Object> redisTemplate;
//
//    private final long PAYMENT_TTL = 60;

    @Override
    @CacheEvict(value = "rooms", allEntries = true)
    @Transactional
    public Booking createBooking(BookingRequest bookingRequest) {
        User user = getUserByCustomUserDetail();

        List<Room> rooms = roomRepository.findAllById(bookingRequest.getRoomIds());
        if (rooms.size() != bookingRequest.getRoomIds().size()) {
            throw new RuntimeException("Some rooms were not found.");
        }

        Booking booking = new Booking();
        booking.setUser(user);
        booking.setRooms(rooms);
        booking.setCheckInDate(bookingRequest.getCheckinDate());
        booking.setCheckOutDate(bookingRequest.getCheckoutDate());

        booking.setStatus(BookingStatus.PENDING);
        booking.setPaymentStatus(BookingPaymentStatus.NOT_PAID);

        return bookingRepository.save(booking);
    }


    private User getUserByCustomUserDetail() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetail userDetail = (CustomUserDetail) auth.getPrincipal();
        Long userId = userDetail.getUserId();
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }
}
