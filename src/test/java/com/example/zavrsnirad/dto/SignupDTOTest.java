package com.example.zavrsnirad.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SignupDTOTest {

    @Test
    @DisplayName("Test constructor")
    void testConstructor() {
        SignupDTO actualSignupDTO = new SignupDTO("janedoe", "iloveyou", "Password Confirmation");

        assertEquals("iloveyou", actualSignupDTO.password());
        assertEquals("janedoe", actualSignupDTO.username());
        assertEquals("Password Confirmation", actualSignupDTO.passwordConfirmation());
    }

    @Test
    @DisplayName("Test password")
    void testPassword() {
        assertEquals("iloveyou", (new SignupDTO("janedoe", "iloveyou", "Password Confirmation")).password());
    }

    @Test
    @DisplayName("Test password confirmation")
    void testPasswordConfirmation() {
        assertEquals("Password Confirmation",
                (new SignupDTO("janedoe", "iloveyou", "Password Confirmation")).passwordConfirmation());
    }

    @Test
    @DisplayName("Test username")
    void testUsername() {
        assertEquals("janedoe", (new SignupDTO("janedoe", "iloveyou", "Password Confirmation")).username());
    }
}

