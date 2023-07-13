package com.example.zavrsnirad.service.impl;

import com.example.zavrsnirad.appenum.Role;
import com.example.zavrsnirad.dto.request.SignupDTO;
import com.example.zavrsnirad.dto.request.UpdatePasswordDTO;
import com.example.zavrsnirad.entity.User;
import com.example.zavrsnirad.entity.UserProfile;
import com.example.zavrsnirad.mapper.UserDtoMapper;
import com.example.zavrsnirad.repository.UserProfileRepository;
import com.example.zavrsnirad.repository.UserRepository;
import com.example.zavrsnirad.service.TokenService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.server.resource.BearerTokenAuthenticationToken;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {UserServiceImpl.class, BCryptPasswordEncoder.class}) // Ovo je potrebno da bi se testirala klasa UserServiceImpl
@ExtendWith(SpringExtension.class)
class UserServiceImplTest {
    @MockBean
    private TokenService tokenService;

    @MockBean
    private UserDtoMapper userDtoMapper;

    @MockBean
    private UserProfileRepository userProfileRepository;

    @MockBean
    private UserRepository userRepository;

    @Autowired
    private UserServiceImpl userServiceImpl;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Test
    @DisplayName("Load user by username and success")
    void testLoadUserByUsernameGood() throws UsernameNotFoundException {
        // Given
        User user = new User();
        // When
        when(userRepository.findByUsername(Mockito.<String>any())).thenReturn(Optional.of(user));
        // Then
        assertSame(user, userServiceImpl.loadUserByUsername("janedoe"));

        // Verify
        verify(userRepository).findByUsername(Mockito.<String>any());
    }

    @Test
    @DisplayName("Load user by username and fail (no user found)")
    void testLoadUserByUsernameNoUser() throws UsernameNotFoundException {
        // when
        when(userRepository.findByUsername(Mockito.<String>any())).thenReturn(Optional.empty());
        // Then
        assertThrows(UsernameNotFoundException.class, () -> userServiceImpl.loadUserByUsername("janedoe"));

        // Verify
        verify(userRepository).findByUsername(Mockito.<String>any());
    }

    @Test
    @DisplayName("Load user by username and fail (exception)")
    void testLoadUserByUsernameNoUserException() throws UsernameNotFoundException {
        //when
        when(userRepository.findByUsername(Mockito.<String>any()))
                .thenThrow(new UsernameNotFoundException("User not found"));
        //then
        assertThrows(UsernameNotFoundException.class, () -> userServiceImpl.loadUserByUsername("janedoe"));

        // Verify
        verify(userRepository).findByUsername(Mockito.<String>any());
    }

    @Test
    @DisplayName("Try to login and success")
    void testLoginOk() {
        // When
        when(tokenService.generateToken(Mockito.<Authentication>any())).thenReturn("ABC123");
        // Then
        ResponseEntity<String> actualLoginResult = userServiceImpl.login(new BearerTokenAuthenticationToken("ABC123")); // Pokušaj logina
        assertEquals("ABC123", actualLoginResult.getBody()); // Provjeri da li je vraćen token
        assertTrue(actualLoginResult.getStatusCode().is2xxSuccessful()); // Provjeri da li je vraćen status 200
        assertTrue(actualLoginResult.getHeaders().isEmpty()); // Provjeri da li je vraćen prazan header

        // Verify
        verify(tokenService).generateToken(Mockito.<Authentication>any());
    }

    @Test
    @DisplayName("Try to login and fail")
    void testLoginFail() {
        // when
        when(tokenService.generateToken(Mockito.<Authentication>any())).thenThrow(new UsernameNotFoundException("Msg"));

        //then
        assertThrows(UsernameNotFoundException.class,
                () -> userServiceImpl.login(new BearerTokenAuthenticationToken("ABC123")));

        // Verify
        verify(tokenService).generateToken(Mockito.<Authentication>any());
    }

    @Test
    @DisplayName("Try to signup and fail with bad password metch")
    void testSignupBadPasswordMetch() {
        // when
        ResponseEntity<String> actualSignupResult = userServiceImpl
                .signup(new SignupDTO("janedoe", "iloveyou", "Password Confirmation"));

        // then
        assertEquals("Passwords don't match", actualSignupResult.getBody());
        assertTrue(actualSignupResult.getStatusCode().is4xxClientError());

        // Verify
        assertTrue(actualSignupResult.getHeaders().isEmpty());
    }

    @Test
    @DisplayName("Try to signup and fail with existing username")
    void testSignupUsernameExists() {
        // given
        User user = new User();

        // when
        when(userRepository.save(Mockito.<User>any())).thenReturn(user);
        when(userRepository.findByUsername(Mockito.<String>any())).thenReturn(Optional.of(user));
        ResponseEntity<String> actualSignupResult = userServiceImpl
                .signup(new SignupDTO("janedoe", "Password Confirmation", "Password Confirmation"));

        // then
        assertEquals("Username already exists", actualSignupResult.getBody());
        assertTrue(actualSignupResult.getStatusCode().is4xxClientError());
        assertTrue(actualSignupResult.getHeaders().isEmpty());

        // verify
        verify(userRepository).findByUsername(Mockito.<String>any());
    }

