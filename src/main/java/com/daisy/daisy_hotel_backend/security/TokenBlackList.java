package com.daisy.daisy_hotel_backend.security;

import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class TokenBlackList {
    private final Set<String> blackListTokens = new HashSet<>();

    public void blackListToken(String token) {
        blackListTokens.add(token);
    }

    public boolean isTokenBlackListed(String token) {
        return blackListTokens.contains(token);
    }
}
