package com.example.zavrsnirad.service.impl;

import com.example.zavrsnirad.service.TokenService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.stream.Collectors;

// Ova klasa predstavlja servis tokena koji se koristi za generiranje tokena i ostale metode vezane uz token
@Service
public class TokenServiceImpl implements TokenService {

    private final JwtEncoder encoder;
    private final JwtDecoder decoder;

    public TokenServiceImpl(JwtEncoder encoder, JwtDecoder decoder) {
        this.encoder = encoder;
        this.decoder = decoder;
    }


    // Metoda koja se koristi za generiranje tokena
    public String generateToken(Authentication authentication) {
        Instant now = Instant.now(); // trenutno vrijeme

        String scope = authentication.getAuthorities().stream() // dohvaćanje uloga korisnika
                .map(GrantedAuthority::getAuthority) // dohvaćanje naziva uloge
                .collect(Collectors.joining()); // spajanje svih uloga u jedan string

        JwtClaimsSet claims = JwtClaimsSet.builder() // kreiranje claims-a
                .issuer("self") // postavljanje issuer-a (self)
                .issuedAt(now) // postavljanje trenutnog vremena
                .expiresAt(now.plus(12, ChronoUnit.HOURS)) // postavljanje vremena isteka tokena
                .subject(authentication.getName()) // postavljanje username-a
                .claim("scope", scope) // postavljanje uloga
                .build();

        return this.encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue(); // generiranje tokena
    }

    // Metoda koja se koristi za dohvaćanje username-a iz tokena
    public String getUsernameFromToken(String bearer) {
        String token = bearer.substring(7); // dohvaćanje tokena iz headera
        return String.valueOf(this.decoder.decode(token).getSubject()); // dohvaćanje username-a iz tokena
    }
}
