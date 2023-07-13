package com.example.zavrsnirad.mapper;

import com.example.zavrsnirad.dto.request.SubjectCreateDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {SubjectCreateDtoMapper.class})
@ExtendWith(org.springframework.test.context.junit.jupiter.SpringExtension.class)
class SubjectCreateDtoMapperTest {
    @Autowired
    private SubjectCreateDtoMapper subjectCreateDtoMapper;

    @Test
    @DisplayName("Test apply all fealds")
    void apply1() {
        SubjectCreateDTO subjectCreateDTO = mock(SubjectCreateDTO.class);
        when(subjectCreateDTO.name()).thenReturn("Subject Name");
        when(subjectCreateDTO.description()).thenReturn("Subject Description");
        when(subjectCreateDTO.ects()).thenReturn(1);
        when(subjectCreateDTO.semester()).thenReturn(1);
        when(subjectCreateDTO.year()).thenReturn(1);

        com.example.zavrsnirad.entity.Subject actualApplyResult = subjectCreateDtoMapper.apply(subjectCreateDTO);

        assertEquals("Subject Name", actualApplyResult.getSubjectName());
        assertEquals("Subject Description", actualApplyResult.getSubjectDescription());
        assertEquals(1, actualApplyResult.getSubjectEcts());
        assertEquals(1, actualApplyResult.getSubjectSemester());
        assertEquals(1, actualApplyResult.getSubjectYear());

        verify(subjectCreateDTO, times(2)).name();
        verify(subjectCreateDTO, times(2)).description();
        verify(subjectCreateDTO, times(2)).ects();
        verify(subjectCreateDTO, times(2)).semester();
        verify(subjectCreateDTO, times(2)).year();
    }

    @Test
    @DisplayName("Test apply no name")
    void apply2() {
        SubjectCreateDTO subjectCreateDTO = mock(SubjectCreateDTO.class);
        when(subjectCreateDTO.name()).thenReturn(null);
        when(subjectCreateDTO.description()).thenReturn("Subject Description");
        when(subjectCreateDTO.ects()).thenReturn(1);
        when(subjectCreateDTO.semester()).thenReturn(1);
        when(subjectCreateDTO.year()).thenReturn(1);

        com.example.zavrsnirad.entity.Subject actualApplyResult = subjectCreateDtoMapper.apply(subjectCreateDTO);

        assertEquals("No name", actualApplyResult.getSubjectName());
        assertEquals("Subject Description", actualApplyResult.getSubjectDescription());
        assertEquals(1, actualApplyResult.getSubjectEcts());
        assertEquals(1, actualApplyResult.getSubjectSemester());
        assertEquals(1, actualApplyResult.getSubjectYear());

        verify(subjectCreateDTO, times(1)).name();
        verify(subjectCreateDTO, times(2)).description();
        verify(subjectCreateDTO, times(2)).ects();
        verify(subjectCreateDTO, times(2)).semester();
        verify(subjectCreateDTO, times(2)).year();
    }

    @Test
    @DisplayName("Test apply no description")
    void apply3() {
        SubjectCreateDTO subjectCreateDTO = mock(SubjectCreateDTO.class);
        when(subjectCreateDTO.name()).thenReturn("Subject name");
        when(subjectCreateDTO.description()).thenReturn(null);
        when(subjectCreateDTO.ects()).thenReturn(1);
        when(subjectCreateDTO.semester()).thenReturn(1);
        when(subjectCreateDTO.year()).thenReturn(1);

        com.example.zavrsnirad.entity.Subject actualApplyResult = subjectCreateDtoMapper.apply(subjectCreateDTO);

        assertEquals("Subject name", actualApplyResult.getSubjectName());
        assertEquals("No description", actualApplyResult.getSubjectDescription());
        assertEquals(1, actualApplyResult.getSubjectEcts());
        assertEquals(1, actualApplyResult.getSubjectSemester());
        assertEquals(1, actualApplyResult.getSubjectYear());

        verify(subjectCreateDTO, times(2)).name();
        verify(subjectCreateDTO, times(1)).description();
        verify(subjectCreateDTO, times(2)).ects();
        verify(subjectCreateDTO, times(2)).semester();
        verify(subjectCreateDTO, times(2)).year();
    }

