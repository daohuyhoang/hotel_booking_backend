package com.daisy.daisy_hotel_backend.repository;

import com.daisy.daisy_hotel_backend.model.HotelImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HotelImageRepository extends JpaRepository<HotelImage, Long> {
}