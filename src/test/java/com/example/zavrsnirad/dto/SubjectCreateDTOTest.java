package com.example.zavrsnirad.dto;

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

    @Test
    @DisplayName("Test name")
    void name() {
        assertEquals("Subject Name", (new SubjectCreateDTO("Subject Name", "Subject Description", 1, 1, 1)).name());
    }

    @Test
    @DisplayName("Test description")
    void description() {
        assertEquals("Subject Description", (new SubjectCreateDTO("Subject Name", "Subject Description", 1, 1, 1)).description());
    }

    @Test
    @DisplayName("Test ects")
    void ects() {
        assertEquals(1, (new SubjectCreateDTO("Subject Name", "Subject Description", 1, 1, 1)).ects());
    }

    @Test
    @DisplayName("Test semester")
    void semester() {
        assertEquals(1, (new SubjectCreateDTO("Subject Name", "Subject Description", 1, 1, 1)).semester());
    }

    @Test
    @DisplayName("Test year")
    void year() {
        assertEquals(1, (new SubjectCreateDTO("Subject Name", "Subject Description", 1, 1, 1)).year());
    }
}