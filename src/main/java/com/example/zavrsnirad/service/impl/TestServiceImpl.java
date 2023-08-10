package com.example.zavrsnirad.service.impl;

import com.example.zavrsnirad.dto.request.TestCreateDTO;
import com.example.zavrsnirad.dto.response.TestApplicationResponseDTO;
import com.example.zavrsnirad.dto.response.TestResponseDTO;
import com.example.zavrsnirad.entity.CostumeErrorException;
import com.example.zavrsnirad.entity.Subject;
import com.example.zavrsnirad.entity.Test;
import com.example.zavrsnirad.entity.TestApplication;
import com.example.zavrsnirad.mapper.TestApplicationResponseDtoMapper;
import com.example.zavrsnirad.mapper.TestResponseDtoMapper;
import com.example.zavrsnirad.repository.TestRepository;
import com.example.zavrsnirad.service.SubjectGetService;
import com.example.zavrsnirad.service.TestApplicationService;
import com.example.zavrsnirad.service.TestService;
import com.example.zavrsnirad.service.UserGetService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Objects;

@Service
public class TestServiceImpl implements TestService {
    private final TestRepository testRepository;
    private final TestApplicationService testApplicationService;
    private final UserGetService userGetService;
    private final SubjectGetService subjectGetService;
    private final TestResponseDtoMapper testResponseDtoMapper;
    private final TestApplicationResponseDtoMapper testApplicationResponseDtoMapper;

    public TestServiceImpl(TestRepository testRepository, TestApplicationService testApplicationService, UserGetService userGetService, SubjectGetService subjectGetService, TestResponseDtoMapper testResponseDtoMapper, TestApplicationResponseDtoMapper testApplicationResponseDtoMapper) {
        this.testRepository = testRepository;
        this.testApplicationService = testApplicationService;
        this.userGetService = userGetService;
        this.subjectGetService = subjectGetService;
        this.testResponseDtoMapper = testResponseDtoMapper;
        this.testApplicationResponseDtoMapper = testApplicationResponseDtoMapper;
    }

    @Override
    public String createTest(String authorization, Long id, TestCreateDTO data) throws ParseException, CostumeErrorException {
        Subject subject = subjectGetService.getTeacherSubjectById(authorization, id);
        SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");

        if(subject.getTests().size() >= 4) throw new CostumeErrorException("Subject has 4 tests already", HttpStatus.BAD_REQUEST);
        if(data.date() == null) throw new CostumeErrorException("Date is required", HttpStatus.BAD_REQUEST);

        Test test = new Test();
        test.setSubject(subject);
        test.setTestDate(formater.parse(data.date()));
        test.setTestNote(data.note());

        testRepository.save(test);

        return "Test created";
    }

    @Override
    public List<TestResponseDTO> getTestsForSubject(String authorization, Long id) throws CostumeErrorException {
        return testResponseDtoMapper.map(subjectGetService.getTeacherSubjectById(authorization, id).getTests());
    }

    @Override
    public String deleteTest(String authorization, Long testId) throws CostumeErrorException {
        Test test = getTestById(authorization, testId);

        test.getTestApplication().forEach(testApplicationService::deleteApplicationEntity);

        testRepository.delete(test);

        return "Test deleted";
    }

    @Override
    public String updateTest(String authorization, Long testId, TestCreateDTO data) throws ParseException, CostumeErrorException {
        Test test = getTestById(authorization, testId);
        SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");

        if(data.date() != null) test.setTestDate(formater.parse(data.date()));
        if(data.note() != null) test.setTestNote(data.note());
        testRepository.save(test);

        return "Test updated";

    }

    @Override
    public List<TestApplicationResponseDTO> getAllTestsApplications(String authorization, Long testId) throws CostumeErrorException {
        return testApplicationResponseDtoMapper.map(getTestById(authorization, testId).getTestApplication());
    }

    @Override
    public String gradeTest(String authorization, Long applicationId, Integer grade) throws CostumeErrorException {
        TestApplication testApplication = testApplicationService.getTestApplicationById(authorization, applicationId);

        testApplication.setTestGrade(grade);
        testApplication.setTestGraded(true);

        return testApplicationService.saveTestApplication(testApplication);
    }

    @Override
    public List<TestResponseDTO> getAllTestesForSubject(String authorization, Long id) throws CostumeErrorException {
        return testResponseDtoMapper.map(
                userGetService.getUserFromToken(authorization)
                        .getSubjects().stream()
                        .filter((s) -> s.getId().equals(id))
                        .findFirst()
                        .orElseThrow(() -> new CostumeErrorException("Subject not found", HttpStatus.BAD_REQUEST))
                        .getTests());
    }

    @Override
    public List<TestApplicationResponseDTO> getAllAppliedTestsForStudent(String authorization, Long id) throws CostumeErrorException {
        return testApplicationResponseDtoMapper.map(testApplicationService.getAllTestApplicationsForUserAndSubject(userGetService.getUserFromToken(authorization), subjectGetService.getSubjectById(authorization, id)));
    }

    public Test getTestById(String auth, Long id) throws CostumeErrorException {
        Test test = testRepository.findById(id).orElseThrow(() -> new CostumeErrorException("Test not found", HttpStatus.BAD_REQUEST));

        if(!Objects.equals(test.getSubject().getSubjectProfessor().getId(), userGetService.getUserFromToken(auth).getId())) throw new CostumeErrorException("User is not a teacher of the subject the test is for", HttpStatus.BAD_REQUEST);

        return test;
    }
}