    @Test
    @DisplayName("Test apply no ects")
    void apply4() {
        SubjectCreateDTO subjectCreateDTO = mock(SubjectCreateDTO.class);
        when(subjectCreateDTO.name()).thenReturn("Subject Name");
        when(subjectCreateDTO.description()).thenReturn("Subject Description");
        when(subjectCreateDTO.ects()).thenReturn(null);
        when(subjectCreateDTO.semester()).thenReturn(1);
        when(subjectCreateDTO.year()).thenReturn(1);

        com.example.zavrsnirad.entity.Subject actualApplyResult = subjectCreateDtoMapper.apply(subjectCreateDTO);

        assertEquals("Subject Name", actualApplyResult.getSubjectName());
        assertEquals("Subject Description", actualApplyResult.getSubjectDescription());
        assertEquals(0, actualApplyResult.getSubjectEcts());
        assertEquals(1, actualApplyResult.getSubjectSemester());
        assertEquals(1, actualApplyResult.getSubjectYear());

        verify(subjectCreateDTO, times(2)).name();
        verify(subjectCreateDTO, times(2)).description();
        verify(subjectCreateDTO, times(1)).ects();
        verify(subjectCreateDTO, times(2)).semester();
        verify(subjectCreateDTO, times(2)).year();
    }

    @Test
    @DisplayName("Test apply no semestar")
    void apply5() {
        SubjectCreateDTO subjectCreateDTO = mock(SubjectCreateDTO.class);
        when(subjectCreateDTO.name()).thenReturn("Subject Name");
        when(subjectCreateDTO.description()).thenReturn("Subject Description");
        when(subjectCreateDTO.ects()).thenReturn(1);
        when(subjectCreateDTO.semester()).thenReturn(null);
        when(subjectCreateDTO.year()).thenReturn(1);

        com.example.zavrsnirad.entity.Subject actualApplyResult = subjectCreateDtoMapper.apply(subjectCreateDTO);

        assertEquals("Subject Name", actualApplyResult.getSubjectName());
        assertEquals("Subject Description", actualApplyResult.getSubjectDescription());
        assertEquals(1, actualApplyResult.getSubjectEcts());
        assertEquals(0, actualApplyResult.getSubjectSemester());
        assertEquals(1, actualApplyResult.getSubjectYear());

        verify(subjectCreateDTO, times(2)).name();
        verify(subjectCreateDTO, times(2)).description();
        verify(subjectCreateDTO, times(2)).ects();
        verify(subjectCreateDTO, times(1)).semester();
        verify(subjectCreateDTO, times(2)).year();
    }

    @Test
    @DisplayName("Test apply no year")
    void apply6() {
        SubjectCreateDTO subjectCreateDTO = mock(SubjectCreateDTO.class);
        when(subjectCreateDTO.name()).thenReturn("Subject Name");
        when(subjectCreateDTO.description()).thenReturn("Subject Description");
        when(subjectCreateDTO.ects()).thenReturn(1);
        when(subjectCreateDTO.semester()).thenReturn(1);
        when(subjectCreateDTO.year()).thenReturn(null);

        com.example.zavrsnirad.entity.Subject actualApplyResult = subjectCreateDtoMapper.apply(subjectCreateDTO);

        assertEquals("Subject Name", actualApplyResult.getSubjectName());
        assertEquals("Subject Description", actualApplyResult.getSubjectDescription());
        assertEquals(1, actualApplyResult.getSubjectEcts());
        assertEquals(1, actualApplyResult.getSubjectSemester());
        assertEquals(0, actualApplyResult.getSubjectYear());

        verify(subjectCreateDTO, times(2)).name();
        verify(subjectCreateDTO, times(2)).description();
        verify(subjectCreateDTO, times(2)).ects();
        verify(subjectCreateDTO, times(2)).semester();
        verify(subjectCreateDTO, times(1)).year();
    }
}