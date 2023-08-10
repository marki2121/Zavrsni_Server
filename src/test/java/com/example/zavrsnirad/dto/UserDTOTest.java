package com.example.zavrsnirad.dto;

import com.example.zavrsnirad.dto.request.UserDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserDTOTest {
    @Test
    @DisplayName("Test constructor")
    void testConstructor() {
        UserDTO actualUserDTO = new UserDTO(1L, "janedoe", "Jane", "Doe", "jane.doe@example.org", "42 Main St", "Oxford",
                "21654", "GB", "6625550144", "Role", "About", "image");

        assertEquals(1L, actualUserDTO.id());
        assertEquals("About", actualUserDTO.about());
        assertEquals("21654", actualUserDTO.zipCode());
        assertEquals("janedoe", actualUserDTO.username());
        assertEquals("Role", actualUserDTO.role());
        assertEquals("6625550144", actualUserDTO.phone());
        assertEquals("Doe", actualUserDTO.lastName());
        assertEquals("Jane", actualUserDTO.firstName());
        assertEquals("jane.doe@example.org", actualUserDTO.email());
        assertEquals("GB", actualUserDTO.country());
        assertEquals("Oxford", actualUserDTO.city());
        assertEquals("42 Main St", actualUserDTO.address());
        assertEquals("image", actualUserDTO.imageUrl());
    }
}

