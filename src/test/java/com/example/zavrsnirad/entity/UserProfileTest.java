package com.example.zavrsnirad.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

class UserProfileTest {
    @Test
    @DisplayName("Test UserProfile constructor")
    void testConstructor() {
        // given
        UserProfile actualUserProfile = new UserProfile();
        actualUserProfile.setAboutMe("About Me");
        actualUserProfile.setAddress("42 Main St");
        actualUserProfile.setCity("Oxford");
        actualUserProfile.setCountry("GB");
        actualUserProfile.setEmail("jane.doe@example.org");
        actualUserProfile.setFirstName("Jane");
        actualUserProfile.setId(1L);
        actualUserProfile.setImageUrl("https://example.org/example");
        actualUserProfile.setLastName("Doe");
        actualUserProfile.setPhoneNumber("6625550144");
        User user = new User();
        actualUserProfile.setUser(user);
        actualUserProfile.setZipCode("21654");

        // assert
        assertEquals("About Me", actualUserProfile.getAboutMe());
        assertEquals("42 Main St", actualUserProfile.getAddress());
        assertEquals("Oxford", actualUserProfile.getCity());
        assertEquals("GB", actualUserProfile.getCountry());
        assertEquals("jane.doe@example.org", actualUserProfile.getEmail());
        assertEquals("Jane", actualUserProfile.getFirstName());
        assertEquals(1L, actualUserProfile.getId().longValue());
        assertEquals("https://example.org/example", actualUserProfile.getImageUrl());
        assertEquals("Doe", actualUserProfile.getLastName());
        assertEquals("6625550144", actualUserProfile.getPhoneNumber());
        assertSame(user, actualUserProfile.getUser());
        assertEquals("21654", actualUserProfile.getZipCode());
    }
}

