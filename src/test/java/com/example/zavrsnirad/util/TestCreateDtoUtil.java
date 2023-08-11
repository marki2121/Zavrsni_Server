package com.example.zavrsnirad.util;

import com.example.zavrsnirad.dto.request.TestCreateDTO;

public final class TestCreateDtoUtil {
    public static TestCreateDTO generate() {
        return new TestCreateDTO(
                "2021-01-01",
                "note");
    }

    public static TestCreateDTO generateNullDate() {
        return new TestCreateDTO(
                null,
                "note");
    }
}
