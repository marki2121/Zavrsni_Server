package com.example.zavrsnirad.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "subject")
public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "subject_name")
    private String subjectName;
    @Column(name = "subject_description")
    private String subjectDescription;
    @Column(name = "subject_ects")
    private Integer subjectEcts;
    @Column(name = "subject_semester")
    private Integer subjectSemester;
    @Column(name = "subject_year")
    private Integer subjectYear;
    @Column(name = "subject_professor")
    @ManyToOne
    private User subjectProfessor;

    public Subject() {
    }

    public Subject(Long id, String subjectName, String subjectDescription, Integer subjectEcts, Integer subjectSemester, Integer subjectYear, User subjectProfessor) {
        this.id = id;
        this.subjectName = subjectName;
        this.subjectDescription = subjectDescription;
        this.subjectEcts = subjectEcts;
        this.subjectSemester = subjectSemester;
        this.subjectYear = subjectYear;
        this.subjectProfessor = subjectProfessor;
    }

    public Subject(String subjectName, String subjectDescription, Integer subjectEcts, Integer subjectSemester, Integer subjectYear, User subjectProfessor) {
        this.subjectName = subjectName;
        this.subjectDescription = subjectDescription;
        this.subjectEcts = subjectEcts;
        this.subjectSemester = subjectSemester;
        this.subjectYear = subjectYear;
        this.subjectProfessor = subjectProfessor;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getSubjectDescription() {
        return subjectDescription;
    }

    public void setSubjectDescription(String subjectDescription) {
        this.subjectDescription = subjectDescription;
    }

    public Integer getSubjectEcts() {
        return subjectEcts;
    }

    public void setSubjectEcts(Integer subjectEcts) {
        this.subjectEcts = subjectEcts;
    }

    public Integer getSubjectSemester() {
        return subjectSemester;
    }

    public void setSubjectSemester(Integer subjectSemester) {
        this.subjectSemester = subjectSemester;
    }

    public Integer getSubjectYear() {
        return subjectYear;
    }

    public void setSubjectYear(Integer subjectYear) {
        this.subjectYear = subjectYear;
    }

    public User getSubjectProfessor() {
        return subjectProfessor;
    }

    public void setSubjectProfessor(User subjectProfessor) {
        this.subjectProfessor = subjectProfessor;
    }
}
