package com.example.zavrsnirad.dto.request;

public record TestApplicationsResonseDTO(
        String testDate,
        String testNote,
        Integer testGrade,
        String professorNote,
        Boolean testGraded
) {
}
