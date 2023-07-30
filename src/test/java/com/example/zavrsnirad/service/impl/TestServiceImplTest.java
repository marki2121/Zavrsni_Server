package com.example.zavrsnirad.service.impl;

import com.example.zavrsnirad.appenum.Role;
import com.example.zavrsnirad.dto.request.TestCreateDTO;
import com.example.zavrsnirad.entity.Subject;
import com.example.zavrsnirad.entity.User;
import com.example.zavrsnirad.mapper.TestApplicationResponseDtoMapper;
import com.example.zavrsnirad.mapper.TestResponseDtoMapper;
import com.example.zavrsnirad.repository.SubjectRepository;
import com.example.zavrsnirad.repository.TestApplicationRepository;
import com.example.zavrsnirad.repository.TestRepository;
import com.example.zavrsnirad.repository.UserRepository;
import com.example.zavrsnirad.service.TokenService;
import org.antlr.v4.runtime.misc.Array2DHashSet;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.text.ParseException;
import java.util.Date;
import java.util.Optional;
import java.util.Set;

import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {TestServiceImpl.class})
@ExtendWith(SpringExtension.class)
class TestServiceImplTest {

    @Autowired
    private TestServiceImpl testServiceImpl;

    @MockBean
    private TestRepository testRepository;
    @MockBean
    private SubjectRepository subjectRepository;
    @MockBean
    private UserRepository userRepository;
    @MockBean
    private TokenService tokenService;
    @MockBean
    private TestResponseDtoMapper testResponseDtoMapper;
    @MockBean
    private TestApplicationResponseDtoMapper testApplicationResponseDtoMapper;
    @MockBean
    private TestApplicationRepository testApplicationRepository;

    @Test
    @DisplayName("Test createTest successfully")
    void createTest() throws ParseException {
        //given
        User user = new User(1L, "username", "password", Role.TEACHER, true,null);
        Subject subject = new Subject(1L, "subject", "subject",1, 1, 2, user);
        TestCreateDTO testCreateDTO = new TestCreateDTO("2022-07-07", "test");
        Set<com.example.zavrsnirad.entity.Test> tests = new Array2DHashSet<>();
        subject.setTests(tests);

        //when
        when(tokenService.getUsernameFromToken("token")).thenReturn("user");
        when(userRepository.findByUsername("user")).thenReturn(Optional.of(user));
        when(subjectRepository.findById(1L)).thenReturn(java.util.Optional.of(subject));

        //then
        ResponseEntity<Object> response = testServiceImpl.createTest("token", 1L, testCreateDTO);
        Assertions.assertTrue(response.getStatusCode().is2xxSuccessful());
        Assertions.assertEquals("Test created", response.getBody());
    }

    @Test
    @DisplayName("Test createTest with wrong subject id")
    void createTestWrongSubjectId() throws ParseException {
        //given
        User user = new User(1L, "username", "password", Role.TEACHER, true,null);
        Subject subject = new Subject(1L, "subject", "subject",1, 1, 2, user);
        TestCreateDTO testCreateDTO = new TestCreateDTO("2022-07-07", "test");
        Set<com.example.zavrsnirad.entity.Test> tests = new Array2DHashSet<>();
        subject.setTests(tests);

        //when
        when(tokenService.getUsernameFromToken("token")).thenReturn("user");
        when(userRepository.findByUsername("user")).thenReturn(Optional.of(user));
        when(subjectRepository.findById(1L)).thenReturn(java.util.Optional.empty());

        //then
        ResponseEntity<Object> response = testServiceImpl.createTest("token", 1L, testCreateDTO);
        Assertions.assertTrue(response.getStatusCode().is4xxClientError());
        Assertions.assertEquals("Subject not found", response.getBody());
    }

