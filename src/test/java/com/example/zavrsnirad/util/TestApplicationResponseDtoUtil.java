package com.example.zavrsnirad.util;

import com.example.zavrsnirad.dto.response.TestApplicationResponseDTO;

public final class TestApplicationResponseDtoUtil {
    public static TestApplicationResponseDTO generate() {
        return new TestApplicationResponseDTO(1L, "Subject Name", "Teacher Name", "Student Name", "Teacher", "Student", 1, true, "Note");
    }
}
