package com.example.zavrsnirad.service.impl;

import com.example.zavrsnirad.appenum.Role;
import com.example.zavrsnirad.dto.UserResponseDTO;
import com.example.zavrsnirad.entity.User;
import com.example.zavrsnirad.mapper.UserResponseDtoMapper;
import com.example.zavrsnirad.repository.UserRepository;
import com.example.zavrsnirad.service.AdminService;
import com.example.zavrsnirad.service.TokenService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AdminServiceImpl implements AdminService {
    private final UserRepository userRepository;
    private final TokenService tokenService;
    private final UserResponseDtoMapper userResponseDtoMapper;
    private final BCryptPasswordEncoder passwordEncoder;

    public AdminServiceImpl(UserRepository userRepository, TokenService tokenService, UserResponseDtoMapper userResponseDtoMapper, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.tokenService = tokenService;
        this.userResponseDtoMapper = userResponseDtoMapper;
        this.passwordEncoder = passwordEncoder;
    }

    //TODO: implement all admin methods like get all users and their tests, subjects, etc.
    //TODO: and all other methods that are not implemented yet
    @Override
    public ResponseEntity<Object> getAllUsers(String authorization) {
        String username = tokenService.getUsernameFromToken(authorization);
        Optional<User> user = userRepository.findByUsername(username);

        if(user.isEmpty()) return ResponseEntity.badRequest().body("User not found");
        if(!user.get().getRole().equals(Role.ADMIN)) return ResponseEntity.badRequest().body("User is not admin");

        List<User> users = userRepository.findAll();

        if(users.isEmpty()) return ResponseEntity.badRequest().body("No users found");

        List<UserResponseDTO> responseDTOList = new ArrayList<>();

        for(User u : users){
            responseDTOList.add(userResponseDtoMapper.apply(u));
        }


        return ResponseEntity.ok(responseDTOList);
    }

    @Override
    public ResponseEntity<Object> getUserById(String authorization, Long id) {
        String username = tokenService.getUsernameFromToken(authorization);
        Optional<User> user = userRepository.findByUsername(username);

        if(user.isEmpty()) return ResponseEntity.badRequest().body("User not found");
        if(!user.get().getRole().equals(Role.ADMIN)) return ResponseEntity.badRequest().body("User is not admin");

        Optional<User> userById = userRepository.findById(id);
        if(userById.isEmpty()) return ResponseEntity.badRequest().body("User not found");

        UserResponseDTO responseDTO = userResponseDtoMapper.apply(userById.get());

        return ResponseEntity.ok(responseDTO);
    }

    @Override
    public ResponseEntity<Object> deleteUserById(String authorization, Long id) {
        String username = tokenService.getUsernameFromToken(authorization);
        Optional<User> user = userRepository.findByUsername(username);
        Optional<User> userById = userRepository.findById(id);

        if(user.isEmpty()) return ResponseEntity.badRequest().body("User not found");
        if(!user.get().getRole().equals(Role.ADMIN)) return ResponseEntity.badRequest().body("User is not admin");
        if(userById.isEmpty()) return ResponseEntity.badRequest().body("User not found");

        userRepository.deleteById(id);

        return ResponseEntity.ok("User deleted");
    }

    @Override
    public ResponseEntity<Object> disableUserById(String authorization, Long id) {
        String username = tokenService.getUsernameFromToken(authorization);
        Optional<User> user = userRepository.findByUsername(username);
        Optional<User> userById = userRepository.findById(id);

        if(user.isEmpty()) return ResponseEntity.badRequest().body("User not found");
        if(!user.get().getRole().equals(Role.ADMIN)) return ResponseEntity.badRequest().body("User is not admin");
        if(userById.isEmpty()) return ResponseEntity.badRequest().body("User not found");

        userById.get().setEnabled(false);

        userRepository.save(userById.get());

        return ResponseEntity.ok("User disabled");
    }

    @Override
    public ResponseEntity<Object> enableUserById(String authorization, Long id) {
        String username = tokenService.getUsernameFromToken(authorization);
        Optional<User> user = userRepository.findByUsername(username);
        Optional<User> userById = userRepository.findById(id);

        if(user.isEmpty()) return ResponseEntity.badRequest().body("User not found");
        if(!user.get().getRole().equals(Role.ADMIN)) return ResponseEntity.badRequest().body("User is not admin");
        if(userById.isEmpty()) return ResponseEntity.badRequest().body("User not found");

        userById.get().setEnabled(true);

        userRepository.save(userById.get());

        return ResponseEntity.ok("User enabled");
    }

    @Override
    public ResponseEntity<Object> promoteUserById(String authorization, Long id) {
        String username = tokenService.getUsernameFromToken(authorization);
        Optional<User> user = userRepository.findByUsername(username);
        Optional<User> userById = userRepository.findById(id);

        if(user.isEmpty()) return ResponseEntity.badRequest().body("User not found");
        if(!user.get().getRole().equals(Role.ADMIN)) return ResponseEntity.badRequest().body("User is not admin");
        if(userById.isEmpty()) return ResponseEntity.badRequest().body("User not found");

        if(userById.get().getRole().equals(Role.STUDENT)){
            userById.get().setRole(Role.TEACHER);
            userRepository.save(userById.get());

            return ResponseEntity.ok("User promoted to teacher");
        }
        else if(userById.get().getRole().equals(Role.TEACHER)){
            userById.get().setRole(Role.ADMIN);
            userRepository.save(userById.get());

            return ResponseEntity.ok("User promoted to admin");
        }
        else return ResponseEntity.badRequest().body("User is already admin");
    }

    //TODO: check if user is not the only admin
    //TODO: delete all tests, subjects, etc. that are created by this user
    @Override
    public ResponseEntity<Object> demoteUserById(String authorization, Long id) {
        String username = tokenService.getUsernameFromToken(authorization);
        Optional<User> user = userRepository.findByUsername(username);
        Optional<User> userById = userRepository.findById(id);

        if(user.isEmpty()) return ResponseEntity.badRequest().body("User not found");
        if(!user.get().getRole().equals(Role.ADMIN)) return ResponseEntity.badRequest().body("User is not admin");
        if(userById.isEmpty()) return ResponseEntity.badRequest().body("User not found");

        if(userById.get().getRole().equals(Role.ADMIN)){
            userById.get().setRole(Role.TEACHER);
            userRepository.save(userById.get());

            return ResponseEntity.ok("User demoted to teacher");
        }
        else if(userById.get().getRole().equals(Role.TEACHER)){
            userById.get().setRole(Role.STUDENT);
            userRepository.save(userById.get());

            return ResponseEntity.ok("User demoted to student");
        }
        else return ResponseEntity.badRequest().body("User is already student");
    }

    @Override
    public ResponseEntity<Object> changePassword(String authorization, Long id, String newPassword) {
        String username = tokenService.getUsernameFromToken(authorization);
        Optional<User> user = userRepository.findByUsername(username);
        Optional<User> userById = userRepository.findById(id);

        if(user.isEmpty()) return ResponseEntity.badRequest().body("User not found");
        if(!user.get().getRole().equals(Role.ADMIN)) return ResponseEntity.badRequest().body("User is not admin");
        if(userById.isEmpty()) return ResponseEntity.badRequest().body("User not found");

        userById.get().setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(userById.get());

        return ResponseEntity.ok("Password changed");
    }
}
