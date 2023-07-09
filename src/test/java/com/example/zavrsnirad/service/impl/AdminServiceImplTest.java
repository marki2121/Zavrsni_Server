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
    @DisplayName("Test getAllUsers and fail no username found in token")
    void getAllUsersFailNoUsernameFoundInToken() {
        //when
        when(tokenService.getUsernameFromToken(Mockito.<String>any())).thenReturn(null);

        //then
        ResponseEntity<Object> responseEntity = adminServiceImpl.getAllUsers("admin");
        Assertions.assertEquals(400, responseEntity.getStatusCodeValue());
        Assertions.assertEquals("User not found", responseEntity.getBody());

        //verify
        Mockito.verify(tokenService, Mockito.times(1)).getUsernameFromToken(Mockito.<String>any());
    }

    @Test
    @DisplayName("Test getAllUsers and fail user is teacher not admin")
    void getAllUsersFailUserIsTeacherNotAdmin() {
        //given
        User user = new User(1l, "teacher", "teacher", Role.TEACHER, true, null);

        //when
        when(tokenService.getUsernameFromToken(Mockito.<String>any())).thenReturn("teacher");
        when(userRepository.findByUsername(Mockito.<String>any())).thenReturn(java.util.Optional.of(user));

        //then
        ResponseEntity<Object> responseEntity = adminServiceImpl.getAllUsers("teacher");
        Assertions.assertEquals(400, responseEntity.getStatusCodeValue());
        Assertions.assertEquals("User is not admin", responseEntity.getBody());

        //verify
        Mockito.verify(tokenService, Mockito.times(1)).getUsernameFromToken(Mockito.<String>any());
        Mockito.verify(userRepository, Mockito.times(1)).findByUsername(Mockito.<String>any());
    }

    @Test
    @DisplayName("Test getAllUsers and fail user is student not admin")
    void getAllUsersFailUserIsStudentNotAdmin() {
        //given
        User user = new User(1l, "student", "student", Role.STUDENT, true, null);

        //when
        when(tokenService.getUsernameFromToken(Mockito.<String>any())).thenReturn("student");
        when(userRepository.findByUsername(Mockito.<String>any())).thenReturn(java.util.Optional.of(user));

        //then
        ResponseEntity<Object> responseEntity = adminServiceImpl.getAllUsers("student");
        Assertions.assertEquals(400, responseEntity.getStatusCodeValue());
        Assertions.assertEquals("User is not admin", responseEntity.getBody());

        //verify
        Mockito.verify(tokenService, Mockito.times(1)).getUsernameFromToken(Mockito.<String>any());
        Mockito.verify(userRepository, Mockito.times(1)).findByUsername(Mockito.<String>any());
    }

    @Test
    @DisplayName("Test getAllUsers and fail no users found")
    void getAllUsersFailNoUsersFound() {
        //given
        User user = new User(1l, "admin", "admin", Role.ADMIN, true, null);

        //when
        when(tokenService.getUsernameFromToken(Mockito.<String>any())).thenReturn("admin");
        when(userRepository.findByUsername(Mockito.<String>any())).thenReturn(java.util.Optional.of(user));
        when(userRepository.findAll()).thenReturn(List.of());

        //then
        ResponseEntity<Object> responseEntity = adminServiceImpl.getAllUsers("admin");
        Assertions.assertEquals(400, responseEntity.getStatusCodeValue());
        Assertions.assertEquals("No users found", responseEntity.getBody());

        //verify
        Mockito.verify(tokenService, Mockito.times(1)).getUsernameFromToken(Mockito.<String>any());
        Mockito.verify(userRepository, Mockito.times(1)).findByUsername(Mockito.<String>any());
        Mockito.verify(userRepository, Mockito.times(1)).findAll();
    }

    @Test
    @DisplayName("Test getAllUsers successfully")
    void getUserById() {
        //given
        User user = new User(1l, "admin", "admin", Role.ADMIN, true, null);
        User testUser = new User(2L, "admin", "admin", Role.ADMIN, true, null);
        UserResponseDTO testUserDTO = new UserResponseDTO(2L, "admin", "admin", "adminovic", "asdf", "asdf", "asdf", "asdf", "asdf", "asdf", "asdf", "asdf");

        //when
        when(tokenService.getUsernameFromToken(Mockito.<String>any())).thenReturn("admin");
        when(userRepository.findByUsername(Mockito.<String>any())).thenReturn(java.util.Optional.of(user));
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(java.util.Optional.of(testUser));
        when(userResponseDtoMapper.apply(Mockito.<User>any())).thenReturn(testUserDTO);

        //then
        ResponseEntity<Object> responseEntity = adminServiceImpl.getUserById("admin", 2L);
        Assertions.assertEquals(200, responseEntity.getStatusCodeValue());
        Assertions.assertEquals(userResponseDtoMapper.apply(testUser), responseEntity.getBody());

        //verify
        Mockito.verify(tokenService, Mockito.times(1)).getUsernameFromToken(Mockito.<String>any());
        Mockito.verify(userRepository, Mockito.times(1)).findByUsername(Mockito.<String>any());
        Mockito.verify(userRepository, Mockito.times(1)).findById(Mockito.<Long>any());
        Mockito.verify(userResponseDtoMapper, Mockito.times(2)).apply(Mockito.<User>any());
    }

    @Test
    @DisplayName("Test getUserById and fail no username found in token")
    void getUserByIdFailNoUsernameFoundInToken() {
        //when
        when(tokenService.getUsernameFromToken(Mockito.<String>any())).thenReturn(null);

        //then
        ResponseEntity<Object> responseEntity = adminServiceImpl.getUserById("admin", 2L);
        Assertions.assertEquals(400, responseEntity.getStatusCodeValue());
        Assertions.assertEquals("User not found", responseEntity.getBody());

        //verify
        Mockito.verify(tokenService, Mockito.times(1)).getUsernameFromToken(Mockito.<String>any());
    }

    @Test
    @DisplayName("Test getUserById and fail user is teacher not admin")
    void getUserByIdFailUserIsTeacherNotAdmin() {
        //given
        User user = new User(1l, "teacher", "teacher", Role.TEACHER, true, null);

        //when
        when(tokenService.getUsernameFromToken(Mockito.<String>any())).thenReturn("teacher");
        when(userRepository.findByUsername(Mockito.<String>any())).thenReturn(java.util.Optional.of(user));

        //then
        ResponseEntity<Object> responseEntity = adminServiceImpl.getUserById("teacher", 2L);
        Assertions.assertEquals(400, responseEntity.getStatusCodeValue());
        Assertions.assertEquals("User is not admin", responseEntity.getBody());

        //verify
        Mockito.verify(tokenService, Mockito.times(1)).getUsernameFromToken(Mockito.<String>any());
        Mockito.verify(userRepository, Mockito.times(1)).findByUsername(Mockito.<String>any());
    }

    @Test
    @DisplayName("Test getUserById and fail user is student not admin")
    void getUserByIdFailUserIsStudentNotAdmin() {
        //given
        User user = new User(1l, "student", "student", Role.STUDENT, true, null);

        //when
        when(tokenService.getUsernameFromToken(Mockito.<String>any())).thenReturn("student");
        when(userRepository.findByUsername(Mockito.<String>any())).thenReturn(java.util.Optional.of(user));

        //then
        ResponseEntity<Object> responseEntity = adminServiceImpl.getUserById("student", 2L);
        Assertions.assertEquals(400, responseEntity.getStatusCodeValue());
        Assertions.assertEquals("User is not admin", responseEntity.getBody());

        //verify
        Mockito.verify(tokenService, Mockito.times(1)).getUsernameFromToken(Mockito.<String>any());
        Mockito.verify(userRepository, Mockito.times(1)).findByUsername(Mockito.<String>any());
    }

    @Test
    @DisplayName("Test getUserById and fail user is not found")
    void getUserByIdFailUserIsNotFound() {
        //given
        User user = new User(1l, "admin", "admin", Role.ADMIN, true, null);

        //when
        when(tokenService.getUsernameFromToken(Mockito.<String>any())).thenReturn("admin");
        when(userRepository.findByUsername(Mockito.<String>any())).thenReturn(java.util.Optional.of(user));
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(java.util.Optional.empty());

        //then
        ResponseEntity<Object> responseEntity = adminServiceImpl.getUserById("admin", 2L);
        Assertions.assertEquals(400, responseEntity.getStatusCodeValue());
        Assertions.assertEquals("User not found", responseEntity.getBody());

        //verify
        Mockito.verify(tokenService, Mockito.times(1)).getUsernameFromToken(Mockito.<String>any());
        Mockito.verify(userRepository, Mockito.times(1)).findByUsername(Mockito.<String>any());
        Mockito.verify(userRepository, Mockito.times(1)).findById(Mockito.<Long>any());
    }

    @Test
    @DisplayName("Test deleteUserById successfully")
    void deleteUserById() {
        //given
        User user = new User(1l, "admin", "admin", Role.ADMIN, true, null);
        User testUser = new User(2L, "admin2", "admin", Role.ADMIN, true, null);
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