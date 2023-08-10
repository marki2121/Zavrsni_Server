package com.example.zavrsnirad.util;

import com.example.zavrsnirad.dto.request.UserDTO;

public final class UserDtoUtils {
    public static UserDTO generate() {
        return new UserDTO(1L, "Username", "Name", "Surname", "emial", "1234", "address", "city", "23000", "croatia", "about", "STUDENT", "TRUE");
    }
}
