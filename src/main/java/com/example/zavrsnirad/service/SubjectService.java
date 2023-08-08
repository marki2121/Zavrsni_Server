package com.example.zavrsnirad.service;

import com.example.zavrsnirad.dto.request.SubjectCreateDTO;
import com.example.zavrsnirad.dto.request.SubjectDTO;
import com.example.zavrsnirad.dto.request.UserDTO;
import com.example.zavrsnirad.entity.Subject;

import java.util.List;

public interface SubjectService {
    String createSubject(String authorization, SubjectCreateDTO data);

    SubjectDTO getSubject(String authorization, Long id);

    List<SubjectDTO> getSubjects(String authorization);

    SubjectDTO getSubjectTeacher(String authorization, Long id);

    String deleteSubject(String authorization, Long id);

    String updateSubject(String authorization, Long id, SubjectCreateDTO data);

    List<SubjectDTO> getSubjectsStudent(String authorization);

    String addStudentToSubject(String authorization, Long id, Long studentId);

    String removeStudentFromSubject(String authorization, Long id, Long studentId);

    List<UserDTO> getStudentsFromSubject(String authorization, Long id);

    Subject getTeacherSubjectById(String auth,Long id);
}
