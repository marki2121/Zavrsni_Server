package com.example.zavrsnirad.controller;

import com.example.zavrsnirad.dto.request.TestCreateDTO;
import com.example.zavrsnirad.dto.response.TestApplicationResponseDTO;
import com.example.zavrsnirad.dto.response.TestResponseDTO;
import com.example.zavrsnirad.service.TestService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/test")
public class TestController {
    private final TestService testService;

    public TestController(TestService testService) {
        this.testService = testService;
    }

    @PostMapping("teacher/{id}/create")
    public ResponseEntity<String> createTest(@RequestHeader String Authorization, @PathVariable Long id, @RequestBody TestCreateDTO data) throws ParseException {
        return ResponseEntity.ok(testService.createTest(Authorization, id, data));
    }

    @GetMapping("teacher/{id}")
    public ResponseEntity<List<TestResponseDTO>> getTests(@RequestHeader String Authorization, @PathVariable Long id){
        return ResponseEntity.ok(testService.getTestsForSubject(Authorization, id));
    }

    // -
    @DeleteMapping("teacher/{testId}")
    public ResponseEntity<String> deleteTest(@RequestHeader String Authorization, @PathVariable Long testId){
        return ResponseEntity.ok(testService.deleteTest(Authorization, testId));
    }

    // -
    @PutMapping("teacher/{testId}/update")
    public ResponseEntity<String> updateTest(@RequestHeader String Authorization, @PathVariable Long testId, @RequestBody TestCreateDTO data) throws ParseException {
        return ResponseEntity.ok(testService.updateTest(Authorization, testId, data));
    }

    @GetMapping("teacher/{id}/applicants")
    public ResponseEntity<List<TestApplicationResponseDTO>> getAllTestsApplications(@RequestHeader String Authorization, @PathVariable Long id){
        return ResponseEntity.ok(testService.getAllTestsApplications(Authorization, id));
    }

    @PostMapping("teacher/{applicationId}/grade/{grade}")
    public ResponseEntity<String> gradeTest(@RequestHeader String Authorization, @PathVariable Long applicationId, @PathVariable Integer grade){
        return ResponseEntity.ok(testService.gradeTest(Authorization, applicationId, grade));
    }

    @GetMapping("{id}")
    public ResponseEntity<List<TestResponseDTO>> getAllTestesForSubject(@RequestHeader String Authorization, @PathVariable Long id){
        return ResponseEntity.ok(testService.getAllTestesForSubject(Authorization, id));
    }

    @GetMapping("{id}/applications")
    public ResponseEntity<List<TestApplicationResponseDTO>> getAllAppliedTestsForStudent(@RequestHeader String Authorization, @PathVariable Long id){
        return ResponseEntity.ok(testService.getAllAppliedTestsForStudent(Authorization, id));
    }
}
