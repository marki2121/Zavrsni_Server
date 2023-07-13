package com.example.zavrsnirad.dto.response;

import java.util.Date;

public record TestResponseDTO(
        Long id,
        String subjectName,
        Date date,
        String note
) {
}
