package com.example.zavrsnirad.service.impl;

import com.example.zavrsnirad.appenum.Role;
import com.example.zavrsnirad.entity.CostumeErrorException;
import com.example.zavrsnirad.entity.Subject;
import com.example.zavrsnirad.entity.User;
import com.example.zavrsnirad.repository.SubjectRepository;
import com.example.zavrsnirad.service.SubjectGetService;
import com.example.zavrsnirad.service.UserGetService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class SubjectGetServiceImpl implements SubjectGetService {
    private final SubjectRepository subjectRepository;
    private final UserGetService userGetService;

    public SubjectGetServiceImpl(SubjectRepository subjectRepository, UserGetService userGetService) {
        this.subjectRepository = subjectRepository;
        this.userGetService = userGetService;
    }

    @Override
    public Subject getTeacherSubjectById(String auth, Long id) throws CostumeErrorException {
        User user = checkIfUserTeacher(auth);

        Optional<Subject> subject = subjectRepository.findById(id);

        if(subject.isEmpty()) throw new CostumeErrorException("Subject not found", HttpStatus.BAD_REQUEST);
        if(!Objects.equals(subject.get().getSubjectProfessor().getId(), user.getId()) && user.getRole() != Role.ADMIN) throw new CostumeErrorException("You are not allowed to see this subject", HttpStatus.BAD_REQUEST);

        return subject.get();
    }

    @Override
    public User checkIfUserTeacher(String auth) throws CostumeErrorException {
        User user = userGetService.getUserFromToken(auth);

        if(user.getRole().equals(Role.STUDENT)) throw new CostumeErrorException("You are not allowed to do this action", HttpStatus.BAD_REQUEST);

        return user;
    }

    @Override
    public Subject getSubjectById(String authorization, Long id) throws CostumeErrorException {
        User user = userGetService.getUserFromToken(authorization);
        Subject subject = subjectRepository.findById(id).orElseThrow(() -> new CostumeErrorException("Subject not found", HttpStatus.BAD_REQUEST));

        if(subject.getStudents().contains(user)) return subject;
        else throw new CostumeErrorException("User not in subject", HttpStatus.BAD_REQUEST);
    }
}