    @Test
    @DisplayName("Test createTest with wrong user")
    void createTestWrongUser() throws ParseException {
        //given
        User user = new User(1L, "username", "password", Role.TEACHER, true,null);
        Subject subject = new Subject(1L, "subject", "subject",1, 1, 2, user);
        TestCreateDTO testCreateDTO = new TestCreateDTO("2022-07-07", "test");
        Set<com.example.zavrsnirad.entity.Test> tests = new Array2DHashSet<>();
        subject.setTests(tests);

        //when
        when(tokenService.getUsernameFromToken("token")).thenReturn("user");
        when(userRepository.findByUsername("user")).thenReturn(Optional.empty());
        when(subjectRepository.findById(1L)).thenReturn(java.util.Optional.of(subject));

        //then
        ResponseEntity<Object> response = testServiceImpl.createTest("token", 1L, testCreateDTO);
        Assertions.assertTrue(response.getStatusCode().is4xxClientError());
        Assertions.assertEquals("User not found", response.getBody());
    }

    @Test
    @DisplayName("Test createTest with wrong user role")
    void createTestWrongUserRole() throws ParseException {
        //given
        User user = new User(1L, "username", "password", Role.STUDENT, true,null);
        Subject subject = new Subject(1L, "subject", "subject",1, 1, 2, user);
        TestCreateDTO testCreateDTO = new TestCreateDTO("2022-07-07", "test");
        Set<com.example.zavrsnirad.entity.Test> tests = new Array2DHashSet<>();
        subject.setTests(tests);

        //when
        when(tokenService.getUsernameFromToken("token")).thenReturn("user");
        when(userRepository.findByUsername("user")).thenReturn(Optional.of(user));
        when(subjectRepository.findById(1L)).thenReturn(java.util.Optional.of(subject));

        //then
        ResponseEntity<Object> response = testServiceImpl.createTest("token", 1L, testCreateDTO);
        Assertions.assertTrue(response.getStatusCode().is4xxClientError());
        Assertions.assertEquals("User is not a teacher", response.getBody());
    }

    @Test
    @DisplayName("Test createTest when User is not a teacher of this subject")
    void createTestWrongUserSubject() throws ParseException {
        //given
        User user = new User(1L, "username", "password", Role.TEACHER, true,null);
        User user2 = new User(2L, "username2", "password", Role.TEACHER, true,null);
        Subject subject = new Subject(1L, "subject", "subject",1, 1, 2, user2);
        TestCreateDTO testCreateDTO = new TestCreateDTO("2022-07-07", "test");
        Set<com.example.zavrsnirad.entity.Test> tests = new Array2DHashSet<>();
        subject.setTests(tests);

        //when
        when(tokenService.getUsernameFromToken("token")).thenReturn("user");
        when(userRepository.findByUsername("user")).thenReturn(Optional.of(user));
        when(subjectRepository.findById(1L)).thenReturn(java.util.Optional.of(subject));

        //then
        ResponseEntity<Object> response = testServiceImpl.createTest("token", 1L, testCreateDTO);
        Assertions.assertTrue(response.getStatusCode().is4xxClientError());
        Assertions.assertEquals("User is not a teacher of this subject", response.getBody());
    }

    @Test
    @DisplayName("Test createTest when Subject already has 4 tests")
    void createTestSubjectHas4Tests() throws ParseException {
        //given
        User user = new User(1L, "username", "password", Role.TEACHER, true,null);
        Subject subject = new Subject(1L, "subject", "subject",1, 1, 2, user);
        TestCreateDTO testCreateDTO = new TestCreateDTO("2022-07-07", "test");
        Set<com.example.zavrsnirad.entity.Test> tests = new Array2DHashSet<>();
        tests.add(new com.example.zavrsnirad.entity.Test(1L, subject, new Date(), "test"));
        tests.add(new com.example.zavrsnirad.entity.Test(2L, subject, new Date(), "test"));
        tests.add(new com.example.zavrsnirad.entity.Test(3L, subject, new Date(), "test"));
        tests.add(new com.example.zavrsnirad.entity.Test(4L,subject, new Date(), "test"));
        subject.setTests(tests);

        //when
        when(tokenService.getUsernameFromToken("token")).thenReturn("user");
        when(userRepository.findByUsername("user")).thenReturn(Optional.of(user));
        when(subjectRepository.findById(1L)).thenReturn(java.util.Optional.of(subject));

        //then
        ResponseEntity<Object> response = testServiceImpl.createTest("token", 1L, testCreateDTO);
        Assertions.assertTrue(response.getStatusCode().is4xxClientError());
        Assertions.assertEquals("Subject already has 4 tests", response.getBody());
    }

