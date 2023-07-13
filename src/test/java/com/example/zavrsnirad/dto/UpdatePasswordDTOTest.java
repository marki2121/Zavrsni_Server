package com.example.zavrsnirad.dto;

import com.example.zavrsnirad.dto.request.UpdatePasswordDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UpdatePasswordDTOTest {

    @Test
    @DisplayName("Test confirmation password")
    void testConfirmationPassword() {
        assertEquals("iloveyou", (new UpdatePasswordDTO("iloveyou", "iloveyou", "iloveyou")).confirmationPassword());
    }


    @Test
    @DisplayName("Test constructor")
    void testConstructor() {
        UpdatePasswordDTO actualUpdatePasswordDTO = new UpdatePasswordDTO("iloveyou", "iloveyou", "iloveyou");

        assertEquals("iloveyou", actualUpdatePasswordDTO.confirmationPassword());
        assertEquals("iloveyou", actualUpdatePasswordDTO.oldPassword());
        assertEquals("iloveyou", actualUpdatePasswordDTO.newPassword());
    }

    @Test
    @DisplayName("Test new password")
    void testNewPassword() {
        assertEquals("iloveyou", (new UpdatePasswordDTO("iloveyou", "iloveyou", "iloveyou")).newPassword());
    }

    @Test
    @DisplayName("Test old password")
    void testOldPassword() {
        assertEquals("iloveyou", (new UpdatePasswordDTO("iloveyou", "iloveyou", "iloveyou")).oldPassword());
    }
}

