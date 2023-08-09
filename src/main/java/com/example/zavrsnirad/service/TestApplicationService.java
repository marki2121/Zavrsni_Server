package com.example.zavrsnirad.service;

import com.example.zavrsnirad.dto.response.TestApplicationResponseDTO;
import com.example.zavrsnirad.entity.CostumeErrorException;
import com.example.zavrsnirad.entity.Subject;
import com.example.zavrsnirad.entity.TestApplication;
import com.example.zavrsnirad.entity.User;

import java.util.List;

public interface TestApplicationService {
    String applyForTest(String authorization, Long testId) throws CostumeErrorException;

    List<TestApplicationResponseDTO> getAllApplications(String authorization) throws CostumeErrorException;

    String deleteApplication(String authorization, Long applicationId) throws CostumeErrorException;

    TestApplication getTestApplicationById(String authorization, Long id) throws CostumeErrorException;

    String saveTestApplication(TestApplication testApplication);

    List<TestApplication> getAllTestApplicationsForUserAndSubject(User user, Subject subject);
}
