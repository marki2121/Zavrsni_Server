package com.example.zavrsnirad.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class TestTest {

    @Test
    @DisplayName("Test getId")
    void getId() {
        com.example.zavrsnirad.entity.Test test = new com.example.zavrsnirad.entity.Test();
        test.setId(1L);

        assertEquals(1L, test.getId());
    }

    @Test
    @DisplayName("Test setId")
    void setId() {
        com.example.zavrsnirad.entity.Test test = new com.example.zavrsnirad.entity.Test();
        test.setId(1L);
        test.setId(2L);

        assertEquals(2L, test.getId());
        assertNotEquals(1L, test.getId());
    }

    @Test
    @DisplayName("Test getSubject")
    void getSubject() {
        com.example.zavrsnirad.entity.Test test = new com.example.zavrsnirad.entity.Test();
        Subject subject = new Subject();
        subject.setId(1L);
        test.setSubject(subject);

        assertEquals(subject, test.getSubject());
    }

    @Test
    @DisplayName("Test setSubject")
    void setSubject() {
        com.example.zavrsnirad.entity.Test test = new com.example.zavrsnirad.entity.Test();
        Subject subject = new Subject();
        subject.setId(1L);
        test.setSubject(subject);

        assertEquals(subject, test.getSubject());
    }

    @Test
    @DisplayName("Test getTestDate")
    void getTestDate() {
        com.example.zavrsnirad.entity.Test test = new com.example.zavrsnirad.entity.Test();
        test.setTestDate(new java.util.Date());

        assertEquals(new java.util.Date(), test.getTestDate());
    }

    @Test
    @DisplayName("Test setTestDate")
    void setTestDate() {
        com.example.zavrsnirad.entity.Test test = new com.example.zavrsnirad.entity.Test();
        test.setTestDate(new java.util.Date());

        assertEquals(new java.util.Date(), test.getTestDate());
    }

    @Test
    @DisplayName("Test getTestNote")
    void getTestNote() {
        com.example.zavrsnirad.entity.Test test = new com.example.zavrsnirad.entity.Test();
        test.setTestNote("Test note");

        assertEquals("Test note", test.getTestNote());
    }

    @Test
    @DisplayName("Test setTestNote")
    void setTestNote() {
        com.example.zavrsnirad.entity.Test test = new com.example.zavrsnirad.entity.Test();
        test.setTestNote("Test note");

        assertEquals("Test note", test.getTestNote());
    }

    @Test
    @DisplayName("Test constructor")
    void constructor() {
        Subject subject = new Subject();
        Date date = new Date();
        subject.setId(1L);
        com.example.zavrsnirad.entity.Test test = new com.example.zavrsnirad.entity.Test(1L, subject, date, "Test note", List.of());

        assertEquals(1L, test.getId());
        assertEquals(subject, test.getSubject());
        assertEquals(date, test.getTestDate());
        assertEquals("Test note", test.getTestNote());
    }
}