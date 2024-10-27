package com.daisy.daisy_hotel_backend.service.client.impl;

import com.daisy.daisy_hotel_backend.dto.request.BookingRequest;
import com.daisy.daisy_hotel_backend.dto.response.BookingResponse;
import com.daisy.daisy_hotel_backend.exception.ResourceNotFoundException;
import com.daisy.daisy_hotel_backend.mapper.BookingMapper;
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
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingServiceImpl implements BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private BookingMapper bookingMapper;

    @Override
    @CacheEvict(value = "rooms", allEntries = true)
    @Transactional
    public BookingResponse createBooking(BookingRequest bookingRequest) {
        User user = getUserByCustomUserDetail();

        List<Room> rooms = roomRepository.findAllById(bookingRequest.getRoomIds());
        if (rooms.size() != bookingRequest.getRoomIds().size()) {
            throw new RuntimeException("Some rooms were not found.");
        }

        checkRoomBooked(bookingRequest, rooms);

        Booking booking = new Booking();
        booking.setUser(user);
        booking.setRooms(rooms);
        booking.setCheckInDate(bookingRequest.getCheckinDate());
        booking.setCheckOutDate(bookingRequest.getCheckoutDate());
        booking.setStatus(BookingStatus.PENDING);
        booking.setPaymentStatus(BookingPaymentStatus.NOT_PAID);

        Booking savedBooking = bookingRepository.save(booking);

        return bookingMapper.toBookingResponse(savedBooking);
    }

    private void checkRoomBooked(BookingRequest bookingRequest, List<Room> rooms) {
        for (Room room : rooms) {
            boolean isRoomBooked = bookingRepository.isRoomBooked(room.getRoomId()
                    , bookingRequest.getCheckinDate(), bookingRequest.getCheckoutDate());
            if (isRoomBooked) {
                throw new RuntimeException("Room " + room.getRoomNumber() + " is already booked during the selected dates.");
            }
        }
    }

    private User getUserByCustomUserDetail() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetail userDetail = (CustomUserDetail) auth.getPrincipal();
        Long userId = userDetail.getUserId();
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }
}
