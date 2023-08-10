package com.example.zavrsnirad.dto;

import com.example.zavrsnirad.dto.request.SignupDTO;
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
}

