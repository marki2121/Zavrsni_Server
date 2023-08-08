package com.example.zavrsnirad.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "test_application")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
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
}
