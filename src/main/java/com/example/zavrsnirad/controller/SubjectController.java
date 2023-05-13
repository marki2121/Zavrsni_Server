package com.example.zavrsnirad.controller;

import com.example.zavrsnirad.dto.SubjectCreateDTO;
import com.example.zavrsnirad.service.SubjectService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/subject")
public class SubjectController {
    private final SubjectService subjectService;

    public SubjectController(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    //Teacher endpoints
    @PostMapping("/teacher/create")
    public ResponseEntity<Object> createSubject(@RequestHeader String Authorization, @RequestBody SubjectCreateDTO data){
        return subjectService.createSubject(Authorization, data);
    }

    @GetMapping("/teacher")
    public ResponseEntity<Object> getSubjects(@RequestHeader String Authorization){
        return subjectService.getSubjects(Authorization);
    }

    @GetMapping("/teacher/{id}")
    public ResponseEntity<Object> getSubjectTeacher(@RequestHeader String Authorization, @PathVariable Long id){
        return subjectService.getSubjectTeacher(Authorization, id);
    }

    @DeleteMapping("/teacher/{id}")
    public ResponseEntity<Object> deleteSubject(@RequestHeader String Authorization, @PathVariable Long id){
        return subjectService.deleteSubject(Authorization, id);
    }

    @PutMapping("/teacher/{id}/update")
    public ResponseEntity<Object> updateSubject(@RequestHeader String Authorization, @PathVariable Long id, @RequestBody SubjectCreateDTO data){
        return subjectService.updateSubject(Authorization, id, data);
    }

    @PostMapping("/teacher/{id}/addStudent/{studentId}")
    public ResponseEntity<Object> addStudentToSubject(@RequestHeader String Authorization, @PathVariable Long id, @PathVariable Long studentId){
        return subjectService.addStudentToSubject(Authorization, id, studentId);
    }

    @DeleteMapping("/teacher/{id}/removeStudent/{studentId}")
    public ResponseEntity<Object> removeStudentFromSubject(@RequestHeader String Authorization, @PathVariable Long id, @PathVariable Long studentId){
        return subjectService.removeStudentFromSubject(Authorization, id, studentId);
    }

    @GetMapping("/teacher/{id}/students")
    public ResponseEntity<Object> getStudentsFromSubject(@RequestHeader String Authorization, @PathVariable Long id){
        return subjectService.getStudentsFromSubject(Authorization, id);
    }

    //Student endpoints
    @GetMapping("/{id}")
    public ResponseEntity<Object> getSubject(@RequestHeader String Authorization, @PathVariable Long id){
        return subjectService.getSubject(Authorization, id);
    }

    @GetMapping("/")
    public ResponseEntity<Object> getSubjectsStudent(@RequestHeader String Authorization){
        return subjectService.getSubjectsStudent(Authorization);
    }
}
