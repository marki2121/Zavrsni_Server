package com.example.zavrsnirad.util;

import com.example.zavrsnirad.dto.response.TestResponseDTO;

import java.util.Date;

public final class TestResponseDtoUtil {
    public static TestResponseDTO generate() {
        return new TestResponseDTO(1L, "Subject Name", new Date().toString(), "Note");
    }
}
