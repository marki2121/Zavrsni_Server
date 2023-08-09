package com.example.zavrsnirad.mapper;

import com.example.zavrsnirad.dto.response.TestApplicationResponseDTO;
import com.example.zavrsnirad.entity.TestApplication;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.function.Function;

@Service
public class TestApplicationResponseDtoMapper implements Function<TestApplication, TestApplicationResponseDTO> {
    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

    @Override
    public TestApplicationResponseDTO apply(TestApplication testApplication) {
        return new TestApplicationResponseDTO(
                testApplication.getId(),
                formatter.format(testApplication.getTest().getTestDate()),
                testApplication.getTest().getTestNote(),
                testApplication.getTest().getSubject().getSubjectName(),
                testApplication.getTest().getSubject().getSubjectProfessor().getUserProfile().getFirstName() + " " + testApplication.getTest().getSubject().getSubjectProfessor().getUserProfile().getLastName(),
                testApplication.getStudent().getUserProfile().getFirstName() + " " + testApplication.getStudent().getUserProfile().getLastName(),
                testApplication.getTestGrade(),
                testApplication.getTestGraded()
        );
    }

    public List<TestApplicationResponseDTO> map(List<TestApplication> testApplications){
        return testApplications.stream().map(this::apply).toList();
    }
}
