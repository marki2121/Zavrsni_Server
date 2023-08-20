package com.example.zavrsnirad.service.impl;

import com.example.zavrsnirad.entity.CostumeErrorException;
import com.example.zavrsnirad.mapper.TestApplicationResponseDtoMapper;
import com.example.zavrsnirad.mapper.TestResponseDtoMapper;
import com.example.zavrsnirad.repository.TestRepository;
import com.example.zavrsnirad.service.SubjectGetService;
import com.example.zavrsnirad.service.TestApplicationService;
import com.example.zavrsnirad.service.TestService;
import com.example.zavrsnirad.service.UserGetService;
import com.example.zavrsnirad.util.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.text.ParseException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@SpringBootTest
class TestServiceImplTest {

    @Autowired
    private TestService testService;
    @MockBean
    private SubjectGetService subjectGetService;
    @MockBean
    private TestRepository testRepository;
    @MockBean
    private TestResponseDtoMapper testResponseDtoMapper;
    @MockBean
    private UserGetService userGetService;
    @MockBean
    private TestApplicationService testApplicationService;
    @MockBean
    private TestApplicationResponseDtoMapper testApplicationResponseDtoMapper;

    @Test
    @DisplayName("Test createTest - success")
    void createTest() throws CostumeErrorException, ParseException {
        when(subjectGetService.getTeacherSubjectById(any(), any())).thenReturn(SubjectUtil.generate());
        when(testRepository.save(any())).thenReturn(null);
        assertEquals("Test created", testService.createTest("token", 1L, TestCreateDtoUtil.generate()));
    }

    @Test
    @DisplayName("Test createTest - subject has 4 tests already")
    void createTestSubjectHas4TestsAlready() throws CostumeErrorException, ParseException {
        when(subjectGetService.getTeacherSubjectById(any(), any())).thenReturn(SubjectUtil.generateMaxTests());
        assertThrows(CostumeErrorException.class, () -> testService.createTest("token", 1L, TestCreateDtoUtil.generate()));
    }

    @Test
    @DisplayName("Test createTest - date is required")
    void createTestDateIsRequired() throws CostumeErrorException, ParseException {
        when(subjectGetService.getTeacherSubjectById(any(), any())).thenReturn(SubjectUtil.generate());
        assertThrows(CostumeErrorException.class, () -> testService.createTest("token", 1L, TestCreateDtoUtil.generateNullDate()));
    }

    @Test
    @DisplayName("Test getTestsForSubject - success")
    void getTestsForSubject() throws CostumeErrorException {
        when(subjectGetService.getTeacherSubjectById(any(), any())).thenReturn(SubjectUtil.generate());
        when(testResponseDtoMapper.map(any())).thenReturn(TestResponseDtoUtil.generateList());
    }

    @Test
    @DisplayName("Test deleteTest - success")
    void deleteTest() throws CostumeErrorException {
        when(testRepository.findById(any())).thenReturn(Optional.of(TestUtil.generate()));
        when(userGetService.getUserFromToken(any())).thenReturn(UserUtil.generateTeacher());
        doNothing().when(testApplicationService).deleteApplicationEntity(any());
        doNothing().when(testRepository).delete(any());
        assertEquals(testService.deleteTest("token", 1L), "Test deleted");
    }

    @Test
    @DisplayName("Test updateTest - success")
    void updateTest() throws CostumeErrorException, ParseException {
        when(testRepository.findById(any())).thenReturn(Optional.of(TestUtil.generate()));
        when(userGetService.getUserFromToken(any())).thenReturn(UserUtil.generateTeacher());
        when(testRepository.save(any())).thenReturn(TestUtil.generate());
        assertEquals(testService.updateTest("token", 1L, TestCreateDtoUtil.generate()), "Test updated");
    }

    @Test
    @DisplayName("Test getAllTestsApplications - success")
    void getAllTestsApplications() throws CostumeErrorException {
        when(testRepository.findById(any())).thenReturn(Optional.of(TestUtil.generate()));
        when(userGetService.getUserFromToken(any())).thenReturn(UserUtil.generateTeacher());
        when(testApplicationResponseDtoMapper.map(any())).thenReturn(TestApplicationResponseDtoUtil.generateList());
        assertEquals(testService.getAllTestsApplications("token", 1L), TestApplicationResponseDtoUtil.generateList());
    }

    @Test
    @DisplayName("Test gradeTest - success")
    void gradeTest() throws CostumeErrorException {
        when(testApplicationService.getTestApplicationById(any(), any())).thenReturn(TestApplicationUtil.generate());
        when(testApplicationService.saveTestApplication(any())).thenReturn("Saved");
        assertEquals(testService.gradeTest("token", 1L, 1), "Saved");
    }

    @Test
    @DisplayName("Test getAllTestesForSubject - success")
    void getAllTestesForSubject() throws CostumeErrorException {
        when(userGetService.getUserFromToken(any())).thenReturn(UserUtil.generateTeacherWithSubject());
        when(testResponseDtoMapper.map(any())).thenReturn(TestResponseDtoUtil.generateList());
        assertEquals(testService.getAllTestesForSubject("token", 1L), TestResponseDtoUtil.generateList());
    }

    @Test
    @DisplayName("Test getAllAppliedTestsForStudent - success")
    void getAllAppliedTestsForStudent() throws CostumeErrorException {
        when(userGetService.getUserFromToken(any())).thenReturn(UserUtil.generate());
        when(subjectGetService.getSubjectById(any(), any())).thenReturn(SubjectUtil.generate());
        when(testApplicationService.getAllTestApplicationsForUserAndSubject(any(), any())).thenReturn(TestApplicationUtil.generateList());
        when(testApplicationResponseDtoMapper.map(any())).thenReturn(TestApplicationResponseDtoUtil.generateList());
        assertEquals(testService.getAllAppliedTestsForStudent("token", 1L), TestApplicationResponseDtoUtil.generateList());
    }
}