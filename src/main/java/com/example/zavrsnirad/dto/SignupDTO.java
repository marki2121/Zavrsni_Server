package com.example.zavrsnirad.dto;

import org.springframework.lang.NonNull;

public record SignupDTO(@NonNull String username,
                        @NonNull String password,
                        @NonNull String passwordConfirmation)
{
}
