package com.example.zavrsnirad.dto.request;

public record TestApplicationsResonseDTO(
        Long id,
        String testDate,
        String testNote,
        Integer testGrade,
        String professorNote,
        Boolean testGraded
) {
}
