package com.example.zavrsnirad.controller;

import com.example.zavrsnirad.service.TestApplicationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api/application")
public class TestApplicationController {
    private final TestApplicationService testApplicationService;

    public TestApplicationController(TestApplicationService testApplicationService) {
        this.testApplicationService = testApplicationService;
    }

    @PostMapping("{testId}")
    public ResponseEntity<Object> applyForTest(@RequestHeader String Authorization, @PathVariable Long testId){
        return testApplicationService.applyForTest(Authorization, testId);
    }

    @GetMapping("/all")
    public ResponseEntity<Object> getAllApplications(@RequestHeader String Authorization){
        return testApplicationService.getAllApplications(Authorization);
    }

    @DeleteMapping("{applicationId}")
    public ResponseEntity<Object> deleteApplication(@RequestHeader String Authorization, @PathVariable Long applicationId){
        return testApplicationService.deleteApplication(Authorization, applicationId);
    }
}
