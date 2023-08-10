package com.example.zavrsnirad.dto;

import com.example.zavrsnirad.dto.response.UserResponseDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserResponseDTOTest {
    @Test
    @DisplayName("UserResponseDTO constructor")
    void userResponseDTO() {
        UserResponseDTO userResponseDTO = new UserResponseDTO(1L, "username", "firstName", "lastName", "email", "phoneNumber", "address", "city", "zipCode", "country", "aboutMe", "role", "true");
        assertEquals(1L, userResponseDTO.id());
        assertEquals("username", userResponseDTO.username());
        assertEquals("firstName", userResponseDTO.firstName());
        assertEquals("lastName", userResponseDTO.lastName());
        assertEquals("email", userResponseDTO.email());
        assertEquals("phoneNumber", userResponseDTO.phoneNumber());
        assertEquals("address", userResponseDTO.address());
        assertEquals("city", userResponseDTO.city());
        assertEquals("zipCode", userResponseDTO.zipCode());
        assertEquals("country", userResponseDTO.country());
        assertEquals("aboutMe", userResponseDTO.aboutMe());
        assertEquals("role", userResponseDTO.role());
        assertEquals("true", userResponseDTO.active());
    }
}