    @Test
    @DisplayName("Try to signup and success")
    void testSignupSuccess() {
        // when
        when(userRepository.save(Mockito.<User>any())).thenReturn(new User());
        when(userRepository.findByUsername(Mockito.<String>any())).thenReturn(Optional.empty());
        when(userProfileRepository.save(Mockito.<UserProfile>any())).thenReturn(new UserProfile());
        ResponseEntity<String> actualSignupResult = userServiceImpl
                .signup(new SignupDTO("janedoe", "Password Confirmation", "Password Confirmation"));

        // then
        assertEquals("User created", actualSignupResult.getBody());
        assertTrue(actualSignupResult.getStatusCode().is2xxSuccessful());
        assertTrue(actualSignupResult.getHeaders().isEmpty());

        // verify
        verify(userRepository).save(Mockito.<User>any());
        verify(userRepository).findByUsername(Mockito.<String>any());
        verify(userProfileRepository).save(Mockito.<UserProfile>any());
    }

    @Test
    @DisplayName("Try to update password and fail (same password as old one)")
    void testUpdatePasswordSamePasswordAsOld() {
        // when
        when(userRepository.findByUsername(Mockito.<String>any())).thenReturn(Optional.of(new User(
                1L,
                "janedoe",
                passwordEncoder.encode("iloveyou"),
                Role.STUDENT,
                Boolean.TRUE,
                null
        )));
        when(tokenService.getUsernameFromToken(Mockito.<String>any())).thenReturn("janedoe");
        ResponseEntity<String> actualUpdatePasswordResult = userServiceImpl.updatePassword("JaneDoe",
                new UpdatePasswordDTO("iloveyou", "iloveyou", "iloveyou"));
        // then
        assertEquals("Passwords don't match or bad password", actualUpdatePasswordResult.getBody());
        assertTrue(actualUpdatePasswordResult.getStatusCode().is4xxClientError());
        assertTrue(actualUpdatePasswordResult.getHeaders().isEmpty());

        // verify
        verify(userRepository).findByUsername(Mockito.<String>any());
        verify(tokenService).getUsernameFromToken(Mockito.<String>any());
    }

    @Test
    @DisplayName("Try to update password and fail (username not found)")
    void testUpdatePasswordNoUsername() {
        //when
        when(userRepository.findByUsername(Mockito.any())).thenThrow(new UsernameNotFoundException("Msg"));
        when(tokenService.getUsernameFromToken(Mockito.any())).thenReturn("janedoe");

        //then
        assertThrows(UsernameNotFoundException.class, () -> userServiceImpl.updatePassword("JaneDoe",
                new UpdatePasswordDTO("asd", "iloveyou", "iloveyou")));

        //verify
        verify(userRepository).findByUsername(Mockito.any());
        verify(tokenService).getUsernameFromToken(Mockito.any());
    }

    @Test
    @DisplayName("Try to update password and fail (bad password metch)")
    void testUpdatePasswordBadPasswordMatch() {
        //given
        User user = new User(
                1L,
                "janedoe",
                passwordEncoder.encode("asd"),
                Role.STUDENT,
                Boolean.TRUE,
                null
        );
        UpdatePasswordDTO passwordDTO = new UpdatePasswordDTO("asd", "iloveyou", "iloveyou1");

        // when
        when(userRepository.findByUsername(Mockito.<String>any())).thenReturn(Optional.of(user));
        when(tokenService.getUsernameFromToken(Mockito.<String>any())).thenReturn("janedoe");
        ResponseEntity<String> responseEntity = userServiceImpl.updatePassword("JaneDoe", passwordDTO);

        //then
        assertEquals("Passwords don't match or bad password", responseEntity.getBody());
        assertTrue(responseEntity.getStatusCode().is4xxClientError());
        assertTrue(responseEntity.getHeaders().isEmpty());

        // verify
        verify(userRepository).findByUsername(Mockito.<String>any());
        verify(tokenService).getUsernameFromToken(Mockito.<String>any());
    }

    @Test
    @DisplayName("Try to update password and success")
    void testUpdatePasswordSuccess() {
        // given
        User user = new User(
                1L,
                "janedoe",
                passwordEncoder.encode("asd"),
                Role.STUDENT,
                Boolean.TRUE,
                null
        );
        UpdatePasswordDTO updatePasswordDTO = new UpdatePasswordDTO("asd", "iloveyou", "iloveyou");

        // when
        when(userRepository.findByUsername(Mockito.<String>any())).thenReturn(Optional.of(user));
        when(tokenService.getUsernameFromToken(Mockito.<String>any())).thenReturn("janedoe");
        when(userRepository.save(Mockito.<User>any())).thenReturn(new User());
        ResponseEntity<String> responseEntity = userServiceImpl.updatePassword("JaneDoe", updatePasswordDTO);

        // then
        assertEquals("Password updated.", responseEntity.getBody());
        assertTrue(responseEntity.getStatusCode().is2xxSuccessful());

        // verify
        verify(userRepository).findByUsername(Mockito.<String>any());
        verify(tokenService).getUsernameFromToken(Mockito.<String>any());
        verify(userRepository).save(Mockito.<User>any());
    }
}

