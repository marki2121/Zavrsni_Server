package com.example.zavrsnirad.service;

import org.springframework.security.core.Authentication;

public interface TokenService {
    String generateToken(Authentication authentication);

    String getUsernameFromToken(String bearer);

}
