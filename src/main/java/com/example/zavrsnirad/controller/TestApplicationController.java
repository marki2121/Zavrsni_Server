package com.example.zavrsnirad.controller;

import com.example.zavrsnirad.dto.response.TestApplicationResponseDTO;
import com.example.zavrsnirad.entity.CostumeErrorException;
import com.example.zavrsnirad.service.TestApplicationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/application")
public class TestApplicationController {
    private final TestApplicationService testApplicationService;

    public TestApplicationController(TestApplicationService testApplicationService) {
        this.testApplicationService = testApplicationService;
    }

    @PostMapping("{testId}")
    public ResponseEntity<String> applyForTest(@RequestHeader String Authorization, @PathVariable Long testId) throws CostumeErrorException {
        return ResponseEntity.ok(testApplicationService.applyForTest(Authorization, testId));
    }

    @GetMapping("/all")
    public ResponseEntity<List<TestApplicationResponseDTO>> getAllApplications(@RequestHeader String Authorization) throws CostumeErrorException {
        return ResponseEntity.ok(testApplicationService.getAllApplications(Authorization));
    }

    @DeleteMapping("{applicationId}")
    public ResponseEntity<String> deleteApplication(@RequestHeader String Authorization, @PathVariable Long applicationId) throws CostumeErrorException {
        return ResponseEntity.ok(testApplicationService.deleteApplication(Authorization, applicationId));
    }
}
