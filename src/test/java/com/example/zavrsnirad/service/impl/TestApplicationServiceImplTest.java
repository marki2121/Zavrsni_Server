package com.example.zavrsnirad.service.impl;

import com.example.zavrsnirad.appenum.Role;
import com.example.zavrsnirad.entity.Subject;
import com.example.zavrsnirad.entity.TestApplication;
import com.example.zavrsnirad.entity.User;
import com.example.zavrsnirad.mapper.TestApplicationResponseDtoMapper;
import com.example.zavrsnirad.repository.TestApplicationRepository;
import com.example.zavrsnirad.repository.TestRepository;
import com.example.zavrsnirad.repository.UserRepository;
import com.example.zavrsnirad.service.TokenService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {TestApplicationServiceImpl.class})
@ExtendWith(SpringExtension.class)
class TestApplicationServiceImplTest {

    @MockBean
    private TestApplicationRepository testApplicationRepository;
    @MockBean
    private TestRepository testRepository;
    @MockBean
    private UserRepository userRepository;
    @MockBean
    private TokenService tokenService;
    @MockBean
    private TestApplicationResponseDtoMapper testApplicationResponseDtoMapper;

    @Autowired
    private TestApplicationServiceImpl testApplicationService;

    @Test
    @DisplayName("Test applyForTest() and success")
    void applyForTest() {
        //given
        Subject subject = new Subject(1L, "Subject", "asd",5, 1, 3, null);
        Set<Subject> subjects = Set.of(subject);
        User user = new User(1L, "username", "password", Role.STUDENT, true, null, subjects);
        subject.setStudents(Set.of(user));
        com.example.zavrsnirad.entity.Test test = new com.example.zavrsnirad.entity.Test(1L, subject, null, "da");

        //when
        when(tokenService.getUsernameFromToken("token")).thenReturn("username");
        when(userRepository.findByUsername("username")).thenReturn(java.util.Optional.of(user));
        when(testRepository.findById(1L)).thenReturn(java.util.Optional.of(test));
        when(testApplicationRepository.findByStudentAndTest(user, test)).thenReturn(java.util.Optional.empty());

        //then
        ResponseEntity<Object> response = testApplicationService.applyForTest("token",1L);
        assertTrue(response.getStatusCode().is2xxSuccessful());
    }

    @Test
    @DisplayName("Test applyForTest() and fail because user already applied")
    void applyForTestFail() {
        //given
        Subject subject = new Subject(1L, "Subject", "asd",5, 1, 3, null);
        Set<Subject> subjects = Set.of(subject);
        User user = new User(1L, "username", "password", Role.STUDENT, true, null, subjects);
        subject.setStudents(Set.of(user));
        com.example.zavrsnirad.entity.Test test = new com.example.zavrsnirad.entity.Test(1L, subject, null, "da");

        //when
        when(tokenService.getUsernameFromToken("token")).thenReturn("username");
        when(userRepository.findByUsername("username")).thenReturn(java.util.Optional.of(user));
        when(testRepository.findById(1L)).thenReturn(java.util.Optional.of(test));
        when(testApplicationRepository.findByStudentAndTest(user, test)).thenReturn(java.util.Optional.of(new com.example.zavrsnirad.entity.TestApplication()));

        //then
        ResponseEntity<Object> response = testApplicationService.applyForTest("token",1L);
        assertTrue(response.getStatusCode().is4xxClientError());
        assertEquals(response.getBody(), "You have already applied for this test");
    }

    @Test
    @DisplayName("Test applyForTest() and fail because username is not found")
    void applyForTestFail2() {
        //when
        when(tokenService.getUsernameFromToken("token")).thenReturn("username");

        //then
        ResponseEntity<Object> response = testApplicationService.applyForTest("token",1L);
        assertTrue(response.getStatusCode().is4xxClientError());
        assertEquals(response.getBody(), "User not found");
    }

    @Test
    @DisplayName("Test applyForTest() and fail because test is not found")
    void applyForTestFail3() {
        //given
        Subject subject = new Subject(1L, "Subject", "asd",5, 1, 3, null);
        Set<Subject> subjects = Set.of(subject);
        User user = new User(1L, "username", "password", Role.STUDENT, true, null, subjects);
        subject.setStudents(Set.of(user));

        //when
        when(tokenService.getUsernameFromToken("token")).thenReturn("username");
        when(userRepository.findByUsername("username")).thenReturn(java.util.Optional.of(user));

        //then
        ResponseEntity<Object> response = testApplicationService.applyForTest("token",1L);
        assertTrue(response.getStatusCode().is4xxClientError());
        assertEquals(response.getBody(), "Test not found");
    }

