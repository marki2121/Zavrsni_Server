package com.example.zavrsnirad.mapper;

import com.example.zavrsnirad.dto.response.TestResponseDTO;
import com.example.zavrsnirad.entity.Test;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

@Service
public class TestResponseDtoMapper implements Function<Test, TestResponseDTO> {
    SimpleDateFormat sm = new SimpleDateFormat("dd-MM-yyyy");
    @Override
    public TestResponseDTO apply(Test test) {
        return new TestResponseDTO(
                test.getId(),
                test.getSubject().getSubjectName(),
                sm.format(test.getTestDate()),
                test.getTestNote());
    }

    public List<TestResponseDTO> map(Set<Test> tests){
        return tests.stream().map(this::apply).toList();
    }
}
