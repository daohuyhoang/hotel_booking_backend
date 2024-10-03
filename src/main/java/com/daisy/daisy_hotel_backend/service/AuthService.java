package com.daisy.daisy_hotel_backend.service;

import com.daisy.daisy_hotel_backend.dto.LoginDto;

public interface AuthService {
    String login(LoginDto loginDto);
}