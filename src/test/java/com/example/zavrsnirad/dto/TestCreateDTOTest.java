package com.example.zavrsnirad.dto;

import com.example.zavrsnirad.dto.request.TestCreateDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TestCreateDTOTest {
    @Test
    @DisplayName("Test constructor")
    void testConstructor() {
        Date date = new Date();
        TestCreateDTO actualTestCreateDTO = new TestCreateDTO(date.toString(), "test");

        assertEquals(date.toString(), actualTestCreateDTO.date());
        assertEquals("test", actualTestCreateDTO.note());
    }
}