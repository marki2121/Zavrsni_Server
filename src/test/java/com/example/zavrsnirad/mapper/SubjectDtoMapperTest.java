package com.example.zavrsnirad.mapper;

import com.example.zavrsnirad.dto.request.SubjectDTO;
import com.example.zavrsnirad.entity.Subject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {SubjectDtoMapper.class})
@ExtendWith(org.springframework.test.context.junit.jupiter.SpringExtension.class)
class SubjectDtoMapperTest {
    @Autowired
    private SubjectDtoMapper subjectDtoMapper;

    @Test
    @DisplayName("Test apply")
    void apply() {
        Subject subject = mock(Subject.class);
        when(subject.getId()).thenReturn(1L);
        when(subject.getSubjectName()).thenReturn("Subject Name");
        when(subject.getSubjectDescription()).thenReturn("Subject Description");
        when(subject.getSubjectEcts()).thenReturn(1);
        when(subject.getSubjectSemester()).thenReturn(1);
        when(subject.getSubjectYear()).thenReturn(1);

        SubjectDTO actualApplyResult = subjectDtoMapper.apply(subject);

        assertEquals(1L, actualApplyResult.id());
        assertEquals("Subject Name", actualApplyResult.Name());
        assertEquals("Subject Description", actualApplyResult.Description());
        assertEquals(1, actualApplyResult.Ects());
        assertEquals(1, actualApplyResult.Semester());
        assertEquals(1, actualApplyResult.Year());

        verify(subject).getId();
        verify(subject).getSubjectName();
        verify(subject).getSubjectDescription();
        verify(subject).getSubjectEcts();
        verify(subject).getSubjectSemester();
        verify(subject).getSubjectYear();

    }
}