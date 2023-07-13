package com.example.zavrsnirad.mapper;

import com.example.zavrsnirad.dto.response.TestApplicationResponseDTO;
import com.example.zavrsnirad.entity.TestApplication;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class TestApplicationResponseDtoMapper implements Function<TestApplication, TestApplicationResponseDTO> {
    @Override
    public TestApplicationResponseDTO apply(TestApplication testApplication) {
        return new TestApplicationResponseDTO(
                testApplication.getId(),
                testApplication.getTest().getSubject().getSubjectName(),
                testApplication.getTest().getSubject().getSubjectProfessor().getUserProfile().getFirstName() + " " + testApplication.getTest().getSubject().getSubjectProfessor().getUserProfile().getLastName(),
                testApplication.getStudent().getUserProfile().getFirstName() + " " + testApplication.getStudent().getUserProfile().getLastName(),
                testApplication.getTestGrade(),
                testApplication.getTestGraded()
        );
    }
}
