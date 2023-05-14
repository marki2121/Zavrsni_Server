package com.example.zavrsnirad.entity;

import jakarta.persistence.*;

import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "tests")
public class Test {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @ManyToOne
    @JoinColumn(name = "subject_id", nullable = false)
    private Subject subject;
    @Column(name = "test_date", nullable = false)
    private Date testDate;
    @Column(name = "test_note")
    private String testNote;
    @OneToMany(mappedBy = "test")
    private Set<TestApplication> testApplication;

    public Test() {
    }

    public Test(Long id, Subject subject, Date testDate, String testNote) {
        this.id = id;
        this.subject = subject;
        this.testDate = testDate;
        this.testNote = testNote;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public Date getTestDate() {
        return testDate;
    }

    public void setTestDate(Date testDate) {
        this.testDate = testDate;
    }

    public String getTestNote() {
        return testNote;
    }

    public void setTestNote(String testNote) {
        this.testNote = testNote;
    }

    public Set<TestApplication> getTestApplication() {
        return testApplication;
    }
}
