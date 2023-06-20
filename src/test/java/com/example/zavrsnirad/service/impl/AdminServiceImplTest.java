package com.example.zavrsnirad.service.impl;

import com.example.zavrsnirad.appenum.Role;
import com.example.zavrsnirad.dto.UserResponseDTO;
import com.example.zavrsnirad.entity.User;
import com.example.zavrsnirad.mapper.UserResponseDtoMapper;
import com.example.zavrsnirad.repository.UserRepository;
import com.example.zavrsnirad.service.TokenService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {AdminServiceImpl.class})
@ExtendWith(SpringExtension.class)
class AdminServiceImplTest {

    @MockBean
    private UserRepository userRepository;
    @MockBean
    private TokenService tokenService;
    @MockBean
    private UserResponseDtoMapper userResponseDtoMapper;
    @MockBean
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private AdminServiceImpl adminServiceImpl;


    @Test
    @DisplayName("Test getAllUsers and succeed")
    void getAllUsers() {
        //given
        User user = new User(1l, "admin", "admin", Role.ADMIN, true, null);
        UserResponseDTO userResponseDTO = new UserResponseDTO(1L, "admin", "admin", "adminovic", "admin@gmail.com", "admin", "admin", "admin", "admin", "admin", "admin", "admin");

        //when
        when(tokenService.getUsernameFromToken(Mockito.<String>any())).thenReturn("admin");
        when(userRepository.findByUsername(Mockito.<String>any())).thenReturn(java.util.Optional.of(user));
        when(userRepository.findAll()).thenReturn(List.of(user));
        when(userResponseDtoMapper.apply(Mockito.<User>any())).thenReturn(userResponseDTO);

        //then
        ResponseEntity<Object> responseEntity = adminServiceImpl.getAllUsers("admin");
        Assertions.assertEquals(200, responseEntity.getStatusCodeValue());
        Assertions.assertEquals(1, ((List<UserResponseDTO>) responseEntity.getBody()).size());
        Assertions.assertEquals(userResponseDTO, ((List<UserResponseDTO>) responseEntity.getBody()).get(0));

        //verify
        Mockito.verify(tokenService, Mockito.times(1)).getUsernameFromToken(Mockito.<String>any());
        Mockito.verify(userRepository, Mockito.times(1)).findByUsername(Mockito.<String>any());
        Mockito.verify(userRepository, Mockito.times(1)).findAll();
        Mockito.verify(userResponseDtoMapper, Mockito.times(1)).apply(Mockito.<User>any());
    }

    @Test
    void getUserById() {
    }

    @Test
    void deleteUserById() {
    }

    @Test
    void disableUserById() {
    }

    @Test
    void enableUserById() {
    }

    @Test
    void promoteUserById() {
    }

    @Test
    void demoteUserById() {
    }

    @Test
    void changePassword() {
    }
}