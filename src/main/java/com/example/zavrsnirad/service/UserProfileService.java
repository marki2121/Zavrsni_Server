package com.example.zavrsnirad.service;

import com.example.zavrsnirad.dto.request.UpdateProfileDTO;
import com.example.zavrsnirad.dto.request.UserDTO;
import org.springframework.http.ResponseEntity;

public interface UserProfileService {
    ResponseEntity<UserDTO> getSelf(String authorization);

    ResponseEntity<String> updateSelfProfile(String authorization, UpdateProfileDTO data);

}
