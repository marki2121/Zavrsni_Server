package com.example.zavrsnirad.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TestApplicationTest {

    @Test
    @DisplayName("Test TestApplication getId")
    void getId() {
        TestApplication testApplication = new TestApplication();
        testApplication.setId(1L);

        assertEquals(1L, testApplication.getId().longValue());
    }

    @Test
    @DisplayName("Test TestApplication setId")
    void setId() {
        TestApplication testApplication = new TestApplication();
        testApplication.setId(1L);
        testApplication.setId(2L);

        assertEquals(2L, testApplication.getId().longValue());
        assertFalse(testApplication.getId().equals(1L));
    }

    @Test
    @DisplayName("Test TestApplication getTest")
    void getTest() {
        TestApplication testApplication = new TestApplication();
        com.example.zavrsnirad.entity.Test test = new com.example.zavrsnirad.entity.Test();
        testApplication.setTest(test);

        assertEquals(test, testApplication.getTest());
    }

    @Test
    @DisplayName("Test TestApplication setTest")
    void setTest() {
        TestApplication testApplication = new TestApplication();
        com.example.zavrsnirad.entity.Test test = new com.example.zavrsnirad.entity.Test();
        testApplication.setTest(test);
        com.example.zavrsnirad.entity.Test test2 = new com.example.zavrsnirad.entity.Test();
        testApplication.setTest(test2);

        assertEquals(test2, testApplication.getTest());
        assertFalse(testApplication.getTest().equals(test));
    }

    @Test
    @DisplayName("Test TestApplication getStudent")
    void getStudent() {
        TestApplication testApplication = new TestApplication();
        User student = new User();
        testApplication.setStudent(student);

        assertEquals(student, testApplication.getStudent());
    }

    @Test
    @DisplayName("Test TestApplication setStudent")
    void setStudent() {
        TestApplication testApplication = new TestApplication();
        User student = new User();
        testApplication.setStudent(student);
        User student2 = new User();
        testApplication.setStudent(student2);

        assertEquals(student2, testApplication.getStudent());
        assertFalse(testApplication.getStudent().equals(student));
    }

    @Test
    @DisplayName("Test TestApplication getTestGrade")
    void getTestGrade() {
        TestApplication testApplication = new TestApplication();
        testApplication.setTestGrade(5);

        assertEquals(5, testApplication.getTestGrade());
    }

    @Test
    @DisplayName("Test TestApplication setTestGrade")
    void setTestGrade() {
        TestApplication testApplication = new TestApplication();
        testApplication.setTestGrade(5);
        testApplication.setTestGrade(6);

        assertEquals(6, testApplication.getTestGrade());
        assertNotEquals(5, (int) testApplication.getTestGrade());
    }

    @Test
    @DisplayName("Test TestApplication getTestNote")
    void getTestNote() {
        TestApplication testApplication = new TestApplication();
        testApplication.setTestNote("Test note");

        assertEquals("Test note", testApplication.getTestNote());
    }

    @Test
    @DisplayName("Test TestApplication setTestNote")
    void setTestNote() {
        TestApplication testApplication = new TestApplication();
        testApplication.setTestNote("Test note");
        testApplication.setTestNote("Test note 2");

        assertEquals("Test note 2", testApplication.getTestNote());
        assertNotEquals("Test note", testApplication.getTestNote());
    }

    @Test
    @DisplayName("Test TestApplication getTestGraded")
    void getTestGraded() {
        TestApplication testApplication = new TestApplication();
        testApplication.setTestGraded(true);

        assertTrue(testApplication.getTestGraded());
    }

    @Test
    @DisplayName("Test TestApplication setTestGraded")
    void setTestGraded() {
        TestApplication testApplication = new TestApplication();
        testApplication.setTestGraded(true);
        testApplication.setTestGraded(false);

        assertFalse(testApplication.getTestGraded());
        assertNotEquals(true, testApplication.getTestGraded());
    }

    @Test
    @DisplayName("Test TestApplication constructor")
    void constructor() {
        TestApplication testApplication = new TestApplication(1L, new com.example.zavrsnirad.entity.Test(), new User(), 5, "Test note", true);

        assertEquals(1L, testApplication.getId().longValue());
        assertEquals(5, testApplication.getTestGrade());
        assertEquals("Test note", testApplication.getTestNote());
        assertTrue(testApplication.getTestGraded());
    }
}