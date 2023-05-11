package com.example.zavrsnirad.service.impl;

import com.example.zavrsnirad.dto.SubjectCreateDTO;
import com.example.zavrsnirad.dto.SubjectDTO;
import com.example.zavrsnirad.entity.Subject;
import com.example.zavrsnirad.entity.User;
import com.example.zavrsnirad.mapper.SubjectCreateDtoMapper;
import com.example.zavrsnirad.mapper.SubjectDtoMapper;
import com.example.zavrsnirad.repository.SubjectRepository;
import com.example.zavrsnirad.repository.UserRepository;
import com.example.zavrsnirad.service.SubjectService;
import com.example.zavrsnirad.service.TokenService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class SubjectServiceImpl implements SubjectService {

    private final SubjectRepository subjectRepository;
    private final UserRepository userRepository;
    private final TokenService tokenService;
    private final SubjectCreateDtoMapper subjectCreateDtoMapper;
    private final SubjectDtoMapper subjectDtoMapper;

    public SubjectServiceImpl(SubjectRepository subjectRepository, UserRepository userRepository, TokenService tokenService, SubjectCreateDtoMapper subjectCreateDtoMapper, SubjectDtoMapper subjectDtoMapper) {
        this.subjectRepository = subjectRepository;
        this.userRepository = userRepository;
        this.tokenService = tokenService;
        this.subjectCreateDtoMapper = subjectCreateDtoMapper;
        this.subjectDtoMapper = subjectDtoMapper;
    }

    @Override
    public ResponseEntity<Object> createSubject(String authorization, SubjectCreateDTO data) {
        String username = tokenService.getUsernameFromToken(authorization);
        Optional<User> user = userRepository.findByUsername(username);

        if(user.isEmpty()) return ResponseEntity.badRequest().body("User not found");
        if(user.get().getRole().equals("ROLE_STUDENT")) return ResponseEntity.badRequest().body("You are not allowed to create subject");

        Subject subject = subjectCreateDtoMapper.apply(data);
        subject.setSubjectProfessor(user.get());

        subjectRepository.save(subject);
        return ResponseEntity.ok("Subject created");
    }

    @Override
    public ResponseEntity<Object> getSubjects(String authorization) {
        String username = tokenService.getUsernameFromToken(authorization);
        Optional<User> user = userRepository.findByUsername(username);

        if(user.isEmpty()) return ResponseEntity.badRequest().body("User not found");
        if(user.get().getRole().equals("ROLE_STUDENT")) return ResponseEntity.badRequest().body("You are not allowed to get subjects");

        List<Subject> subjects = subjectRepository.findAllBySubjectProfessor(user.get());

        if(subjects.isEmpty()) return ResponseEntity.badRequest().body("No subjects found");

        List<SubjectDTO> subjectDTOS = new ArrayList<>();

        for (Subject subject : subjects) {
            subjectDTOS.add(subjectDtoMapper.apply(subject));
        }

        return ResponseEntity.ok(subjectDTOS);
    }

    @Override
    public ResponseEntity<Object> getSubjectTeacher(String authorization, Long id) {
        String username = tokenService.getUsernameFromToken(authorization);
        Optional<User> user = userRepository.findByUsername(username);

        if(user.isEmpty()) return ResponseEntity.badRequest().body("User not found");
        if(user.get().getRole().equals("ROLE_STUDENT")) return ResponseEntity.badRequest().body("You are not allowed to get subjects");

        Optional<Subject> subject = subjectRepository.findById(id);

        if(subject.isEmpty()) return ResponseEntity.badRequest().body("Subject not found");
        if(subject.get().getSubjectProfessor().getId() != user.get().getId()) return ResponseEntity.badRequest().body("You are not allowed to get this subject");

        return ResponseEntity.ok(subjectDtoMapper.apply(subject.get()));
    }

    @Override
    public ResponseEntity<Object> deleteSubject(String authorization, Long id) {
        String username = tokenService.getUsernameFromToken(authorization);
        Optional<User> user = userRepository.findByUsername(username);
        Optional<Subject> subject = subjectRepository.findById(id);

        if(user.isEmpty()) return ResponseEntity.badRequest().body("User not found");
        if(subject.isEmpty()) return ResponseEntity.badRequest().body("Subject not found");
        if(user.get().getRole().equals("ROLE_STUDENT") || !Objects.equals(subject.get().getSubjectProfessor().getId(), user.get().getId())) return ResponseEntity.badRequest().body("You are not allowed to delete subjects");


        subjectRepository.delete(subject.get());
        return new ResponseEntity<>("Subject deleted", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Object> getSubject(String authorization, Long id) {
        return null;
    }
}
