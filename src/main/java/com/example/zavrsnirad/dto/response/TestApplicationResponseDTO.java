package com.example.zavrsnirad.dto.response;

public record TestApplicationResponseDTO(
        Long id,
        String subjectName,
        String teacherName,
        String studentName,
        Integer grade,
        Boolean testGraded
) {
}
