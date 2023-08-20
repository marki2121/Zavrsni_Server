package com.example.zavrsnirad.controller;

import com.example.zavrsnirad.service.TestApplicationService;
import com.example.zavrsnirad.util.TestApplicationResponseDtoUtil;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class TestApplicationControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TestApplicationService testApplicationService;

    @Test
    @DisplayName("Test applyForTest() method with user admin")
    void applyForTest() throws Exception {
        when(testApplicationService.applyForTest(null, 1L)).thenReturn("Ok");
        mockMvc.perform(post("/api/application/1")
                        .with(jwt().authorities(List.of(new SimpleGrantedAuthority("SCOPE_ADMIN")))
                                .jwt(jwt -> jwt.claim(StandardClaimNames.PREFERRED_USERNAME, "admin")))
                        .header("Authorization", ""))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Test applyForTest() method with user teacher")
    void applyForTestTeacher() throws Exception {
        when(testApplicationService.applyForTest(null, 1L)).thenReturn("Ok");
        mockMvc.perform(post("/api/application/1")
                        .with(jwt().authorities(List.of(new SimpleGrantedAuthority("SCOPE_TEACHER")))
                                .jwt(jwt -> jwt.claim(StandardClaimNames.PREFERRED_USERNAME, "teacher")))
                        .header("Authorization", ""))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Test applyForTest() method with user user")
    void applyForTestUser() throws Exception {
        when(testApplicationService.applyForTest(null, 1L)).thenReturn("Ok");
        mockMvc.perform(post("/api/application/1")
                        .with(jwt().authorities(List.of(new SimpleGrantedAuthority("SCOPE_USER")))
                                .jwt(jwt -> jwt.claim(StandardClaimNames.PREFERRED_USERNAME, "user")))
                        .header("Authorization", ""))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Test applyForTest() method with no credentials")
    void applyForTestnoCreds() throws Exception {
        when(testApplicationService.applyForTest(null, 1L)).thenReturn("Ok");
        mockMvc.perform(post("/api/application/1")
                        .header("Authorization", ""))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("Test applyForTest() method with user admin")
    void getAllApplications() throws Exception {
        when(testApplicationService.getAllApplications(any())).thenReturn(List.of(TestApplicationResponseDtoUtil.generate()));
        mockMvc.perform(get("/api/application/all")
                        .with(jwt().authorities(List.of(new SimpleGrantedAuthority("SCOPE_ADMIN")))
                                .jwt(jwt -> jwt.claim(StandardClaimNames.PREFERRED_USERNAME, "admin")))
                        .header("Authorization", ""))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Test applyForTest() method with user teacher")
    void getAllApplicationsTeacher() throws Exception {
        when(testApplicationService.getAllApplications(any())).thenReturn(List.of(TestApplicationResponseDtoUtil.generate()));
        mockMvc.perform(get("/api/application/all")
                        .with(jwt().authorities(List.of(new SimpleGrantedAuthority("SCOPE_TEACHER")))
                                .jwt(jwt -> jwt.claim(StandardClaimNames.PREFERRED_USERNAME, "teacher")))
                        .header("Authorization", ""))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Test applyForTest() method with user")
    void getAllApplicationsUser() throws Exception {
        when(testApplicationService.getAllApplications(any())).thenReturn(List.of(TestApplicationResponseDtoUtil.generate()));
        mockMvc.perform(get("/api/application/all")
                        .with(jwt().authorities(List.of(new SimpleGrantedAuthority("SCOPE_USER")))
                                .jwt(jwt -> jwt.claim(StandardClaimNames.PREFERRED_USERNAME, "user")))
                        .header("Authorization", ""))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Test applyForTest() method with no credentials")
    void getAllApplicationsNoCreds() throws Exception {
        when(testApplicationService.getAllApplications(any())).thenReturn(List.of(TestApplicationResponseDtoUtil.generate()));
        mockMvc.perform(get("/api/application/all")
                        .header("Authorization", ""))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("Test applyForTest() method with user admin")
    void deleteApplication() throws Exception {
        when(testApplicationService.deleteApplication(null, 1L)).thenReturn("Deleted");
        mockMvc.perform(delete("/api/application/1")
                        .with(jwt().authorities(List.of(new SimpleGrantedAuthority("SCOPE_ADMIN")))
                                .jwt(jwt -> jwt.claim(StandardClaimNames.PREFERRED_USERNAME, "admin")))
                        .header("Authorization", ""))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Test applyForTest() method with user teacher")
    void deleteApplicationTeacher() throws Exception {
        when(testApplicationService.deleteApplication(null, 1L)).thenReturn("Deleted");
        mockMvc.perform(delete("/api/application/1")
                        .with(jwt().authorities(List.of(new SimpleGrantedAuthority("SCOPE_TEACHER")))
                                .jwt(jwt -> jwt.claim(StandardClaimNames.PREFERRED_USERNAME, "teacher")))
                        .header("Authorization", ""))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Test applyForTest() method with user")
    void deleteApplicationUser() throws Exception {
        when(testApplicationService.deleteApplication(null, 1L)).thenReturn("Deleted");
        mockMvc.perform(delete("/api/application/1")
                        .with(jwt().authorities(List.of(new SimpleGrantedAuthority("SCOPE_USER")))
                                .jwt(jwt -> jwt.claim(StandardClaimNames.PREFERRED_USERNAME, "user")))
                        .header("Authorization", ""))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Test applyForTest() method with no credentials")
    void deleteApplicationNoCreds() throws Exception {
        when(testApplicationService.deleteApplication(null, 1L)).thenReturn("Deleted");
        mockMvc.perform(delete("/api/application/1")
                        .header("Authorization", ""))
                .andExpect(status().isUnauthorized());
    }
}