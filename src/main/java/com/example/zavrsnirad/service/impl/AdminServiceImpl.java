package com.example.zavrsnirad.service.impl;

import com.example.zavrsnirad.repository.UserRepository;
import com.example.zavrsnirad.service.AdminService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {
    private final UserRepository userRepository;

    public AdminServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //TODO: implement all admin methods like get all users and their tests, subjects, etc.
    //TODO: and all other methods that are not implemented yet
    @Override
    public ResponseEntity<Object> getAllUsers(String authorization) {

        return null;
    }
}
