package com.example.zavrsnirad.dto;

import com.example.zavrsnirad.dto.response.UserResponseDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserResponseDTOTest {
    @Test
    @DisplayName("UserResponseDTO constructor")
    void userResponseDTO() {
        UserResponseDTO userResponseDTO = new UserResponseDTO(1L, "username", "firstName", "lastName", "email", "phoneNumber", "address", "city", "zipCode", "country", "aboutMe", "role");
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
    }

    @Test
    @DisplayName("UserResponseDTO id")
    void id() {
        assertEquals(1L, new UserResponseDTO(1L, null, null, null, null, null, null, null, null, null, null, null).id());
    }

    @Test
    @DisplayName("UserResponseDTO username")
    void username() {
        assertEquals("username", new UserResponseDTO(null, "username", null, null, null, null, null, null, null, null, null, null).username());
    }

    @Test
    @DisplayName("UserResponseDTO firstName")
    void firstName() {
        assertEquals("firstName", new UserResponseDTO(null, null, "firstName", null, null, null, null, null, null, null, null, null).firstName());
    }

    @Test
    @DisplayName("UserResponseDTO lastName")
    void lastName() {
        assertEquals("lastName", new UserResponseDTO(null, null, null, "lastName", null, null, null, null, null, null, null, null).lastName());
    }

    @Test
    @DisplayName("UserResponseDTO email")
    void email() {
        assertEquals("email", new UserResponseDTO(null, null, null, null, "email", null, null, null, null, null, null, null).email());
    }

    @Test
    @DisplayName("UserResponseDTO phoneNumber")
    void phoneNumber() {
        assertEquals("phoneNumber", new UserResponseDTO(null, null, null, null, null, "phoneNumber", null, null, null, null, null, null).phoneNumber());
    }

    @Test
    @DisplayName("UserResponseDTO address")
    void address() {
        assertEquals("address", new UserResponseDTO(null, null, null, null, null, null, "address", null, null, null, null, null).address());
    }

    @Test
    @DisplayName("UserResponseDTO city")
    void city() {
        assertEquals("city", new UserResponseDTO(null, null, null, null, null, null, null, "city", null, null, null, null).city());
    }

    @Test
    @DisplayName("UserResponseDTO zipCode")
    void zipCode() {
        assertEquals("zipCode", new UserResponseDTO(null, null, null, null, null, null, null, null, "zipCode", null, null, null).zipCode());
    }

    @Test
    @DisplayName("UserResponseDTO country")
    void country() {
        assertEquals("country", new UserResponseDTO(null, null, null, null, null, null, null, null, null, "country", null, null).country());
    }

    @Test
    @DisplayName("UserResponseDTO aboutMe")
    void aboutMe() {
        assertEquals("aboutMe", new UserResponseDTO(null, null, null, null, null, null, null, null, null, null, "aboutMe", null).aboutMe());
    }

    @Test
    @DisplayName("UserResponseDTO role")
    void role() {
        assertEquals("role", new UserResponseDTO(null, null, null, null, null, null, null, null, null, null, null, "role").role());
    }
}