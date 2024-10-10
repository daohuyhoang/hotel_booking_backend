package com.daisy.daisy_hotel_backend.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoomDTO {
    private Long roomId;
    private String roomNumber;
    private String type;
    private BigDecimal price;
    private Integer capacity;
    private String description;
}
