package com.example.zavrsnirad.dto;

import java.util.Date;

public record TestResponseDTO(
        Long id,
        String subjectName,
        Date date,
        String note
) {
}
