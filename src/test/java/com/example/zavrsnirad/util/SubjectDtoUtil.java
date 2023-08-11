package com.example.zavrsnirad.util;

import com.example.zavrsnirad.dto.request.SubjectDTO;

import java.util.List;

public final class SubjectDtoUtil {
    public static SubjectDTO generate() {
        return new SubjectDTO(1L, "Subject", "Desc", 5, 5, 5);
    }

    public static List<SubjectDTO> generateList() {
        return List.of(generate());
    }
}
