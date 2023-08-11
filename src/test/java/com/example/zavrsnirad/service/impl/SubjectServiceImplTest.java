package com.example.zavrsnirad.service.impl;

import com.example.zavrsnirad.entity.CostumeErrorException;
import com.example.zavrsnirad.entity.Subject;
import com.example.zavrsnirad.entity.User;
import com.example.zavrsnirad.mapper.SubjectCreateDtoMapper;
import com.example.zavrsnirad.mapper.SubjectDtoMapper;
import com.example.zavrsnirad.mapper.UserDtoMapper;
import com.example.zavrsnirad.repository.SubjectRepository;
import com.example.zavrsnirad.service.SubjectGetService;
import com.example.zavrsnirad.service.SubjectService;
import com.example.zavrsnirad.service.TestService;
import com.example.zavrsnirad.service.UserGetService;
import com.example.zavrsnirad.util.SubjectCreateDtoUtil;
import com.example.zavrsnirad.util.SubjectDtoUtil;
import com.example.zavrsnirad.util.SubjectUtil;
import com.example.zavrsnirad.util.UserUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@SpringBootTest
class SubjectServiceImplTest {

    @Autowired
    private SubjectService subjectService;
    @MockBean
    private SubjectRepository subjectRepository;
    @MockBean
    private SubjectGetService subjectGetService;
    @MockBean
    private UserGetService userGetService;
    @MockBean
    private TestService testService;
    @MockBean
    private SubjectCreateDtoMapper subjectCreateDtoMapper;
    @MockBean
    private SubjectDtoMapper subjectDtoMapper;
    @MockBean
    private UserDtoMapper userDtoMapper;

    @Test
    @DisplayName("Test createTest - success")
    void createSubject() throws CostumeErrorException {
        //when
        when(subjectGetService.checkIfUserTeacher(any())).thenReturn(UserUtil.generateTeacher());
        when(subjectCreateDtoMapper.applyWithTeacher(any(), any())).thenReturn(SubjectUtil.generate());

        //then
        assertEquals("Subject created", subjectService.createSubject("token", SubjectCreateDtoUtil.generate()));
    }

    @Test
    @DisplayName("Test getSubject - success")
    void getSubjects() throws CostumeErrorException {
        //when
        when(subjectGetService.checkIfUserTeacher(any())).thenReturn(UserUtil.generateTeacher());
        when(subjectDtoMapper.map(any())).thenReturn(SubjectDtoUtil.generateList());
        when(subjectRepository.findAllBySubjectProfessor(any())).thenReturn(SubjectUtil.generateList());

        //then
        assertEquals(SubjectDtoUtil.generateList(), subjectService.getSubjects("token"));
    }

    @Test
    @DisplayName("Test getSubjectTeacher - success")
    void getSubjectTeacher() throws CostumeErrorException {
        //when
        when(subjectGetService.getTeacherSubjectById(any(), any())).thenReturn(SubjectUtil.generate());
        when(subjectDtoMapper.map(any())).thenReturn(SubjectDtoUtil.generateList());

        //then
        assertDoesNotThrow(() -> subjectService.getSubjectTeacher("token", 1L));
    }

    @Test
    @DisplayName("Test deleteSubject - success")
    void deleteSubject() throws CostumeErrorException {
        //when
        when(subjectGetService.getTeacherSubjectById(any(), any())).thenReturn(SubjectUtil.generate());
        when(testService.deleteTest(any(), any())).thenReturn("Test deleted");
        doNothing().when(subjectRepository).delete(any());

        //then
        assertEquals("Subject deleted", subjectService.deleteSubject("token", 1L));
    }

    @Test
    @DisplayName("Test updateSubject - success")
    void updateSubject() throws CostumeErrorException {
        //when
        when(subjectGetService.getTeacherSubjectById(any(), any())).thenReturn(SubjectUtil.generate());
        when(subjectRepository.save(any())).thenReturn(SubjectUtil.generate());

        //then
        assertEquals("Subject updated", subjectService.updateSubject("token", 1L, SubjectCreateDtoUtil.generate2()));
    }

    @Test
    @DisplayName("Test getSubjectsStudent - success")
    void getSubjectsStudent() throws CostumeErrorException {
        //when
        when(subjectDtoMapper.map(any())).thenReturn(SubjectDtoUtil.generateList());
        when(userGetService.getUserFromToken(any())).thenReturn(UserUtil.generateTeacherWithSubject());

        //then
        assertEquals(SubjectDtoUtil.generateList(), subjectService.getSubjectsStudent("token"));
    }

    @Test
    @DisplayName("Test getSubjectStudent - success")
    void addStudentToSubject() throws CostumeErrorException {
        //given
        User user = UserUtil.generate();
        Subject subject = SubjectUtil.generate();
        subject.setStudents(new ArrayList<>());
        user.setSubjects(List.of(subject));

        //when
        when(subjectGetService.getTeacherSubjectById(any(), any())).thenReturn(subject);
        when(userGetService.getUserById(any())).thenReturn(user);
        when(subjectRepository.save(any())).thenReturn(subject);

        //then
        assertDoesNotThrow(() -> subjectService.addStudentToSubject("token", 1L, 1L));
    }

    @Test
    @DisplayName("Test removeStudentFromSubject - success")
    void removeStudentFromSubject() throws CostumeErrorException {
        //given
        User user = UserUtil.generate();
        Subject subject = SubjectUtil.generate();
        subject.setStudents(new ArrayList<>());
        subject.getStudents().add(user);

        //when
        when(subjectGetService.getTeacherSubjectById(any(), any())).thenReturn(subject);
        when(userGetService.getUserById(any())).thenReturn(user);
        when(subjectRepository.save(any())).thenReturn(subject);

        //then
        assertDoesNotThrow(() -> subjectService.removeStudentFromSubject("token", 1L, 1L));
    }

    @Test
    @DisplayName("Test getStudentsFromSubject - success")
    void getStudentsFromSubject() throws CostumeErrorException {
        //when
        when(subjectGetService.getTeacherSubjectById(any(), any())).thenReturn(SubjectUtil.generate());
        when(subjectDtoMapper.apply(any())).thenReturn(SubjectDtoUtil.generate());

        //then
        assertDoesNotThrow(() -> subjectService.getStudentsFromSubject("token", 1L));
    }

    @Test
    @DisplayName("Test getSubject - success")
    void getSubject() throws CostumeErrorException {
        //given
        User user = UserUtil.generate();
        Subject subject = SubjectUtil.generate();
        subject.setStudents(List.of(user));
        user.setSubjects(List.of(subject));

        //when
        when(userGetService.getUserFromToken(any())).thenReturn(user);
        when(subjectRepository.findById(any())).thenReturn(java.util.Optional.of(subject));
        when(subjectDtoMapper.apply(any())).thenReturn(SubjectDtoUtil.generate());

        //then
        assertDoesNotThrow(() -> subjectService.getSubject("token", 1L));
    }
}