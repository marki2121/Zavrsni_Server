package com.example.zavrsnirad.mapper;

import com.example.zavrsnirad.dto.SubjectCreateDTO;
import com.example.zavrsnirad.entity.Subject;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class SubjectCreateDtoMapper implements Function<SubjectCreateDTO, Subject> {

    @Override
    public Subject apply(SubjectCreateDTO subjectCreateDTO) {
        Subject subject = new Subject();

        if(subjectCreateDTO.name() != null) subject.setSubjectName(subjectCreateDTO.name());
        else subject.setSubjectName("No name");
        if(subjectCreateDTO.description() != null) subject.setSubjectDescription(subjectCreateDTO.description());
        else subject.setSubjectDescription("No description");
        if(subjectCreateDTO.ects() != null) subject.setSubjectEcts(subjectCreateDTO.ects());
        else subject.setSubjectEcts(0);
        if(subjectCreateDTO.semester() != null) subject.setSubjectSemester(subjectCreateDTO.semester());
        else subject.setSubjectSemester(0);
        if(subjectCreateDTO.year() != null) subject.setSubjectYear(subjectCreateDTO.year());
        else subject.setSubjectYear(0);

        return subject;
    }
}
