package com.example.zavrsnirad.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TestCreateDTOTest {
    @Test
    @DisplayName("Test constructor")
    void testConstructor() {
        Date date = new Date();
        TestCreateDTO actualTestCreateDTO = new TestCreateDTO(date, "test");

        assertEquals(date, actualTestCreateDTO.date());
        assertEquals("test", actualTestCreateDTO.note());
    }

    @Test
    @DisplayName("Test date")
    void date() {
        Date date = new Date();
        assertEquals(date, (new TestCreateDTO(date, "test")).date());
    }

    @Test
    @DisplayName("Test note")
    void note() {
        assertEquals("test", (new TestCreateDTO(new Date(), "test")).note());
    }
}