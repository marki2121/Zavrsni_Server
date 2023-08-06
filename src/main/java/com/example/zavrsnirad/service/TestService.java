package com.example.zavrsnirad.service;

import com.example.zavrsnirad.dto.request.TestCreateDTO;
import org.springframework.http.ResponseEntity;

import java.text.ParseException;

public interface TestService {
    ResponseEntity<Object> createTest(String authorization, Long id, TestCreateDTO data) throws ParseException;

    ResponseEntity<Object> getTestsForSubject(String authorization, Long id);

    ResponseEntity<Object> deleteTest(String authorization, Long id, Long testId);

    ResponseEntity<Object> updateTest(String authorization, Long id, Long testId, TestCreateDTO data) throws ParseException;

    ResponseEntity<Object> getAllTestsApplications(String authorization, Long id);

    ResponseEntity<Object> gradeTest(String authorization, Long applicationId, Integer grade);

    ResponseEntity<Object> getAllTestesForSubject(String authorization, Long id);

    ResponseEntity<Object> getAllAppliedTestsForStudent(String authorization, Long id);
}
