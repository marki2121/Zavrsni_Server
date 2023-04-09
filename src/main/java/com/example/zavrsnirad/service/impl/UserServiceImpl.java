package com.example.zavrsnirad.service.impl;

import com.example.zavrsnirad.repository.UserRepository;
import com.example.zavrsnirad.service.UserService;
import org.springframework.stereotype.Service;

// Ova klasa predstavlja servis korisnika koji se koristi za pozivanje metoda iz repozitorija te za logiku aplikacije
@Service
public class UserServiceImpl implements UserService {
    // Injektiranje repozitorija
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}
