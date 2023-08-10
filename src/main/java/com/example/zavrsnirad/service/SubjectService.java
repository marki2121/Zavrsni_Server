package com.example.zavrsnirad.service;

import com.example.zavrsnirad.dto.request.SubjectCreateDTO;
import com.example.zavrsnirad.dto.request.SubjectDTO;
import com.example.zavrsnirad.dto.request.UserDTO;
import com.example.zavrsnirad.entity.CostumeErrorException;

import java.util.List;

public interface SubjectService {
    String createSubject(String authorization, SubjectCreateDTO data) throws CostumeErrorException;

    SubjectDTO getSubject(String authorization, Long id) throws CostumeErrorException;

    List<SubjectDTO> getSubjects(String authorization) throws CostumeErrorException;

    SubjectDTO getSubjectTeacher(String authorization, Long id) throws CostumeErrorException;

    String deleteSubject(String authorization, Long id) throws CostumeErrorException;

    String updateSubject(String authorization, Long id, SubjectCreateDTO data) throws CostumeErrorException;

    List<SubjectDTO> getSubjectsStudent(String authorization) throws CostumeErrorException;

    String addStudentToSubject(String authorization, Long id, Long studentId) throws CostumeErrorException;

    String removeStudentFromSubject(String authorization, Long id, Long studentId) throws CostumeErrorException;

    List<UserDTO> getStudentsFromSubject(String authorization, Long id) throws CostumeErrorException;
}