    @Test
    @DisplayName("Test createTest when Date is invalid")
    void createTestInvalidDate() throws ParseException {
        //given
        User user = new User(1L, "username", "password", Role.TEACHER, true,null);
        Subject subject = new Subject(1L, "subject", "subject",1, 1, 2, user);
        TestCreateDTO testCreateDTO = new TestCreateDTO( null, "test");
        Set<com.example.zavrsnirad.entity.Test> tests = new Array2DHashSet<>();
        subject.setTests(tests);

        //when
        when(tokenService.getUsernameFromToken("token")).thenReturn("user");
        when(userRepository.findByUsername("user")).thenReturn(Optional.of(user));
        when(subjectRepository.findById(1L)).thenReturn(java.util.Optional.of(subject));

        //then
        ResponseEntity<Object> response = testServiceImpl.createTest("token", 1L, testCreateDTO);
        Assertions.assertTrue(response.getStatusCode().is4xxClientError());
        Assertions.assertEquals("Date is not valid", response.getBody());
    }

    @Test
    @DisplayName("Test getTestsForSubject success")
    void getTestsForSubject() {
        //given
        User user = new User(1L, "username", "password", Role.TEACHER, true,null);
        Subject subject = new Subject(1L, "subject", "subject",1, 1, 2, user);
        Set<com.example.zavrsnirad.entity.Test> tests = new Array2DHashSet<>();
        tests.add(new com.example.zavrsnirad.entity.Test(1L, subject, new Date(), "test"));
        subject.setTests(tests);

        //when
        when(subjectRepository.findById(1L)).thenReturn(java.util.Optional.of(subject));
        when(tokenService.getUsernameFromToken("token")).thenReturn("user");
        when(userRepository.findByUsername("user")).thenReturn(Optional.of(user));

        //then
        ResponseEntity<Object> response = testServiceImpl.getTestsForSubject("token", 1L);
        Assertions.assertTrue(response.getStatusCode().is2xxSuccessful());
        Assertions.assertNotNull(response.getBody());
    }

    @Test
    @DisplayName("Test getTestsForSubject when Subject doesn't exist")
    void getTestsForSubjectSubjectDoesntExist() {
        //given
        User user = new User(1L, "username", "password", Role.TEACHER, true,null);

        //when
        when(subjectRepository.findById(1L)).thenReturn(Optional.empty());
        when(tokenService.getUsernameFromToken("token")).thenReturn("user");
        when(userRepository.findByUsername("user")).thenReturn(Optional.of(user));

        //then
        ResponseEntity<Object> response = testServiceImpl.getTestsForSubject("token", 1L);
        Assertions.assertTrue(response.getStatusCode().is4xxClientError());
        Assertions.assertEquals("Subject not found", response.getBody());
    }

    @Test
    @DisplayName("Test getTestsForSubject when User is not a teacher of this subject")
    void getTestsForSubjectWrongUserSubject() {
        //given
        User user = new User(1L, "username", "password", Role.TEACHER, true,null);
        User user2 = new User(2L, "username2", "password", Role.TEACHER, true,null);
        Subject subject = new Subject(1L, "subject", "subject",1, 1, 2, user2);

        //when
        when(subjectRepository.findById(1L)).thenReturn(java.util.Optional.of(subject));
        when(tokenService.getUsernameFromToken("token")).thenReturn("user");
        when(userRepository.findByUsername("user")).thenReturn(Optional.of(user));

        //then
        ResponseEntity<Object> response = testServiceImpl.getTestsForSubject("token", 1L);
        Assertions.assertTrue(response.getStatusCode().is4xxClientError());
        Assertions.assertEquals("User is not a teacher of this subject", response.getBody());
    }

