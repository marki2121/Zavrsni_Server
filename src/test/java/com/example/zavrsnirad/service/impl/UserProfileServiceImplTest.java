package com.example.zavrsnirad.service.impl;

import com.example.zavrsnirad.config.CostumeErrorException;
import com.example.zavrsnirad.mapper.UserDtoMapper;
import com.example.zavrsnirad.repository.UserRepository;
import com.example.zavrsnirad.service.UserGetService;
import com.example.zavrsnirad.service.UserProfileService;
import com.example.zavrsnirad.util.UpdateProfileDTOUtil;
import com.example.zavrsnirad.util.UserDtoUtils;
import com.example.zavrsnirad.util.UserProfileUtil;
import com.example.zavrsnirad.util.UserUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class UserProfileServiceImplTest {

    @Autowired
    private UserProfileService userProfileService;
    @MockBean
    private UserGetService userGetService;
    @MockBean
    private UserDtoMapper userDtoMapper;
    @MockBean
    private UserRepository userRepository;

    @Test
    @DisplayName("getSelf() - success")
    void getSelf() throws CostumeErrorException {
        when(userGetService.getUserFromToken("token")).thenReturn(UserUtil.generate());
        when(userDtoMapper.apply(any())).thenReturn(UserDtoUtils.generate());
        assertEquals(userProfileService.getSelf("token").id(), UserUtil.generate().getId());
    }

    @Test
    @DisplayName("updateSelfProfile() - success")
    void updateSelfProfile() throws CostumeErrorException {
        when(userGetService.getUserFromToken("token")).thenReturn(UserUtil.generate());
        when(userRepository.save(any())).thenReturn(UserProfileUtil.generate());
        assertDoesNotThrow(() -> userProfileService.updateSelfProfile("token", UpdateProfileDTOUtil.generate()));
    }

    @Test
    @DisplayName("saveProfile() - success")
    void saveProfile() {
        when(userRepository.save(any())).thenReturn(UserProfileUtil.generate());
        assertDoesNotThrow(() -> userProfileService.saveProfile(UserProfileUtil.generate()));
    }
}