package com.daisy.daisy_hotel_backend.service;

import com.daisy.daisy_hotel_backend.dto.LoginDto;

import java.util.Set;

public interface AuthService {
    String login(LoginDto loginDto);

    Set<String> getUserRoles(LoginDto loginDto);
}