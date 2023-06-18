package com.example.zavrsnirad.mapper;

import com.example.zavrsnirad.entity.Subject;
import com.example.zavrsnirad.entity.TestApplication;
import com.example.zavrsnirad.entity.User;
import com.example.zavrsnirad.entity.UserProfile;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {TestApplicationResponseDtoMapper.class})
@ExtendWith(org.springframework.test.context.junit.jupiter.SpringExtension.class)
class TestApplicationResponseDtoMapperTest {

    @Test
    @DisplayName("Test apply")
    void apply() {
        User user = new User();
        UserProfile userProfile = new UserProfile();
        com.example.zavrsnirad.entity.Test test = new com.example.zavrsnirad.entity.Test();
        Subject subject = new Subject();
        subject.setSubjectName("Subject Name");
        subject.setSubjectProfessor(user);
        userProfile.setAboutMe("First Name");
        userProfile.setLastName("Last Name");
        user.setUserProfile(userProfile);
        test.setSubject(subject);

        TestApplication testApplication = mock(TestApplication.class);
        when(testApplication.getId()).thenReturn(1L);
        when(testApplication.getTestGrade()).thenReturn(1);
        when(testApplication.getTestGraded()).thenReturn(true);
        when(testApplication.getTest()).thenReturn(test);
        when(testApplication.getStudent()).thenReturn(user);


        com.example.zavrsnirad.dto.TestApplicationResponseDTO actualApplyResult = new TestApplicationResponseDtoMapper().apply(testApplication);

        assertEquals(1L, actualApplyResult.id());
        assertEquals(1, actualApplyResult.grade());
        assertEquals(true, actualApplyResult.testGraded());

        verify(testApplication).getId();
        verify(testApplication).getTestGrade();
        verify(testApplication).getTestGraded();
    }
}