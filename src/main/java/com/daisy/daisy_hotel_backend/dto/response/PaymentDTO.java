package com.daisy.daisy_hotel_backend.dto.response;

import lombok.*;

public abstract class PaymentDTO {
    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    public static class VNPayResponse {
        private String code;
        private String message;
        private String paymentUrl;
    }
}
