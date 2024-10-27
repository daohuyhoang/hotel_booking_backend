package com.daisy.daisy_hotel_backend.controller.client;

import com.daisy.daisy_hotel_backend.dto.response.PaymentDTO;
import com.daisy.daisy_hotel_backend.dto.response.ResponseObject;
import com.daisy.daisy_hotel_backend.model.enums.BookingStatus;
import com.daisy.daisy_hotel_backend.service.client.BookingService;
import com.daisy.daisy_hotel_backend.service.client.PaymentService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequiredArgsConstructor
@RequestMapping("${spring.application.api-prefix}/payment")
public class PaymentController {

    @Autowired
    private final PaymentService paymentService;

    @Autowired
    private final BookingService bookingService;

    @PreAuthorize("hasAuthority('ROLE_USER')")
    @GetMapping("/vn-pay")
    public ResponseObject<PaymentDTO.VNPayResponse> vnPay(@RequestParam Long bookingId, HttpServletRequest request) {
        return new ResponseObject<>(HttpStatus.OK, "Success", paymentService.createVnPayPayment(bookingId, request));
    }

    @GetMapping("/vn-pay-callback")
    public ResponseObject<PaymentDTO.VNPayResponse> payCallbackHandler(
            @RequestParam("vnp_ResponseCode") String vnp_ResponseCode,
            @RequestParam("vnp_TxnRef") String vnp_TxnRef) {
        Long bookingId = Long.parseLong(vnp_TxnRef);
        if (vnp_ResponseCode.equals("00")) {
            paymentService.handlePaymentCallback(vnp_ResponseCode, bookingId);
            return new ResponseObject<>(HttpStatus.OK, "Success", PaymentDTO.VNPayResponse.builder()
                    .code("00")
                    .message("success")
                    .paymentUrl("").build());
        } else {
            return new ResponseObject<>(HttpStatus.BAD_REQUEST, "Failed", null);
        }
    }
}
