package com.example.zavrsnirad.service;

import com.example.zavrsnirad.entity.CostumeErrorException;
import com.example.zavrsnirad.entity.Subject;
import com.example.zavrsnirad.entity.User;

public interface SubjectGetService {

    Subject getTeacherSubjectById(String auth, Long id) throws CostumeErrorException;

    User checkIfUserTeacher(String auth) throws CostumeErrorException;

    Subject getSubjectById(String authorization, Long id) throws CostumeErrorException;
}
