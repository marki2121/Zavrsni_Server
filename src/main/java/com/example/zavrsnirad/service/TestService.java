package com.example.zavrsnirad.service;

import com.example.zavrsnirad.dto.request.TestCreateDTO;
import com.example.zavrsnirad.dto.response.TestApplicationResponseDTO;
import com.example.zavrsnirad.dto.response.TestResponseDTO;
import com.example.zavrsnirad.entity.CostumeErrorException;

import java.text.ParseException;
import java.util.List;

public interface TestService {
    String createTest(String authorization, Long id, TestCreateDTO data) throws ParseException, CostumeErrorException;

    List<TestResponseDTO> getTestsForSubject(String authorization, Long id) throws CostumeErrorException;

    String deleteTest(String authorization, Long testId) throws CostumeErrorException;

    String updateTest(String authorization, Long testId, TestCreateDTO data) throws ParseException, CostumeErrorException;

    List<TestApplicationResponseDTO> getAllTestsApplications(String authorization, Long id) throws CostumeErrorException;

    String gradeTest(String authorization, Long applicationId, Integer grade) throws CostumeErrorException;

    List<TestResponseDTO> getAllTestesForSubject(String authorization, Long id) throws CostumeErrorException;

    List<TestApplicationResponseDTO> getAllAppliedTestsForStudent(String authorization, Long id) throws CostumeErrorException;

}
