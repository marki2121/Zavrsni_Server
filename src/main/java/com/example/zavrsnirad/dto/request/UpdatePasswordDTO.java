package com.example.zavrsnirad.dto.request;

import jakarta.validation.constraints.NotBlank;

public record UpdatePasswordDTO(
        @NotBlank String oldPassword,
        @NotBlank String newPassword,
        @NotBlank String confirmationPassword
) {
}
