package com.example.zavrsnirad.dto;

import com.example.zavrsnirad.dto.request.SubjectCreateDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SubjectCreateDTOTest {

    @Test
    @DisplayName("Test constructor")
    void testConstructor() {
        SubjectCreateDTO actualSubjectCreateDTO = new SubjectCreateDTO("Subject Name", "Subject Description", 1, 1, 1);

        assertEquals("Subject Description", actualSubjectCreateDTO.description());
        assertEquals(1, actualSubjectCreateDTO.year());
        assertEquals(1, actualSubjectCreateDTO.semester());
        assertEquals(1, actualSubjectCreateDTO.ects());
        assertEquals("Subject Name", actualSubjectCreateDTO.name());
    }
}