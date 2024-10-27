package com.daisy.daisy_hotel_backend.mapper;

import com.daisy.daisy_hotel_backend.dto.response.BookingResponse;
import com.daisy.daisy_hotel_backend.model.Booking;
import com.daisy.daisy_hotel_backend.model.Room;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface BookingMapper {


    @Mapping(source = "rooms", target = "roomIds")
    BookingResponse toBookingResponse(Booking booking);

    default List<Long> mapRoomsToRoomList(List<Room> rooms) {
        return rooms.stream().map(Room::getRoomId).collect(Collectors.toList());
    }
}
