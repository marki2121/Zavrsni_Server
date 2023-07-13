package com.example.zavrsnirad.mapper;

import com.example.zavrsnirad.dto.response.TestResponseDTO;
import com.example.zavrsnirad.entity.Test;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class TestResponseDtoMapper implements Function<Test, TestResponseDTO> {
    @Override
    public TestResponseDTO apply(Test test) {
        return new TestResponseDTO(
                test.getId(),
                test.getSubject().getSubjectName(),
                test.getTestDate(),
                test.getTestNote());
    }
}
