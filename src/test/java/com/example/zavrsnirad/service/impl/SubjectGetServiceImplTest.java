package com.example.zavrsnirad.service.impl;

import com.example.zavrsnirad.entity.CostumeErrorException;
import com.example.zavrsnirad.entity.Subject;
import com.example.zavrsnirad.entity.User;
import com.example.zavrsnirad.repository.SubjectRepository;
import com.example.zavrsnirad.service.SubjectGetService;
import com.example.zavrsnirad.service.UserGetService;
import com.example.zavrsnirad.util.SubjectUtil;
import com.example.zavrsnirad.util.UserUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class SubjectGetServiceImplTest {

    @Autowired
    private SubjectGetService subjectGetService;
    @MockBean
    private UserGetService userGetService;
    @MockBean
    private SubjectRepository subjectRepository;

    @Test
    @DisplayName("Test getTeacherSubjectById - success")
    void getTeacherSubjectById() throws CostumeErrorException {
        //when
        when(userGetService.getUserFromToken(any())).thenReturn(UserUtil.generateTeacher());
        when(subjectRepository.findById(any())).thenReturn(Optional.of(SubjectUtil.generate()));

        //then
        assertDoesNotThrow(() -> subjectGetService.getTeacherSubjectById("token", 1L));
    }

    @Test
    @DisplayName("Test checkIfUserTeacher - success")
    void checkIfUserTeacher() throws CostumeErrorException {
        //when
        when(userGetService.getUserFromToken(any())).thenReturn(UserUtil.generateTeacher());

        //then
        assertDoesNotThrow(() -> subjectGetService.checkIfUserTeacher("token"));
    }

    @Test
    @DisplayName("Test checkIfUserTeacher - fail user is student")
    void checkIfUserTeacherFail() throws CostumeErrorException {
        //when
        when(userGetService.getUserFromToken(any())).thenReturn(UserUtil.generate());

        //then
        assertThrows(CostumeErrorException.class, () -> subjectGetService.checkIfUserTeacher("token"));
    }

    @Test
    @DisplayName("Test getSubjectById - success")
    void getSubjectById() throws CostumeErrorException {
        //given
        User user = UserUtil.generate();
        Subject subject = SubjectUtil.generate();
        subject.setStudents(List.of(user));

        //when
        when(userGetService.getUserFromToken(any())).thenReturn(user);
        when(subjectRepository.findById(any())).thenReturn(Optional.of(subject));

        //then
        assertDoesNotThrow(() -> subjectGetService.getSubjectById("token", 1L));
    }
}