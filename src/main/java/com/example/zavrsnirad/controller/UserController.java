package com.example.zavrsnirad.controller;

import com.example.zavrsnirad.config.CostumeErrorException;
import com.example.zavrsnirad.dto.request.UpdateProfileDTO;
import com.example.zavrsnirad.dto.request.UserDTO;
import com.example.zavrsnirad.service.UserGetService;
import com.example.zavrsnirad.service.UserProfileService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/user")
public class UserController {
    private final UserGetService userGetService;
    private final UserProfileService userProfileService;

    public UserController(UserGetService userGetService, UserProfileService userProfileService) {
        this.userGetService = userGetService;
        this.userProfileService = userProfileService;
    }

    @GetMapping("/profile")
    public ResponseEntity<UserDTO> getSelf(@RequestHeader String Authorization) throws CostumeErrorException {
        return ResponseEntity.ok(userProfileService.getSelf(Authorization));
    }

    @PutMapping("/profile/update")
    public ResponseEntity<String> updateSelfProfile(@RequestHeader String Authorization, @RequestBody UpdateProfileDTO data) throws CostumeErrorException {
        return ResponseEntity.ok(userProfileService.updateSelfProfile(Authorization, data));
    }

    @GetMapping("/{username}")
    public ResponseEntity<List<UserDTO>> getUsersByUsername(@PathVariable String username) {
        return ResponseEntity.ok(userGetService.getUsersByUsername(username));
    }
}
