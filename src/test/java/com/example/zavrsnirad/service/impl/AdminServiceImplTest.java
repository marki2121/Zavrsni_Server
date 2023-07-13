package com.example.zavrsnirad.service.impl;

import com.example.zavrsnirad.appenum.Role;
import com.example.zavrsnirad.dto.response.UserResponseDTO;
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
        Assertions.assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
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
        Assertions.assertTrue(responseEntity.getStatusCode().is4xxClientError());
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
        Assertions.assertTrue(responseEntity.getStatusCode().is4xxClientError());
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
        Assertions.assertTrue(responseEntity.getStatusCode().is4xxClientError());
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
        Assertions.assertTrue(responseEntity.getStatusCode().is4xxClientError());
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
        Assertions.assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
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
        Assertions.assertTrue(responseEntity.getStatusCode().is4xxClientError());
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
        Assertions.assertTrue(responseEntity.getStatusCode().is4xxClientError());
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
        Assertions.assertTrue(responseEntity.getStatusCode().is4xxClientError());
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
        Assertions.assertTrue(responseEntity.getStatusCode().is4xxClientError());
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

        //when
        when(tokenService.getUsernameFromToken(Mockito.<String>any())).thenReturn("admin");
        when(userRepository.findByUsername(Mockito.<String>any())).thenReturn(java.util.Optional.of(user));
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(java.util.Optional.of(testUser));

        //then
        ResponseEntity<Object> responseEntity = adminServiceImpl.deleteUserById("admin", 2L);
        Assertions.assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
        Assertions.assertEquals("User deleted", responseEntity.getBody());

        //verify
        Mockito.verify(tokenService, Mockito.times(1)).getUsernameFromToken(Mockito.<String>any());
        Mockito.verify(userRepository, Mockito.times(1)).findByUsername(Mockito.<String>any());
        Mockito.verify(userRepository, Mockito.times(1)).findById(Mockito.<Long>any());
        Mockito.verify(userRepository, Mockito.times(1)).delete(Mockito.<User>any());
    }

    @Test
    @DisplayName("Test deleteUserById and fail no username found in token")
    void deleteUserByIdFailNoUsernameFoundInToken() {
        //when
        when(tokenService.getUsernameFromToken(Mockito.<String>any())).thenReturn(null);

        //then
        ResponseEntity<Object> responseEntity = adminServiceImpl.deleteUserById("admin", 2L);
        Assertions.assertTrue(responseEntity.getStatusCode().is4xxClientError());
        Assertions.assertEquals("User not found", responseEntity.getBody());

        //verify
        Mockito.verify(tokenService, Mockito.times(1)).getUsernameFromToken(Mockito.<String>any());
    }

    @Test
    @DisplayName("Test deleteUserById and fail user is teacher not admin")
    void deleteUserByIdFailUserIsTeacherNotAdmin() {
        //given
        User user = new User(1l, "teacher", "teacher", Role.TEACHER, true, null);

        //when
        when(tokenService.getUsernameFromToken(Mockito.<String>any())).thenReturn("teacher");
        when(userRepository.findByUsername(Mockito.<String>any())).thenReturn(java.util.Optional.of(user));

        //then
        ResponseEntity responseEntity = adminServiceImpl.deleteUserById("teacher", 2L);
        Assertions.assertTrue(responseEntity.getStatusCode().is4xxClientError());
        Assertions.assertEquals("User is not admin", responseEntity.getBody());

        //verify
        Mockito.verify(tokenService, Mockito.times(1)).getUsernameFromToken(Mockito.<String>any());
        Mockito.verify(userRepository, Mockito.times(1)).findByUsername(Mockito.<String>any());
    }

    @Test
    @DisplayName("Test deleteUserById and fail user is student not admin")
    void deleteUserByIdFailUserIsStudentNotAdmin() {
        //given
        User user = new User(1l, "student", "student", Role.STUDENT, true, null);

        //when
        when(tokenService.getUsernameFromToken(Mockito.<String>any())).thenReturn("student");
        when(userRepository.findByUsername(Mockito.<String>any())).thenReturn(java.util.Optional.of(user));

        //then
        ResponseEntity responseEntity = adminServiceImpl.deleteUserById("student", 2L);
        Assertions.assertTrue(responseEntity.getStatusCode().is4xxClientError());
        Assertions.assertEquals("User is not admin", responseEntity.getBody());

        //verify
        Mockito.verify(tokenService, Mockito.times(1)).getUsernameFromToken(Mockito.<String>any());
        Mockito.verify(userRepository, Mockito.times(1)).findByUsername(Mockito.<String>any());
    }

    @Test
    @DisplayName("Test deleteUserById and fail user is not found")
    void deleteUserByIdFailUserIsNotFound() {
        //given
        User user = new User(1l, "admin", "admin", Role.ADMIN, true, null);

        //when
        when(tokenService.getUsernameFromToken(Mockito.<String>any())).thenReturn("admin");
        when(userRepository.findByUsername(Mockito.<String>any())).thenReturn(java.util.Optional.of(user));
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(java.util.Optional.empty());

        //then
        ResponseEntity responseEntity = adminServiceImpl.deleteUserById("admin", 2L);
        Assertions.assertTrue(responseEntity.getStatusCode().is4xxClientError());
        Assertions.assertEquals("User not found", responseEntity.getBody());

        //verify
        Mockito.verify(tokenService, Mockito.times(1)).getUsernameFromToken(Mockito.<String>any());
        Mockito.verify(userRepository, Mockito.times(1)).findByUsername(Mockito.<String>any());
        Mockito.verify(userRepository, Mockito.times(1)).findById(Mockito.<Long>any());
    }

    @Test
    @DisplayName("Test disableUserById successfully")
    void disableUserById() {
        //given
        User user = new User(1l, "admin", "admin", Role.ADMIN, true, null);
        User testUser = new User(2L, "admin2", "admin", Role.ADMIN, true, null);

        //when
        when(tokenService.getUsernameFromToken(Mockito.<String>any())).thenReturn("admin");
        when(userRepository.findByUsername(Mockito.<String>any())).thenReturn(java.util.Optional.of(user));
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(java.util.Optional.of(testUser));


        //then
        ResponseEntity responseEntity = adminServiceImpl.disableUserById("admin", 2L);
        Assertions.assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
        Assertions.assertEquals("User disabled", responseEntity.getBody());
        Assertions.assertEquals(testUser.getEnabled(), false);

        //verify
        Mockito.verify(tokenService, Mockito.times(1)).getUsernameFromToken(Mockito.<String>any());
        Mockito.verify(userRepository, Mockito.times(1)).findByUsername(Mockito.<String>any());
        Mockito.verify(userRepository, Mockito.times(1)).findById(Mockito.<Long>any());
        Mockito.verify(userRepository, Mockito.times(1)).save(Mockito.<User>any());
    }

    @Test
    @DisplayName("Test disableUserById and fail no username found in token")
    void disableUserByIdFailNoUsernameFoundInToken() {
        //when
        when(tokenService.getUsernameFromToken(Mockito.<String>any())).thenReturn(null);

        //then
        ResponseEntity responseEntity = adminServiceImpl.disableUserById("admin", 2L);
        Assertions.assertTrue(responseEntity.getStatusCode().is4xxClientError());
        Assertions.assertEquals("User not found", responseEntity.getBody());

        //verify
        Mockito.verify(tokenService, Mockito.times(1)).getUsernameFromToken(Mockito.<String>any());
    }

    @Test
    @DisplayName("Test disableUserById and fail user is teacher not admin")
    void disableUserByIdFailUserIsTeacherNotAdmin() {
        //given
        User user = new User(1l, "teacher", "teacher", Role.TEACHER, true, null);

        //when
        when(tokenService.getUsernameFromToken(Mockito.<String>any())).thenReturn("teacher");
        when(userRepository.findByUsername(Mockito.<String>any())).thenReturn(java.util.Optional.of(user));

        //then
        ResponseEntity responseEntity = adminServiceImpl.disableUserById("teacher", 2L);
        Assertions.assertTrue(responseEntity.getStatusCode().is4xxClientError());
        Assertions.assertEquals("User is not admin", responseEntity.getBody());

        //verify
        Mockito.verify(tokenService, Mockito.times(1)).getUsernameFromToken(Mockito.<String>any());
        Mockito.verify(userRepository, Mockito.times(1)).findByUsername(Mockito.<String>any());
    }

    @Test
    @DisplayName("Test disableUserById and fail user is student not admin")
    void disableUserByIdFailUserIsStudentNotAdmin() {
        //given
        User user = new User(1l, "student", "student", Role.STUDENT, true, null);

        //when
        when(tokenService.getUsernameFromToken(Mockito.<String>any())).thenReturn("student");
        when(userRepository.findByUsername(Mockito.<String>any())).thenReturn(java.util.Optional.of(user));

        //then
        ResponseEntity responseEntity = adminServiceImpl.disableUserById("student", 2L);
        Assertions.assertTrue(responseEntity.getStatusCode().is4xxClientError());
        Assertions.assertEquals("User is not admin", responseEntity.getBody());

        //verify
        Mockito.verify(tokenService, Mockito.times(1)).getUsernameFromToken(Mockito.<String>any());
        Mockito.verify(userRepository, Mockito.times(1)).findByUsername(Mockito.<String>any());
    }

    @Test
    @DisplayName("Test disableUserById and fail user is not found")
    void disableUserByIdFailUserIsNotFound() {
        //given
        User user = new User(1l, "admin", "admin", Role.ADMIN, true, null);

        //when
        when(tokenService.getUsernameFromToken(Mockito.<String>any())).thenReturn("admin");
        when(userRepository.findByUsername(Mockito.<String>any())).thenReturn(java.util.Optional.of(user));
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(java.util.Optional.empty());

        //then
        ResponseEntity responseEntity = adminServiceImpl.disableUserById("admin", 2L);
        Assertions.assertTrue(responseEntity.getStatusCode().is4xxClientError());
        Assertions.assertEquals("User not found", responseEntity.getBody());

        //verify
        Mockito.verify(tokenService, Mockito.times(1)).getUsernameFromToken(Mockito.<String>any());
        Mockito.verify(userRepository, Mockito.times(1)).findByUsername(Mockito.<String>any());
        Mockito.verify(userRepository, Mockito.times(1)).findById(Mockito.<Long>any());
    }

    @Test
    @DisplayName("Test enableUserById successfully")
    void enableUserById() {
        //given
        User user = new User(1l, "admin", "admin", Role.ADMIN, true, null);
        User testUser = new User(2L, "admin2", "admin", Role.ADMIN, false, null);

        //when
        when(tokenService.getUsernameFromToken(Mockito.<String>any())).thenReturn("admin");
        when(userRepository.findByUsername(Mockito.<String>any())).thenReturn(java.util.Optional.of(user));
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(java.util.Optional.of(testUser));

        //then
        ResponseEntity responseEntity = adminServiceImpl.enableUserById("admin", 2L);
        Assertions.assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
        Assertions.assertEquals("User enabled", responseEntity.getBody());

        //verify
        Mockito.verify(tokenService, Mockito.times(1)).getUsernameFromToken(Mockito.<String>any());
        Mockito.verify(userRepository, Mockito.times(1)).findByUsername(Mockito.<String>any());
        Mockito.verify(userRepository, Mockito.times(1)).findById(Mockito.<Long>any());
        Mockito.verify(userRepository, Mockito.times(1)).save(Mockito.<User>any());
    }

    @Test
    @DisplayName("Test enableUserById and fail no username found in token")
    void enableUserByIdFailNoUsernameFoundInToken() {
        //when
        when(tokenService.getUsernameFromToken(Mockito.<String>any())).thenReturn(null);

        //then
        ResponseEntity responseEntity = adminServiceImpl.enableUserById("admin", 2L);
        Assertions.assertTrue(responseEntity.getStatusCode().is4xxClientError());
        Assertions.assertEquals("User not found", responseEntity.getBody());

        //verify
        Mockito.verify(tokenService, Mockito.times(1)).getUsernameFromToken(Mockito.<String>any());
    }

    @Test
    @DisplayName("Test enableUserById and fail user is teacher not admin")
    void enableUserByIdFailUserIsTeacherNotAdmin() {
        //given
        User user = new User(1l, "teacher", "teacher", Role.TEACHER, true, null);

        //when
        when(tokenService.getUsernameFromToken(Mockito.<String>any())).thenReturn("teacher");
        when(userRepository.findByUsername(Mockito.<String>any())).thenReturn(java.util.Optional.of(user));

        //then
        ResponseEntity responseEntity = adminServiceImpl.enableUserById("teacher", 2L);
        Assertions.assertTrue(responseEntity.getStatusCode().is4xxClientError());
        Assertions.assertEquals("User is not admin", responseEntity.getBody());

        //verify
        Mockito.verify(tokenService, Mockito.times(1)).getUsernameFromToken(Mockito.<String>any());
        Mockito.verify(userRepository, Mockito.times(1)).findByUsername(Mockito.<String>any());
    }

    @Test
    @DisplayName("Test enableUserById and fail user is student not admin")
    void enableUserByIdFailUserIsStudentNotAdmin() {
        //given
        User user = new User(1l, "student", "student", Role.STUDENT, true, null);

        //when
        when(tokenService.getUsernameFromToken(Mockito.<String>any())).thenReturn("student");
        when(userRepository.findByUsername(Mockito.<String>any())).thenReturn(java.util.Optional.of(user));

        //then
        ResponseEntity responseEntity = adminServiceImpl.enableUserById("student", 2L);
        Assertions.assertTrue(responseEntity.getStatusCode().is4xxClientError());
        Assertions.assertEquals("User is not admin", responseEntity.getBody());

        //verify
        Mockito.verify(tokenService, Mockito.times(1)).getUsernameFromToken(Mockito.<String>any());
        Mockito.verify(userRepository, Mockito.times(1)).findByUsername(Mockito.<String>any());
    }

    @Test
    @DisplayName("Test enableUserById and fail user is not found")
    void enableUserByIdFailUserIsNotFound() {
        //given
        User user = new User(1l, "admin", "admin", Role.ADMIN, true, null);

        //when
        when(tokenService.getUsernameFromToken(Mockito.<String>any())).thenReturn("admin");
        when(userRepository.findByUsername(Mockito.<String>any())).thenReturn(java.util.Optional.of(user));
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(java.util.Optional.empty());

        //then
        ResponseEntity responseEntity = adminServiceImpl.enableUserById("admin", 2L);
        Assertions.assertTrue(responseEntity.getStatusCode().is4xxClientError());
        Assertions.assertEquals("User not found", responseEntity.getBody());

        //verify
        Mockito.verify(tokenService, Mockito.times(1)).getUsernameFromToken(Mockito.<String>any());
        Mockito.verify(userRepository, Mockito.times(1)).findByUsername(Mockito.<String>any());
        Mockito.verify(userRepository, Mockito.times(1)).findById(Mockito.<Long>any());
    }

    @Test
    @DisplayName("Test enableUserById and fail user is already enabled")
    void enableUserByIdFailUserIsAlreadyEnabled() {
        //given
        User user = new User(1l, "admin", "admin", Role.ADMIN, true, null);
        User testUser = new User(2L, "admin2", "admin", Role.ADMIN, true, null);

        //when
        when(tokenService.getUsernameFromToken(Mockito.<String>any())).thenReturn("admin");
        when(userRepository.findByUsername(Mockito.<String>any())).thenReturn(java.util.Optional.of(user));
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(java.util.Optional.of(testUser));

        //then
        ResponseEntity responseEntity = adminServiceImpl.enableUserById("admin", 2L);
        Assertions.assertTrue(responseEntity.getStatusCode().is4xxClientError());
        Assertions.assertEquals("User is already enabled", responseEntity.getBody());

        //verify
        Mockito.verify(tokenService, Mockito.times(1)).getUsernameFromToken(Mockito.<String>any());
        Mockito.verify(userRepository, Mockito.times(1)).findByUsername(Mockito.<String>any());
        Mockito.verify(userRepository, Mockito.times(1)).findById(Mockito.<Long>any());
    }

    @Test
    @DisplayName("Test promoteUserById and success")
    void promoteUserById() {
        //given
        User user = new User(1l, "admin", "admin", Role.ADMIN, true, null);
        User testUser = new User(2L, "admin2", "admin", Role.STUDENT, false, null);

        //when
        when(tokenService.getUsernameFromToken(Mockito.<String>any())).thenReturn("admin");
        when(userRepository.findByUsername(Mockito.<String>any())).thenReturn(java.util.Optional.of(user));
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(java.util.Optional.of(testUser));

        //then
        ResponseEntity responseEntity = adminServiceImpl.promoteUserById("admin", 2L);
        Assertions.assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
        Assertions.assertEquals("User promoted to teacher", responseEntity.getBody());

        //verify
        Mockito.verify(tokenService, Mockito.times(1)).getUsernameFromToken(Mockito.<String>any());
        Mockito.verify(userRepository, Mockito.times(1)).findByUsername(Mockito.<String>any());
        Mockito.verify(userRepository, Mockito.times(1)).findById(Mockito.<Long>any());
        Mockito.verify(userRepository, Mockito.times(1)).save(Mockito.<User>any());
    }

    @Test
    @DisplayName("Test promoteUserById and success when user is teacher")
    void promoteUserByIdSuccessWhenUserIsTeacher() {
        //given
        User user = new User(1l, "admin", "admin", Role.ADMIN, true, null);
        User testUser = new User(2L, "admin2", "admin", Role.TEACHER, false, null);

        //when
        when(tokenService.getUsernameFromToken(Mockito.<String>any())).thenReturn("admin");
        when(userRepository.findByUsername(Mockito.<String>any())).thenReturn(java.util.Optional.of(user));
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(java.util.Optional.of(testUser));

        //then
        ResponseEntity responseEntity = adminServiceImpl.promoteUserById("admin", 2L);
        Assertions.assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
        Assertions.assertEquals("User promoted to admin", responseEntity.getBody());

        //verify
        Mockito.verify(tokenService, Mockito.times(1)).getUsernameFromToken(Mockito.<String>any());
        Mockito.verify(userRepository, Mockito.times(1)).findByUsername(Mockito.<String>any());
        Mockito.verify(userRepository, Mockito.times(1)).findById(Mockito.<Long>any());
    }

    @Test
    @DisplayName("Test promoteUserById and fail because user is admin")
    void promoteUserByIdFailBecauseUserIsAdmin() {
        //given
        User user = new User(1l, "admin", "admin", Role.ADMIN, true, null);
        User testUser = new User(2L, "admin2", "admin", Role.ADMIN, false, null);

        //when
        when(tokenService.getUsernameFromToken(Mockito.<String>any())).thenReturn("admin");
        when(userRepository.findByUsername(Mockito.<String>any())).thenReturn(java.util.Optional.of(user));
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(java.util.Optional.of(testUser));

        //then
        ResponseEntity responseEntity = adminServiceImpl.promoteUserById("admin", 2L);
        Assertions.assertTrue(responseEntity.getStatusCode().is4xxClientError());
        Assertions.assertEquals("User is already admin", responseEntity.getBody());

        //verify
        Mockito.verify(tokenService, Mockito.times(1)).getUsernameFromToken(Mockito.<String>any());
        Mockito.verify(userRepository, Mockito.times(1)).findByUsername(Mockito.<String>any());
        Mockito.verify(userRepository, Mockito.times(1)).findById(Mockito.<Long>any());
    }

    @Test
    @DisplayName("Test promoteUserById and fail user is teacher not admin")
    void promoteUserByIdFailUserIsTeacherNotAdmin() {
        //given
        User user = new User(1l, "teacher", "teacher", Role.TEACHER, true, null);

        //when
        when(tokenService.getUsernameFromToken(Mockito.<String>any())).thenReturn("teacher");
        when(userRepository.findByUsername(Mockito.<String>any())).thenReturn(java.util.Optional.of(user));

        //then
        ResponseEntity responseEntity = adminServiceImpl.promoteUserById("teacher", 2L);
        Assertions.assertTrue(responseEntity.getStatusCode().is4xxClientError());
        Assertions.assertEquals("User is not admin", responseEntity.getBody());

        //verify
        Mockito.verify(tokenService, Mockito.times(1)).getUsernameFromToken(Mockito.<String>any());
        Mockito.verify(userRepository, Mockito.times(1)).findByUsername(Mockito.<String>any());
    }

    @Test
    @DisplayName("Test promoteUserById and fail user is student not admin")
    void promoteUserByIdFailUserIsStudentNotAdmin() {
        //given
        User user = new User(1l, "student", "student", Role.STUDENT, true, null);

        //when
        when(tokenService.getUsernameFromToken(Mockito.<String>any())).thenReturn("student");
        when(userRepository.findByUsername(Mockito.<String>any())).thenReturn(java.util.Optional.of(user));

        //then
        ResponseEntity responseEntity = adminServiceImpl.promoteUserById("student", 2L);
        Assertions.assertTrue(responseEntity.getStatusCode().is4xxClientError());
        Assertions.assertEquals("User is not admin", responseEntity.getBody());

        //verify
        Mockito.verify(tokenService, Mockito.times(1)).getUsernameFromToken(Mockito.<String>any());
        Mockito.verify(userRepository, Mockito.times(1)).findByUsername(Mockito.<String>any());
    }

    @Test
    @DisplayName("Test promoteUserById and fail user is not found")
    void promoteUserByIdFailUserIsNotFound() {
        //given
        User user = new User(1l, "admin", "admin", Role.ADMIN, true, null);

        //when
        when(tokenService.getUsernameFromToken(Mockito.<String>any())).thenReturn("admin");
        when(userRepository.findByUsername(Mockito.<String>any())).thenReturn(java.util.Optional.of(user));
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(java.util.Optional.empty());

        //then
        ResponseEntity responseEntity = adminServiceImpl.promoteUserById("admin", 2L);
        Assertions.assertTrue(responseEntity.getStatusCode().is4xxClientError());
        Assertions.assertEquals("User not found", responseEntity.getBody());

        //verify
        Mockito.verify(tokenService, Mockito.times(1)).getUsernameFromToken(Mockito.<String>any());
        Mockito.verify(userRepository, Mockito.times(1)).findByUsername(Mockito.<String>any());
        Mockito.verify(userRepository, Mockito.times(1)).findById(Mockito.<Long>any());
    }

    @Test
    @DisplayName("Test demoteUserById and success teacher to student")
    void demoteUserById() {
        //given
        User user = new User(1l, "admin", "admin", Role.ADMIN, true, null);
        User testUser = new User(2L, "admin2", "admin", Role.TEACHER, false, null);

        //when
        when(tokenService.getUsernameFromToken(Mockito.<String>any())).thenReturn("admin");
        when(userRepository.findByUsername(Mockito.<String>any())).thenReturn(java.util.Optional.of(user));
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(java.util.Optional.of(testUser));

        //then
        ResponseEntity responseEntity = adminServiceImpl.demoteUserById("admin", 2L);
        Assertions.assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
        Assertions.assertEquals("User demoted to student", responseEntity.getBody());

        //verify
        Mockito.verify(tokenService, Mockito.times(1)).getUsernameFromToken(Mockito.<String>any());
        Mockito.verify(userRepository, Mockito.times(1)).findByUsername(Mockito.<String>any());
        Mockito.verify(userRepository, Mockito.times(1)).findById(Mockito.<Long>any());
        Mockito.verify(userRepository, Mockito.times(1)).save(Mockito.<User>any());
    }

    @Test
    @DisplayName("Test demoteUserById and success admin to teacher")
    void demoteUserByIdSuccessAdminToTeacher() {
        //given
        User user = new User(1l, "admin", "admin", Role.ADMIN, true, null);
        User testUser = new User(2L, "admin2", "admin", Role.ADMIN, false, null);

        //when
        when(tokenService.getUsernameFromToken(Mockito.<String>any())).thenReturn("admin");
        when(userRepository.findByUsername(Mockito.<String>any())).thenReturn(java.util.Optional.of(user));
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(java.util.Optional.of(testUser));

        //then
        ResponseEntity responseEntity = adminServiceImpl.demoteUserById("admin", 2L);
        Assertions.assertTrue(responseEntity.getStatusCode().is2xxSuccessful());
        Assertions.assertEquals("User demoted to teacher", responseEntity.getBody());

        //verify
        Mockito.verify(tokenService, Mockito.times(1)).getUsernameFromToken(Mockito.<String>any());
        Mockito.verify(userRepository, Mockito.times(1)).findByUsername(Mockito.<String>any());
        Mockito.verify(userRepository, Mockito.times(1)).findById(Mockito.<Long>any());
        Mockito.verify(userRepository, Mockito.times(1)).save(Mockito.<User>any());
    }

    @Test
    @DisplayName("Test demoteUserById and fail user is not admin")
    void demoteUserByIdFailUserIsNotAdmin() {
        //given
        User user = new User(1l, "teacher", "teacher", Role.TEACHER, true, null);

        //when
        when(tokenService.getUsernameFromToken(Mockito.<String>any())).thenReturn("teacher");
        when(userRepository.findByUsername(Mockito.<String>any())).thenReturn(java.util.Optional.of(user));

        //then
        ResponseEntity responseEntity = adminServiceImpl.demoteUserById("teacher", 2L);
        Assertions.assertTrue(responseEntity.getStatusCode().is4xxClientError());
        Assertions.assertEquals("User is not admin", responseEntity.getBody());

        //verify
        Mockito.verify(tokenService, Mockito.times(1)).getUsernameFromToken(Mockito.<String>any());
        Mockito.verify(userRepository, Mockito.times(1)).findByUsername(Mockito.<String>any());
    }

    @Test
    @DisplayName("Test demoteUserById and fail user is student not admin")
    void demoteUserByIdFailUserIsStudentNotAdmin() {
        //given
        User user = new User(1l, "student", "student", Role.STUDENT, true, null);

        //when
        when(tokenService.getUsernameFromToken(Mockito.<String>any())).thenReturn("student");
        when(userRepository.findByUsername(Mockito.<String>any())).thenReturn(java.util.Optional.of(user));

        //then
        ResponseEntity responseEntity = adminServiceImpl.demoteUserById("student", 2L);
        Assertions.assertTrue(responseEntity.getStatusCode().is4xxClientError());
        Assertions.assertEquals("User is not admin", responseEntity.getBody());

        //verify
        Mockito.verify(tokenService, Mockito.times(1)).getUsernameFromToken(Mockito.<String>any());
        Mockito.verify(userRepository, Mockito.times(1)).findByUsername(Mockito.<String>any());
    }

    @Test
    @DisplayName("Test demoteUserById and fail user is not found")
    void demoteUserByIdFailUserIsNotFound() {
        //given
        User user = new User(1l, "admin", "admin", Role.ADMIN, true, null);

        //when
        when(tokenService.getUsernameFromToken(Mockito.<String>any())).thenReturn("admin");
        when(userRepository.findByUsername(Mockito.<String>any())).thenReturn(java.util.Optional.of(user));
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(java.util.Optional.empty());

        //then
        ResponseEntity responseEntity = adminServiceImpl.demoteUserById("admin", 2L);
        Assertions.assertTrue(responseEntity.getStatusCode().is4xxClientError());
        Assertions.assertEquals("User not found", responseEntity.getBody());

        //verify
        Mockito.verify(tokenService, Mockito.times(1)).getUsernameFromToken(Mockito.<String>any());
        Mockito.verify(userRepository, Mockito.times(1)).findByUsername(Mockito.<String>any());
        Mockito.verify(userRepository, Mockito.times(1)).findById(Mockito.<Long>any());
    }

    @Test
    @DisplayName("Test demoteUserById and fail user is student and cannot be demoted")
    void demoteUserByIdFailUserIsStudentAndCannotBeDemoted() {
        //given
        User user = new User(1l, "admin", "admin", Role.ADMIN, true, null);
        User testUser = new User(2L, "admin2", "admin", Role.STUDENT, false, null);

        //when
        when(tokenService.getUsernameFromToken(Mockito.<String>any())).thenReturn("admin");
        when(userRepository.findByUsername(Mockito.<String>any())).thenReturn(java.util.Optional.of(user));
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(java.util.Optional.of(testUser));

        //then
        ResponseEntity responseEntity = adminServiceImpl.demoteUserById("admin", 2L);
        Assertions.assertTrue(responseEntity.getStatusCode().is4xxClientError());
        Assertions.assertEquals("User is already student", responseEntity.getBody());

        //verify
        Mockito.verify(tokenService, Mockito.times(1)).getUsernameFromToken(Mockito.<String>any());
        Mockito.verify(userRepository, Mockito.times(1)).findByUsername(Mockito.<String>any());
        Mockito.verify(userRepository, Mockito.times(1)).findById(Mockito.<Long>any());
    }
}
