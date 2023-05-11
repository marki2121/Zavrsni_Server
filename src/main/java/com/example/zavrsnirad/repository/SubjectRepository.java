package com.example.zavrsnirad.repository;

import com.example.zavrsnirad.entity.Subject;
import com.example.zavrsnirad.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long> {
    List<Subject> findAllBySubjectProfessor(User user);
}
