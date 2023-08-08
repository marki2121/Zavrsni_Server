package com.example.zavrsnirad.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name = "subject")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
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
    @ManyToOne
    @JoinColumn(name = "subject_professor")
    private User subjectProfessor;
    @ManyToMany
    @JoinTable(
            name = "user_subject",
            joinColumns = @JoinColumn(name = "subject_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> students;
    @OneToMany(mappedBy = "subject")
    private Set<Test> tests;

}