    @Test
    @DisplayName("Test applyForTest() and fail because user is not enrolled in subject")
    void applyForTestFail4() {
        //given
        Subject subject = new Subject(1L, "Subject", "asd",5, 1, 3, null);
        User user = new User(1L, "username", "password", Role.STUDENT, true, null, null);
        com.example.zavrsnirad.entity.Test test = new com.example.zavrsnirad.entity.Test(1L, subject, null, "da");
        subject.setStudents(Set.of());

        //when
        when(tokenService.getUsernameFromToken("token")).thenReturn("username");
        when(userRepository.findByUsername("username")).thenReturn(java.util.Optional.of(user));
        when(testRepository.findById(1L)).thenReturn(java.util.Optional.of(test));

        //then
        ResponseEntity<Object> response = testApplicationService.applyForTest("token",1L);
        assertTrue(response.getStatusCode().is4xxClientError());
        assertEquals(response.getBody(), "You are not enrolled in this subject");
    }

    @Test
    @DisplayName("Test applyForTest() and fail because user is already applied for test")
    void applyForTestFail5() {
        //given
        Subject subject = new Subject(1L, "Subject", "asd",5, 1, 3, null);
        Set<Subject> subjects = Set.of(subject);
        User user = new User(1L, "username", "password", Role.STUDENT, true, null, subjects);
        subject.setStudents(Set.of(user));
        com.example.zavrsnirad.entity.Test test = new com.example.zavrsnirad.entity.Test(1L, subject, null, "da");

        //when
        when(tokenService.getUsernameFromToken("token")).thenReturn("username");
        when(userRepository.findByUsername("username")).thenReturn(java.util.Optional.of(user));
        when(testRepository.findById(1L)).thenReturn(java.util.Optional.of(test));
        when(testApplicationRepository.findByStudentAndTest(user, test)).thenReturn(java.util.Optional.of(new com.example.zavrsnirad.entity.TestApplication()));

        //then
        ResponseEntity<Object> response = testApplicationService.applyForTest("token",1L);
        assertTrue(response.getStatusCode().is4xxClientError());
        assertEquals(response.getBody(), "You have already applied for this test");
    }

    @Test
    @DisplayName("Test getAllApplications() and succeed")
    void getAllApplications() {
        //given
        Subject subject = new Subject(1L, "Subject", "asd",5, 1, 3, null);
        Set<Subject> subjects = Set.of(subject);
        User user = new User(1L, "username", "password", Role.STUDENT, true, null, subjects);
        subject.setStudents(Set.of(user));
        com.example.zavrsnirad.entity.Test test = new com.example.zavrsnirad.entity.Test(1L, subject, null, "da");
        com.example.zavrsnirad.entity.TestApplication testApplication = new com.example.zavrsnirad.entity.TestApplication(1L, test, user, 1, "da", true);

        //when
        when(tokenService.getUsernameFromToken("token")).thenReturn("username");
        when(userRepository.findByUsername("username")).thenReturn(java.util.Optional.of(user));
        when(testApplicationRepository.findAllByStudent(user)).thenReturn(List.of(testApplication));

        //then
        ResponseEntity<Object> response = testApplicationService.getAllApplications("token");
        assertTrue(response.getStatusCode().is2xxSuccessful());
    }

    @Test
    @DisplayName("Test getAllApplications() and fail because user is not found")
    void getAllApplicationsFail() {
        //when
        when(tokenService.getUsernameFromToken("token")).thenReturn("username");

        //then
        ResponseEntity<Object> response = testApplicationService.getAllApplications("token");
        assertTrue(response.getStatusCode().is4xxClientError());
        assertEquals(response.getBody(), "User not found");
    }

