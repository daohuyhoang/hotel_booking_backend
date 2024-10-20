package com.daisy.daisy_hotel_backend.service.client.impl;

import com.daisy.daisy_hotel_backend.config.VNPAYConfig;
import com.daisy.daisy_hotel_backend.dto.response.PaymentDTO;
import com.daisy.daisy_hotel_backend.service.client.PaymentService;
import com.daisy.daisy_hotel_backend.util.VNPayUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private final VNPAYConfig vnPayConfig;

    @Override
    public PaymentDTO.VNPayResponse createVnPayPayment(HttpServletRequest request) {

        long amount = Integer.parseInt(request.getParameter("amount")) * 100L;
        String bankCode = request.getParameter("bankCode");
        Map<String, String> vnPayParams = vnPayConfig.getVNPayConfig();
        vnPayParams.put("vnp_Amount", String.valueOf(amount));

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
}
