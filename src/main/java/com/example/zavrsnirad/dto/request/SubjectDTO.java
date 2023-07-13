package com.example.zavrsnirad.dto.request;

public record SubjectDTO(
        Long id,
        String Name,
        String Description,
        Integer Ects,
        Integer Semester,
        Integer Year) {
}
