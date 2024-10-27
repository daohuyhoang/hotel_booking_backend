package com.daisy.daisy_hotel_backend.service.client.impl;

import com.daisy.daisy_hotel_backend.config.VNPAYConfig;
import com.daisy.daisy_hotel_backend.dto.response.BookingResponse;
import com.daisy.daisy_hotel_backend.dto.response.PaymentDTO;
import com.daisy.daisy_hotel_backend.exception.ResourceNotFoundException;
import com.daisy.daisy_hotel_backend.mapper.BookingMapper;
import com.daisy.daisy_hotel_backend.model.Booking;
import com.daisy.daisy_hotel_backend.model.Payment;
import com.daisy.daisy_hotel_backend.model.PaymentMethod;
import com.daisy.daisy_hotel_backend.model.Room;
import com.daisy.daisy_hotel_backend.model.enums.BookingStatus;
import com.daisy.daisy_hotel_backend.model.enums.PaymentTransactionStatus;
import com.daisy.daisy_hotel_backend.repository.BookingRepository;
import com.daisy.daisy_hotel_backend.repository.PaymentMethodRepository;
import com.daisy.daisy_hotel_backend.repository.PaymentRepository;
import com.daisy.daisy_hotel_backend.repository.RoomRepository;
import com.daisy.daisy_hotel_backend.service.client.PaymentService;
import com.daisy.daisy_hotel_backend.util.VNPayUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private final VNPAYConfig vnPayConfig;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private PaymentMethodRepository paymentMethodRepository;

    @Override
    public PaymentDTO.VNPayResponse createVnPayPayment(Long bookingId, HttpServletRequest request) {

        Booking booking = bookingRepository.findByIdWithRooms(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found"));

        List<Room> rooms = booking.getRooms();

        BigDecimal amount = totalAmount(rooms);

        String bankCode = request.getParameter("bankCode");
        Map<String, String> vnPayParams = vnPayConfig.getVNPayConfig(bookingId);
        vnPayParams.put("vnp_Amount", String.valueOf(amount.longValue()));

        if (bankCode != null && !bankCode.isEmpty()) {
            vnPayParams.put("vnp_BankCode", bankCode);
        }
        vnPayParams.put("vnp_IpAddr", VNPayUtil.getIpAddress(request));

        String queryUrl = VNPayUtil.getPaymentURL(vnPayParams, true);
        String hashData = VNPayUtil.getPaymentURL(vnPayParams, false);
        String vnpSecureHash = VNPayUtil.hmacSHA512(vnPayConfig.getSecretKey(), hashData);
        queryUrl += "&vnp_SecureHash=" + vnpSecureHash;
        String paymentUrl = vnPayConfig.getVnp_PayUrl() + "?" + queryUrl;

        return PaymentDTO.VNPayResponse.builder()
                .code("ok")
                .message("success")
                .paymentUrl(paymentUrl).build();
    }

    @Override
    @Transactional
    public void handlePaymentCallback(String vnp_ResponseCode, Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found for bookingId: " + bookingId));

        Payment payment = paymentRepository.findByBookingId(bookingId)
                        .orElseGet(() -> {
                           Payment newPayment = new Payment();
                           newPayment.setBooking(booking);
                           newPayment.setUser(booking.getUser());
                           newPayment.setPaymentMethod(paymentMethodRepository.findById(1L).orElseThrow());
                           newPayment.setPaymentStatus(PaymentTransactionStatus.PENDING);
                           return newPayment;
                        });

        payment.setPaymentStatus(PaymentTransactionStatus.SUCCESS);
        paymentRepository.save(payment);

        bookingRepository.updateBookingStatus(bookingId, BookingStatus.COMPLETED);
    }

    private BigDecimal totalAmount(List<Room> rooms) {
        BigDecimal totalAmount = BigDecimal.ZERO;
        for (Room room : rooms) {
            totalAmount = totalAmount.add(room.getPrice());
        }
        return totalAmount.multiply(BigDecimal.valueOf(100));
    }
}
