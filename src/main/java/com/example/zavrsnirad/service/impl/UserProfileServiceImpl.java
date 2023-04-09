package com.example.zavrsnirad.service.impl;

import com.example.zavrsnirad.repository.UserProfileRepository;
import com.example.zavrsnirad.service.UserProfileService;
import org.springframework.stereotype.Service;

// Ova klasa predstavlja servis korisnickog profila koji se koristi za pozivanje metoda iz repozitorija te za logiku aplikacije
@Service
public class UserProfileServiceImpl implements UserProfileService {
    // Injektiranje repozitorija
    private final UserProfileRepository userProfileRepository;

    public UserProfileServiceImpl(UserProfileRepository userProfileRepository) {
        this.userProfileRepository = userProfileRepository;
    }
}
