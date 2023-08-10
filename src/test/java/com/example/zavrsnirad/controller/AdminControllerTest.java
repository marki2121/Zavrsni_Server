package com.example.zavrsnirad.controller;

import com.example.zavrsnirad.service.AdminService;
import com.example.zavrsnirad.util.UserResponseDtoUtil;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class AdminControllerTest {
    @Autowired
    private MockMvc mockMvc;


    @MockBean
    private AdminService adminService;

    @Test
    @DisplayName("Test getAllusers endpoint with valid credentials admin")
    void getAllUsersValid() throws Exception {
        //when
        when(adminService.getAllUsers(null)).thenReturn(List.of());

        //then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/admin/user/all")
                        .with(jwt().authorities(List.of(new SimpleGrantedAuthority("SCOPE_ADMIN")))
                        .jwt(jwt -> jwt.claim(StandardClaimNames.PREFERRED_USERNAME, "admin")))
                        .header("Authorization", ""))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Test getAllusers endpoint with valid credentials teacher")
    void getAllUsersTeacher() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/admin/user/all")
                .with(jwt().authorities(List.of(new SimpleGrantedAuthority("SCOPE_TEACHER")))
                        .jwt(jwt -> jwt.claim(StandardClaimNames.PREFERRED_USERNAME, "teacher")))
                        .header("Authorization", ""))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("Test getAllusers endpoint with valid credentials user")
    void getAllUsersUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/admin/user/all")
                        .with(jwt().authorities(List.of(new SimpleGrantedAuthority("SCOPE_USER")))
                                .jwt(jwt -> jwt.claim(StandardClaimNames.PREFERRED_USERNAME, "user")))
                        .header("Authorization", ""))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("Test getAllusers endpoint with valid credentials user")
    void getAllUsersNoUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/admin/user/all")
                        .header("Authorization", ""))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("Test getUserById endpoint with valid credentials admin")
    void getUserById() throws Exception {
        //when
        when(adminService.getUserById(null, 1L)).thenReturn(UserResponseDtoUtil.generateUserResponseDto());

        //then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/admin/user/1")
                        .with(jwt().authorities(List.of(new SimpleGrantedAuthority("SCOPE_ADMIN")))
                                .jwt(jwt -> jwt.claim(StandardClaimNames.PREFERRED_USERNAME, "admin")))
                        .header("Authorization", ""))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Test getUserById endpoint with valid credentials teacher")
    void getUserByIdTeacher() throws Exception {
        //when
        when(adminService.getUserById(null, 1L)).thenReturn(UserResponseDtoUtil.generateUserResponseDto());

        //then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/admin/user/1")
                        .with(jwt().authorities(List.of(new SimpleGrantedAuthority("SCOPE_TEACHER")))
                                .jwt(jwt -> jwt.claim(StandardClaimNames.PREFERRED_USERNAME, "teacher")))
                        .header("Authorization", ""))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("Test getUserById endpoint with valid credentials admin")
    void getUserByIdUser() throws Exception {
        //when
        when(adminService.getUserById(null, 1L)).thenReturn(UserResponseDtoUtil.generateUserResponseDto());

        //then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/admin/user/1")
                        .with(jwt().authorities(List.of(new SimpleGrantedAuthority("SCOPE_USER")))
                                .jwt(jwt -> jwt.claim(StandardClaimNames.PREFERRED_USERNAME, "user")))
                        .header("Authorization", ""))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("Test getUserById endpoint no creadentials")
    void getUserByIdNoCreds() throws Exception {
        //when
        when(adminService.getUserById(null, 1L)).thenReturn(UserResponseDtoUtil.generateUserResponseDto());

        //then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/admin/user/1")
                        .header("Authorization", ""))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("Test deleteUserById endpoint with valid credentials admin")
    void deleteUserById() throws Exception {
        //given
        when(adminService.deleteUserById(null, null)).thenReturn("Deleted");

        //when
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/admin/user/1")
                        .with(jwt().authorities(List.of(new SimpleGrantedAuthority("SCOPE_ADMIN")))
                                .jwt(jwt -> jwt.claim(StandardClaimNames.PREFERRED_USERNAME, "admin")))
                        .header("Authorization", ""))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Test deleteUserById endpoint with valid credentials teacher")
    void deleteUserByIdTeacher() throws Exception {
        //given
        when(adminService.deleteUserById(null, null)).thenReturn("Deleted");

        //when
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/admin/user/1")
                        .with(jwt().authorities(List.of(new SimpleGrantedAuthority("SCOPE_TEACHER")))
                                .jwt(jwt -> jwt.claim(StandardClaimNames.PREFERRED_USERNAME, "teacher")))
                        .header("Authorization", ""))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("Test deleteUserById endpoint with valid credentials user")
    void deleteUserByIdUser() throws Exception {
        //given
        when(adminService.deleteUserById(null, null)).thenReturn("Deleted");

        //when
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/admin/user/1")
                        .with(jwt().authorities(List.of(new SimpleGrantedAuthority("SCOPE_USER")))
                                .jwt(jwt -> jwt.claim(StandardClaimNames.PREFERRED_USERNAME, "user")))
                        .header("Authorization", ""))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("Test deleteUserById endpoint with no creds")
    void deleteUserByIdNoCreads() throws Exception {
        //given
        when(adminService.deleteUserById(null, null)).thenReturn("Deleted");

        //when
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/admin/user/1")
                        .header("Authorization", ""))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("Test disableUserById endpoint with valid credentials admin")
    void disableUserById() throws Exception {
        //when
        when(adminService.disableUserById(null, null)).thenReturn("Deleted");

        //then
        mockMvc.perform(MockMvcRequestBuilders.put("/api/admin/user/1/disable")
                        .with(jwt().authorities(List.of(new SimpleGrantedAuthority("SCOPE_ADMIN")))
                                .jwt(jwt -> jwt.claim(StandardClaimNames.PREFERRED_USERNAME, "admin")))
                        .header("Authorization", ""))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Test disableUserById endpoint with valid credentials teacher")
    void disableUserByIdTeacher() throws Exception {
        //when
        when(adminService.disableUserById(null, null)).thenReturn("Deleted");

        //then
        mockMvc.perform(MockMvcRequestBuilders.put("/api/admin/user/1/disable")
                        .with(jwt().authorities(List.of(new SimpleGrantedAuthority("SCOPE_TEACHER")))
                                .jwt(jwt -> jwt.claim(StandardClaimNames.PREFERRED_USERNAME, "teacher")))
                        .header("Authorization", ""))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("Test disableUserById endpoint with valid credentials user")
    void disableUserByIdUser() throws Exception {
        //when
        when(adminService.disableUserById(null, null)).thenReturn("Deleted");

        //then
        mockMvc.perform(MockMvcRequestBuilders.put("/api/admin/user/1/disable")
                        .with(jwt().authorities(List.of(new SimpleGrantedAuthority("SCOPE_USER")))
                                .jwt(jwt -> jwt.claim(StandardClaimNames.PREFERRED_USERNAME, "user")))
                        .header("Authorization", ""))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("Test disableUserById endpoint with no creds")
    void disableUserByIdNoCreads() throws Exception {
        //when
        when(adminService.disableUserById(null, null)).thenReturn("Deleted");

        //then
        mockMvc.perform(MockMvcRequestBuilders.put("/api/admin/user/1/disable")
                        .header("Authorization", ""))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("Test enableUserById endpoint with valid credentials admin")
    void enableUserById() throws Exception {
        //when
        when(adminService.enableUserById(null, null)).thenReturn("Deleted");

        //then
        mockMvc.perform(MockMvcRequestBuilders.put("/api/admin/user/1/enable")
                        .with(jwt().authorities(List.of(new SimpleGrantedAuthority("SCOPE_ADMIN")))
                                .jwt(jwt -> jwt.claim(StandardClaimNames.PREFERRED_USERNAME, "admin")))
                        .header("Authorization", ""))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Test enableUserById endpoint with valid credentials teacher")
    void enableUserByIdTeacher() throws Exception {
        //when
        when(adminService.enableUserById(null, null)).thenReturn("Deleted");

        //then
        mockMvc.perform(MockMvcRequestBuilders.put("/api/admin/user/1/enable")
                        .with(jwt().authorities(List.of(new SimpleGrantedAuthority("SCOPE_TEACHER")))
                                .jwt(jwt -> jwt.claim(StandardClaimNames.PREFERRED_USERNAME, "teacher")))
                        .header("Authorization", ""))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("Test enableUserById endpoint with valid credentials user")
    void enableUserByIdUser() throws Exception {
        //when
        when(adminService.enableUserById(null, null)).thenReturn("Deleted");

        //then
        mockMvc.perform(MockMvcRequestBuilders.put("/api/admin/user/1/enable")
                        .with(jwt().authorities(List.of(new SimpleGrantedAuthority("SCOPE_USER")))
                                .jwt(jwt -> jwt.claim(StandardClaimNames.PREFERRED_USERNAME, "user")))
                        .header("Authorization", ""))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("Test enableUserById endpoint with no creds")
    void enableUserByIdNoCreads() throws Exception {
        //when
        when(adminService.enableUserById(null, null)).thenReturn("Deleted");

        //then
        mockMvc.perform(MockMvcRequestBuilders.put("/api/admin/user/1/enable")
                        .header("Authorization", ""))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("Test promoteUserById endpoint with valid credentials admin")
    void promoteUserById() throws Exception {
        //when
        when(adminService.promoteUserById(null, null)).thenReturn("Deleted");

        //then
        mockMvc.perform(MockMvcRequestBuilders.put("/api/admin/user/1/promote")
                        .with(jwt().authorities(List.of(new SimpleGrantedAuthority("SCOPE_ADMIN")))
                                .jwt(jwt -> jwt.claim(StandardClaimNames.PREFERRED_USERNAME, "admin")))
                        .header("Authorization", ""))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Test promoteUserById endpoint with valid credentials teacher")
    void promoteUserByIdTeacher() throws Exception {
        //when
        when(adminService.promoteUserById(null, null)).thenReturn("Deleted");

        //then
        mockMvc.perform(MockMvcRequestBuilders.put("/api/admin/user/1/promote")
                        .with(jwt().authorities(List.of(new SimpleGrantedAuthority("SCOPE_TEACHER")))
                                .jwt(jwt -> jwt.claim(StandardClaimNames.PREFERRED_USERNAME, "teacher")))
                        .header("Authorization", ""))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("Test promoteUserById endpoint with valid credentials user")
    void promoteUserByIdUser() throws Exception {
        //when
        when(adminService.promoteUserById(null, null)).thenReturn("Deleted");

        //then
        mockMvc.perform(MockMvcRequestBuilders.put("/api/admin/user/1/promote")
                        .with(jwt().authorities(List.of(new SimpleGrantedAuthority("SCOPE_USER")))
                                .jwt(jwt -> jwt.claim(StandardClaimNames.PREFERRED_USERNAME, "user")))
                        .header("Authorization", ""))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("Test promoteUserById endpoint with no creds")
    void promoteUserByIdNoCreds() throws Exception {
        //when
        when(adminService.promoteUserById(null, null)).thenReturn("Deleted");

        //then
        mockMvc.perform(MockMvcRequestBuilders.put("/api/admin/user/1/promote")
                        .header("Authorization", ""))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("Test demoteUserById endpoint with valid credentials admin")
    void demoteUserById() throws Exception {
        //when
        when(adminService.demoteUserById(null, null)).thenReturn("Deleted");

        //then
        mockMvc.perform(MockMvcRequestBuilders.put("/api/admin/user/1/demote")
                        .with(jwt().authorities(List.of(new SimpleGrantedAuthority("SCOPE_ADMIN")))
                                .jwt(jwt -> jwt.claim(StandardClaimNames.PREFERRED_USERNAME, "admin")))
                        .header("Authorization", ""))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Test demoteUserById endpoint with valid credentials teacher")
    void demoteUserByIdTeacher() throws Exception {
        //when
        when(adminService.demoteUserById(null, null)).thenReturn("Deleted");

        //then
        mockMvc.perform(MockMvcRequestBuilders.put("/api/admin/user/1/demote")
                        .with(jwt().authorities(List.of(new SimpleGrantedAuthority("SCOPE_TEACHER")))
                                .jwt(jwt -> jwt.claim(StandardClaimNames.PREFERRED_USERNAME, "teacher")))
                        .header("Authorization", ""))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("Test demoteUserById endpoint with valid credentials user")
    void demoteUserByIdUser() throws Exception {
        //when
        when(adminService.demoteUserById(null, null)).thenReturn("Deleted");

        //then
        mockMvc.perform(MockMvcRequestBuilders.put("/api/admin/user/1/demote")
                        .with(jwt().authorities(List.of(new SimpleGrantedAuthority("SCOPE_USER")))
                                .jwt(jwt -> jwt.claim(StandardClaimNames.PREFERRED_USERNAME, "user")))
                        .header("Authorization", ""))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("Test demoteUserById endpoint with no creds")
    void demoteUserByIdNoCreds() throws Exception {
        //when
        when(adminService.demoteUserById(null, null)).thenReturn("Deleted");

        //then
        mockMvc.perform(MockMvcRequestBuilders.put("/api/admin/user/1/demote")
                        .header("Authorization", ""))
                .andExpect(status().isUnauthorized());
    }
}