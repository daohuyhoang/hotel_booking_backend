package com.daisy.daisy_hotel_backend.model;

import com.daisy.daisy_hotel_backend.model.enums.RoomStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Entity
@Table(name = "rooms")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_id")
    private Long roomId;

    @Column(name ="room_number",nullable = false, unique = true, length = 20)
    private String roomNumber;

    @Column(name = "type", nullable = false, length = 100)
    private String type;

    @Column(name = "price", nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(name = "capacity", columnDefinition = "int default 1")
    private Integer capacity;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "ENUM('AVAILABLE', 'BOOKED', 'OUT_OF_SERVICE') default 'AVAILABLE'")
    private RoomStatus availabilityStatus = RoomStatus.AVAILABLE;

    @ManyToOne
    @JoinColumn(name = "hotel_id", nullable = false)
    private Hotel hotel;

    @Lob
    private String description;

}
