package com.daisy.daisy_hotel_backend.repository;

import com.daisy.daisy_hotel_backend.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
}
