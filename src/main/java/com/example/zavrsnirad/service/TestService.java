package com.example.zavrsnirad.service;

import com.example.zavrsnirad.dto.request.TestCreateDTO;
import org.springframework.http.ResponseEntity;

public interface TestService {
    ResponseEntity<Object> createTest(String authorization, Long id, TestCreateDTO data);

    ResponseEntity<Object> getTestsForSubject(String authorization, Long id);

    ResponseEntity<Object> deleteTest(String authorization, Long id, Long testId);

    ResponseEntity<Object> updateTest(String authorization, Long id, Long testId, TestCreateDTO data);

    ResponseEntity<Object> getAllTestsApplications(String authorization, Long id, Long testId);

    ResponseEntity<Object> gradeTest(String authorization, Long applicationId, Integer grade);
}
