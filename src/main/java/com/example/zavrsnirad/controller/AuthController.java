package com.example.zavrsnirad.controller;

import com.example.zavrsnirad.config.CostumeErrorException;
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

    @GetMapping("/login")
    public ResponseEntity<String> login(Authentication authentication) {
        return ResponseEntity.ok(userService.login(authentication));
    }

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@Validated @RequestBody SignupDTO data) throws CostumeErrorException {
        return ResponseEntity.ok(userService.signup(data));
    }

}
