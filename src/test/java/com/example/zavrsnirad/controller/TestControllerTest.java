package com.example.zavrsnirad.controller;

import com.example.zavrsnirad.dto.request.TestCreateDTO;
import com.example.zavrsnirad.service.TestService;
import com.example.zavrsnirad.util.TestApplicationResponseDtoUtil;
import com.example.zavrsnirad.util.TestResponseDtoUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.oidc.StandardClaimNames;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class TestControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TestService testService;

    @Test
    @DisplayName("Test createTest with user admin")
    void createTest() throws Exception {
        TestCreateDTO testCreateDTO = new TestCreateDTO(null, null);
        ObjectMapper objectMapper = new ObjectMapper();
        when(testService.createTest(null, 1L, testCreateDTO)).thenReturn("Created");
        mockMvc.perform(post("/api/test/teacher/1/create")
                        .with(jwt().authorities(List.of(new SimpleGrantedAuthority("SCOPE_ADMIN")))
                                .jwt(jwt -> jwt.claim(StandardClaimNames.PREFERRED_USERNAME, "admin")))
                        .header("Authorization", "")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(testCreateDTO)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Test createTest with user teacher")
    void createTestTeacher() throws Exception {
        TestCreateDTO testCreateDTO = new TestCreateDTO(null, null);
        ObjectMapper objectMapper = new ObjectMapper();
        when(testService.createTest(null, 1L, testCreateDTO)).thenReturn("Created");
        mockMvc.perform(post("/api/test/teacher/1/create")
                        .with(jwt().authorities(List.of(new SimpleGrantedAuthority("SCOPE_TEACHER")))
                                .jwt(jwt -> jwt.claim(StandardClaimNames.PREFERRED_USERNAME, "teacher")))
                        .header("Authorization", "")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(testCreateDTO)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Test createTest with user")
    void createTestUser() throws Exception {
        TestCreateDTO testCreateDTO = new TestCreateDTO(null, null);
        ObjectMapper objectMapper = new ObjectMapper();
        when(testService.createTest(null, 1L, testCreateDTO)).thenReturn("Created");
        mockMvc.perform(post("/api/test/teacher/1/create")
                        .with(jwt().authorities(List.of(new SimpleGrantedAuthority("SCOPE_USER")))
                                .jwt(jwt -> jwt.claim(StandardClaimNames.PREFERRED_USERNAME, "user")))
                        .header("Authorization", "")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(testCreateDTO)))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("Test createTest with no credentials")
    void createTestNoCreds() throws Exception {
        TestCreateDTO testCreateDTO = new TestCreateDTO(null, null);
        ObjectMapper objectMapper = new ObjectMapper();
        when(testService.createTest(null, 1L, testCreateDTO)).thenReturn("Created");
        mockMvc.perform(post("/api/test/teacher/1/create")
                        .header("Authorization", "")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(testCreateDTO)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("Test createTest with user admin")
    void getTests() throws Exception {
        when(testService.getTestsForSubject(null, 1L)).thenReturn(List.of(TestResponseDtoUtil.generate()));
        mockMvc.perform(get("/api/test/teacher/1")
                        .with(jwt().authorities(List.of(new SimpleGrantedAuthority("SCOPE_ADMIN")))
                                .jwt(jwt -> jwt.claim(StandardClaimNames.PREFERRED_USERNAME, "admin")))
                        .header("Authorization", ""))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Test createTest with user teacher")
    void getTestsTeacher() throws Exception {
        when(testService.getTestsForSubject(null, 1L)).thenReturn(List.of(TestResponseDtoUtil.generate()));
        mockMvc.perform(get("/api/test/teacher/1")
                        .with(jwt().authorities(List.of(new SimpleGrantedAuthority("SCOPE_TEACHER")))
                                .jwt(jwt -> jwt.claim(StandardClaimNames.PREFERRED_USERNAME, "teacher")))
                        .header("Authorization", ""))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Test createTest with user")
    void getTestsUser() throws Exception {
        when(testService.getTestsForSubject(null, 1L)).thenReturn(List.of(TestResponseDtoUtil.generate()));
        mockMvc.perform(get("/api/test/teacher/1")
                        .with(jwt().authorities(List.of(new SimpleGrantedAuthority("SCOPE_USER")))
                                .jwt(jwt -> jwt.claim(StandardClaimNames.PREFERRED_USERNAME, "user")))
                        .header("Authorization", ""))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("Test createTest with no credentials")
    void getTestsNoCreds() throws Exception {
        when(testService.getTestsForSubject(null, 1L)).thenReturn(List.of(TestResponseDtoUtil.generate()));
        mockMvc.perform(get("/api/test/teacher/1")
                        .header("Authorization", ""))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("Test createTest with user admin")
    void deleteTest() throws Exception {
        when(testService.deleteTest(null, 1L)).thenReturn("Deleted");
        mockMvc.perform(delete("/api/test/teacher/1")
                        .with(jwt().authorities(List.of(new SimpleGrantedAuthority("SCOPE_ADMIN")))
                                .jwt(jwt -> jwt.claim(StandardClaimNames.PREFERRED_USERNAME, "admin")))
                        .header("Authorization", ""))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Test createTest with user teacher")
    void deleteTestTeacher() throws Exception {
        when(testService.deleteTest(null, 1L)).thenReturn("Deleted");
        mockMvc.perform(delete("/api/test/teacher/1")
                        .with(jwt().authorities(List.of(new SimpleGrantedAuthority("SCOPE_TEACHER")))
                                .jwt(jwt -> jwt.claim(StandardClaimNames.PREFERRED_USERNAME, "teacher")))
                        .header("Authorization", ""))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Test createTest with user")
    void deleteTestUser() throws Exception {
        when(testService.deleteTest(null, 1L)).thenReturn("Deleted");
        mockMvc.perform(delete("/api/test/teacher/1")
                        .with(jwt().authorities(List.of(new SimpleGrantedAuthority("SCOPE_USER")))
                                .jwt(jwt -> jwt.claim(StandardClaimNames.PREFERRED_USERNAME, "user")))
                        .header("Authorization", ""))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("Test createTest with no credentials")
    void deleteTestNoCreds() throws Exception {
        when(testService.deleteTest(null, 1L)).thenReturn("Deleted");
        mockMvc.perform(delete("/api/test/teacher/1")
                        .header("Authorization", ""))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("Test updateTest with user admin")
    void updateTest() throws Exception {
        TestCreateDTO testCreateDTO = new TestCreateDTO(null, null);
        ObjectMapper objectMapper = new ObjectMapper();
        when(testService.updateTest(null, 1L, testCreateDTO)).thenReturn("Updated");
        mockMvc.perform(put("/api/test/teacher/1/update")
                        .with(jwt().authorities(List.of(new SimpleGrantedAuthority("SCOPE_ADMIN")))
                                .jwt(jwt -> jwt.claim(StandardClaimNames.PREFERRED_USERNAME, "admin")))
                        .header("Authorization", "")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(testCreateDTO)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Test updateTest with user teacher")
    void updateTestTeacher() throws Exception {
        TestCreateDTO testCreateDTO = new TestCreateDTO(null, null);
        ObjectMapper objectMapper = new ObjectMapper();
        when(testService.updateTest(null, 1L, testCreateDTO)).thenReturn("Updated");
        mockMvc.perform(put("/api/test/teacher/1/update")
                        .with(jwt().authorities(List.of(new SimpleGrantedAuthority("SCOPE_TEACHER")))
                                .jwt(jwt -> jwt.claim(StandardClaimNames.PREFERRED_USERNAME, "teacher")))
                        .header("Authorization", "")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(testCreateDTO)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Test updateTest with user")
    void updateTestUser() throws Exception {
        TestCreateDTO testCreateDTO = new TestCreateDTO(null, null);
        ObjectMapper objectMapper = new ObjectMapper();
        when(testService.updateTest(null, 1L, testCreateDTO)).thenReturn("Updated");
        mockMvc.perform(put("/api/test/teacher/1/update")
                        .with(jwt().authorities(List.of(new SimpleGrantedAuthority("SCOPE_USER")))
                                .jwt(jwt -> jwt.claim(StandardClaimNames.PREFERRED_USERNAME, "user")))
                        .header("Authorization", "")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(testCreateDTO)))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("Test updateTest with no credentials")
    void updateTestNoCreds() throws Exception {
        TestCreateDTO testCreateDTO = new TestCreateDTO(null, null);
        ObjectMapper objectMapper = new ObjectMapper();
        when(testService.updateTest(null, 1L, testCreateDTO)).thenReturn("Updated");
        mockMvc.perform(put("/api/test/teacher/1/update")
                        .header("Authorization", "")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(testCreateDTO)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("Test getAllTestsApplications with user admin")
    void getAllTestsApplications() throws Exception {
        when(testService.getAllTestsApplications(null, 1L)).thenReturn(List.of(TestApplicationResponseDtoUtil.generate()));
        mockMvc.perform(get("/api/test/teacher/1/applicants")
                        .with(jwt().authorities(List.of(new SimpleGrantedAuthority("SCOPE_ADMIN")))
                                .jwt(jwt -> jwt.claim(StandardClaimNames.PREFERRED_USERNAME, "admin")))
                        .header("Authorization", ""))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Test getAllTestsApplications with user teacher")
    void getAllTestsApplicationsTeacher() throws Exception {
        when(testService.getAllTestsApplications(null, 1L)).thenReturn(List.of(TestApplicationResponseDtoUtil.generate()));
        mockMvc.perform(get("/api/test/teacher/1/applicants")
                        .with(jwt().authorities(List.of(new SimpleGrantedAuthority("SCOPE_TEACHER")))
                                .jwt(jwt -> jwt.claim(StandardClaimNames.PREFERRED_USERNAME, "teacher")))
                        .header("Authorization", ""))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Test getAllTestsApplications with user")
    void getAllTestsApplicationsUser() throws Exception {
        when(testService.getAllTestsApplications(null, 1L)).thenReturn(List.of(TestApplicationResponseDtoUtil.generate()));
        mockMvc.perform(get("/api/test/teacher/1/applicants")
                        .with(jwt().authorities(List.of(new SimpleGrantedAuthority("SCOPE_USER")))
                                .jwt(jwt -> jwt.claim(StandardClaimNames.PREFERRED_USERNAME, "user")))
                        .header("Authorization", ""))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("Test getAllTestsApplications with no credentials")
    void getAllTestsApplicationsNoCreds() throws Exception {
        when(testService.getAllTestsApplications(null, 1L)).thenReturn(List.of(TestApplicationResponseDtoUtil.generate()));
        mockMvc.perform(get("/api/test/teacher/1/applicants")
                        .header("Authorization", ""))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("Test gradeTest with user admin")
    void gradeTest() throws Exception {
        when(testService.gradeTest(null, 1L, 1)).thenReturn("Graded");
        mockMvc.perform(post("/api/test/teacher/1/grade/1")
                        .with(jwt().authorities(List.of(new SimpleGrantedAuthority("SCOPE_ADMIN")))
                                .jwt(jwt -> jwt.claim(StandardClaimNames.PREFERRED_USERNAME, "admin")))
                        .header("Authorization", ""))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Test gradeTest with user teacher")
    void gradeTestTeacher() throws Exception {
        when(testService.gradeTest(null, 1L, 1)).thenReturn("Graded");
        mockMvc.perform(post("/api/test/teacher/1/grade/1")
                        .with(jwt().authorities(List.of(new SimpleGrantedAuthority("SCOPE_TEACHER")))
                                .jwt(jwt -> jwt.claim(StandardClaimNames.PREFERRED_USERNAME, "teacher")))
                        .header("Authorization", ""))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Test gradeTest with user")
    void gradeTestUser() throws Exception {
        when(testService.gradeTest(null, 1L, 1)).thenReturn("Graded");
        mockMvc.perform(post("/api/test/teacher/1/grade/1")
                        .with(jwt().authorities(List.of(new SimpleGrantedAuthority("SCOPE_USER")))
                                .jwt(jwt -> jwt.claim(StandardClaimNames.PREFERRED_USERNAME, "user")))
                        .header("Authorization", ""))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("Test gradeTest with no credentials")
    void gradeTestNoCreds() throws Exception {
        when(testService.gradeTest(null, 1L, 1)).thenReturn("Graded");
        mockMvc.perform(post("/api/test/teacher/1/grade/1")
                        .header("Authorization", ""))
                .andExpect(status().isUnauthorized());
    }
}