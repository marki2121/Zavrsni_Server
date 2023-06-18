package com.example.zavrsnirad.dto;

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

    @Test
    @DisplayName("Test id")
    void id() {
        assertEquals(1L, (new SubjectDTO(1L, "Subject Name", "Subject Description", 1, 1, 1)).id());
    }

    @Test
    @DisplayName("Test name")
    void name() {
        assertEquals("Subject Name", (new SubjectDTO(1L, "Subject Name", "Subject Description", 1, 1, 1)).Name());
    }

    @Test
    @DisplayName("Test description")
    void description() {
        assertEquals("Subject Description", (new SubjectDTO(1L, "Subject Name", "Subject Description", 1, 1, 1)).Description());
    }

    @Test
    @DisplayName("Test ects")
    void ects() {
        assertEquals(1, (new SubjectDTO(1L, "Subject Name", "Subject Description", 1, 1, 1)).Ects());
    }

    @Test
    @DisplayName("Test semester")
    void semester() {
        assertEquals(1, (new SubjectDTO(1L, "Subject Name", "Subject Description", 1, 1, 1)).Semester());
    }

    @Test
    @DisplayName("Test year")
    void year() {
        assertEquals(1, (new SubjectDTO(1L, "Subject Name", "Subject Description", 1, 1, 1)).Year());
    }
}