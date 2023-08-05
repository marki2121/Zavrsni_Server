package com.example.zavrsnirad.controller;

import com.example.zavrsnirad.dto.request.TestCreateDTO;
import com.example.zavrsnirad.service.TestService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@RestController
@CrossOrigin
@RequestMapping("/api/test")
public class TestController {
    private final TestService testService;

    public TestController(TestService testService) {
        this.testService = testService;
    }

    @PostMapping("teacher/{id}/create")
    public ResponseEntity<Object> createTest(@RequestHeader String Authorization, @PathVariable Long id, @RequestBody TestCreateDTO data) throws ParseException {
        return testService.createTest(Authorization, id, data);
    }

    @GetMapping("teacher/{id}")
    public ResponseEntity<Object> getTests(@RequestHeader String Authorization, @PathVariable Long id){
        return testService.getTestsForSubject(Authorization, id);
    }

    @DeleteMapping("teacher/{id}/{testId}")
    public ResponseEntity<Object> deleteTest(@RequestHeader String Authorization, @PathVariable Long id, @PathVariable Long testId){
        return testService.deleteTest(Authorization, id, testId);
    }

    @PutMapping("teacher/{id}/{testId}/update")
    public ResponseEntity<Object> updateTest(@RequestHeader String Authorization, @PathVariable Long id, @PathVariable Long testId, @RequestBody TestCreateDTO data) throws ParseException {
        return testService.updateTest(Authorization, id, testId, data);
    }

    @GetMapping("teacher/{id}/{testId}/all")
    public ResponseEntity<Object> getAllTestsApplications(@RequestHeader String Authorization, @PathVariable Long id, @PathVariable Long testId){
        return testService.getAllTestsApplications(Authorization, id, testId);
    }

    @PostMapping("teacher/{applicationId}/grade/{grade}")
    public ResponseEntity<Object> gradeTest(@RequestHeader String Authorization, @PathVariable Long applicationId, @PathVariable Integer grade){
        return testService.gradeTest(Authorization, applicationId, grade);
    }

    @GetMapping("{id}")
    public ResponseEntity<Object> getAllTestesForSubject(@RequestHeader String Authorization, @PathVariable Long id){
        return testService.getAllTestesForSubject(Authorization, id);
    }

    @GetMapping("{id}/applications")
    public ResponseEntity<Object> getAllAppliedTestsForStudent(@RequestHeader String Authorization, @PathVariable Long id){
        return testService.getAllAppliedTestsForStudent(Authorization, id);
    }
}
