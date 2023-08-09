package com.example.zavrsnirad.service.impl;

import com.example.zavrsnirad.appenum.Role;
import com.example.zavrsnirad.dto.request.SubjectCreateDTO;
import com.example.zavrsnirad.dto.request.SubjectDTO;
import com.example.zavrsnirad.dto.request.UserDTO;
import com.example.zavrsnirad.entity.CostumeErrorException;
import com.example.zavrsnirad.entity.Subject;
import com.example.zavrsnirad.entity.User;
import com.example.zavrsnirad.mapper.SubjectCreateDtoMapper;
import com.example.zavrsnirad.mapper.SubjectDtoMapper;
import com.example.zavrsnirad.mapper.UserDtoMapper;
import com.example.zavrsnirad.repository.SubjectRepository;
import com.example.zavrsnirad.service.SubjectService;
import com.example.zavrsnirad.service.UserGetService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

//TODO: Add admin role check
@Service
public class SubjectServiceImpl implements SubjectService {

    private final SubjectRepository subjectRepository;
    private final UserGetService userGetService;
    private final SubjectCreateDtoMapper subjectCreateDtoMapper;
    private final SubjectDtoMapper subjectDtoMapper;
    private final UserDtoMapper userDtoMapper;

    public SubjectServiceImpl(SubjectRepository subjectRepository, UserGetService userGetService, SubjectCreateDtoMapper subjectCreateDtoMapper, SubjectDtoMapper subjectDtoMapper, UserDtoMapper userDtoMapper) {
        this.subjectRepository = subjectRepository;
        this.userGetService = userGetService;
        this.subjectCreateDtoMapper = subjectCreateDtoMapper;
        this.subjectDtoMapper = subjectDtoMapper;
        this.userDtoMapper = userDtoMapper;
    }

    @Override
    public String createSubject(String authorization, SubjectCreateDTO data) throws CostumeErrorException {
        User user = checkIfUserTeacher(authorization);

        subjectRepository.save(subjectCreateDtoMapper.applyWithTeacher(data, user));

        return "Subject created";
    }

    @Override
    public List<SubjectDTO> getSubjects(String authorization) throws CostumeErrorException {
        User user = checkIfUserTeacher(authorization);

        return subjectDtoMapper.map(subjectRepository.findAllBySubjectProfessor(user));
    }

    @Override
    public SubjectDTO getSubjectTeacher(String authorization, Long id) throws CostumeErrorException {
        return subjectDtoMapper.apply(getTeacherSubjectById(authorization, id));
    }

    @Override
    public String deleteSubject(String authorization, Long id) throws CostumeErrorException {
        subjectRepository.delete(getTeacherSubjectById(authorization, id));

        return "Subject deleted";
    }

    @Override
    public String updateSubject(String authorization, Long id, SubjectCreateDTO data) throws CostumeErrorException {
        Subject subject = getTeacherSubjectById(authorization, id);

        if(data.name() != null || !Objects.equals(data.name(), subject.getSubjectName())) {
            subject.setSubjectName(data.name());
        }
        if(data.description() != null || !Objects.equals(data.description(), subject.getSubjectDescription())) {
            subject.setSubjectDescription(data.description());
        }
        if(data.semester() != null || !Objects.equals(data.semester(), subject.getSubjectSemester())) {
            subject.setSubjectSemester(data.semester());
        }
        if(data.year() != null || !Objects.equals(data.year(), subject.getSubjectYear())) {
            subject.setSubjectYear(data.year());
        }
        if(data.ects() != null || !Objects.equals(data.ects(), subject.getSubjectEcts())) {
            subject.setSubjectEcts(data.ects());
        }

        subjectRepository.save(subject);

        return "Subject updated";
    }

    @Override
    public List<SubjectDTO> getSubjectsStudent(String authorization) throws CostumeErrorException {
        return subjectDtoMapper.map(userGetService.getUserFromToken(authorization).getSubjects());
    }

    @Override
    public String addStudentToSubject(String authorization, Long id, Long studentId) throws CostumeErrorException {
        Subject subject = getTeacherSubjectById(authorization, id);
        User student = userGetService.getUserById(studentId);

        if(subject.getStudents().contains(student)) throw new CostumeErrorException("Student already in subject", HttpStatus.BAD_REQUEST);

        subject.getStudents().add(student);

        subjectRepository.save(subject);

        return "Student added to subject";
    }

    @Override
    public String removeStudentFromSubject(String authorization, Long id, Long studentId) throws CostumeErrorException {
        Subject subject = getTeacherSubjectById(authorization, id);
        User student = userGetService.getUserById(studentId);

        if(!subject.getStudents().contains(student)) throw new CostumeErrorException("Student not in subject", HttpStatus.BAD_REQUEST);

        subject.getStudents().remove(student);

        subjectRepository.save(subject);

        return "Student removed from subject";
    }

    @Override
    public List<UserDTO> getStudentsFromSubject(String authorization, Long id) throws CostumeErrorException {
        return userDtoMapper.map(getTeacherSubjectById(authorization, id).getStudents());
    }

    @Override
    public SubjectDTO getSubject(String authorization, Long id) throws CostumeErrorException {
        User user = userGetService.getUserFromToken(authorization);
        Optional<Subject> subject = subjectRepository.findById(id);

        if(subject.isEmpty()) throw new CostumeErrorException("Subject not found", HttpStatus.BAD_REQUEST);
        if(!user.getSubjects().contains(subject.get())) throw new CostumeErrorException("You are not allowed to see this subject", HttpStatus.BAD_REQUEST);


        return subjectDtoMapper.apply(subject.get());
    }

    public User checkIfUserTeacher(String auth) throws CostumeErrorException {
        User user = userGetService.getUserFromToken(auth);

        if(user.getRole().equals(Role.STUDENT)) throw new CostumeErrorException("You are not allowed to do this action", HttpStatus.BAD_REQUEST);

        return user;
    }

    @Override
    public Subject getTeacherSubjectById(String auth,Long id) throws CostumeErrorException {
        User user = checkIfUserTeacher(auth);

        Optional<Subject> subject = subjectRepository.findById(id);

        if(subject.isEmpty()) throw new CostumeErrorException("Subject not found", HttpStatus.BAD_REQUEST);
        if(!Objects.equals(subject.get().getSubjectProfessor().getId(), user.getId()) && user.getRole() != Role.ADMIN) throw new CostumeErrorException("You are not allowed to see this subject", HttpStatus.BAD_REQUEST);

        return subject.get();
    }

    @Override
    public Subject getSubjectById(String authorization, Long id) throws CostumeErrorException {
        User user = userGetService.getUserFromToken(authorization);
        Subject subject = subjectRepository.findById(id).orElseThrow(() -> new CostumeErrorException("Subject not found", HttpStatus.BAD_REQUEST));

        if(subject.getStudents().contains(user)) return subject;
        else throw new CostumeErrorException("User not in subject", HttpStatus.BAD_REQUEST);
    }
}
