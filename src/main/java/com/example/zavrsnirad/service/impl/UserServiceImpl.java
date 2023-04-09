package com.example.zavrsnirad.service.impl;

import com.example.zavrsnirad.repository.UserRepository;
import com.example.zavrsnirad.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

// Ova klasa predstavlja servis korisnika koji se koristi za pozivanje metoda iz repozitorija te za logiku aplikacije
@Service
public class UserServiceImpl implements UserService, UserDetailsService {
    // Injektiranje repozitorija
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Metoda koja se koristi za dohvaćanje korisnika iz baze podataka prema njegovom username-u
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

}

