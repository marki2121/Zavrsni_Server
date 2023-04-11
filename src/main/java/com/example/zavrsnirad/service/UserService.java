package com.example.zavrsnirad.service;

import com.example.zavrsnirad.dto.SignupDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

public interface UserService {
    ResponseEntity<String> login(Authentication authentication);

    ResponseEntity<String> signup(SignupDTO data);
}
