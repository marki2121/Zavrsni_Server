package com.example.zavrsnirad.service.impl;

import com.example.zavrsnirad.appenum.Role;
import com.example.zavrsnirad.config.CostumeErrorException;
import com.example.zavrsnirad.mapper.SignupDtoMapper;
import com.example.zavrsnirad.repository.UserRepository;
import com.example.zavrsnirad.service.TokenService;
import com.example.zavrsnirad.service.UserGetService;
import com.example.zavrsnirad.service.UserProfileService;
import com.example.zavrsnirad.service.UserService;
import com.example.zavrsnirad.util.AuthenticationUtil;
import com.example.zavrsnirad.util.SignupDtoUtil;
import com.example.zavrsnirad.util.UserUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@SpringBootTest
class UserServiceImplTest {

    @Autowired
    private UserService userService;
    @MockBean
    private UserGetService userGetService;
    @MockBean
    private UserRepository userRepository;
    @MockBean
    private TokenService tokenService;
    @MockBean
    private SignupDtoMapper signupDtoMapper;
    @MockBean
    private UserProfileService userProfileService;

    @Test
    @DisplayName("Test login - success")
    void login() {
        when(tokenService.generateToken(AuthenticationUtil.getAuthentication())).thenReturn("token");
        assertEquals(userService.login(AuthenticationUtil.getAuthentication()), "token");
    }

    @Test
    @DisplayName("Test signup - success")
    void signup() {
        when(userRepository.findByUsername("username")).thenReturn(Optional.empty());
        when(signupDtoMapper.apply(SignupDtoUtil.SignupDTO())).thenReturn(UserUtil.generate());
        when(userRepository.save(any())).thenReturn(UserUtil.generate());
        when(userProfileService.saveProfile(any())).thenReturn(UserUtil.generate().getUserProfile());
        assertDoesNotThrow(() -> userService.signup(SignupDtoUtil.SignupDTO()));
    }

    @Test
    @DisplayName("Test signup - fail password mismatch")
    void signupFail() {
        assertThrows(CostumeErrorException.class, () -> userService.signup(SignupDtoUtil.SignupDTObad()));
    }

    @Test
    @DisplayName("Test signup - fail username taken")
    void signupFail2() {
        when(userRepository.findByUsername("username")).thenReturn(Optional.of(UserUtil.generate()));
        assertThrows(CostumeErrorException.class, () -> userService.signup(SignupDtoUtil.SignupDTO()));
    }

    @Test
    @DisplayName("Test deleteUserById - success")
    void deleteUserById() throws CostumeErrorException {
        when(userGetService.getUserById(1L)).thenReturn(UserUtil.generate());
        doNothing().when(userRepository).deleteById(1L);
        assertDoesNotThrow(() -> userService.deleteUserById(1L));
        assertEquals(userService.deleteUserById(1L), "User deleted");
    }

    @Test
    @DisplayName("Test disableUserById - success")
    void disableUserById() throws CostumeErrorException {
        when(userGetService.getUserById(1L)).thenReturn(UserUtil.generate());
        when(userRepository.save(any())).thenReturn(UserUtil.generate());
        assertDoesNotThrow(() -> userService.disableUserById(1L));
        assertEquals(userService.disableUserById(1L), "User disabled");
    }

    @Test
    @DisplayName("Test enableUserById - success")
    void enableUserById() throws CostumeErrorException {
        when(userGetService.getUserById(1L)).thenReturn(UserUtil.generate());
        when(userRepository.save(any())).thenReturn(UserUtil.generate());
        assertDoesNotThrow(() -> userService.enableUserById(1L));
        assertEquals(userService.enableUserById(1L), "User enabled");
    }

    @Test
    @DisplayName("Test promoteUserById - success")
    void promoteUserById() throws CostumeErrorException {
        when(userGetService.getUserById(1L)).thenReturn(UserUtil.generate());
        when(userRepository.save(any())).thenReturn(UserUtil.generate());
        assertDoesNotThrow(() -> userService.promoteUserById(1L));
        assertEquals(userService.promoteUserById(1L), "User promoted");
    }

    @Test
    @DisplayName("Test promoteUserById - fail user is already admin")
    void promoteUserByIdFail() throws CostumeErrorException {
        when(userGetService.getUserById(1L)).thenReturn(UserUtil.generateAdmin());
        assertThrows(CostumeErrorException.class, () -> userService.promoteUserById(1L));
    }

    @Test
    @DisplayName("Test demoteUserById - success")
    void demoteUserById() throws CostumeErrorException {
        when(userGetService.getUserById(1L)).thenReturn(UserUtil.generateTeacher());
        when(userRepository.save(any())).thenReturn(UserUtil.generate());
        assertDoesNotThrow(() -> userService.demoteUserById(1L));
    }

    @Test
    @DisplayName("Test demoteUserById - fail user is already student")
    void demoteUserByIdFail() throws CostumeErrorException {
        when(userGetService.getUserById(1L)).thenReturn(UserUtil.generate());
        assertThrows(CostumeErrorException.class, () -> userService.demoteUserById(1L));
    }

    @Test
    @DisplayName("Test demoteUserById - fail user is the last admin")
    void demoteUserByIdFail2() throws CostumeErrorException {
        when(userGetService.getUserById(1L)).thenReturn(UserUtil.generateAdmin());
        when(userRepository.findAllByRole(Role.ADMIN)).thenReturn(UserUtil.generateListAdmin());
        assertThrows(CostumeErrorException.class, () -> userService.demoteUserById(1L));
    }
}