package com.example.zavrsnirad.controller;

import com.example.zavrsnirad.dto.SignupDTO;
import com.example.zavrsnirad.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public ResponseEntity<String> login(Authentication authentication) {
        return userService.login(authentication);
    }

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody @Validated SignupDTO data) {
        return userService.signup(data);
    }

    @GetMapping("/test")
    public String test() {
        return "test";
    }
}
