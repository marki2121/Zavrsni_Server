package com.example.zavrsnirad.dto;

import com.example.zavrsnirad.dto.response.TestApplicationResponseDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TestApplicationResponseDTOTest {

    @Test
    @DisplayName("Test constructor")
    void testConstructor() {
        TestApplicationResponseDTO actualTestApplicationResponseDTO = new TestApplicationResponseDTO(1L, "Subject Name", "Teacher Name", "Student Name", "Teacher", "Student", 1, true, "Note");

        assertEquals("Teacher", actualTestApplicationResponseDTO.teacherName());
        assertEquals("Student", actualTestApplicationResponseDTO.studentName());
        assertEquals(1, actualTestApplicationResponseDTO.grade());
        assertEquals("Student Name", actualTestApplicationResponseDTO.subjectName());
        assertEquals(1L, actualTestApplicationResponseDTO.id());
        assertTrue(actualTestApplicationResponseDTO.testGraded());
    }
}