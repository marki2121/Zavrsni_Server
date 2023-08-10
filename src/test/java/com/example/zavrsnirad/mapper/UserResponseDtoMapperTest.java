package com.example.zavrsnirad.mapper;

import com.example.zavrsnirad.appenum.Role;
import com.example.zavrsnirad.dto.response.UserResponseDTO;
import com.example.zavrsnirad.entity.User;
import com.example.zavrsnirad.entity.UserProfile;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {UserResponseDtoMapper.class})
@ExtendWith(SpringExtension.class)
class UserResponseDtoMapperTest {
    @Autowired
    private UserResponseDtoMapper userResponseDtoMapper;

    @Test
    @DisplayName("Test apply")
    void testApply() {
        //when
        User user = new User();
        UserProfile userProfile = mock(UserProfile.class);
        when(userProfile.getAboutMe()).thenReturn("About Me");
        when(userProfile.getAddress()).thenReturn("42 Main St");
        when(userProfile.getCity()).thenReturn("Oxford");
        when(userProfile.getCountry()).thenReturn("Country");
        when(userProfile.getEmail()).thenReturn("jane.doe@example.org");
        when(userProfile.getFirstName()).thenReturn("Jane");
        when(userProfile.getLastName()).thenReturn("Doe");
        when(userProfile.getPhoneNumber()).thenReturn("123456789");
        when(userProfile.getZipCode()).thenReturn("12345");

        user.setRole(Role.ADMIN);
        user.setId(1L);
        user.setUsername("jane.doe");
        user.setUserProfile(userProfile);
        user.setEnabled(true);
        UserResponseDTO actualApplyResult = userResponseDtoMapper.apply(user);

        //then
        assertEquals("About Me", actualApplyResult.aboutMe());
        assertEquals("12345", actualApplyResult.zipCode());
        assertEquals("jane.doe", actualApplyResult.username());
        assertEquals("ADMIN", actualApplyResult.role());
        assertEquals("123456789", actualApplyResult.phoneNumber());
        assertEquals("Doe", actualApplyResult.lastName());
        assertEquals("Jane", actualApplyResult.firstName());
        assertEquals("Country", actualApplyResult.country());
        assertEquals("Oxford", actualApplyResult.city());
        assertEquals("42 Main St", actualApplyResult.address());
        assertEquals(1L, actualApplyResult.id());
        assertEquals("jane.doe@example.org", actualApplyResult.email());

        //verify
        verify(userProfile).getAboutMe();
        verify(userProfile).getAddress();
        verify(userProfile).getCity();
        verify(userProfile).getCountry();
        verify(userProfile).getEmail();
        verify(userProfile).getFirstName();
        verify(userProfile).getLastName();
        verify(userProfile).getPhoneNumber();
        verify(userProfile).getZipCode();
    }
}