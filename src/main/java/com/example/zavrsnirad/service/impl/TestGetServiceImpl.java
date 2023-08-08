package com.example.zavrsnirad.service.impl;

import com.example.zavrsnirad.entity.CostumeErrorException;
import com.example.zavrsnirad.entity.Test;
import com.example.zavrsnirad.entity.User;
import com.example.zavrsnirad.repository.TestRepository;
import com.example.zavrsnirad.service.TestGetService;
import com.example.zavrsnirad.service.UserGetService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class TestGetServiceImpl implements TestGetService {
    private final UserGetService userGetService;
    private final TestRepository testRepository;

    public TestGetServiceImpl(UserGetService userGetService, TestRepository testRepository) {
        this.userGetService = userGetService;
        this.testRepository = testRepository;
    }

    @Override
    public Test getTestForUser(String authorization, Long testId) {
        User user = userGetService.getUserFromToken(authorization);
        Test test = testRepository.findById(testId).orElseThrow(() -> new CostumeErrorException("Test not found", HttpStatus.BAD_REQUEST));

        test.getSubject().getStudents().stream().filter((s) -> s.getId().equals(user.getId())).findAny().orElseThrow(() -> new CostumeErrorException("Student isn't enrolled in the subject", HttpStatus.BAD_REQUEST));

        return test;
    }
}
