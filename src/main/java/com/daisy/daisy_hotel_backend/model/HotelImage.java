package com.daisy.daisy_hotel_backend.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "hotel_images")
@Data
public class HotelImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id")
    private Long imageId;

    @ManyToOne
    @JoinColumn(name = "hotel_id", nullable = false)
    private Hotel hotel;

    @Column(name = "image_url", nullable = false, length = 255)
    private String imageUrl;
}
