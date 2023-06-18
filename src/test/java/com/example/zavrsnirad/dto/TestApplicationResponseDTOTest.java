package com.example.zavrsnirad.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TestApplicationResponseDTOTest {

    @Test
    @DisplayName("Test constructor")
    void testConstructor() {
        TestApplicationResponseDTO actualTestApplicationResponseDTO = new TestApplicationResponseDTO(1L, "Subject Name", "Teacher Name", "Student Name", 1, true);

        assertEquals("Teacher Name", actualTestApplicationResponseDTO.teacherName());
        assertEquals("Student Name", actualTestApplicationResponseDTO.studentName());
        assertEquals(1, actualTestApplicationResponseDTO.grade());
        assertEquals("Subject Name", actualTestApplicationResponseDTO.subjectName());
        assertEquals(1L, actualTestApplicationResponseDTO.id());
        assertTrue(actualTestApplicationResponseDTO.testGraded());
    }

    @Test
    @DisplayName("Test id")
    void id() {
        assertEquals(1L, (new TestApplicationResponseDTO(1L, "Subject Name", "Teacher Name", "Student Name", 1, true)).id());
    }

    @Test
    @DisplayName("Test subjectName")
    void subjectName() {
        assertEquals("Subject Name", (new TestApplicationResponseDTO(1L, "Subject Name", "Teacher Name", "Student Name", 1, true)).subjectName());
    }

    @Test
    @DisplayName("Test teacherName")
    void teacherName() {
        assertEquals("Teacher Name", (new TestApplicationResponseDTO(1L, "Subject Name", "Teacher Name", "Student Name", 1, true)).teacherName());
    }

    @Test
    @DisplayName("Test studentName")
    void studentName() {
        assertEquals("Student Name", (new TestApplicationResponseDTO(1L, "Subject Name", "Teacher Name", "Student Name", 1, true)).studentName());
    }

    @Test
    @DisplayName("Test grade")
    void grade() {
        assertEquals(1, (new TestApplicationResponseDTO(1L, "Subject Name", "Teacher Name", "Student Name", 1, true)).grade());
    }

    @Test
    @DisplayName("Test testGraded")
    void testGraded() {
        assertTrue((new TestApplicationResponseDTO(1L, "Subject Name", "Teacher Name", "Student Name", 1, true)).testGraded());
    }
}