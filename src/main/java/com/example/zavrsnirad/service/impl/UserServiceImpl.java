package com.example.zavrsnirad.service.impl;

import com.example.zavrsnirad.appenum.Role;
import com.example.zavrsnirad.dto.SignupDTO;
import com.example.zavrsnirad.dto.UpdatePasswordDTO;
import com.example.zavrsnirad.dto.UpdateProfileDTO;
import com.example.zavrsnirad.dto.UserDTO;
import com.example.zavrsnirad.entity.User;
import com.example.zavrsnirad.entity.UserProfile;
import com.example.zavrsnirad.mapper.UserDtoMapper;
import com.example.zavrsnirad.repository.UserProfileRepository;
import com.example.zavrsnirad.repository.UserRepository;
import com.example.zavrsnirad.service.TokenService;
import com.example.zavrsnirad.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

// Ova klasa predstavlja servis korisnika koji se koristi za pozivanje metoda iz repozitorija te za logiku aplikacije
@Service
public class UserServiceImpl implements UserService, UserDetailsService {
    // Injektiranje repozitorija
    private final UserRepository userRepository;
    private final UserProfileRepository userProfileRepository;
    private final TokenService tokenService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final UserDtoMapper userDtoMapper;

    public UserServiceImpl(UserRepository userRepository, UserProfileRepository userProfileRepository, TokenService tokenService, BCryptPasswordEncoder passwordEncoder, UserDtoMapper userDtoMapper) {
        this.userRepository = userRepository;
        this.userProfileRepository = userProfileRepository;
        this.tokenService = tokenService;
        this.passwordEncoder = passwordEncoder;
        this.userDtoMapper = userDtoMapper;
    }

    // Metoda koja se koristi za dohvaćanje korisnika iz baze podataka prema njegovom username-u
    // Testirano
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    // Testirano
    @Override
    public ResponseEntity<String> login(Authentication authentication) {
        String token = tokenService.generateToken(authentication); // generiranje tokena
        return ResponseEntity.ok(token);
    }

    // Testirano
    @Override
    public ResponseEntity<String> signup(SignupDTO data) {
        if(data.password().equals(data.passwordConfirmation())) { // provjera da li se lozinke podudaraju
            if(userRepository.findByUsername(data.username()).isEmpty()) { // provjera da li korisnik već postoji
                User user = new User();
                UserProfile userProfile = new UserProfile();

                // postavljanje vrijednosti atributa
                user.setUsername(data.username());
                user.setPassword(passwordEncoder.encode(data.password()));
                user.setRole(Role.STUDENT);
                user.setEnabled(true);

                userRepository.save(user);

                userProfile.setUser(user);

                userProfileRepository.save(userProfile);

                return new ResponseEntity("User created", HttpStatus.CREATED);
            }
            return ResponseEntity.badRequest().body("Username already exists");
        }
        return ResponseEntity.badRequest().body("Passwords don't match");
    }

    // Testirano
    @Override
    public ResponseEntity<UserDTO> getSelf(String authorization) {
        String username = tokenService.getUsernameFromToken(authorization); // dohvaćanje username-a iz tokena
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found")); // dohvaćanje korisnika iz baze podataka prema username-u

        return ResponseEntity.ok(userDtoMapper.apply(user));
    }

    // TODO: Test
    @Override
    public ResponseEntity<String> updateSelfProfile(String authorization, UpdateProfileDTO data) {
        String username = tokenService.getUsernameFromToken(authorization); // dohvaćanje username-a iz tokena
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found")); // dohvaćanje korisnika iz baze podataka prema username-u
        UserProfile userProfile = user.getUserProfile(); // dohvaćanje korisnickog profila

        // TODO: map the dto to user profile and check for empty strings so we can reuse the function
        return null;
    }

    @Override
    public ResponseEntity<String> updatePassword(String authorization, UpdatePasswordDTO data) {
        String username = tokenService.getUsernameFromToken(authorization); // dohvaćanje username-a iz tokena
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found")); // dohvaćanje korisnika iz baze podataka prema username-u

        if(passwordEncoder.matches(data.oldPassword(), user.getPassword())) {
            if(!passwordEncoder.matches(data.newPassword(), user.getPassword())) {
                if (Objects.equals(data.confirmationPassword(), data.newPassword())) {
                    user.setPassword(passwordEncoder.encode(data.newPassword()));

                    userRepository.save(user);

                    return ResponseEntity.ok("Password updated.");
                }
            }
        }
        return new ResponseEntity<>( "Passwords don't match or bad password", HttpStatus.I_AM_A_TEAPOT);
    }
}

