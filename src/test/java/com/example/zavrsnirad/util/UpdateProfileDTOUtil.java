package com.example.zavrsnirad.util;

import com.example.zavrsnirad.dto.request.UpdateProfileDTO;

public final class UpdateProfileDTOUtil {
    public static UpdateProfileDTO generate() {
        return new UpdateProfileDTO(
                "firstName",
                "lastName",
                "email",
                "phoneNumber",
                "address",
                "city",
                "country",
                "postalCode",
                "aboutMe");
    }
}
