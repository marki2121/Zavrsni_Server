package com.example.zavrsnirad.dto;

public record UpdateProfileDTO(
        String first_name,
        String last_name,
        String email,
        String phone_number,
        String address,
        String city,
        String zip_code,
        String country,
        String about_me
) {
}
