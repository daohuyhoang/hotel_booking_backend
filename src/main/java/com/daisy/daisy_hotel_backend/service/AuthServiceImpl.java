package com.daisy.daisy_hotel_backend.service;

import com.daisy.daisy_hotel_backend.dto.LoginDto;
import com.daisy.daisy_hotel_backend.model.Role;
import com.daisy.daisy_hotel_backend.model.User;
import com.daisy.daisy_hotel_backend.repository.UserRepository;
import com.daisy.daisy_hotel_backend.security.JwtTokenProvider;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

    private AuthenticationManager authenticationManager;

    private JwtTokenProvider jwtTokenProvider;

    private UserRepository userRepository;

    @Override
    public String login(LoginDto loginDto) {

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getUsernameOrEmail(),
                loginDto.getPassword()
        ));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtTokenProvider.generateToken(authentication);

        return token;
    }

    public Set<String> getUserRoles(LoginDto loginDto) {
        User user = userRepository.findByUsernameOrEmail(loginDto.getUsernameOrEmail(), loginDto.getUsernameOrEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return user.getRoles().stream()
                .map(Role::getName)
                .collect(Collectors.toSet());
    }
}