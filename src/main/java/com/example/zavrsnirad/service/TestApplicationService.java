package com.example.zavrsnirad.service;

import com.example.zavrsnirad.dto.response.TestApplicationResponseDTO;
import com.example.zavrsnirad.entity.Subject;
import com.example.zavrsnirad.entity.TestApplication;
import com.example.zavrsnirad.entity.User;

import java.util.List;

public interface TestApplicationService {
    String applyForTest(String authorization, Long testId);

    List<TestApplicationResponseDTO> getAllApplications(String authorization);

    String deleteApplication(String authorization, Long applicationId);

    TestApplication getTestApplicationById(String authorization, Long id);

    String saveTestApplication(TestApplication testApplication);

    List<TestApplication> getAllTestApplicationsForUserAndSubject(User user, Subject subject);
}
