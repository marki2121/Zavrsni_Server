package com.example.zavrsnirad.util;

import com.example.zavrsnirad.dto.request.SignupDTO;

public final class SignupDtoUtil {
    public static SignupDTO SignupDTO() {
        return new SignupDTO("username", "password", "password");
    }

    public static SignupDTO SignupDTObad() {
        return new SignupDTO("username", "password", "password1");
    }
}
