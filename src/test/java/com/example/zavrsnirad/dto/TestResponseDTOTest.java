package com.example.zavrsnirad.dto;

import com.example.zavrsnirad.dto.response.TestResponseDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

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
}