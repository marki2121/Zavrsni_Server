package com.example.zavrsnirad.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class SubjectTest {

    @Test
    @DisplayName("Test getId")
    void getId() {
        Subject subject = new Subject();
        subject.setId(1L);

        assertEquals(1L, subject.getId());
    }

    @Test
    @DisplayName("Test setId")
    void setId() {
        Subject subject = new Subject();
        subject.setId(1L);
        subject.setId(2L);

        assertEquals(2L, subject.getId());
        assertNotEquals(1L, subject.getId());
    }

    @Test
    @DisplayName("Test getSubjectName")
    void getSubjectName() {
        Subject subject = new Subject();
        subject.setSubjectName("Subject");

        assertEquals("Subject", subject.getSubjectName());
    }

    @Test
    @DisplayName("Test setSubjectName")
    void setSubjectName() {
        Subject subject = new Subject();
        subject.setSubjectName("Subject");
        subject.setSubjectName("Subject2");

        assertEquals("Subject2", subject.getSubjectName());
        assertNotEquals("Subject", subject.getSubjectName());
    }

    @Test
    @DisplayName("Test getSubjectDescription")
    void getSubjectDescription() {
        Subject subject = new Subject();
        subject.setSubjectDescription("Description");

        assertEquals("Description", subject.getSubjectDescription());
    }

    @Test
    @DisplayName("Test setSubjectDescription")
    void setSubjectDescription() {
        Subject subject = new Subject();
        subject.setSubjectDescription("Description");
        subject.setSubjectDescription("Description2");

        assertEquals("Description2", subject.getSubjectDescription());
        assertNotEquals("Description", subject.getSubjectDescription());
    }

    @Test
    @DisplayName("Test getSubjectEcts")
    void getSubjectEcts() {
        Subject subject = new Subject();
        subject.setSubjectEcts(1);

        assertEquals(1, subject.getSubjectEcts());
    }

    @Test
    @DisplayName("Test setSubjectEcts")
    void setSubjectEcts() {
        Subject subject = new Subject();
        subject.setSubjectEcts(1);
        subject.setSubjectEcts(2);

        assertEquals(2, subject.getSubjectEcts());
        assertNotEquals(1, subject.getSubjectEcts());
    }

    @Test
    @DisplayName("Test getSubjectSemester")
    void getSubjectSemester() {
        Subject subject = new Subject();
        subject.setSubjectSemester(1);

        assertEquals(1, subject.getSubjectSemester());
    }

    @Test
    @DisplayName("Test setSubjectSemester")
    void setSubjectSemester() {
        Subject subject = new Subject();
        subject.setSubjectSemester(1);
        subject.setSubjectSemester(2);

        assertEquals(2, subject.getSubjectSemester());
        assertNotEquals(1, subject.getSubjectSemester());
    }

    @Test
    @DisplayName("Test getSubjectYear")
    void getSubjectYear() {
        Subject subject = new Subject();
        subject.setSubjectYear(1);

        assertEquals(1, subject.getSubjectYear());
    }

    @Test
    @DisplayName("Test setSubjectYear")
    void setSubjectYear() {
        Subject subject = new Subject();
        subject.setSubjectYear(1);
        subject.setSubjectYear(2);

        assertEquals(2, subject.getSubjectYear());
        assertNotEquals(1, subject.getSubjectYear());
    }

    @Test
    @DisplayName("Test getSubjectProfessor")
    void getSubjectProfessor() {
        Subject subject = new Subject();
        User professor = new User();
        professor.setId(1L);
        subject.setSubjectProfessor(professor);

        assertEquals(professor, subject.getSubjectProfessor());
    }

    @Test
    @DisplayName("Test setSubjectProfessor")
    void setSubjectProfessor() {
        Subject subject = new Subject();
        User professor = new User();
        professor.setId(1L);
        subject.setSubjectProfessor(professor);
        User professor2 = new User();
        professor2.setId(2L);
        subject.setSubjectProfessor(professor2);

        assertEquals(professor2, subject.getSubjectProfessor());
        assertNotEquals(professor, subject.getSubjectProfessor());
    }
}