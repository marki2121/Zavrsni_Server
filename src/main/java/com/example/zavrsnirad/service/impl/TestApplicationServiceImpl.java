package com.example.zavrsnirad.service.impl;

import com.example.zavrsnirad.dto.response.TestApplicationResponseDTO;
import com.example.zavrsnirad.entity.Test;
import com.example.zavrsnirad.entity.TestApplication;
import com.example.zavrsnirad.entity.User;
import com.example.zavrsnirad.mapper.TestApplicationResponseDtoMapper;
import com.example.zavrsnirad.repository.SubjectRepository;
import com.example.zavrsnirad.repository.TestApplicationRepository;
import com.example.zavrsnirad.repository.TestRepository;
import com.example.zavrsnirad.repository.UserRepository;
import com.example.zavrsnirad.service.TestApplicationService;
import com.example.zavrsnirad.service.TokenService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TestApplicationServiceImpl implements TestApplicationService {
    private final TestApplicationRepository testApplicationRepository;
    private final TokenService tokenService;
    private final UserRepository userRepository;
    private final TestRepository testRepository;
    private final SubjectRepository subjectRepository;
    private final TestApplicationResponseDtoMapper testApplicationResponseDtoMapper;

    public TestApplicationServiceImpl(TestApplicationRepository testApplicationRepository, TokenService tokenService, UserRepository userRepository, TestRepository testRepository, SubjectRepository subjectRepository, TestApplicationResponseDtoMapper testApplicationResponseDtoMapper) {
        this.testApplicationRepository = testApplicationRepository;
        this.tokenService = tokenService;
        this.userRepository = userRepository;
        this.testRepository = testRepository;
        this.subjectRepository = subjectRepository;
        this.testApplicationResponseDtoMapper = testApplicationResponseDtoMapper;
    }

    @Override
    public ResponseEntity<Object> applyForTest(String authorization, Long testId) {
        String username = tokenService.getUsernameFromToken(authorization);
        Optional<User> user = userRepository.findByUsername(username);
        Optional<Test> test = testRepository.findById(testId);

        if(user.isEmpty()) return ResponseEntity.badRequest().body("User not found");
        if(test.isEmpty()) return ResponseEntity.badRequest().body("Test not found");
        if(!test.get().getSubject().getStudents().contains(user.get())){
            return ResponseEntity.badRequest().body("You are not enrolled in this subject");
        }

        Optional<TestApplication> testApplication = testApplicationRepository.findByStudentAndTest(user.get(), test.get());

        if(testApplication.isPresent()) return ResponseEntity.badRequest().body("You have already applied for this test");

        TestApplication newTestApplication = new TestApplication();
        newTestApplication.setStudent(user.get());
        newTestApplication.setTest(test.get());
        newTestApplication.setTestGraded(false);

        testApplicationRepository.save(newTestApplication);

        return ResponseEntity.ok("Successfully applied for test");
    }

    @Override
    public ResponseEntity<Object> getAllApplications(String authorization) {
        String username = tokenService.getUsernameFromToken(authorization);
        Optional<User> user = userRepository.findByUsername(username);

        if(user.isEmpty()) return ResponseEntity.badRequest().body("User not found");

        List<TestApplication> testApplications = testApplicationRepository.findAllByStudent(user.get());

        if(testApplications.isEmpty()) return ResponseEntity.badRequest().body("You have not applied for any tests");

        List<TestApplicationResponseDTO> testApplicationsResponse = new ArrayList<>();

        for(TestApplication testApplication : testApplications){
            testApplicationsResponse.add(testApplicationResponseDtoMapper.apply(testApplication));
        }

        return ResponseEntity.ok(testApplicationsResponse);
    }

    @Override
    public ResponseEntity<Object> deleteApplication(String authorization, Long applicationId) {
        String username = tokenService.getUsernameFromToken(authorization);
        Optional<User> user = userRepository.findByUsername(username);
        Optional<TestApplication> testApplication = testApplicationRepository.findById(applicationId);

        if(user.isEmpty()) return ResponseEntity.badRequest().body("User not found");
        if(testApplication.isEmpty()) return ResponseEntity.badRequest().body("Test application not found");
        if(!testApplication.get().getStudent().equals(user.get())) return ResponseEntity.badRequest().body("You are not the owner of this test application");

        testApplicationRepository.delete(testApplication.get());

        return ResponseEntity.ok("Successfully deleted test application");
    }
}
