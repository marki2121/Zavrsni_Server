package com.example.zavrsnirad.util;

import com.example.zavrsnirad.dto.request.SubjectCreateDTO;

public final class SubjectCreateDtoUtil {
    public static SubjectCreateDTO generate() {
        return new SubjectCreateDTO("name", "description", 1, 1, 1);
    }

    public static SubjectCreateDTO generate2() {
        return new SubjectCreateDTO("name2", "description2", 2, 2, 2);
    }
}
