package com.example.zavrsnirad.dto.request;

import java.util.Date;

public record TestCreateDTO(
        Date date,
        String note
) {
}
