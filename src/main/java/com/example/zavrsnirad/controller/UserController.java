package com.example.zavrsnirad.controller;

import com.example.zavrsnirad.dto.request.UpdatePasswordDTO;
import com.example.zavrsnirad.dto.request.UpdateProfileDTO;
import com.example.zavrsnirad.dto.request.UserDTO;
import com.example.zavrsnirad.service.UserProfileService;
import com.example.zavrsnirad.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;
    private final UserProfileService userProfileService;

    public UserController(UserService userService, UserProfileService userProfileService) {
        this.userService = userService;
        this.userProfileService = userProfileService;
    }

    @PostMapping("/password/update")
    public ResponseEntity<String> updatePassword(@RequestHeader String Authorization, @RequestBody UpdatePasswordDTO data) {
        return userService.updatePassword(Authorization, data);
    }

    @GetMapping("/profile")
    public ResponseEntity<UserDTO> getSelf(@RequestHeader String Authorization) {
        return userProfileService.getSelf(Authorization);
    }

    @PutMapping("/profile/update")
    public ResponseEntity<String> updateSelfProfile(@RequestHeader String Authorization, @RequestBody UpdateProfileDTO data){
        return userProfileService.updateSelfProfile(Authorization, data);
    }
}
