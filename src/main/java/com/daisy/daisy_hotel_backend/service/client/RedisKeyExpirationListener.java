//package com.daisy.daisy_hotel_backend.service.client;
//
//import com.daisy.daisy_hotel_backend.exception.ResourceNotFoundException;
//import com.daisy.daisy_hotel_backend.model.Booking;
//import com.daisy.daisy_hotel_backend.model.Room;
//import com.daisy.daisy_hotel_backend.model.enums.BookingStatus;
//import com.daisy.daisy_hotel_backend.model.enums.RoomStatus;
//import com.daisy.daisy_hotel_backend.repository.BookingRepository;
//import com.daisy.daisy_hotel_backend.repository.RoomRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.redis.connection.Message;
//import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
//import org.springframework.data.redis.listener.RedisMessageListenerContainer;
//import org.springframework.stereotype.Service;
//
//@Service
//public class RedisKeyExpirationListener extends KeyExpirationEventMessageListener {
//    @Autowired
//    private BookingRepository bookingRepository;
//
//    @Autowired
//    private RoomRepository roomRepository;
//
//    public RedisKeyExpirationListener(RedisMessageListenerContainer listenerContainer) {
//        super(listenerContainer);
//    }
//
//    @Override
//    public void onMessage(Message message, byte[] pattern) {
//        String expiredKey = message.toString();
//        if (expiredKey.startsWith("booking:")) {
//            Long bookingId = Long.parseLong(expiredKey.split(":")[1]);
//            handleExpiredBooking(bookingId);
//        }
//    }
//
//    public void handleExpiredBooking(Long bookingId) {
//        Booking booking = bookingRepository.findById(bookingId)
//                .orElseThrow(() -> new ResourceNotFoundException("Booking not found"));
//
//        if (booking.getStatus() != BookingStatus.COMPLETED) {
//            booking.setStatus(BookingStatus.CANCELLED);
//            bookingRepository.save(booking);
//
//            Room room = booking.getRoom();
//            room.setAvailabilityStatus(RoomStatus.AVAILABLE);
//            roomRepository.save(room);
//        }
//    }
//}
