package com.example.zavrsnirad.service.impl;

import com.example.zavrsnirad.appenum.Role;
import com.example.zavrsnirad.dto.SignupDTO;
import com.example.zavrsnirad.entity.User;
import com.example.zavrsnirad.repository.UserRepository;
import com.example.zavrsnirad.service.TokenService;
import com.example.zavrsnirad.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

// Ova klasa predstavlja servis korisnika koji se koristi za pozivanje metoda iz repozitorija te za logiku aplikacije
@Service
public class UserServiceImpl implements UserService, UserDetailsService {
    // Injektiranje repozitorija
    private final UserRepository userRepository;
    private final TokenService tokenService;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, TokenService tokenService, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.tokenService = tokenService;
        this.passwordEncoder = passwordEncoder;
    }

    // Metoda koja se koristi za dohvaćanje korisnika iz baze podataka prema njegovom username-u
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    @Override
    public ResponseEntity<String> login(Authentication authentication) {
        String token = tokenService.generateToken(authentication); // generiranje tokena
        return ResponseEntity.ok(token);
    }

    @Override
    public ResponseEntity<String> signup(SignupDTO data) {
        if(data.password().equals(data.passwordConfirmation())) { // provjera da li se lozinke podudaraju
            if(userRepository.findByUsername(data.username()).isEmpty()) { // provjera da li korisnik već postoji
                User user = new User();

                // postavljanje vrijednosti atributa
                user.setUsername(data.username());
                user.setPassword(passwordEncoder.encode(data.password()));
                user.setRole(Role.STUDENT);
                user.setEnabled(true);

                userRepository.save(user);

                return ResponseEntity.ok("User created");
            }
            return ResponseEntity.badRequest().body("Username already exists");
        }
        return ResponseEntity.badRequest().body("Passwords don't match");
    }
}

