package com.example.zavrsnirad.service.impl;

import com.example.zavrsnirad.config.CostumeErrorException;
import com.example.zavrsnirad.dto.request.UpdateProfileDTO;
import com.example.zavrsnirad.dto.request.UserDTO;
import com.example.zavrsnirad.entity.User;
import com.example.zavrsnirad.entity.UserProfile;
import com.example.zavrsnirad.mapper.UserDtoMapper;
import com.example.zavrsnirad.repository.UserProfileRepository;
import com.example.zavrsnirad.service.UserGetService;
import com.example.zavrsnirad.service.UserProfileService;
import org.springframework.stereotype.Service;

@Service
public class UserProfileServiceImpl implements UserProfileService {
    private final UserProfileRepository userProfileRepository;
    private final UserGetService userService;
    private final UserDtoMapper userDtoMapper;

    public UserProfileServiceImpl(UserProfileRepository userProfileRepository, UserGetService userService, UserDtoMapper userDtoMapper) {
        this.userProfileRepository = userProfileRepository;
        this.userService = userService;
        this.userDtoMapper = userDtoMapper;
    }

    @Override
    public UserDTO getSelf(String authorization) throws CostumeErrorException {
        return userDtoMapper.apply(userService.getUserFromToken(authorization));
    }

    @Override
    public String updateSelfProfile(String authorization, UpdateProfileDTO data) throws CostumeErrorException {
        User user = userService.getUserFromToken(authorization);
        UserProfile userProfile = user.getUserProfile();

        boolean updated = false;

        if (data.firstName() != null && !data.firstName().isEmpty() && !data.firstName().isBlank() && !data.firstName().equals(userProfile.getFirstName())) {
            userProfile.setFirstName(data.firstName());
            updated = true;
        }
        if (data.lastName() != null && !data.lastName().isEmpty() && !data.lastName().isBlank() && !data.lastName().equals(userProfile.getLastName())) {
            userProfile.setLastName(data.lastName());
            updated = true;
        }
        if (data.email() != null && !data.email().isEmpty() && !data.email().isBlank() && !data.email().equals(userProfile.getEmail())) {
            userProfile.setEmail(data.email());
            updated = true;
        }
        if (data.phoneNumber() != null && !data.phoneNumber().isEmpty() && !data.phoneNumber().isBlank() && !data.phoneNumber().equals(userProfile.getPhoneNumber())) {
            userProfile.setPhoneNumber(data.phoneNumber());
            updated = true;
        }
        if (data.address() != null && !data.address().isEmpty() && !data.address().isBlank() && !data.address().equals(userProfile.getAddress())) {
            userProfile.setAddress(data.address());
            updated = true;
        }
        if (data.city() != null && !data.city().isEmpty() && !data.city().isBlank() && !data.city().equals(userProfile.getCity())) {
            userProfile.setCity(data.city());
            updated = true;
        }
        if (data.zipCode() != null && !data.zipCode().isEmpty() && !data.zipCode().isBlank() && !data.zipCode().equals(userProfile.getZipCode())) {
            userProfile.setZipCode(data.zipCode());
            updated = true;
        }
        if (data.country() != null && !data.country().isEmpty() && !data.country().isBlank() && !data.country().equals(userProfile.getCountry())) {
            userProfile.setCountry(data.country());
            updated = true;
        }
        if (data.aboutMe() != null && !data.aboutMe().isEmpty() && !data.aboutMe().isBlank() && !data.aboutMe().equals(userProfile.getAboutMe())) {
            userProfile.setAboutMe(data.aboutMe());
            updated = true;
        }

        if (!updated) {
            return "No changes";
        }

        userProfileRepository.save(userProfile);

        return "Updated profile";
    }

    @Override
    public UserProfile saveProfile(UserProfile profile) {
        return userProfileRepository.save(profile);
    }


}
