package com.example.zavrsnirad.controller;

import com.example.zavrsnirad.config.CostumeErrorException;
import com.example.zavrsnirad.dto.request.SubjectCreateDTO;
import com.example.zavrsnirad.dto.request.SubjectDTO;
import com.example.zavrsnirad.dto.request.UserDTO;
import com.example.zavrsnirad.service.SubjectService;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/subject")
public class SubjectController {
    private final SubjectService subjectService;

    public SubjectController(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @PostMapping("/teacher/create")
    public ResponseEntity<String> createSubject(@RequestHeader String Authorization, @RequestBody SubjectCreateDTO data) throws CostumeErrorException {
        return ResponseEntity.ok(subjectService.createSubject(Authorization, data));
    }

    @GetMapping("/teacher")
    public ResponseEntity<List<SubjectDTO>> getSubjects(@RequestHeader String Authorization) throws CostumeErrorException {
        return ResponseEntity.ok(subjectService.getSubjects(Authorization));
    }

    @GetMapping("/teacher/{id}")
    public ResponseEntity<SubjectDTO> getSubjectTeacher(@RequestHeader String Authorization, @PathVariable Long id) throws CostumeErrorException {
        return ResponseEntity.ok(subjectService.getSubjectTeacher(Authorization, id));
    }

    @Transactional
    @DeleteMapping("/teacher/{id}")
    public ResponseEntity<String> deleteSubject(@RequestHeader String Authorization, @PathVariable Long id) throws CostumeErrorException {
        return ResponseEntity.ok(subjectService.deleteSubject(Authorization, id));
    }

    @PutMapping("/teacher/{id}/update")
    public ResponseEntity<String> updateSubject(@RequestHeader String Authorization, @PathVariable Long id, @RequestBody SubjectCreateDTO data) throws CostumeErrorException {
        return ResponseEntity.ok(subjectService.updateSubject(Authorization, id, data));
    }

    @PostMapping("/teacher/{id}/addStudent/{studentId}")
    public ResponseEntity<String> addStudentToSubject(@RequestHeader String Authorization, @PathVariable Long id, @PathVariable Long studentId) throws CostumeErrorException {
        return ResponseEntity.ok(subjectService.addStudentToSubject(Authorization, id, studentId));
    }

    @DeleteMapping("/teacher/{id}/removeStudent/{studentId}")
    public ResponseEntity<String> removeStudentFromSubject(@RequestHeader String Authorization, @PathVariable Long id, @PathVariable Long studentId) throws CostumeErrorException {
        return ResponseEntity.ok(subjectService.removeStudentFromSubject(Authorization, id, studentId));
    }

    @GetMapping("/teacher/{id}/students")
    public ResponseEntity<List<UserDTO>> getStudentsFromSubject(@RequestHeader String Authorization, @PathVariable Long id) throws CostumeErrorException {
        return ResponseEntity.ok(subjectService.getStudentsFromSubject(Authorization, id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubjectDTO> getSubject(@RequestHeader String Authorization, @PathVariable Long id) throws CostumeErrorException {
        return ResponseEntity.ok(subjectService.getSubject(Authorization, id));
    }

    @GetMapping("/")
    public ResponseEntity<List<SubjectDTO>> getSubjectsStudent(@RequestHeader String Authorization) throws CostumeErrorException {
        return ResponseEntity.ok(subjectService.getSubjectsStudent(Authorization));
    }
}
