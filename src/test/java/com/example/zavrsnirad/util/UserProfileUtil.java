package com.example.zavrsnirad.util;

import com.example.zavrsnirad.entity.UserProfile;

public final class UserProfileUtil {
    public static UserProfile generate() {
        return UserProfile.builder()
                .id(1L)
                .firstName("firstName")
                .lastName("lastName")
                .email("email")
                .address("address")
                .city("city")
                .zipCode("zipCode")
                .country("country")
                .phoneNumber("phoneNumber")
                .aboutMe("aboutMe")
                .imageUrl("imageUrl")
                .build();
    }
}
