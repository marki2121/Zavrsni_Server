package com.example.zavrsnirad.dto;

public record TestApplicationResponseDTO(
        Long id,
        String subjectName,
        String teacherName,
        String studentName,
        Integer grade,
        Boolean testGraded
) {
}
