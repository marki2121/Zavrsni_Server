package com.example.zavrsnirad.service.impl;

import com.example.zavrsnirad.dto.SubjectCreateDTO;
import com.example.zavrsnirad.entity.Subject;
import com.example.zavrsnirad.entity.User;
import com.example.zavrsnirad.mapper.SubjectCreateDtoMapper;
import com.example.zavrsnirad.repository.SubjectRepository;
import com.example.zavrsnirad.repository.UserRepository;
import com.example.zavrsnirad.service.SubjectService;
import com.example.zavrsnirad.service.TokenService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SubjectServiceImpl implements SubjectService {

    private final SubjectRepository subjectRepository;
    private final UserRepository userRepository;
    private final TokenService tokenService;
    private final SubjectCreateDtoMapper subjectCreateDtoMapper;

    public SubjectServiceImpl(SubjectRepository subjectRepository, UserRepository userRepository, TokenService tokenService, SubjectCreateDtoMapper subjectCreateDtoMapper) {
        this.subjectRepository = subjectRepository;
        this.userRepository = userRepository;
        this.tokenService = tokenService;
        this.subjectCreateDtoMapper = subjectCreateDtoMapper;
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
    public ResponseEntity<Object> getSubject(String authorization, Long id) {
        return null;
    }
}
