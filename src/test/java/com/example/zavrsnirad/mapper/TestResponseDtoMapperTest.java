package com.example.zavrsnirad.mapper;

import com.example.zavrsnirad.dto.response.TestResponseDTO;
import com.example.zavrsnirad.entity.Subject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {TestResponseDtoMapper.class})
@ExtendWith(SpringExtension.class)
class TestResponseDtoMapperTest {
    @Autowired
    private TestResponseDtoMapper testResponseDtoMapper;
    @Test
    @DisplayName("Test apply")
    void apply() {

        com.example.zavrsnirad.entity.Test test = mock(com.example.zavrsnirad.entity.Test.class);
        when(test.getId()).thenReturn(1L);
        when(test.getTestDate()).thenReturn(new Date(1,1,1,1,1,1));
        when(test.getTestNote()).thenReturn("Test Note");
        when(test.getSubject()).thenReturn(new Subject());


        TestResponseDTO actualApplyResult = testResponseDtoMapper.apply(test);

        assertEquals("Test Note", actualApplyResult.note());
        assertEquals(1L, actualApplyResult.id());
        assertEquals("01-02-1901", actualApplyResult.date());

        verify(test).getTestDate();
        verify(test).getTestNote();
        verify(test).getId();
    }
}