package com.example.zavrsnirad.controller;

import com.example.zavrsnirad.dto.request.SignupDTO;
import com.example.zavrsnirad.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }


    // Endpoint za prijavu postojećeg korisnika
    @GetMapping("/login")
    public ResponseEntity<String> login(Authentication authentication) {
        return ResponseEntity.ok(userService.login(authentication));
    }


    // Endpoint za registraciju novog korisnika
    @PostMapping("/signup")
    public ResponseEntity<String> signup(@Validated @RequestBody SignupDTO data) {
        return userService.signup(data);
    }

}
