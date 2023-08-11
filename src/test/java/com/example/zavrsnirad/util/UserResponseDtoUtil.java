package com.example.zavrsnirad.util;

import com.example.zavrsnirad.dto.response.UserResponseDTO;

import java.util.List;

public final class UserResponseDtoUtil {
    public static UserResponseDTO generateUserResponseDto() {
        return new UserResponseDTO(1L, "Username", "Name", "Surname", "emial", "1234", "address", "city", "23000", "croatia", "about", "STUDENT", "TRUE");
    }

    public static List<UserResponseDTO> generateList() {
        return List.of(generateUserResponseDto());
    }
}
