package com.example.zavrsnirad.controller;

import com.example.zavrsnirad.dto.request.UpdateProfileDTO;
import com.example.zavrsnirad.dto.request.UserDTO;
import com.example.zavrsnirad.service.UserGetService;
import com.example.zavrsnirad.service.UserProfileService;
import com.example.zavrsnirad.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;
    private final UserGetService userGetService;
    private final UserProfileService userProfileService;

    public UserController(UserService userService, UserGetService userGetService, UserProfileService userProfileService) {
        this.userService = userService;
        this.userGetService = userGetService;
        this.userProfileService = userProfileService;
    }

    @GetMapping("/profile")
    public ResponseEntity<UserDTO> getSelf(@RequestHeader String Authorization) {
        return ResponseEntity.ok(userProfileService.getSelf(Authorization));
    }

    @PutMapping("/profile/update")
    public ResponseEntity<String> updateSelfProfile(@RequestHeader String Authorization, @RequestBody UpdateProfileDTO data){
        return ResponseEntity.ok(userProfileService.updateSelfProfile(Authorization, data));
    }

    @GetMapping("/{username}")
    public ResponseEntity<List<UserDTO>> getUsersByUsername(@PathVariable String username) {
        return ResponseEntity.ok(userGetService.getUsersByUsername(username));
    }
}
