package com.example.zavrsnirad.dto.response;

public record UserResponseDTO(
        Long id,
        String username,
        String firstName,
        String lastName,
        String email,
        String phoneNumber,
        String address,
        String city,
        String zipCode,
        String country,
        String aboutMe,
        String role
) {
}
