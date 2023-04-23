package com.example.zavrsnirad.controller;

import com.example.zavrsnirad.dto.SubjectCreateDTO;
import com.example.zavrsnirad.service.SubjectService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/subject")
public class SubjectController {
    private final SubjectService subjectService;

    public SubjectController(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @PostMapping("/create")
    public ResponseEntity<Object> createSubject(@RequestHeader String Authorization, @RequestBody SubjectCreateDTO data){
        return subjectService.createSubject(Authorization, data);
    }
}
