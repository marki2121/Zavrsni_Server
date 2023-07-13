package com.example.zavrsnirad.dto;

import com.example.zavrsnirad.dto.response.TestResponseDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class TestResponseDTOTest {
    @Test
    @DisplayName("TestResponseDTO constructor")
    void testResponseDTO() {
        TestResponseDTO testResponseDTO = new TestResponseDTO(1L, "subjectName", null, "note");
        assertEquals(1L, testResponseDTO.id());
        assertEquals("subjectName", testResponseDTO.subjectName());
        assertNull(testResponseDTO.date());
        assertEquals("note", testResponseDTO.note());
    }

    @Test
    @DisplayName("TestResponseDTO id")
    void id() {
        assertEquals(1L, new TestResponseDTO(1L, null, null, null).id());
    }

    @Test
    @DisplayName("TestResponseDTO subjectName")
    void subjectName() {
        assertEquals("subjectName", new TestResponseDTO(null, "subjectName", null, null).subjectName());
    }

    @Test
    @DisplayName("TestResponseDTO date")
    void date() {
        Date date = new Date();
        assertEquals(date, new TestResponseDTO(null, null, date, null).date());
    }

    @Test
    @DisplayName("TestResponseDTO note")
    void note() {
        assertEquals("note", new TestResponseDTO(null, null, null, "note").note());
    }
}