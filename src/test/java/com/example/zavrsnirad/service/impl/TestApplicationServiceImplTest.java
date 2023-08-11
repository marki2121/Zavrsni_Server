package com.example.zavrsnirad.service.impl;

import com.example.zavrsnirad.entity.CostumeErrorException;
import com.example.zavrsnirad.repository.TestApplicationRepository;
import com.example.zavrsnirad.service.TestApplicationService;
import com.example.zavrsnirad.service.TestGetService;
import com.example.zavrsnirad.service.UserGetService;
import com.example.zavrsnirad.util.SubjectUtil;
import com.example.zavrsnirad.util.TestApplicationUtil;
import com.example.zavrsnirad.util.TestUtil;
import com.example.zavrsnirad.util.UserUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@SpringBootTest
class TestApplicationServiceImplTest {

    @Autowired
    private TestApplicationService testApplicationService;
    @MockBean
    private UserGetService userGetService;
    @MockBean
    private TestGetService testService;
    @MockBean
    private TestApplicationRepository testApplicationRepository;

    @Test
    @DisplayName("Test applyForTest - success")
    void applyForTest() throws CostumeErrorException {
        //when
        when(userGetService.getUserFromToken(any())).thenReturn(UserUtil.generate());
        when(testService.getTestForUser(any(), any())).thenReturn(TestUtil.generate());
        when(testApplicationRepository.findByStudentAndTest(any(), any())).thenReturn(Optional.empty());
        when(testApplicationRepository.save(any())).thenReturn(TestApplicationUtil.generate());

        //then
        assertEquals("Applied for test", testApplicationService.applyForTest("token", 1L));
    }

    @Test
    @DisplayName("Test applyForTest - user already applied")
    void applyForTestUserAlreadyApplied() throws CostumeErrorException {
        //when
        when(userGetService.getUserFromToken(any())).thenReturn(UserUtil.generate());
        when(testService.getTestForUser(any(), any())).thenReturn(TestUtil.generate());
        when(testApplicationRepository.findByStudentAndTest(any(), any())).thenReturn(Optional.of(TestApplicationUtil.generate()));

        //then
        assertThrows(CostumeErrorException.class, () -> testApplicationService.applyForTest("token", 1L));
    }

    @Test
    @DisplayName("Test getAllApplications - success")
    void getAllApplications() throws CostumeErrorException {
        //when
        when(userGetService.getUserFromToken(any())).thenReturn(UserUtil.generate());
        when(testApplicationRepository.findAllByStudent(any())).thenReturn(TestApplicationUtil.generateList());

        //then
        assertDoesNotThrow(() -> testApplicationService.getAllApplications("token"));
    }

    @Test
    @DisplayName("Test deleteApplication - success")
    void deleteApplication() throws CostumeErrorException {
        //when
        when(userGetService.getUserFromToken(any())).thenReturn(UserUtil.generate());
        when(testApplicationRepository.findById(any())).thenReturn(Optional.of(TestApplicationUtil.generate()));
        doNothing().when(testApplicationRepository).delete(any());

        //then
        assertDoesNotThrow(() -> testApplicationService.deleteApplication("token", 1L));
    }

    @Test
    @DisplayName("Test getTestApplicationById - success")
    void getTestApplicationById() throws CostumeErrorException {
        //when
        when(userGetService.getUserFromToken(any())).thenReturn(UserUtil.generate());
        when(testApplicationRepository.findById(any())).thenReturn(Optional.of(TestApplicationUtil.generate()));

        //then
        assertDoesNotThrow(() -> testApplicationService.getTestApplicationById("token", 1L));
    }

    @Test
    @DisplayName("Test saveTestApplication - success")
    void saveTestApplication() {
        //when
        when(testApplicationRepository.save(any())).thenReturn(TestApplicationUtil.generate());

        //then
        assertDoesNotThrow(() -> testApplicationService.saveTestApplication(TestApplicationUtil.generate()));
    }

    @Test
    @DisplayName("Test getAllTestApplicationsForUserAndSubject - success")
    void getAllTestApplicationsForUserAndSubject() {
        //when
        when(testApplicationRepository.findAllByStudentAndTest_Subject(any(), any())).thenReturn(TestApplicationUtil.generateList());

        //then
        assertDoesNotThrow(() -> testApplicationService.getAllTestApplicationsForUserAndSubject(UserUtil.generate(), SubjectUtil.generate()));
    }

    @Test
    @DisplayName("Test getAllTestApplicationsForUserAndSubject - success")
    void deleteApplicationEntity() {
        //when
        doNothing().when(testApplicationRepository).delete(any());

        //then
        assertDoesNotThrow(() -> testApplicationService.deleteApplicationEntity(TestApplicationUtil.generate()));
    }

    @Test
    @DisplayName("Test deleteAllUserApplications - success")
    void deleteAllUserApplications() {
        //when
        doNothing().when(testApplicationRepository).deleteByStudent(any());

        //then
        assertDoesNotThrow(() -> testApplicationService.deleteAllUserApplications(UserUtil.generate()));
    }
}