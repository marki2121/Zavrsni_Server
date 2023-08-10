package com.example.zavrsnirad.dto;

import com.example.zavrsnirad.dto.request.SubjectDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SubjectDTOTest {

    @Test
    @DisplayName("Test constructor")
    void testConstructor() {
        SubjectDTO actualSubjectDTO = new SubjectDTO(1L, "Subject Name", "Subject Description", 1, 1, 1);

        assertEquals("Subject Description", actualSubjectDTO.Description());
        assertEquals(1, actualSubjectDTO.Year());
        assertEquals(1, actualSubjectDTO.Semester());
        assertEquals(1, actualSubjectDTO.Ects());
        assertEquals("Subject Name", actualSubjectDTO.Name());
        assertEquals(1L, actualSubjectDTO.id());
    }

}