package com.example.zavrsnirad.dto.request;

public record UserDTO(String username,
                      String firstName,
                      String lastName,
                      String email,
                      String address,
                      String city,
                      String zipCode,
                      String country,
                      String phone,
                      String role,
                      String about) {
}
