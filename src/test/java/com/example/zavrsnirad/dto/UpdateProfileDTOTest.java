package com.example.zavrsnirad.dto;

import com.example.zavrsnirad.dto.request.UpdateProfileDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UpdateProfileDTOTest {

    @Test
    @DisplayName("Test about me")
    void testAbout_me() {
        assertEquals("About me", (new UpdateProfileDTO("Jane", "Doe", "jane.doe@example.org", "6625550144", "42 Main St",
                "Oxford", "21654", "GB", "About me")).aboutMe());
    }

    @Test
    @DisplayName("Test address")
    void testAddress() {
        assertEquals("42 Main St", (new UpdateProfileDTO("Jane", "Doe", "jane.doe@example.org", "6625550144",
                "42 Main St", "Oxford", "21654", "GB", "About me")).address());
    }

    @Test
    @DisplayName("Test city")
    void testCity() {
        assertEquals("Oxford", (new UpdateProfileDTO("Jane", "Doe", "jane.doe@example.org", "6625550144", "42 Main St",
                "Oxford", "21654", "GB", "About me")).city());
    }

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

    @Test
    @DisplayName("Test country")
    void testCountry() {
        assertEquals("GB", (new UpdateProfileDTO("Jane", "Doe", "jane.doe@example.org", "6625550144", "42 Main St",
                "Oxford", "21654", "GB", "About me")).country());
    }

    @Test
    @DisplayName("Test email")
    void testEmail() {
        assertEquals("jane.doe@example.org", (new UpdateProfileDTO("Jane", "Doe", "jane.doe@example.org", "6625550144",
                "42 Main St", "Oxford", "21654", "GB", "About me")).email());
    }

    @Test
    @DisplayName("Test first name")
    void testFirst_name() {
        assertEquals("Jane", (new UpdateProfileDTO("Jane", "Doe", "jane.doe@example.org", "6625550144", "42 Main St",
                "Oxford", "21654", "GB", "About me")).firstName());
    }

    @Test
    @DisplayName("Test last name")
    void testLast_name() {
        assertEquals("Doe", (new UpdateProfileDTO("Jane", "Doe", "jane.doe@example.org", "6625550144", "42 Main St",
                "Oxford", "21654", "GB", "About me")).lastName());
    }

    @Test
    @DisplayName("Test phone number")
    void testPhone_number() {
        assertEquals("6625550144", (new UpdateProfileDTO("Jane", "Doe", "jane.doe@example.org", "6625550144",
                "42 Main St", "Oxford", "21654", "GB", "About me")).phoneNumber());
    }

    @Test
    @DisplayName("Test zip code")
    void testZip_code() {
        assertEquals("21654", (new UpdateProfileDTO("Jane", "Doe", "jane.doe@example.org", "6625550144", "42 Main St",
                "Oxford", "21654", "GB", "About me")).zipCode());
    }
}

