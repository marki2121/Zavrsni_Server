package com.example.zavrsnirad.dto.request;

import jakarta.validation.constraints.NotBlank;
import org.springframework.lang.NonNull;

public record SignupDTO(@NotBlank String username,
                        @NotBlank String password,
                        @NotBlank String passwordConfirmation)
{
}
