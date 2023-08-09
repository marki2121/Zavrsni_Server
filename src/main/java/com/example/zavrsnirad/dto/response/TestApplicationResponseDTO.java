package com.example.zavrsnirad.dto.response;

public record TestApplicationResponseDTO(
        Long id,
        String testDate,
        String testNote,
        String subjectName,
        String teacherName,
        String studentName,
        Integer grade,
        Boolean testGraded
) {
}
