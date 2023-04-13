package com.example.zavrsnirad.dto;

public record UpdatePasswordDTO(
        String oldPassword,
        String newPassword,
        String confirmationPassword
) {
}
