package com.example.zavrsnirad.mapper;

import com.example.zavrsnirad.dto.SubjectDTO;
import com.example.zavrsnirad.entity.Subject;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class SubjectDtoMapper implements Function<Subject, SubjectDTO> {
    @Override
    public SubjectDTO apply(Subject subject) {
        return new SubjectDTO(
                subject.getId(),
                subject.getSubjectName(),
                subject.getSubjectDescription(),
                subject.getSubjectEcts(),
                subject.getSubjectSemester(),
                subject.getSubjectYear()
        );
    }
}
