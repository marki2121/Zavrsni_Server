package com.example.zavrsnirad.dto;

import com.example.zavrsnirad.dto.request.UpdateProfileDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UpdateProfileDTOTest {
    @Test
    @DisplayName("Test constructor")
    void testConstructor() {
        UpdateProfileDTO actualUpdateProfileDTO = new UpdateProfileDTO("Jane", "Doe", "jane.doe@example.org", "6625550144",
                "42 Main St", "Oxford", "21654", "GB", "About me");

        assertEquals("About me", actualUpdateProfileDTO.aboutMe());
        assertEquals("21654", actualUpdateProfileDTO.zipCode());
        assertEquals("6625550144", actualUpdateProfileDTO.phoneNumber());
        assertEquals("Doe", actualUpdateProfileDTO.lastName());
        assertEquals("Jane", actualUpdateProfileDTO.firstName());
        assertEquals("jane.doe@example.org", actualUpdateProfileDTO.email());
        assertEquals("GB", actualUpdateProfileDTO.country());
        assertEquals("Oxford", actualUpdateProfileDTO.city());
        assertEquals("42 Main St", actualUpdateProfileDTO.address());
    }
}

