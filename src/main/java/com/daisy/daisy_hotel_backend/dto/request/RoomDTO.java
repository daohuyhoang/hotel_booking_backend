package com.daisy.daisy_hotel_backend.dto.request;

import com.daisy.daisy_hotel_backend.model.enums.RoomStatus;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoomDTO {
    private Long roomId;

    @NotBlank(message = "Room number must be not blank")
    @Size(max = 20, message = "Room number must not exceed 20 characters")
    private String roomNumber;

    @NotBlank(message = "Room type cannot be blank")
    @Size(max = 100, message = "Room type must not exceed 100 characters")
    private String type;

    @NotNull(message = "Price cannot be null")
    @DecimalMin(value = "0.00", message = "Price must be greater than or equal to 0.00")
    @DecimalMax(value = "9999999999.99", message = "Price must be less than or equal to 9999999999.99")
    private BigDecimal price;

    @Min(value = 1, message = "Capacity must be at least 1")
    private Integer capacity;

    @NotBlank(message = "Description cannot be null")
    private String description;

    @NotNull(message = "Availability status cannot be null")
    private RoomStatus availabilityStatus;
}
