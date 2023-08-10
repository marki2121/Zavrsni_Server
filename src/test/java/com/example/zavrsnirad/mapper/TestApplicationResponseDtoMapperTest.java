package com.example.zavrsnirad.mapper;

import com.example.zavrsnirad.dto.response.TestApplicationResponseDTO;
import com.example.zavrsnirad.entity.Subject;
import com.example.zavrsnirad.entity.TestApplication;
import com.example.zavrsnirad.entity.User;
import com.example.zavrsnirad.entity.UserProfile;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ContextConfiguration;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
        TestApplication testApplication = new TestApplication();
        subject.setSubjectName("Subject Name");
        subject.setSubjectProfessor(user);
        userProfile.setFirstName("First Name");
        userProfile.setLastName("Last Name");
        user.setUserProfile(userProfile);
        test.setSubject(subject);
        test.setTestNote("asd");
        test.setTestDate(new Date());
        test.setId(1L);

        testApplication.setTest(test);
        testApplication.setTestNote("Test Note");
        testApplication.setTestGrade(1);
        testApplication.setTestGraded(true);
        testApplication.setId(1L);
        testApplication.setStudent(user);


        TestApplicationResponseDTO actualApplyResult = new TestApplicationResponseDtoMapper().apply(testApplication);

        assertEquals(1L, actualApplyResult.id());
        assertEquals(1, actualApplyResult.grade());
        assertEquals(true, actualApplyResult.testGraded());
    }
}