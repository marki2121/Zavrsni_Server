package com.example.zavrsnirad.mapper;

import com.example.zavrsnirad.appenum.Role;
import com.example.zavrsnirad.dto.UserDTO;
import com.example.zavrsnirad.entity.User;
import com.example.zavrsnirad.entity.UserProfile;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {UserDtoMapper.class})
@ExtendWith(SpringExtension.class)
class UserDtoMapperTest {
    @Autowired
    private UserDtoMapper userDtoMapper;

    @Test
    @DisplayName("Test apply")
    void testApply() {
        //when
        UserProfile userProfile = mock(UserProfile.class);
        when(userProfile.getAboutMe()).thenReturn("About Me");
        when(userProfile.getAddress()).thenReturn("42 Main St");
        when(userProfile.getCity()).thenReturn("Oxford");
        when(userProfile.getCountry()).thenReturn("GB");
        when(userProfile.getEmail()).thenReturn("jane.doe@example.org");
        when(userProfile.getFirstName()).thenReturn("Jane");
        when(userProfile.getLastName()).thenReturn("Doe");
        when(userProfile.getPhoneNumber()).thenReturn("6625550144");
        when(userProfile.getZipCode()).thenReturn("21654");

        User user = new User();
        user.setRole(Role.ADMIN);
        user.setUserProfile(userProfile);
        UserDTO actualApplyResult = userDtoMapper.apply(user);
        //then
        assertEquals("About Me", actualApplyResult.about());
        assertEquals("21654", actualApplyResult.zipCode());
        assertNull(actualApplyResult.username());
        assertEquals("ADMIN", actualApplyResult.role());
        assertEquals("6625550144", actualApplyResult.phone());
        assertEquals("Doe", actualApplyResult.lastName());
        assertEquals("Jane", actualApplyResult.firstName());
        assertEquals("jane.doe@example.org", actualApplyResult.email());
        assertEquals("GB", actualApplyResult.country());
        assertEquals("Oxford", actualApplyResult.city());
        assertEquals("42 Main St", actualApplyResult.address());
        //veifty
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

