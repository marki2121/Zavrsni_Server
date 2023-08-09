package com.example.zavrsnirad.service;

import com.example.zavrsnirad.dto.request.UpdateProfileDTO;
import com.example.zavrsnirad.dto.request.UserDTO;
import com.example.zavrsnirad.entity.CostumeErrorException;
import com.example.zavrsnirad.entity.UserProfile;

public interface UserProfileService {
    UserDTO getSelf(String authorization) throws CostumeErrorException;

    String updateSelfProfile(String authorization, UpdateProfileDTO data) throws CostumeErrorException;

    UserProfile saveProfile(UserProfile profile);
}
