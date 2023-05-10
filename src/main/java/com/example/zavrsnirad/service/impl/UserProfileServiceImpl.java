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

        if(data.firstName() != null && !data.firstName().isEmpty() && !data.firstName().isBlank() && !data.firstName().equals(userProfile.getFirstName())) {
            userProfile.setFirstName(data.firstName());
            updated = true;
        }
        if(data.lastName() != null && !data.lastName().isEmpty() && !data.lastName().isBlank() && !data.lastName().equals(userProfile.getLastName())) {
            userProfile.setLastName(data.lastName());
            updated = true;
        }
        if(data.email() != null && !data.email().isEmpty() && !data.email().isBlank() && !data.email().equals(userProfile.getEmail())) {
            userProfile.setEmail(data.email());
            updated = true;
        }
        if(data.phoneNumber() != null && !data.phoneNumber().isEmpty() && !data.phoneNumber().isBlank() && !data.phoneNumber().equals(userProfile.getPhoneNumber())) {
            userProfile.setPhoneNumber(data.phoneNumber());
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
        if(data.zipCode() != null && !data.zipCode().isEmpty() && !data.zipCode().isBlank() && !data.zipCode().equals(userProfile.getZipCode())) {
            userProfile.setZipCode(data.zipCode());
            updated = true;
        }
        if(data.country() != null && !data.country().isEmpty() && !data.country().isBlank() && !data.country().equals(userProfile.getCountry())) {
            userProfile.setCountry(data.country());
            updated = true;
        }
        if(data.aboutMe() != null && !data.aboutMe().isEmpty() && !data.aboutMe().isBlank() && !data.aboutMe().equals(userProfile.getAboutMe())) {
            userProfile.setAboutMe(data.aboutMe());
            updated = true;
        }

        if(!updated) {
            return new ResponseEntity<>("No changes", HttpStatus.NOT_MODIFIED);
        }

        userProfileRepository.save(userProfile);

        return new ResponseEntity<>("Updated profile", HttpStatus.ACCEPTED);
    }
}
