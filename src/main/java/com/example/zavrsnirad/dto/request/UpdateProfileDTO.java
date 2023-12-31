package com.example.zavrsnirad.dto.request;

public record UpdateProfileDTO(
        String firstName,
        String lastName,
        String email,
        String phoneNumber,
        String address,
        String city,
        String zipCode,
        String country,
        String aboutMe
) {
}
