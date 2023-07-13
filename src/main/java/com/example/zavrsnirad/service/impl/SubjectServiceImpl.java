package com.example.zavrsnirad.service.impl;

import com.example.zavrsnirad.appenum.Role;
import com.example.zavrsnirad.dto.request.SubjectCreateDTO;
import com.example.zavrsnirad.dto.request.SubjectDTO;
import com.example.zavrsnirad.dto.request.UserDTO;
import com.example.zavrsnirad.entity.Subject;
import com.example.zavrsnirad.entity.User;
import com.example.zavrsnirad.mapper.SubjectCreateDtoMapper;
import com.example.zavrsnirad.mapper.SubjectDtoMapper;
import com.example.zavrsnirad.mapper.UserDtoMapper;
import com.example.zavrsnirad.repository.SubjectRepository;
import com.example.zavrsnirad.repository.UserRepository;
import com.example.zavrsnirad.service.SubjectService;
import com.example.zavrsnirad.service.TokenService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

//TODO: Add admin role check
@Service
public class SubjectServiceImpl implements SubjectService {

    private final SubjectRepository subjectRepository;
    private final UserRepository userRepository;
    private final TokenService tokenService;
    private final SubjectCreateDtoMapper subjectCreateDtoMapper;
    private final SubjectDtoMapper subjectDtoMapper;
    private final UserDtoMapper userDtoMapper;

    public SubjectServiceImpl(SubjectRepository subjectRepository, UserRepository userRepository, TokenService tokenService, SubjectCreateDtoMapper subjectCreateDtoMapper, SubjectDtoMapper subjectDtoMapper, UserDtoMapper userDtoMapper) {
        this.subjectRepository = subjectRepository;
        this.userRepository = userRepository;
        this.tokenService = tokenService;
        this.subjectCreateDtoMapper = subjectCreateDtoMapper;
        this.subjectDtoMapper = subjectDtoMapper;
        this.userDtoMapper = userDtoMapper;
    }

