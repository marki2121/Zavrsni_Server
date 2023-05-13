package com.example.zavrsnirad.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "test_application")
public class TestApplication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "test_id", nullable = false)
    private Test test;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User student;
    @Column(name = "test_grade")
    private Integer testGrade;
    @Column(name = "test_note")
    private String testNote;
    @Column(name = "test_graded")
    private Boolean testGraded;

    public TestApplication() {
    }

    public TestApplication(Long id, Test test, User student, Integer testGrade, String testNote, Boolean testGraded) {
        this.id = id;
        this.test = test;
        this.student = student;
        this.testGrade = testGrade;
        this.testNote = testNote;
        this.testGraded = testGraded;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Test getTest() {
        return test;
    }

    public void setTest(Test test) {
        this.test = test;
    }

    public User getStudent() {
        return student;
    }

    public void setStudent(User student) {
        this.student = student;
    }

    public Integer getTestGrade() {
        return testGrade;
    }

    public void setTestGrade(Integer testGrade) {
        this.testGrade = testGrade;
    }

    public String getTestNote() {
        return testNote;
    }

    public void setTestNote(String testNote) {
        this.testNote = testNote;
    }

    public Boolean getTestGraded() {
        return testGraded;
    }

    public void setTestGraded(Boolean testGraded) {
        this.testGraded = testGraded;
    }
}
