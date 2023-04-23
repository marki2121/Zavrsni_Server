package com.example.zavrsnirad.service.impl;

import com.example.zavrsnirad.dto.UserDTO;
import com.example.zavrsnirad.entity.User;
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
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {UserProfileServiceImpl.class})
@ExtendWith(SpringExtension.class)
class UserProfileServiceImplTest {
    @MockBean
    private TokenService tokenService;

    @MockBean
    private UserDtoMapper userDtoMapper;

    @MockBean
    private UserProfileRepository userProfileRepository;

    @Autowired
    private UserProfileServiceImpl userProfileServiceImpl;

    @MockBean
    private UserRepository userRepository;

    @Test
    @DisplayName("Test GetSelf and succeed")
    void testGetSelfSucccess() {

        //when
        when(userRepository.findByUsername(Mockito.<String>any())).thenReturn(Optional.of(new User()));
        when(tokenService.getUsernameFromToken(Mockito.<String>any())).thenReturn("janedoe");
        when(userDtoMapper.apply(Mockito.<User>any())).thenReturn(new UserDTO("janedoe", "Jane", "Doe",
                "jane.doe@example.org", "42 Main St", "Oxford", "21654", "GB", "6625550144", "Role", "About"));

        //then
        ResponseEntity<UserDTO> actualSelf = userProfileServiceImpl.getSelf("JaneDoe");
        assertTrue(actualSelf.hasBody());
        assertTrue(actualSelf.getHeaders().isEmpty());
        assertEquals(200, actualSelf.getStatusCodeValue());

        //verify
        verify(userRepository).findByUsername(Mockito.<String>any());
        verify(tokenService).getUsernameFromToken(Mockito.<String>any());
        verify(userDtoMapper).apply(Mockito.<User>any());
    }

    @Test
    @DisplayName("Test GetSelf and fail")
    void testGetSelfFail() {
        //when
        when(userRepository.findByUsername(Mockito.<String>any())).thenReturn(Optional.of(new User()));
        when(tokenService.getUsernameFromToken(Mockito.<String>any())).thenReturn("janedoe");
        when(userDtoMapper.apply(Mockito.<User>any())).thenThrow(new UsernameNotFoundException("Msg"));
        //then
        assertThrows(UsernameNotFoundException.class, () -> userProfileServiceImpl.getSelf("JaneDoe"));
        //verify
        verify(userRepository).findByUsername(Mockito.<String>any());
        verify(tokenService).getUsernameFromToken(Mockito.<String>any());
        verify(userDtoMapper).apply(Mockito.<User>any());
    }


    @Test
    @DisplayName("Test GetSelf and fail(casing)")
    void testGetSelf3() {
        //when
        when(userRepository.findByUsername(Mockito.<String>any())).thenReturn(Optional.empty());
        when(tokenService.getUsernameFromToken(Mockito.<String>any())).thenReturn("janedoe");
        when(userDtoMapper.apply(Mockito.<User>any())).thenReturn(new UserDTO("janedoe", "Jane", "Doe",
                "jane.doe@example.org", "42 Main St", "Oxford", "21654", "GB", "6625550144", "Role", "About"));
        //then
        assertThrows(UsernameNotFoundException.class, () -> userProfileServiceImpl.getSelf("JaneDoe"));
        //verify
        verify(userRepository).findByUsername(Mockito.<String>any());
        verify(tokenService).getUsernameFromToken(Mockito.<String>any());
    }

    //TODO: Test updateProfile
}

