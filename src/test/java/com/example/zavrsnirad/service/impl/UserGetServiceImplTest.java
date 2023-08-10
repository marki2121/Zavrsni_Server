package com.example.zavrsnirad.service.impl;

import com.example.zavrsnirad.entity.CostumeErrorException;
import com.example.zavrsnirad.mapper.UserDtoMapper;
import com.example.zavrsnirad.repository.UserRepository;
import com.example.zavrsnirad.service.TokenService;
import com.example.zavrsnirad.service.UserGetService;
import com.example.zavrsnirad.util.UserDtoUtils;
import com.example.zavrsnirad.util.UserUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class UserGetServiceImplTest {

    @Autowired
    private UserGetService userGetService;
    @MockBean
    private UserRepository userRepository;
    @MockBean
    private TokenService tokenService;
    @MockBean
    private UserDtoMapper userDtoMapper;

    @Test
    @DisplayName("getUserById() - success")
    void getUserById() throws CostumeErrorException {
        //when
        when(userRepository.findById(1L)).thenReturn(Optional.of(UserUtil.generate()));

        //then
        assertEquals(userGetService.getUserById(1L).getId(), UserUtil.generate().getId());
        assertDoesNotThrow(() -> userGetService.getUserById(1L));
    }

    @Test
    @DisplayName("getUserById() - fail - no user with given id")
    void getUserByIdFail() throws CostumeErrorException {
        //when
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        //then
        assertThrows(CostumeErrorException.class, () -> userGetService.getUserById(1L));
    }

    @Test
    @DisplayName("findAllUsers() - success")
    void findAllUsers() {
        //when
        when(userRepository.findAll()).thenReturn(UserUtil.generateList());

        //then
        assertEquals(userGetService.findAllUsers().size(), UserUtil.generateList().size());
    }

    @Test
    @DisplayName("getUserFromToken() - success")
    void getUserFromToken() throws CostumeErrorException {
        //when
        when(userRepository.findByUsername("username")).thenReturn(Optional.of(UserUtil.generate()));
        when(tokenService.getUsernameFromToken("token")).thenReturn("username");

        //then
        assertDoesNotThrow(() -> userGetService.getUserFromToken("token"));
        assertEquals(userGetService.getUserFromToken("token").getId(), UserUtil.generate().getId());
    }

    @Test
    @DisplayName("getUserFromToken() - fail - no user with given username")
    void getUserFromTokenFail() throws CostumeErrorException {
        //when
        when(userRepository.findByUsername("username")).thenReturn(Optional.empty());
        when(tokenService.getUsernameFromToken("token")).thenReturn("username");

        //then
        assertThrows(CostumeErrorException.class, () -> userGetService.getUserFromToken("token"));
    }

    @Test
    @DisplayName("getUsersByUsername() - success")
    void getUsersByUsername() {
        //when
        when(userRepository.findAllByUsernameContaining("username")).thenReturn(UserUtil.generateList());
        when(userDtoMapper.map(any(List.class))).thenReturn(UserDtoUtils.generateList());

        //then
        assertEquals(userGetService.getUsersByUsername("username").size(), UserUtil.generateList().size());
    }
}