package com.example.zavrsnirad.service.impl;

import com.example.zavrsnirad.appenum.Role;
import com.example.zavrsnirad.dto.response.TestApplicationResponseDTO;
import com.example.zavrsnirad.entity.*;
import com.example.zavrsnirad.mapper.TestApplicationResponseDtoMapper;
import com.example.zavrsnirad.repository.TestApplicationRepository;
import com.example.zavrsnirad.service.TestApplicationService;
import com.example.zavrsnirad.service.TestGetService;
import com.example.zavrsnirad.service.UserGetService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestApplicationServiceImpl implements TestApplicationService {
    private final TestApplicationRepository testApplicationRepository;
    private final TestGetService testService;
    private final UserGetService userGetService;
    private final TestApplicationResponseDtoMapper testApplicationResponseDtoMapper;

    public TestApplicationServiceImpl(TestApplicationRepository testApplicationRepository, TestGetService testService, UserGetService userGetService, TestApplicationResponseDtoMapper testApplicationResponseDtoMapper) {
        this.testApplicationRepository = testApplicationRepository;
        this.testService = testService;
        this.userGetService = userGetService;
        this.testApplicationResponseDtoMapper = testApplicationResponseDtoMapper;
    }

    @Override
    public String applyForTest(String authorization, Long testId) throws CostumeErrorException {
        User user = userGetService.getUserFromToken(authorization);
        Test test = testService.getTestForUser(authorization, testId);

        if(testApplicationRepository.findByStudentAndTest(user, test).isPresent()) throw new CostumeErrorException("User already applied for the test");

        TestApplication newTestApplication = new TestApplication();
        newTestApplication.setStudent(user);
        newTestApplication.setTest(test);
        newTestApplication.setTestGraded(false);

        testApplicationRepository.save(newTestApplication);

        return "Applied for test";
    }

    @Override
    public List<TestApplicationResponseDTO> getAllApplications(String authorization) throws CostumeErrorException {
        return testApplicationResponseDtoMapper.map(testApplicationRepository.findAllByStudent(userGetService.getUserFromToken(authorization)));
    }

    @Override
    public String deleteApplication(String authorization, Long applicationId) throws CostumeErrorException {
        User user = userGetService.getUserFromToken(authorization);
        TestApplication testApplication = testApplicationRepository.findById(applicationId).orElseThrow(() -> new CostumeErrorException("Test application not found", HttpStatus.BAD_REQUEST));

        if(!testApplication.getStudent().equals(user)) throw new CostumeErrorException("You are not the owner of this test application", HttpStatus.BAD_REQUEST);

        testApplicationRepository.delete(testApplication);

        return "Successfully deleted test application";
    }

    @Override
    public TestApplication getTestApplicationById(String authorization, Long id) throws CostumeErrorException {
        User user = userGetService.getUserFromToken(authorization);
        TestApplication testApplication = testApplicationRepository.findById(id).orElseThrow(() -> new CostumeErrorException("Test application not found", HttpStatus.BAD_REQUEST));

        if(!testApplication.getStudent().equals(user) && user.getRole().equals(Role.STUDENT)) throw new CostumeErrorException("You are not the owner of this test application", HttpStatus.BAD_REQUEST);

        return testApplication;
    }

    @Override
    public String saveTestApplication(TestApplication testApplication) {
        testApplicationRepository.save(testApplication);
        return "Test application saved";
    }

    @Override
    public List<TestApplication> getAllTestApplicationsForUserAndSubject(User user, Subject subject) {
        return testApplicationRepository.findAllByStudentAndTest_Subject(user, subject);
    }
}
