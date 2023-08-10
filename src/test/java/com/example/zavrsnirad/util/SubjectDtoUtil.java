package com.example.zavrsnirad.util;

import com.example.zavrsnirad.dto.request.SubjectDTO;

public final class SubjectDtoUtil {
    public static SubjectDTO generate() {
        return new SubjectDTO(1L, "Subject", "Desc", 5, 5, 5);
    }
}