    @Override
    public ResponseEntity<Object> createSubject(String authorization, SubjectCreateDTO data) {
        String username = tokenService.getUsernameFromToken(authorization);
        Optional<User> user = userRepository.findByUsername(username);

        if(user.isEmpty()) return ResponseEntity.badRequest().body("User not found");
        if(user.get().getRole().equals(Role.STUDENT)) return ResponseEntity.badRequest().body("You are not allowed to create subject");

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
        if(user.get().getRole().equals(Role.STUDENT)) return ResponseEntity.badRequest().body("You are not allowed to get subjects");

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
        if(user.get().getRole().equals(Role.STUDENT)) return ResponseEntity.badRequest().body("You are not allowed to get subjects");

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
        if(user.get().getRole().equals(Role.STUDENT) || !Objects.equals(subject.get().getSubjectProfessor().getId(), user.get().getId())) return ResponseEntity.badRequest().body("You are not allowed to delete subjects");


        subjectRepository.delete(subject.get());
        return new ResponseEntity<>("Subject deleted", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Object> updateSubject(String authorization, Long id, SubjectCreateDTO data) {
        String username = tokenService.getUsernameFromToken(authorization);
        Optional<User> user = userRepository.findByUsername(username);
        Optional<Subject> subject = subjectRepository.findById(id);

        if(user.isEmpty()) return ResponseEntity.badRequest().body("User not found");
        if(subject.isEmpty()) return ResponseEntity.badRequest().body("Subject not found");
        if(user.get().getRole().equals(Role.STUDENT) || !Objects.equals(subject.get().getSubjectProfessor().getId(), user.get().getId())) {
            return ResponseEntity.badRequest().body("You are not allowed to update subjects");
            }

        if(data.name() != null || !Objects.equals(data.name(), subject.get().getSubjectName())) {
            subject.get().setSubjectName(data.name());
        }
        if(data.description() != null || !Objects.equals(data.description(), subject.get().getSubjectDescription())) {
            subject.get().setSubjectDescription(data.description());
        }
        if(data.semester() != null || !Objects.equals(data.semester(), subject.get().getSubjectSemester())) {
            subject.get().setSubjectSemester(data.semester());
        }
        if(data.year() != null || !Objects.equals(data.year(), subject.get().getSubjectYear())) {
            subject.get().setSubjectYear(data.year());
        }
        if(data.ects() != null || !Objects.equals(data.ects(), subject.get().getSubjectEcts())) {
            subject.get().setSubjectEcts(data.ects());
        }

        subjectRepository.save(subject.get());

        return ResponseEntity.ok("Subject updated");
    }

    @Override
    public ResponseEntity<Object> getSubjectsStudent(String authorization) {
        String username = tokenService.getUsernameFromToken(authorization);
        Optional<User> user = userRepository.findByUsername(username);

        if(user.isEmpty()) return ResponseEntity.badRequest().body("User not found");

        Set<Subject> subjects = user.get().getSubjects();

        if(subjects.isEmpty()) return ResponseEntity.badRequest().body("No subjects found");

        List<SubjectDTO> subjectDTOS = new ArrayList<>();

        for (Subject subject : subjects) {
            subjectDTOS.add(subjectDtoMapper.apply(subject));
        }

        return ResponseEntity.ok(subjectDTOS);
    }

    @Override
    public ResponseEntity<Object> addStudentToSubject(String authorization, Long id, Long studentId) {
        String username = tokenService.getUsernameFromToken(authorization);
        Optional<User> user = userRepository.findByUsername(username);
        Optional<Subject> subject = subjectRepository.findById(id);
        Optional<User> student = userRepository.findById(studentId);

        if(user.isEmpty()) return ResponseEntity.badRequest().body("User not found");
        if(subject.isEmpty()) return ResponseEntity.badRequest().body("Subject not found");
        if(student.isEmpty()) return ResponseEntity.badRequest().body("Student not found");
        if(user.get().getRole().equals(Role.STUDENT) || !Objects.equals(subject.get().getSubjectProfessor().getId(), user.get().getId())) {
            return ResponseEntity.badRequest().body("You are not allowed to add students to subjects");
        }
        if(subject.get().getStudents().contains(student.get())) return ResponseEntity.badRequest().body("Student already in subject");

        subject.get().getStudents().add(student.get());

        subjectRepository.save(subject.get());

        return ResponseEntity.ok("Student added to subject");
    }

    @Override
    public ResponseEntity<Object> removeStudentFromSubject(String authorization, Long id, Long studentId) {
        String username = tokenService.getUsernameFromToken(authorization);
        Optional<User> user = userRepository.findByUsername(username);
        Optional<Subject> subject = subjectRepository.findById(id);
        Optional<User> student = userRepository.findById(studentId);

        if(user.isEmpty()) return ResponseEntity.badRequest().body("User not found");
        if(subject.isEmpty()) return ResponseEntity.badRequest().body("Subject not found");
        if(student.isEmpty()) return ResponseEntity.badRequest().body("Student not found");
        if(user.get().getRole().equals(Role.STUDENT) || !Objects.equals(subject.get().getSubjectProfessor().getId(), user.get().getId())) {
            return ResponseEntity.badRequest().body("You are not allowed to remove students from subjects");
        }
        if(!subject.get().getStudents().contains(student.get())) return ResponseEntity.badRequest().body("Student not in subject");

        subject.get().getStudents().remove(student.get());

        subjectRepository.save(subject.get());

        return ResponseEntity.ok("Student removed from subject");
    }

    @Override
    public ResponseEntity<Object> getStudentsFromSubject(String authorization, Long id) {
        String username = tokenService.getUsernameFromToken(authorization);
        Optional<User> user = userRepository.findByUsername(username);
        Optional<Subject> subject = subjectRepository.findById(id);

        if(user.isEmpty()) return ResponseEntity.badRequest().body("User not found");
        if(subject.isEmpty()) return ResponseEntity.badRequest().body("Subject not found");
        if(user.get().getRole().equals(Role.STUDENT) || !Objects.equals(subject.get().getSubjectProfessor().getId(), user.get().getId())) {
            return ResponseEntity.badRequest().body("You are not allowed to get students from subjects");
        }

        Set<User> students = subject.get().getStudents();

        if(students.isEmpty()) return ResponseEntity.badRequest().body("No students found");

        List<UserDTO> studentDTOS = new ArrayList<>();

        for (User student : students) {
            studentDTOS.add(userDtoMapper.apply(student));
        }

        return ResponseEntity.ok(studentDTOS);
    }

    @Override
    public ResponseEntity<Object> getSubject(String authorization, Long id) {
        String username = tokenService.getUsernameFromToken(authorization);
        Optional<User> user = userRepository.findByUsername(username);
        Optional<Subject> subject = subjectRepository.findById(id);

        if(user.isEmpty()) return ResponseEntity.badRequest().body("User not found");
        if(subject.isEmpty()) return ResponseEntity.badRequest().body("Subject not found");
        if(user.get().getSubjects().contains(subject.get())) return ResponseEntity.ok(subjectDtoMapper.apply(subject.get()));


        return ResponseEntity.badRequest().body("You are not allowed to get this subject");
    }
}
