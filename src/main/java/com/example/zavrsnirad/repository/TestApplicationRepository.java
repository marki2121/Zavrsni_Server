package com.example.zavrsnirad.repository;

import com.example.zavrsnirad.entity.Test;
import com.example.zavrsnirad.entity.TestApplication;
import com.example.zavrsnirad.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TestApplicationRepository extends JpaRepository<TestApplication, Long> {
    Optional<TestApplication> findByStudentAndTest(User user, Test test);

    List<TestApplication> findAllByStudent(User user);

    @Query(name = "SELECT * FROM test_application WHERE test_id = ?1",
            nativeQuery = true)
    Optional<List<TestApplication>> findByTestId(Long testId);
}
