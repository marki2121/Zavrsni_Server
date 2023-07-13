package com.example.zavrsnirad.controller;

import com.example.zavrsnirad.dto.request.TestCreateDTO;
import com.example.zavrsnirad.service.TestService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
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
        //given
        TestCreateDTO testCreateDTO = new TestCreateDTO(null,  null);
        ObjectMapper objectMapper = new ObjectMapper();


        //when
        when(testService.createTest(null, 1L, testCreateDTO)).thenReturn(ResponseEntity.ok("OK"));

        //then
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
        //given
        TestCreateDTO testCreateDTO = new TestCreateDTO(null,  null);
        ObjectMapper objectMapper = new ObjectMapper();


        //when
        when(testService.createTest(null, 1L, testCreateDTO)).thenReturn(ResponseEntity.ok("OK"));

        //then
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
        //given
        TestCreateDTO testCreateDTO = new TestCreateDTO(null,  null);
        ObjectMapper objectMapper = new ObjectMapper();


        //when
        when(testService.createTest(null, 1L, testCreateDTO)).thenReturn(ResponseEntity.ok("OK"));

        //then
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
        //given
        TestCreateDTO testCreateDTO = new TestCreateDTO(null,  null);
        ObjectMapper objectMapper = new ObjectMapper();


        //when
        when(testService.createTest(null, 1L, testCreateDTO)).thenReturn(ResponseEntity.ok("OK"));

        //then
        mockMvc.perform(post("/api/test/teacher/1/create")
                        .header("Authorization", "")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(testCreateDTO)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("Test createTest with user admin")
    void getTests() throws Exception {
        //when
        when(testService.getTestsForSubject(null, 1L)).thenReturn(ResponseEntity.ok("OK"));

        //then
        mockMvc.perform(get("/api/test/teacher/1")
                        .with(jwt().authorities(List.of(new SimpleGrantedAuthority("SCOPE_ADMIN")))
                                .jwt(jwt -> jwt.claim(StandardClaimNames.PREFERRED_USERNAME, "admin")))
                        .header("Authorization", ""))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Test createTest with user teacher")
    void getTestsTeacher() throws Exception {
        //when
        when(testService.getTestsForSubject(null, 1L)).thenReturn(ResponseEntity.ok("OK"));

        //then
        mockMvc.perform(get("/api/test/teacher/1")
                        .with(jwt().authorities(List.of(new SimpleGrantedAuthority("SCOPE_TEACHER")))
                                .jwt(jwt -> jwt.claim(StandardClaimNames.PREFERRED_USERNAME, "teacher")))
                        .header("Authorization", ""))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Test createTest with user")
    void getTestsUser() throws Exception {
        //when
        when(testService.getTestsForSubject(null, 1L)).thenReturn(ResponseEntity.ok("OK"));

        //then
        mockMvc.perform(get("/api/test/teacher/1")
                        .with(jwt().authorities(List.of(new SimpleGrantedAuthority("SCOPE_USER")))
                                .jwt(jwt -> jwt.claim(StandardClaimNames.PREFERRED_USERNAME, "user")))
                        .header("Authorization", ""))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("Test createTest with no credentials")
    void getTestsNoCreds() throws Exception {
        //when
        when(testService.getTestsForSubject(null, 1L)).thenReturn(ResponseEntity.ok("OK"));

        //then
        mockMvc.perform(get("/api/test/teacher/1")
                        .header("Authorization", ""))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("Test createTest with user admin")
    void deleteTest() throws Exception {
        //when
        when(testService.deleteTest(null, 1L, 1L)).thenReturn(ResponseEntity.ok("OK"));

        //then
        mockMvc.perform(delete("/api/test/teacher/1/1")
                        .with(jwt().authorities(List.of(new SimpleGrantedAuthority("SCOPE_ADMIN")))
                                .jwt(jwt -> jwt.claim(StandardClaimNames.PREFERRED_USERNAME, "admin")))
                        .header("Authorization", ""))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Test createTest with user teacher")
    void deleteTestTeacher() throws Exception {
        //when
        when(testService.deleteTest(null, 1L, 1L)).thenReturn(ResponseEntity.ok("OK"));

        //then
        mockMvc.perform(delete("/api/test/teacher/1/1")
                        .with(jwt().authorities(List.of(new SimpleGrantedAuthority("SCOPE_TEACHER")))
                                .jwt(jwt -> jwt.claim(StandardClaimNames.PREFERRED_USERNAME, "teacher")))
                        .header("Authorization", ""))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Test createTest with user")
    void deleteTestUser() throws Exception {
        //when
        when(testService.deleteTest(null, 1L, 1L)).thenReturn(ResponseEntity.ok("OK"));

        //then
        mockMvc.perform(delete("/api/test/teacher/1/1")
                        .with(jwt().authorities(List.of(new SimpleGrantedAuthority("SCOPE_USER")))
                                .jwt(jwt -> jwt.claim(StandardClaimNames.PREFERRED_USERNAME, "user")))
                        .header("Authorization", ""))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("Test createTest with no credentials")
    void deleteTestNoCreds() throws Exception {
        //when
        when(testService.deleteTest(null, 1L, 1L)).thenReturn(ResponseEntity.ok("OK"));

        //then
        mockMvc.perform(delete("/api/test/teacher/1/1")
                        .header("Authorization", ""))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("Test updateTest with user admin")
    void updateTest() throws Exception {
        //given
        TestCreateDTO testCreateDTO = new TestCreateDTO(null,  null);
        ObjectMapper objectMapper = new ObjectMapper();

        //when
        when(testService.updateTest(null, 1L, 1L, testCreateDTO)).thenReturn(ResponseEntity.ok("OK"));

        //then
        mockMvc.perform(put("/api/test/teacher/1/1/update")
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
        //given
        TestCreateDTO testCreateDTO = new TestCreateDTO(null,  null);
        ObjectMapper objectMapper = new ObjectMapper();

        //when
        when(testService.updateTest(null, 1L, 1L, testCreateDTO)).thenReturn(ResponseEntity.ok("OK"));

        //then
        mockMvc.perform(put("/api/test/teacher/1/1/update")
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
        //given
        TestCreateDTO testCreateDTO = new TestCreateDTO(null,  null);
        ObjectMapper objectMapper = new ObjectMapper();

        //when
        when(testService.updateTest(null, 1L, 1L, testCreateDTO)).thenReturn(ResponseEntity.ok("OK"));

        //then
        mockMvc.perform(put("/api/test/teacher/1/1/update")
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
        //given
        TestCreateDTO testCreateDTO = new TestCreateDTO(null,  null);
        ObjectMapper objectMapper = new ObjectMapper();

        //when
        when(testService.updateTest(null, 1L, 1L, testCreateDTO)).thenReturn(ResponseEntity.ok("OK"));

        //then
        mockMvc.perform(put("/api/test/teacher/1/1/update")
                        .header("Authorization", "")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(testCreateDTO)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("Test getAllTestsApplications with user admin")
    void getAllTestsApplications() throws Exception {
        //when
        when(testService.getAllTestsApplications(null, 1L, 1L)).thenReturn(ResponseEntity.ok("OK"));

        //then
        mockMvc.perform(get("/api/test/teacher/1/1/all")
                        .with(jwt().authorities(List.of(new SimpleGrantedAuthority("SCOPE_ADMIN")))
                                .jwt(jwt -> jwt.claim(StandardClaimNames.PREFERRED_USERNAME, "admin")))
                        .header("Authorization", ""))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Test getAllTestsApplications with user teacher")
    void getAllTestsApplicationsTeacher() throws Exception {
        //when
        when(testService.getAllTestsApplications(null, 1L, 1L)).thenReturn(ResponseEntity.ok("OK"));

        //then
        mockMvc.perform(get("/api/test/teacher/1/1/all")
                        .with(jwt().authorities(List.of(new SimpleGrantedAuthority("SCOPE_TEACHER")))
                                .jwt(jwt -> jwt.claim(StandardClaimNames.PREFERRED_USERNAME, "teacher")))
                        .header("Authorization", ""))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Test getAllTestsApplications with user")
    void getAllTestsApplicationsUser() throws Exception {
        //when
        when(testService.getAllTestsApplications(null, 1L, 1L)).thenReturn(ResponseEntity.ok("OK"));

        //then
        mockMvc.perform(get("/api/test/teacher/1/1/all")
                        .with(jwt().authorities(List.of(new SimpleGrantedAuthority("SCOPE_USER")))
                                .jwt(jwt -> jwt.claim(StandardClaimNames.PREFERRED_USERNAME, "user")))
                        .header("Authorization", ""))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("Test getAllTestsApplications with no credentials")
    void getAllTestsApplicationsNoCreds() throws Exception {
        //when
        when(testService.getAllTestsApplications(null, 1L, 1L)).thenReturn(ResponseEntity.ok("OK"));

        //then
        mockMvc.perform(get("/api/test/teacher/1/1/all")
                        .header("Authorization", ""))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("Test gradeTest with user admin")
    void gradeTest() throws Exception {
        //when
        when(testService.gradeTest(null, 1L, 1)).thenReturn(ResponseEntity.ok("OK"));

        //then
        mockMvc.perform(post("/api/test/teacher/1/grade/1")
                        .with(jwt().authorities(List.of(new SimpleGrantedAuthority("SCOPE_ADMIN")))
                                .jwt(jwt -> jwt.claim(StandardClaimNames.PREFERRED_USERNAME, "admin")))
                        .header("Authorization", ""))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Test gradeTest with user teacher")
    void gradeTestTeacher() throws Exception {
        //when
        when(testService.gradeTest(null, 1L, 1)).thenReturn(ResponseEntity.ok("OK"));

        //then
        mockMvc.perform(post("/api/test/teacher/1/grade/1")
                        .with(jwt().authorities(List.of(new SimpleGrantedAuthority("SCOPE_TEACHER")))
                                .jwt(jwt -> jwt.claim(StandardClaimNames.PREFERRED_USERNAME, "teacher")))
                        .header("Authorization", ""))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Test gradeTest with user")
    void gradeTestUser() throws Exception {
        //when
        when(testService.gradeTest(null, 1L, 1)).thenReturn(ResponseEntity.ok("OK"));

        //then
        mockMvc.perform(post("/api/test/teacher/1/grade/1")
                        .with(jwt().authorities(List.of(new SimpleGrantedAuthority("SCOPE_USER")))
                                .jwt(jwt -> jwt.claim(StandardClaimNames.PREFERRED_USERNAME, "user")))
                        .header("Authorization", ""))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("Test gradeTest with no credentials")
    void gradeTestNoCreds() throws Exception {
        //when
        when(testService.gradeTest(null, 1L, 1)).thenReturn(ResponseEntity.ok("OK"));

        //then
        mockMvc.perform(post("/api/test/teacher/1/grade/1")
                        .header("Authorization", ""))
                .andExpect(status().isUnauthorized());
    }
}