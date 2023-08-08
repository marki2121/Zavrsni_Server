package com.example.zavrsnirad.service;

import com.example.zavrsnirad.dto.request.TestCreateDTO;
import com.example.zavrsnirad.dto.response.TestApplicationResponseDTO;
import com.example.zavrsnirad.dto.response.TestResponseDTO;

import java.text.ParseException;
import java.util.List;

public interface TestService {
    String createTest(String authorization, Long id, TestCreateDTO data) throws ParseException;

    List<TestResponseDTO> getTestsForSubject(String authorization, Long id);

    String deleteTest(String authorization, Long testId);

    String updateTest(String authorization, Long testId, TestCreateDTO data) throws ParseException;

    List<TestApplicationResponseDTO> getAllTestsApplications(String authorization, Long id);

    String gradeTest(String authorization, Long applicationId, Integer grade);

    List<TestResponseDTO> getAllTestesForSubject(String authorization, Long id);

    List<TestApplicationResponseDTO> getAllAppliedTestsForStudent(String authorization, Long id);
}
