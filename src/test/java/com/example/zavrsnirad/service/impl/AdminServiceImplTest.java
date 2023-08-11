package com.example.zavrsnirad.service.impl;

import com.example.zavrsnirad.entity.CostumeErrorException;
import com.example.zavrsnirad.mapper.UserResponseDtoMapper;
import com.example.zavrsnirad.service.AdminService;
import com.example.zavrsnirad.service.TestApplicationService;
import com.example.zavrsnirad.service.UserGetService;
import com.example.zavrsnirad.service.UserService;
import com.example.zavrsnirad.util.UserResponseDtoUtil;
import com.example.zavrsnirad.util.UserUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@SpringBootTest
class AdminServiceImplTest {
    
    @Autowired
    private AdminService adminService;
    @MockBean
    private UserService userService;
    @MockBean
    private UserGetService userGetService;
    @MockBean
    private TestApplicationService testApplicationService;
    @MockBean
    private UserResponseDtoMapper userResponseDtoMapper;

    @BeforeEach
    void setUp() throws CostumeErrorException {
        when(userGetService.getUserFromToken(any())).thenReturn(UserUtil.generateAdmin());
        when(userService.deleteUserById(any())).thenReturn("User deleted");
        when(userService.disableUserById(any())).thenReturn("User disabled");
        when(userService.enableUserById(any())).thenReturn("User enabled");
        when(userService.promoteUserById(any())).thenReturn("User promoted");
        when(userService.demoteUserById(any())).thenReturn("User demoted");
        when(userGetService.getUserById(any())).thenReturn(UserUtil.generate());
        when(userResponseDtoMapper.apply(any())).thenReturn(UserResponseDtoUtil.generateUserResponseDto());
    }

    @Test
    @DisplayName("Test getAllUsers - success")
    void getAllUsers() {
        //when
        when(userGetService.findAllUsers()).thenReturn(UserUtil.generateList());
        when(userResponseDtoMapper.map(any())).thenReturn(UserResponseDtoUtil.generateList());

        //then
        assertDoesNotThrow(() -> adminService.getAllUsers("token"));
    }

    @Test
    @DisplayName("Test getUserById - success")
    void getUserById() throws CostumeErrorException {
        //then
        assertDoesNotThrow(() -> adminService.getUserById("token", 1L));
    }

    @Test
    @DisplayName("Test deleteUserById - success")
    void deleteUserById() {
        //when
        doNothing().when(testApplicationService).deleteAllUserApplications(any());

        //then
        assertDoesNotThrow(() -> adminService.deleteUserById("token", 1L));
    }

    @Test
    @DisplayName("Test disableUserById - success")
    void disableUserById() {
        //then
        assertDoesNotThrow(() -> adminService.disableUserById("token", 1L));
    }

    @Test
    @DisplayName("Test enableUserById - success")
    void enableUserById() {
        //then
        assertDoesNotThrow(() -> adminService.enableUserById("token", 1L));
    }

    @Test
    @DisplayName("Test promoteUserById - success")
    void promoteUserById() {
        //then
        assertDoesNotThrow(() -> adminService.promoteUserById("token", 1L));
    }

    @Test
    @DisplayName("Test demoteUserById - success")
    void demoteUserById() {
        //then
        assertDoesNotThrow(() -> adminService.demoteUserById("token", 1L));
    }
}