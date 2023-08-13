package com.example.zavrsnirad.repository;

import com.example.zavrsnirad.entity.Subject;
import com.example.zavrsnirad.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long> {
    List<Subject> findAllBySubjectProfessor(User user);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM user_subject where user_subject.subject_id=?1",
            nativeQuery = true)
    void deleteUserLink(Long id);
}
