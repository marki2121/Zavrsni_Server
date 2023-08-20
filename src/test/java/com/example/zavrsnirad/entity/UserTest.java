package com.example.zavrsnirad.entity;

import com.example.zavrsnirad.appenum.Role;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {
    @Test
    @DisplayName("Test User authorities")
    void testGetAuthorities() {
        User user = new User();
        user.setRole(Role.ADMIN);
        assertEquals(1, user.getAuthorities().size());
    }

    @Test
    @DisplayName("User is enabled true")
    void testIsEnabledTrue() {
        User user = new User();
        user.setEnabled(true);
        assertTrue(user.isEnabled());
    }

    @Test
    @DisplayName("User is enabled false")
    void testIsEnabledFalse() {
        User user = new User();
        user.setEnabled(false);
        assertFalse(user.isEnabled());
    }

    @Test
    @DisplayName("Test User constructor")
    void testConstructor() {
        User actualUser = new User();
        actualUser.setEnabled(true);
        actualUser.setId(1L);
        actualUser.setPassword("iloveyou");
        actualUser.setRole(Role.ADMIN);
        UserProfile userProfile = new UserProfile();
        actualUser.setUserProfile(userProfile);
        actualUser.setUsername("janedoe");
        assertTrue(actualUser.getEnabled());
        assertEquals(1L, actualUser.getId().longValue());
        assertEquals("iloveyou", actualUser.getPassword());
        assertEquals(Role.ADMIN, actualUser.getRole());
        assertSame(userProfile, actualUser.getUserProfile());
        assertEquals("janedoe", actualUser.getUsername());
        assertTrue(actualUser.isAccountNonExpired());
        assertTrue(actualUser.isAccountNonLocked());
        assertTrue(actualUser.isCredentialsNonExpired());
    }

    @Test
    @DisplayName("Test User constructor 2")
    void testConstructor2() {
        UserProfile userProfile = new UserProfile();
        User actualUser = new User(1L, "janedoe", "iloveyou", Role.ADMIN, true, userProfile, List.of());
        assertEquals(1L, actualUser.getId().longValue());
        assertEquals("iloveyou", actualUser.getPassword());
        assertEquals(Role.ADMIN, actualUser.getRole());
        assertSame(userProfile, actualUser.getUserProfile());
        assertEquals("janedoe", actualUser.getUsername());
        assertTrue(actualUser.isAccountNonExpired());
        assertTrue(actualUser.isAccountNonLocked());
        assertTrue(actualUser.isCredentialsNonExpired());
    }
}

