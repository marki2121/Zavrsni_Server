package com.example.zavrsnirad.service.impl;

import com.example.zavrsnirad.appenum.Role;
import com.example.zavrsnirad.dto.request.TestCreateDTO;
import com.example.zavrsnirad.dto.response.TestApplicationResponseDTO;
import com.example.zavrsnirad.dto.response.TestResponseDTO;
import com.example.zavrsnirad.entity.Subject;
import com.example.zavrsnirad.entity.Test;
import com.example.zavrsnirad.entity.TestApplication;
import com.example.zavrsnirad.entity.User;
import com.example.zavrsnirad.mapper.TestApplicationResponseDtoMapper;
import com.example.zavrsnirad.mapper.TestResponseDtoMapper;
import com.example.zavrsnirad.repository.SubjectRepository;
import com.example.zavrsnirad.repository.TestApplicationRepository;
import com.example.zavrsnirad.repository.TestRepository;
import com.example.zavrsnirad.repository.UserRepository;
import com.example.zavrsnirad.service.TestService;
import com.example.zavrsnirad.service.TokenService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TestServiceImpl implements TestService {
    final private TestRepository testRepository;
    final private SubjectRepository subjectRepository;
    final private UserRepository userRepository;
    final private TokenService tokenService;
    final private TestResponseDtoMapper testResponseDtoMapper;
    final private TestApplicationResponseDtoMapper testApplicationResponseDtoMapper;
    final private TestApplicationRepository testApplicationRepository;

    public TestServiceImpl(TestRepository testRepository, SubjectRepository subjectRepository, UserRepository userRepository, TokenService tokenService, TestResponseDtoMapper testResponseDtoMapper, TestApplicationResponseDtoMapper testApplicationResponseDtoMapper, TestApplicationRepository testApplicationRepository) {
        this.testRepository = testRepository;
        this.subjectRepository = subjectRepository;
        this.userRepository = userRepository;
        this.tokenService = tokenService;
        this.testResponseDtoMapper = testResponseDtoMapper;
        this.testApplicationResponseDtoMapper = testApplicationResponseDtoMapper;
        this.testApplicationRepository = testApplicationRepository;
    }

    @Override
    public ResponseEntity<Object> createTest(String authorization, Long id, TestCreateDTO data) throws ParseException {
        String username = tokenService.getUsernameFromToken(authorization);
        Optional<User> user = userRepository.findByUsername(username);
        Optional<Subject> subject = subjectRepository.findById(id);
        SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");

        if(user.isEmpty()) return ResponseEntity.badRequest().body("User not found");
        if(user.get().getRole().equals(Role.STUDENT)) return ResponseEntity.badRequest().body("User is not a teacher");
        if(subject.isEmpty()) return ResponseEntity.badRequest().body("Subject not found");
        if(!subject.get().getSubjectProfessor().equals(user.get()) && !user.get().getRole().equals(Role.ADMIN)) return ResponseEntity.badRequest().body("User is not a teacher of this subject");

        if(subject.get().getTests().size() >= 4) return ResponseEntity.badRequest().body("Subject already has 4 tests");
        if(data.date() == null) return ResponseEntity.badRequest().body("Date is not valid");

        Test test = new Test();
        test.setSubject(subject.get());
        test.setTestDate(formater.parse(data.date()));
        if(data.note() != null) test.setTestNote(data.note());
        else test.setTestNote("");

        testRepository.save(test);

        return ResponseEntity.ok().body("Test created");
    }

    @Override
    public ResponseEntity<Object> getTestsForSubject(String authorization, Long id) {
        String username = tokenService.getUsernameFromToken(authorization);
        Optional<User> user = userRepository.findByUsername(username);
        Optional<Subject> subject = subjectRepository.findById(id);

        if(user.isEmpty()) return ResponseEntity.badRequest().body("User not found");
        if(subject.isEmpty()) return ResponseEntity.badRequest().body("Subject not found");
        if(!subject.get().getSubjectProfessor().equals(user.get()) && !user.get().getRole().equals(Role.ADMIN)) return ResponseEntity.badRequest().body("User is not a teacher of this subject");
        if(subject.get().getTests().isEmpty()) return ResponseEntity.badRequest().body("Subject has no tests");

        List<TestResponseDTO> tests = new ArrayList<>();

        for(Test test : subject.get().getTests()) {
            tests.add(testResponseDtoMapper.apply(test));
        }

        return ResponseEntity.ok().body(tests);
    }

    @Override
    public ResponseEntity<Object> deleteTest(String authorization, Long id, Long testId) {
        String username = tokenService.getUsernameFromToken(authorization);
        Optional<User> user = userRepository.findByUsername(username);
        Optional<Subject> subject = subjectRepository.findById(id);

        if(user.isEmpty()) return ResponseEntity.badRequest().body("User not found");
        if(user.get().getRole().equals(Role.STUDENT)) return ResponseEntity.badRequest().body("User is not a teacher");
        if(subject.isEmpty()) return ResponseEntity.badRequest().body("Subject not found");
        if(!subject.get().getSubjectProfessor().equals(user.get()) && !user.get().getRole().equals(Role.ADMIN)) return ResponseEntity.badRequest().body("User is not a teacher of this subject");
        if(subject.get().getTests().isEmpty()) return ResponseEntity.badRequest().body("Subject has no tests");

        for(Test test : subject.get().getTests()) {
            if(test.getId().equals(testId)) {
                testRepository.delete(test);
                return ResponseEntity.ok().body("Test deleted");
            }
        }

        return ResponseEntity.badRequest().body("Test not found");
    }

    @Override
    public ResponseEntity<Object> updateTest(String authorization, Long id, Long testId, TestCreateDTO data) throws ParseException {
        String username = tokenService.getUsernameFromToken(authorization);
        Optional<User> user = userRepository.findByUsername(username);
        Optional<Subject> subject = subjectRepository.findById(id);
        SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");

        if(user.isEmpty()) return ResponseEntity.badRequest().body("User not found");
        if(user.get().getRole().equals(Role.STUDENT)) return ResponseEntity.badRequest().body("User is not a teacher");
        if(subject.isEmpty()) return ResponseEntity.badRequest().body("Subject not found");
        if(!subject.get().getSubjectProfessor().equals(user.get()) && !user.get().getRole().equals(Role.ADMIN)) return ResponseEntity.badRequest().body("User is not a teacher of this subject");
        if(subject.get().getTests().isEmpty()) return ResponseEntity.badRequest().body("Subject has no tests");

        for(Test test : subject.get().getTests()) {
            if(test.getId().equals(testId)) {
                if(data.date() != null) test.setTestDate(formater.parse(data.date()));
                if(data.note() != null) test.setTestNote(data.note());
                testRepository.save(test);
                return ResponseEntity.ok().body("Test updated");
            }
        }
        return ResponseEntity.badRequest().body("Test not found");
    }

    @Override
    public ResponseEntity<Object> getAllTestsApplications(String authorization, Long id, Long testId) {
        String username = tokenService.getUsernameFromToken(authorization);
        Optional<User> user = userRepository.findByUsername(username);
        Optional<Subject> subject = subjectRepository.findById(id);

        if(user.isEmpty()) return ResponseEntity.badRequest().body("User not found");
        if(user.get().getRole().equals(Role.STUDENT)) return ResponseEntity.badRequest().body("User is not a teacher");
        if(subject.isEmpty()) return ResponseEntity.badRequest().body("Subject not found");
        if(!subject.get().getSubjectProfessor().equals(user.get()) && !user.get().getRole().equals(Role.ADMIN)) return ResponseEntity.badRequest().body("User is not a teacher of this subject");
        if(subject.get().getTests().isEmpty()) return ResponseEntity.badRequest().body("Subject has no tests");

        for(Test test : subject.get().getTests()) {
            if(test.getId().equals(testId)) {
                List<TestApplicationResponseDTO> applications = new ArrayList<>();
                for(TestApplication application : test.getTestApplication()) {
                    applications.add(testApplicationResponseDtoMapper.apply(application));
                }
                return ResponseEntity.ok().body(applications);
            }
        }

        return ResponseEntity.badRequest().body("Test not found");
    }

    @Override
    public ResponseEntity<Object> gradeTest(String authorization, Long applicationId, Integer grade) {
        String username = tokenService.getUsernameFromToken(authorization);
        Optional<User> user = userRepository.findByUsername(username);
        Optional<TestApplication> testApplication = testApplicationRepository.findById(applicationId);

        if(user.isEmpty()) return ResponseEntity.badRequest().body("User not found");
        if(user.get().getRole().equals(Role.STUDENT)) return ResponseEntity.badRequest().body("User is not a teacher");
        if(testApplication.isEmpty()) return ResponseEntity.badRequest().body("Test application not found");
        if(testApplication.get().getTest().getSubject().getSubjectProfessor().equals(user.get()) || user.get().getRole().equals(Role.ADMIN)) {
            testApplication.get().setTestGrade(grade);
            testApplication.get().setTestGraded(true);

            testApplicationRepository.save(testApplication.get());

            return ResponseEntity.ok().body("Test application graded");
        } else return ResponseEntity.badRequest().body("User is not a teacher of this subject");
    }
}
