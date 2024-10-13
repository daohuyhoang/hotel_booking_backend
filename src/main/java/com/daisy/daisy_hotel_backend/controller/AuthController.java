package com.daisy.daisy_hotel_backend.controller;

import com.daisy.daisy_hotel_backend.dto.JwtAuthResponse;
import com.daisy.daisy_hotel_backend.dto.request.LoginDto;
import com.daisy.daisy_hotel_backend.security.JwtAuthenticationFilter;
import com.daisy.daisy_hotel_backend.security.TokenBlackList;
import com.daisy.daisy_hotel_backend.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
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

    private TokenBlackList tokenBlackList;

    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> login(@Valid @RequestBody LoginDto loginDto){
        String token = authService.login(loginDto);
        Set<String> roles = authService.getUserRoles(loginDto);

        JwtAuthResponse jwtAuthResponse = new JwtAuthResponse();
        jwtAuthResponse.setAccessToken(token);
        jwtAuthResponse.setRoles(roles);

        return new ResponseEntity<>(jwtAuthResponse, HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request){
        String token = jwtAuthenticationFilter.getTokenFromRequest(request);
        if (token != null){
            tokenBlackList.blackListToken(token);
        }
        return new ResponseEntity<>("Logout successful", HttpStatus.OK);
    }

}