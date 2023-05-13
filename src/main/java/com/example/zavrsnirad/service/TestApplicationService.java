package com.example.zavrsnirad.service;

import org.springframework.http.ResponseEntity;

public interface TestApplicationService {
    ResponseEntity<Object> applyForTest(String authorization, Long testId);

    ResponseEntity<Object> getAllApplications(String authorization);

    ResponseEntity<Object> deleteApplication(String authorization, Long applicationId);
}