    @Test
    @DisplayName("Test getTestsForSubject when User doesn't exist")
    void getTestsForSubjectUserDoesntExist() {
        //given
        User user = new User(1L, "username", "password", Role.TEACHER, true,null);
        Subject subject = new Subject(1L, "subject", "subject",1, 1, 2, user);

        //when
        when(subjectRepository.findById(1L)).thenReturn(java.util.Optional.of(subject));
        when(tokenService.getUsernameFromToken("token")).thenReturn("user");
        when(userRepository.findByUsername("user")).thenReturn(Optional.empty());

        //then
        ResponseEntity<Object> response = testServiceImpl.getTestsForSubject("token", 1L);
        Assertions.assertTrue(response.getStatusCode().is4xxClientError());
        Assertions.assertEquals("User not found", response.getBody());
    }

    @Test
    @DisplayName("Test deleteTest success")
    void deleteTest() {
        //given
        User user = new User(1L, "username", "password", Role.TEACHER, true,null);
        Subject subject = new Subject(1L, "subject", "subject",1, 1, 2, user);
        com.example.zavrsnirad.entity.Test test = new com.example.zavrsnirad.entity.Test(1L, subject, new Date(), "test");
        Set<com.example.zavrsnirad.entity.Test> tests = new Array2DHashSet<>();
        tests.add(test);
        subject.setTests(tests);

        //when
        when(tokenService.getUsernameFromToken("token")).thenReturn("user");
        when(userRepository.findByUsername("user")).thenReturn(Optional.of(user));
        when(testRepository.findById(1L)).thenReturn(java.util.Optional.of(test));
        when(subjectRepository.findById(1L)).thenReturn(java.util.Optional.of(subject));

        //then
        ResponseEntity<Object> response = testServiceImpl.deleteTest("token", 1L, 1L);
        Assertions.assertTrue(response.getStatusCode().is2xxSuccessful());
        Assertions.assertEquals("Test deleted", response.getBody());
    }

    @Test
    @DisplayName("Test deleteTest when User is not a teacher of this subject")
    void deleteTestWrongUserSubject() {
        //given
        User user = new User(1L, "username", "password", Role.TEACHER, true,null);
        User user2 = new User(2L, "username2", "password", Role.TEACHER, true,null);
        Subject subject = new Subject(1L, "subject", "subject",1, 1, 2, user2);
        com.example.zavrsnirad.entity.Test test = new com.example.zavrsnirad.entity.Test(1L, subject, new Date(), "test");
        Set<com.example.zavrsnirad.entity.Test> tests = new Array2DHashSet<>();
        tests.add(test);
        subject.setTests(tests);

        //when
        when(tokenService.getUsernameFromToken("token")).thenReturn("user");
        when(userRepository.findByUsername("user")).thenReturn(Optional.of(user));
        when(testRepository.findById(1L)).thenReturn(java.util.Optional.of(test));
        when(subjectRepository.findById(1L)).thenReturn(java.util.Optional.of(subject));

        //then
        ResponseEntity<Object> response = testServiceImpl.deleteTest("token", 1L, 1L);
        Assertions.assertTrue(response.getStatusCode().is4xxClientError());
        Assertions.assertEquals("User is not a teacher of this subject", response.getBody());
    }

    @Test
    @DisplayName("Test deleteTest when User doesn't exist")
    void deleteTestUserDoesntExist() {
        //given
        User user = new User(1L, "username", "password", Role.TEACHER, true,null);
        Subject subject = new Subject(1L, "subject", "subject",1, 1, 2, user);
        com.example.zavrsnirad.entity.Test test = new com.example.zavrsnirad.entity.Test(1L, subject, new Date(), "test");
        Set<com.example.zavrsnirad.entity.Test> tests = new Array2DHashSet<>();
        tests.add(test);
        subject.setTests(tests);

        //when
        when(tokenService.getUsernameFromToken("token")).thenReturn("user");
        when(userRepository.findByUsername("user")).thenReturn(Optional.empty());
        when(testRepository.findById(1L)).thenReturn(java.util.Optional.of(test));
        when(subjectRepository.findById(1L)).thenReturn(java.util.Optional.of(subject));

        //then
        ResponseEntity<Object> response = testServiceImpl.deleteTest("token", 1L, 1L);
        Assertions.assertTrue(response.getStatusCode().is4xxClientError());
        Assertions.assertEquals("User not found", response.getBody());
    }

