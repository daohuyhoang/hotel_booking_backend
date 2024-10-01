package com.daisy.daisy_hotel_backend.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name ="payment_methods")
public class PaymentMethod {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "method_id")
    private Long methodId;

    @Column(name = "method_name", length = 50, unique = true, nullable = false)
    private String methodName;
}
