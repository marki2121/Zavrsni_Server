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

    //TODO: napravi call za update predmeta


    //TODO: napraviti funkciju za dohvacanje svih predmeta na koje je ucenik dodan
    @GetMapping("/{id}")
    public ResponseEntity<Object> getSubject(@RequestHeader String Authorization, @PathVariable Long id){
        return subjectService.getSubject(Authorization, id);
    }
}
