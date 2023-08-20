package com.example.zavrsnirad.service.impl;

import com.example.zavrsnirad.appenum.Role;
import com.example.zavrsnirad.dto.request.SignupDTO;
import com.example.zavrsnirad.entity.CostumeErrorException;
import com.example.zavrsnirad.entity.User;
import com.example.zavrsnirad.entity.UserProfile;
import com.example.zavrsnirad.mapper.SignupDtoMapper;
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
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserRepository userRepository;
    private final UserGetService userGetService;
    private final UserProfileService userProfileService;
    private final TokenService tokenService;
    private final SignupDtoMapper signupDtoMapper;

    public UserServiceImpl(UserRepository userRepository, UserGetService userGetService, SignupDtoMapper signupDtoMapper, UserProfileService userProfileService, TokenService tokenService) {
        this.userRepository = userRepository;
        this.userGetService = userGetService;
        this.signupDtoMapper = signupDtoMapper;
        this.userProfileService = userProfileService;
        this.tokenService = tokenService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    @Override
    public String login(Authentication authentication) {
        return tokenService.generateToken(authentication);
    }

    @Override
    public String signup(SignupDTO data) throws CostumeErrorException {
        if (data.password().equals(data.passwordConfirmation())) {
            if (userRepository.findByUsername(data.username()).isEmpty()) {
                User user = signupDtoMapper.apply(data);
                UserProfile userProfile = new UserProfile();

                userRepository.save(user);

                userProfile.setUser(user);

                userProfileService.saveProfile(userProfile);

                return "User created";
            }
            throw new CostumeErrorException("Username already exists", HttpStatus.BAD_REQUEST);
        }

        throw new CostumeErrorException("Password's don't match", HttpStatus.BAD_REQUEST);
    }

    @Override
    @Transactional
    public String deleteUserById(Long id) throws CostumeErrorException {
        User user = userGetService.getUserById(id);

        userRepository.deleteConnections(user.getId());
        userRepository.delete(user);

        return "User deleted";
    }

    @Override
    public String disableUserById(Long id) throws CostumeErrorException {
        User user = userGetService.getUserById(id);

        user.setEnabled(false);

        userRepository.save(user);

        return "User disabled";
    }

    @Override
    public String enableUserById(Long id) throws CostumeErrorException {
        User user = userGetService.getUserById(id);

        user.setEnabled(true);

        userRepository.save(user);

        return "User enabled";
    }

    @Override
    public String promoteUserById(Long id) throws CostumeErrorException {
        User user = userGetService.getUserById(id);

        if (user.getRole().equals(Role.ADMIN)) {
            throw new CostumeErrorException("User is already admin", HttpStatus.BAD_REQUEST);
        }

        if (user.getRole().equals(Role.TEACHER)) {
            user.setRole(Role.ADMIN);
            userRepository.save(user);
        }

        if (user.getRole().equals(Role.STUDENT)) {
            user.setRole(Role.TEACHER);
            userRepository.save(user);
        }

        return "User promoted";
    }

    @Override
    public String demoteUserById(Long id) throws CostumeErrorException {
        User user = userGetService.getUserById(id);

        if (checkIfUserIsOnlyAdmin() && user.getRole().equals(Role.ADMIN)) {
            throw new CostumeErrorException("User is only admin", HttpStatus.BAD_REQUEST);
        }

        if (user.getRole().equals(Role.STUDENT)) {
            throw new CostumeErrorException("User is already student", HttpStatus.BAD_REQUEST);
        }

        if (user.getRole().equals(Role.TEACHER)) {
            user.setRole(Role.STUDENT);
            userRepository.save(user);
        }

        if (user.getRole().equals(Role.ADMIN)) {
            user.setRole(Role.TEACHER);
            userRepository.save(user);
        }

        return "User demoted";
    }

    public boolean checkIfUserIsOnlyAdmin() {
        return userRepository.findAllByRole(Role.ADMIN).size() == 1;
    }


}

