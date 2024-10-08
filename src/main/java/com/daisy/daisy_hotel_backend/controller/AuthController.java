package com.daisy.daisy_hotel_backend.controller;

import com.daisy.daisy_hotel_backend.dto.JwtAuthResponse;
import com.daisy.daisy_hotel_backend.dto.request.LoginDto;
import com.daisy.daisy_hotel_backend.service.AuthService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@AllArgsConstructor
@CrossOrigin("*")
@RestController
@RequestMapping("/api/auth")
@Validated
public class AuthController {

    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> login(@Valid @RequestBody LoginDto loginDto){
        String token = authService.login(loginDto);
        Set<String> roles = authService.getUserRoles(loginDto);

        JwtAuthResponse jwtAuthResponse = new JwtAuthResponse();
        jwtAuthResponse.setAccessToken(token);
        jwtAuthResponse.setRoles(roles);

        return new ResponseEntity<>(jwtAuthResponse, HttpStatus.OK);
    }

}