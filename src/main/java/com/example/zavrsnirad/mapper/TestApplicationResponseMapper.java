package com.example.zavrsnirad.mapper;

import com.example.zavrsnirad.dto.request.TestApplicationsResonseDTO;
import com.example.zavrsnirad.entity.TestApplication;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.function.Function;

@Component
public class TestApplicationResponseMapper implements Function<TestApplication, TestApplicationsResonseDTO> {
    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
    @Override
    public TestApplicationsResonseDTO apply(TestApplication testApplication) {
        return new TestApplicationsResonseDTO(
                testApplication.getId(),
                formatter.format(testApplication.getTest().getTestDate()),
                testApplication.getTest().getTestNote(),
                testApplication.getTestGrade(),
                testApplication.getTestNote(),
                testApplication.getTestGraded()
        );
    }

    public List<TestApplicationsResonseDTO> map(List<TestApplication> testApplications){
        return testApplications.stream().map(this::apply).toList();
    }
}
