package com.example.zavrsnirad.util;

import com.example.zavrsnirad.dto.response.TestResponseDTO;

import java.util.Date;
import java.util.List;

public final class TestResponseDtoUtil {
    public static TestResponseDTO generate() {
        return new TestResponseDTO(1L, "Subject Name", new Date().toString(), "Note");
    }

    public static List<TestResponseDTO> generateList() {
        return List.of(generate());
    }
}