    @Test
    @DisplayName("Test getAllApplications() and fail because user has no tests")
    void getAllApplicationsFail2() {
        //given
        Subject subject = new Subject(1L, "Subject", "asd",5, 1, 3, null);
        Set<Subject> subjects = Set.of(subject);
        User user = new User(1L, "username", "password", Role.STUDENT, true, null, subjects);
        subject.setStudents(Set.of(user));
        List<TestApplication> testApplication = new ArrayList<>();

        //when
        when(tokenService.getUsernameFromToken("token")).thenReturn("username");
        when(userRepository.findByUsername("username")).thenReturn(java.util.Optional.of(user));
        when(testApplicationRepository.findAllByStudent(user)).thenReturn(testApplication);

        //then
        ResponseEntity<Object> response = testApplicationService.getAllApplications("token");
        assertTrue(response.getStatusCode().is4xxClientError());
        assertEquals(response.getBody(), "You have not applied for any tests");
    }

    @Test
    @DisplayName("Test deleteApplication() and succeed")
    void deleteApplication() {
        //given
        Subject subject = new Subject(1L, "Subject", "asd",5, 1, 3, null);
        Set<Subject> subjects = Set.of(subject);
        User user = new User(1L, "username", "password", Role.STUDENT, true, null, subjects);
        subject.setStudents(Set.of(user));
        com.example.zavrsnirad.entity.Test test = new com.example.zavrsnirad.entity.Test(1L, subject, null, "da");
        com.example.zavrsnirad.entity.TestApplication testApplication = new com.example.zavrsnirad.entity.TestApplication(1L, test, user, 1, "da", true);

        //when
        when(tokenService.getUsernameFromToken("token")).thenReturn("username");
        when(userRepository.findByUsername("username")).thenReturn(java.util.Optional.of(user));
        when(testApplicationRepository.findById(1L)).thenReturn(java.util.Optional.of(testApplication));

        //then
        ResponseEntity<Object> response = testApplicationService.deleteApplication("token", 1L);
        assertTrue(response.getStatusCode().is2xxSuccessful());
        assertEquals(response.getBody(), "Successfully deleted test application");
    }

    @Test
    @DisplayName("Test deleteApplication() and fail because user is not found")
    void deleteApplicationFail() {
        //when
        when(tokenService.getUsernameFromToken("token")).thenReturn("username");

        //then
        ResponseEntity<Object> response = testApplicationService.deleteApplication("token", 1L);
        assertTrue(response.getStatusCode().is4xxClientError());
        assertEquals(response.getBody(), "User not found");
    }

    @Test
    @DisplayName("Test deleteApplication() and fail because test application is not found")
    void deleteApplicationFail2() {
        //given
        Subject subject = new Subject(1L, "Subject", "asd",5, 1, 3, null);
        Set<Subject> subjects = Set.of(subject);
        User user = new User(1L, "username", "password", Role.STUDENT, true, null, subjects);
        subject.setStudents(Set.of(user));

        //when
        when(tokenService.getUsernameFromToken("token")).thenReturn("username");
        when(userRepository.findByUsername("username")).thenReturn(java.util.Optional.of(user));

        //then
        ResponseEntity<Object> response = testApplicationService.deleteApplication("token", 1L);
        assertTrue(response.getStatusCode().is4xxClientError());
        assertEquals(response.getBody(), "Test application not found");
    }

    @Test
    @DisplayName("Test deleteApplication() and fail because test application is not users")
    void deleteApplicationFail3() {
        //given
        Subject subject = new Subject(1L, "Subject", "asd",5, 1, 3, null);
        Set<Subject> subjects = Set.of(subject);
        User user = new User(1L, "username", "password", Role.STUDENT, true, null, subjects);
        User user2 = new User(2L, "username2", "password", Role.STUDENT, true, null, subjects);
        subject.setStudents(Set.of(user));
        com.example.zavrsnirad.entity.Test test = new com.example.zavrsnirad.entity.Test(1L, subject, null, "da");
        com.example.zavrsnirad.entity.TestApplication testApplication = new com.example.zavrsnirad.entity.TestApplication(1L, test, user2, 1, "da", true);

        //when
        when(tokenService.getUsernameFromToken("token")).thenReturn("username");
        when(userRepository.findByUsername("username")).thenReturn(java.util.Optional.of(user));
        when(testApplicationRepository.findById(1L)).thenReturn(java.util.Optional.of(testApplication));

        //then
        ResponseEntity<Object> response = testApplicationService.deleteApplication("token", 1L);
        assertTrue(response.getStatusCode().is4xxClientError());
        assertEquals(response.getBody(), "You are not the owner of this test application");
    }
}