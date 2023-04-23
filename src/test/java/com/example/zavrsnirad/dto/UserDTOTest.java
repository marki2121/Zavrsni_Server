package com.example.zavrsnirad.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserDTOTest {

    @Test
    @DisplayName("Test about")
    void testAbout() {
        assertEquals("About", (new UserDTO("janedoe", "Jane", "Doe", "jane.doe@example.org", "42 Main St", "Oxford",
                "21654", "GB", "6625550144", "Role", "About")).about());
    }

    @Test
    @DisplayName("Test address")
    void testAddress() {
        assertEquals("42 Main St", (new UserDTO("janedoe", "Jane", "Doe", "jane.doe@example.org", "42 Main St", "Oxford",
                "21654", "GB", "6625550144", "Role", "About")).address());
    }

    @Test
    @DisplayName("Test city")
    void testCity() {
        assertEquals("Oxford", (new UserDTO("janedoe", "Jane", "Doe", "jane.doe@example.org", "42 Main St", "Oxford",
                "21654", "GB", "6625550144", "Role", "About")).city());
    }

    @Test
    @DisplayName("Test constructor")
    void testConstructor() {
        UserDTO actualUserDTO = new UserDTO("janedoe", "Jane", "Doe", "jane.doe@example.org", "42 Main St", "Oxford",
                "21654", "GB", "6625550144", "Role", "About");

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
    }

    @Test
    @DisplayName("Test country")
    void testCountry() {
        assertEquals("GB", (new UserDTO("janedoe", "Jane", "Doe", "jane.doe@example.org", "42 Main St", "Oxford", "21654",
                "GB", "6625550144", "Role", "About")).country());
    }

    @Test
    @DisplayName("Test email")
    void testEmail() {
        assertEquals("jane.doe@example.org", (new UserDTO("janedoe", "Jane", "Doe", "jane.doe@example.org", "42 Main St",
                "Oxford", "21654", "GB", "6625550144", "Role", "About")).email());
    }

    @Test
    @DisplayName("Test first name")
    void testFirstName() {
        assertEquals("Jane", (new UserDTO("janedoe", "Jane", "Doe", "jane.doe@example.org", "42 Main St", "Oxford",
                "21654", "GB", "6625550144", "Role", "About")).firstName());
    }

    @Test
    @DisplayName("Test last name")
    void testLastName() {
        assertEquals("Doe", (new UserDTO("janedoe", "Jane", "Doe", "jane.doe@example.org", "42 Main St", "Oxford",
                "21654", "GB", "6625550144", "Role", "About")).lastName());
    }

    @Test
    @DisplayName("Test phone")
    void testPhone() {
        assertEquals("6625550144", (new UserDTO("janedoe", "Jane", "Doe", "jane.doe@example.org", "42 Main St", "Oxford",
                "21654", "GB", "6625550144", "Role", "About")).phone());
    }

    @Test
    @DisplayName("Test role")
    void testRole() {
        assertEquals("Role", (new UserDTO("janedoe", "Jane", "Doe", "jane.doe@example.org", "42 Main St", "Oxford",
                "21654", "GB", "6625550144", "Role", "About")).role());
    }

    @Test
    @DisplayName("Test username")
    void testUsername() {
        assertEquals("janedoe", (new UserDTO("janedoe", "Jane", "Doe", "jane.doe@example.org", "42 Main St", "Oxford",
                "21654", "GB", "6625550144", "Role", "About")).username());
    }

    @Test
    @DisplayName("Test zip code")
    void testZipCode() {
        assertEquals("21654", (new UserDTO("janedoe", "Jane", "Doe", "jane.doe@example.org", "42 Main St", "Oxford",
                "21654", "GB", "6625550144", "Role", "About")).zipCode());
    }
}

