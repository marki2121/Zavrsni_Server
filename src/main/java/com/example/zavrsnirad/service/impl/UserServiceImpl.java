package com.example.zavrsnirad.service.impl;

import com.example.zavrsnirad.appenum.Role;
import com.example.zavrsnirad.dto.request.SignupDTO;
import com.example.zavrsnirad.entity.CostumeErrorException;
import com.example.zavrsnirad.entity.User;
import com.example.zavrsnirad.entity.UserProfile;
import com.example.zavrsnirad.mapper.SignupDtoMapper;
import com.example.zavrsnirad.mapper.UserDtoMapper;
import com.example.zavrsnirad.repository.UserRepository;
import com.example.zavrsnirad.service.TokenService;
import com.example.zavrsnirad.service.UserGetService;
import com.example.zavrsnirad.service.UserProfileService;
import com.example.zavrsnirad.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

// Ova klasa predstavlja servis korisnika koji se koristi za pozivanje metoda iz repozitorija te za logiku aplikacije
@Service
public class UserServiceImpl implements UserService, UserDetailsService {
    // Injektiranje repozitorija
    private final UserRepository userRepository;
    private final UserGetService userGetService;
    private final UserProfileService userProfileService;
    private final TokenService tokenService;
    private final SignupDtoMapper signupDtoMapper;
    private final UserDtoMapper userDtoMapper;

    public UserServiceImpl(UserRepository userRepository, UserGetService userGetService, SignupDtoMapper signupDtoMapper, UserProfileService userProfileService, TokenService tokenService, UserDtoMapper userDtoMapper) {
        this.userRepository = userRepository;
        this.userGetService = userGetService;
        this.signupDtoMapper = signupDtoMapper;
        this.userProfileService = userProfileService;
        this.tokenService = tokenService;
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
    public String login(Authentication authentication) {
        return tokenService.generateToken(authentication); // generiranje tokena
    }

    // Testirano
    @Override
    public String signup(SignupDTO data) {
        if(data.password().equals(data.passwordConfirmation())) { // provjera da li se lozinke podudaraju
            if(userRepository.findByUsername(data.username()).isEmpty()) { // provjera da li korisnik već postoji
                User user = signupDtoMapper.apply(data); // mapiranje podataka iz DTO-a u entitet (User)
                UserProfile userProfile = new UserProfile();

                userRepository.save(user);

                userProfile.setUser(user);

                userProfileService.saveProfile(userProfile); // spremanje korisničkog profila (UserProfile

                return "User created";
            }
            throw new CostumeErrorException("Username already exists", HttpStatus.BAD_REQUEST);
        }

        throw new CostumeErrorException("Password's don't match", HttpStatus.BAD_REQUEST);
    }

    @Override
    public String deleteUserById(Long id) {
        User user = userGetService.getUserById(id);;

        userRepository.delete(user);

        return "User deleted";
    }

    @Override
    public String disableUserById(Long id) {
        User user = userGetService.getUserById(id);;

        user.setEnabled(false);

        userRepository.save(user);

        return "User disabled";
    }

    @Override
    public String enableUserById(Long id) {
        User user = userGetService.getUserById(id);;

        user.setEnabled(true);

        userRepository.save(user);

        return "User enabled";
    }

    @Override
    public String promoteUserById(Long id) {
        User user = userGetService.getUserById(id);;

        if(user.getRole().equals(Role.ADMIN)) {
            throw new CostumeErrorException("User is already admin", HttpStatus.BAD_REQUEST);
        }

        if(user.getRole().equals(Role.TEACHER)) {
            user.setRole(Role.ADMIN);
            userRepository.save(user);
        }

        if(user.getRole().equals(Role.TEACHER)) {
            user.setRole(Role.TEACHER);
            userRepository.save(user);
        }

        return "User promoted";
    }

    @Override
    public String demoteUserById(Long id) {
        User user = userGetService.getUserById(id);

        if(checkIfUserIsOnlyAdmin()) {
            throw new CostumeErrorException("User is only admin", HttpStatus.BAD_REQUEST);
        }

        if(user.getRole().equals(Role.STUDENT)) {
            throw new CostumeErrorException("User is already student", HttpStatus.BAD_REQUEST);
        }

        if(user.getRole().equals(Role.TEACHER)) {
            user.setRole(Role.STUDENT);
            userRepository.save(user);
        }

        if(user.getRole().equals(Role.ADMIN)) {
            user.setRole(Role.TEACHER);
            userRepository.save(user);
        }

        return "User demoted";
    }

    public boolean checkIfUserIsOnlyAdmin() {
        return userRepository.findAllByRole(Role.ADMIN).size() == 1;
    }


}