    @Test
    @DisplayName("Test deleteTest when Test doesn't exist")
    void deleteTestTestDoesntExist() {
        //given
        User user = new User(1L, "username", "password", Role.TEACHER, true,null);
        Subject subject = new Subject(1L, "subject", "subject",1, 1, 2, user);
        com.example.zavrsnirad.entity.Test test = new com.example.zavrsnirad.entity.Test(1L, subject, new Date(), "test");
        Set<com.example.zavrsnirad.entity.Test> tests = new Array2DHashSet<>();
        tests.add(test);
        subject.setTests(tests);

        //when
        when(tokenService.getUsernameFromToken("token")).thenReturn("user");
        when(userRepository.findByUsername("user")).thenReturn(Optional.of(user));
        when(testRepository.findById(1L)).thenReturn(Optional.empty());
        when(subjectRepository.findById(1L)).thenReturn(java.util.Optional.of(subject));

        //then
        ResponseEntity<Object> response = testServiceImpl.deleteTest("token", 1L, 2L);
        Assertions.assertTrue(response.getStatusCode().is4xxClientError());
        Assertions.assertEquals("Test not found", response.getBody());
    }

    @Test
    @DisplayName("Test deleteTest when Subject doesn't exist")
    void deleteTestSubjectDoesntExist() {
        //given
        User user = new User(1L, "username", "password", Role.TEACHER, true,null);
        Subject subject = new Subject(1L, "subject", "subject",1, 1, 2, user);
        com.example.zavrsnirad.entity.Test test = new com.example.zavrsnirad.entity.Test(1L, subject, new Date(), "test");
        Set<com.example.zavrsnirad.entity.Test> tests = new Array2DHashSet<>();
        tests.add(test);
        subject.setTests(tests);

        //when
        when(tokenService.getUsernameFromToken("token")).thenReturn("user");
        when(userRepository.findByUsername("user")).thenReturn(Optional.of(user));
        when(testRepository.findById(1L)).thenReturn(java.util.Optional.of(test));
        when(subjectRepository.findById(1L)).thenReturn(Optional.empty());

        //then
        ResponseEntity<Object> response = testServiceImpl.deleteTest("token", 1L, 1L);
        Assertions.assertTrue(response.getStatusCode().is4xxClientError());
        Assertions.assertEquals("Subject not found", response.getBody());
    }

    @Test
    @DisplayName("Test deleteTest when Test is not in Subject")
    void deleteTestTestNotInSubject() {
        //given
        User user = new User(1L, "username", "password", Role.TEACHER, true, null);
        Subject subject = new Subject(1L, "subject", "subject", 1, 1, 2, user);
        com.example.zavrsnirad.entity.Test test = new com.example.zavrsnirad.entity.Test(1L, subject, new Date(), "test");
        Set<com.example.zavrsnirad.entity.Test> tests = new Array2DHashSet<>();
        tests.add(test);
        subject.setTests(tests);

        //when
        when(tokenService.getUsernameFromToken("token")).thenReturn("user");
        when(userRepository.findByUsername("user")).thenReturn(Optional.of(user));
        when(testRepository.findById(1L)).thenReturn(java.util.Optional.of(test));
        when(subjectRepository.findById(1L)).thenReturn(java.util.Optional.of(subject));

        //then
        ResponseEntity<Object> response = testServiceImpl.deleteTest("token", 1L, 2L);
        Assertions.assertTrue(response.getStatusCode().is4xxClientError());
        Assertions.assertEquals("Test not found", response.getBody());
    }

    @Test
    void updateTest() {
    }

    @Test
    void getAllTestsApplications() {
    }

    @Test
    void gradeTest() {
    }
}