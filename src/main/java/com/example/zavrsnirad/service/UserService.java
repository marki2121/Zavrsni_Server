package com.example.zavrsnirad.service;

import com.example.zavrsnirad.dto.request.SignupDTO;
import com.example.zavrsnirad.dto.request.UpdatePasswordDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

public interface UserService {
    String login(Authentication authentication);

    ResponseEntity<String> signup(SignupDTO data);

    ResponseEntity<String> updatePassword(String authorization, UpdatePasswordDTO data);
}
