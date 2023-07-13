package com.example.zavrsnirad.dto.request;

public record SubjectCreateDTO(
        String name,
        String description,
        Integer ects,
        Integer semester,
        Integer year) {
}
