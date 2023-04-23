package com.example.zavrsnirad.service.impl;

import com.example.zavrsnirad.dto.UpdateProfileDTO;
import com.example.zavrsnirad.dto.UserDTO;
import com.example.zavrsnirad.entity.User;
import com.example.zavrsnirad.entity.UserProfile;
import com.example.zavrsnirad.mapper.UserDtoMapper;
import com.example.zavrsnirad.repository.UserProfileRepository;
import com.example.zavrsnirad.repository.UserRepository;
import com.example.zavrsnirad.service.TokenService;
import com.example.zavrsnirad.service.UserProfileService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

// Ova klasa predstavlja servis korisnickog profila koji se koristi za pozivanje metoda iz repozitorija te za logiku aplikacije
@Service
public class UserProfileServiceImpl implements UserProfileService {
    // Injektiranje repozitorija
    private final UserProfileRepository userProfileRepository;
    private final UserRepository userRepository;
    private final TokenService tokenService;
    private final UserDtoMapper userDtoMapper;

    public UserProfileServiceImpl(UserProfileRepository userProfileRepository, UserRepository userRepository, TokenService tokenService, UserDtoMapper userDtoMapper) {
        this.userProfileRepository = userProfileRepository;
        this.userRepository = userRepository;
        this.tokenService = tokenService;
        this.userDtoMapper = userDtoMapper;
    }

    @Override
    public ResponseEntity<UserDTO> getSelf(String authorization) {
        String username = tokenService.getUsernameFromToken(authorization); // dohvaćanje username-a iz tokena
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found")); // dohvaćanje korisnika iz baze podataka prema username-u

        return ResponseEntity.ok(userDtoMapper.apply(user));
    }

    @Override
    public ResponseEntity<String> updateSelfProfile(String authorization, UpdateProfileDTO data) {
        String username = tokenService.getUsernameFromToken(authorization); // dohvaćanje username-a iz tokena
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found")); // dohvaćanje korisnika iz baze podataka prema username-u
        UserProfile userProfile = user.getUserProfile(); // dohvaćanje korisnickog profila
        boolean updated = false;

        if(data.first_name() != null && !data.first_name().isEmpty() && !data.first_name().isBlank() && !data.first_name().equals(userProfile.getFirstName())) {
            userProfile.setFirstName(data.first_name());
            updated = true;
        }
        if(data.last_name() != null && !data.last_name().isEmpty() && !data.last_name().isBlank() && !data.last_name().equals(userProfile.getLastName())) {
            userProfile.setLastName(data.last_name());
            updated = true;
        }
        if(data.email() != null && !data.email().isEmpty() && !data.email().isBlank() && !data.email().equals(userProfile.getEmail())) {
            userProfile.setEmail(data.email());
            updated = true;
        }
        if(data.phone_number() != null && !data.phone_number().isEmpty() && !data.phone_number().isBlank() && !data.phone_number().equals(userProfile.getPhoneNumber())) {
            userProfile.setPhoneNumber(data.phone_number());
            updated = true;
        }
        if(data.address() != null && !data.address().isEmpty() && !data.address().isBlank() && !data.address().equals(userProfile.getAddress())) {
            userProfile.setAddress(data.address());
            updated = true;
        }
        if(data.city() != null && !data.city().isEmpty() && !data.city().isBlank() && !data.city().equals(userProfile.getCity())) {
            userProfile.setCity(data.city());
            updated = true;
        }
        if(data.zip_code() != null && !data.zip_code().isEmpty() && !data.zip_code().isBlank() && !data.zip_code().equals(userProfile.getZipCode())) {
            userProfile.setZipCode(data.zip_code());
            updated = true;
        }
        if(data.country() != null && !data.country().isEmpty() && !data.country().isBlank() && !data.country().equals(userProfile.getCountry())) {
            userProfile.setCountry(data.country());
            updated = true;
        }
        if(data.about_me() != null && !data.about_me().isEmpty() && !data.about_me().isBlank() && !data.about_me().equals(userProfile.getAboutMe())) {
            userProfile.setAboutMe(data.about_me());
            updated = true;
        }

        if(!updated) {
            return new ResponseEntity<>("No changes", HttpStatus.NOT_MODIFIED);
        }

        userProfileRepository.save(userProfile);

        return new ResponseEntity<>("Updated profile", HttpStatus.ACCEPTED);
    }
}
