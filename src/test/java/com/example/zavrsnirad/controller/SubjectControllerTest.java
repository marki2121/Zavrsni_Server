package com.example.zavrsnirad.controller;

import com.example.zavrsnirad.dto.request.SubjectCreateDTO;
import com.example.zavrsnirad.service.SubjectService;
import com.example.zavrsnirad.util.SubjectDtoUtil;
import com.example.zavrsnirad.util.UserDtoUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.oidc.StandardClaimNames;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class SubjectControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SubjectService subjectService;

    @Test
    @DisplayName("Test createSubject endpoint with valid user admin")
    void createSubject() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        SubjectCreateDTO subjectCreateDTO = new SubjectCreateDTO(null, "Test", 1, 1, 1);
        when(subjectService.createSubject(anyString(), any(SubjectCreateDTO.class))).thenReturn("Ok");
        mockMvc.perform(MockMvcRequestBuilders.post("/api/subject/teacher/create")
                        .with(jwt().authorities(List.of(new SimpleGrantedAuthority("SCOPE_ADMIN")))
                                .jwt(jwt -> jwt.claim(StandardClaimNames.PREFERRED_USERNAME, "admin")))
                        .header("Authorization", "")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(subjectCreateDTO)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("Test createSubject endpoint with valid user teacher")
    void createSubjectTeacher() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        SubjectCreateDTO subjectCreateDTO = new SubjectCreateDTO(null, "Test", 1, 1, 1);
        when(subjectService.createSubject(anyString(), any(SubjectCreateDTO.class))).thenReturn("Ok");
        mockMvc.perform(MockMvcRequestBuilders.post("/api/subject/teacher/create")
                        .with(jwt().authorities(List.of(new SimpleGrantedAuthority("SCOPE_TEACHER")))
                                .jwt(jwt -> jwt.claim(StandardClaimNames.PREFERRED_USERNAME, "teacher")))
                        .header("Authorization", "")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(subjectCreateDTO)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("Test createSubject endpoint with valid user user")
    void createSubjectUser() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        SubjectCreateDTO subjectCreateDTO = new SubjectCreateDTO(null, "Test", 1, 1, 1);
        when(subjectService.createSubject(anyString(), any(SubjectCreateDTO.class))).thenReturn("Ok");
        mockMvc.perform(MockMvcRequestBuilders.post("/api/subject/teacher/create")
                        .with(jwt().authorities(List.of(new SimpleGrantedAuthority("SCOPE_USER")))
                                .jwt(jwt -> jwt.claim(StandardClaimNames.PREFERRED_USERNAME, "user")))
                        .header("Authorization", "")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(subjectCreateDTO)))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    @DisplayName("Test createSubject endpoint with no creds")
    void createSubjectUserNoCreds() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        SubjectCreateDTO subjectCreateDTO = new SubjectCreateDTO(null, "Test", 1, 1, 1);
        when(subjectService.createSubject(anyString(), any(SubjectCreateDTO.class))).thenReturn("Ok");
        mockMvc.perform(MockMvcRequestBuilders.post("/api/subject/teacher/create")
                        .header("Authorization", "")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(subjectCreateDTO)))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @Test
    @DisplayName("Test getSubjects endpoint with valid user admin")
    void getSubjects() throws Exception {
        when(subjectService.getSubjects(anyString())).thenReturn(List.of(SubjectDtoUtil.generate()));
        mockMvc.perform(MockMvcRequestBuilders.get("/api/subject/teacher")
                        .with(jwt().authorities(List.of(new SimpleGrantedAuthority("SCOPE_ADMIN")))
                                .jwt(jwt -> jwt.claim(StandardClaimNames.PREFERRED_USERNAME, "admin")))
                        .header("Authorization", ""))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("Test getSubjects endpoint with valid user teacher")
    void getSubjectsTeacher() throws Exception {
        when(subjectService.getSubjects(anyString())).thenReturn(List.of(SubjectDtoUtil.generate()));
        mockMvc.perform(MockMvcRequestBuilders.get("/api/subject/teacher")
                        .with(jwt().authorities(List.of(new SimpleGrantedAuthority("SCOPE_TEACHER")))
                                .jwt(jwt -> jwt.claim(StandardClaimNames.PREFERRED_USERNAME, "teacher")))
                        .header("Authorization", ""))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("Test getSubjects endpoint with valid user user")
    void getSubjectsUser() throws Exception {
        when(subjectService.getSubjects(anyString())).thenReturn(List.of(SubjectDtoUtil.generate()));
        mockMvc.perform(MockMvcRequestBuilders.get("/api/subject/teacher")
                        .with(jwt().authorities(List.of(new SimpleGrantedAuthority("SCOPE_USER")))
                                .jwt(jwt -> jwt.claim(StandardClaimNames.PREFERRED_USERNAME, "user")))
                        .header("Authorization", ""))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    @DisplayName("Test getSubjects endpoint with no user")
    void getSubjectsNoUser() throws Exception {
        when(subjectService.getSubjects(anyString())).thenReturn(List.of(SubjectDtoUtil.generate()));
        mockMvc.perform(MockMvcRequestBuilders.get("/api/subject/teacher")
                        .header("Authorization", ""))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @Test
    @DisplayName("Test getSubject endpoint with valid user admin")
    void getSubjectTeacherAdmin() throws Exception {
        when(subjectService.getSubject(anyString(), anyLong())).thenReturn(SubjectDtoUtil.generate());
        mockMvc.perform(MockMvcRequestBuilders.get("/api/subject/teacher/1")
                        .with(jwt().authorities(List.of(new SimpleGrantedAuthority("SCOPE_ADMIN")))
                                .jwt(jwt -> jwt.claim(StandardClaimNames.PREFERRED_USERNAME, "admin")))
                        .header("Authorization", ""))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("Test getSubject endpoint with valid user teacher")
    void getSubjectTeacherTeacher() throws Exception {
        when(subjectService.getSubject(anyString(), anyLong())).thenReturn(SubjectDtoUtil.generate());
        mockMvc.perform(MockMvcRequestBuilders.get("/api/subject/teacher/1")
                        .with(jwt().authorities(List.of(new SimpleGrantedAuthority("SCOPE_TEACHER")))
                                .jwt(jwt -> jwt.claim(StandardClaimNames.PREFERRED_USERNAME, "teacher")))
                        .header("Authorization", ""))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("Test getSubject endpoint with valid user user")
    void getSubjectTeacherUser() throws Exception {
        when(subjectService.getSubject(anyString(), anyLong())).thenReturn(SubjectDtoUtil.generate());
        mockMvc.perform(MockMvcRequestBuilders.get("/api/subject/teacher/1")
                        .with(jwt().authorities(List.of(new SimpleGrantedAuthority("SCOPE_USER")))
                                .jwt(jwt -> jwt.claim(StandardClaimNames.PREFERRED_USERNAME, "user")))
                        .header("Authorization", ""))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    @DisplayName("Test getSubject endpoint with no creds")
    void getSubjectTeacherNoCreds() throws Exception {
        when(subjectService.getSubject(anyString(), anyLong())).thenReturn(SubjectDtoUtil.generate());
        mockMvc.perform(MockMvcRequestBuilders.get("/api/subject/teacher/1")
                        .header("Authorization", ""))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @Test
    @DisplayName("Test deleteSubject endpoint with valid user admin")
    void deleteSubject() throws Exception {
        when(subjectService.deleteSubject(anyString(), anyLong())).thenReturn("Deleted");
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/subject/teacher/1")
                        .with(jwt().authorities(List.of(new SimpleGrantedAuthority("SCOPE_ADMIN")))
                                .jwt(jwt -> jwt.claim(StandardClaimNames.PREFERRED_USERNAME, "admin")))
                        .header("Authorization", ""))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("Test deleteSubject endpoint with valid user teacher")
    void deleteSubjectTeacher() throws Exception {
        when(subjectService.deleteSubject(anyString(), anyLong())).thenReturn("Deleted");
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/subject/teacher/1")
                        .with(jwt().authorities(List.of(new SimpleGrantedAuthority("SCOPE_TEACHER")))
                                .jwt(jwt -> jwt.claim(StandardClaimNames.PREFERRED_USERNAME, "teacher")))
                        .header("Authorization", ""))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("Test deleteSubject endpoint with valid user user")
    void deleteSubjectUser() throws Exception {
        when(subjectService.deleteSubject(anyString(), anyLong())).thenReturn("Deleted");
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/subject/teacher/1")
                        .with(jwt().authorities(List.of(new SimpleGrantedAuthority("SCOPE_USER")))
                                .jwt(jwt -> jwt.claim(StandardClaimNames.PREFERRED_USERNAME, "user")))
                        .header("Authorization", ""))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    @DisplayName("Test deleteSubject endpoint with no creds")
    void deleteSubjectNoCreds() throws Exception {
        when(subjectService.deleteSubject(anyString(), anyLong())).thenReturn("Deleted");
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/subject/teacher/1")
                        .header("Authorization", ""))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @Test
    @DisplayName("Test updateSubject endpoint with valid user admin")
    void updateSubject() throws Exception {
        SubjectCreateDTO subjectCreateDTO = new SubjectCreateDTO(null, null, null, null, null);
        ObjectMapper objectMapper = new ObjectMapper();
        when(subjectService.updateSubject(anyString(), anyLong(), any(SubjectCreateDTO.class))).thenReturn("Updated");
        mockMvc.perform(MockMvcRequestBuilders.put("/api/subject/teacher/1/update")
                        .with(jwt().authorities(List.of(new SimpleGrantedAuthority("SCOPE_ADMIN")))
                                .jwt(jwt -> jwt.claim(StandardClaimNames.PREFERRED_USERNAME, "admin")))
                        .header("Authorization", "")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(subjectCreateDTO)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("Test updateSubject endpoint with valid user teacher")
    void updateSubjectTeacher() throws Exception {
        SubjectCreateDTO subjectCreateDTO = new SubjectCreateDTO(null, null, null, null, null);
        ObjectMapper objectMapper = new ObjectMapper();
        when(subjectService.updateSubject(anyString(), anyLong(), any(SubjectCreateDTO.class))).thenReturn("Updated");
        mockMvc.perform(MockMvcRequestBuilders.put("/api/subject/teacher/1/update")
                        .with(jwt().authorities(List.of(new SimpleGrantedAuthority("SCOPE_TEACHER")))
                                .jwt(jwt -> jwt.claim(StandardClaimNames.PREFERRED_USERNAME, "teacher")))
                        .header("Authorization", "")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(subjectCreateDTO)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("Test updateSubject endpoint with valid user")
    void updateSubjectUser() throws Exception {
        SubjectCreateDTO subjectCreateDTO = new SubjectCreateDTO(null, null, null, null, null);
        ObjectMapper objectMapper = new ObjectMapper();
        when(subjectService.updateSubject(anyString(), anyLong(), any(SubjectCreateDTO.class))).thenReturn("Updated");
        mockMvc.perform(MockMvcRequestBuilders.put("/api/subject/teacher/1/update")
                        .with(jwt().authorities(List.of(new SimpleGrantedAuthority("SCOPE_USER")))
                                .jwt(jwt -> jwt.claim(StandardClaimNames.PREFERRED_USERNAME, "user")))
                        .header("Authorization", "")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(subjectCreateDTO)))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    @DisplayName("Test updateSubject endpoint with no creds")
    void updateSubjectNoCreds() throws Exception {
        SubjectCreateDTO subjectCreateDTO = new SubjectCreateDTO(null, null, null, null, null);
        ObjectMapper objectMapper = new ObjectMapper();
        when(subjectService.updateSubject(anyString(), anyLong(), any(SubjectCreateDTO.class))).thenReturn("Updated");
        mockMvc.perform(MockMvcRequestBuilders.put("/api/subject/teacher/1/update")
                        .header("Authorization", "")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(subjectCreateDTO)))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }


    @Test
    @DisplayName("Test getSubject endpoint with valid user admin")
    void addStudentToSubject() throws Exception {
        when(subjectService.addStudentToSubject(anyString(), anyLong(), anyLong())).thenReturn("Added");
        mockMvc.perform(MockMvcRequestBuilders.post("/api/subject/teacher/1/addStudent/1")
                        .with(jwt().authorities(List.of(new SimpleGrantedAuthority("SCOPE_ADMIN")))
                                .jwt(jwt -> jwt.claim(StandardClaimNames.PREFERRED_USERNAME, "admin")))
                        .header("Authorization", ""))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("Test getSubject endpoint with valid user teacher")
    void addStudentToSubjectTeacher() throws Exception {
        when(subjectService.addStudentToSubject(anyString(), anyLong(), anyLong())).thenReturn("Added");
        mockMvc.perform(MockMvcRequestBuilders.post("/api/subject/teacher/1/addStudent/1")
                        .with(jwt().authorities(List.of(new SimpleGrantedAuthority("SCOPE_TEACHER")))
                                .jwt(jwt -> jwt.claim(StandardClaimNames.PREFERRED_USERNAME, "teacher")))
                        .header("Authorization", ""))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("Test getSubject endpoint with valid user ")
    void addStudentToSubjectUser() throws Exception {
        when(subjectService.addStudentToSubject(anyString(), anyLong(), anyLong())).thenReturn("Added");
        mockMvc.perform(MockMvcRequestBuilders.post("/api/subject/teacher/1/addStudent/1")
                        .with(jwt().authorities(List.of(new SimpleGrantedAuthority("SCOPE_USER")))
                                .jwt(jwt -> jwt.claim(StandardClaimNames.PREFERRED_USERNAME, "user")))
                        .header("Authorization", ""))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    @DisplayName("Test getSubject endpoint with no creds")
    void addStudentToSubjectNoCreds() throws Exception {
        when(subjectService.addStudentToSubject(anyString(), anyLong(), anyLong())).thenReturn("Added");
        mockMvc.perform(MockMvcRequestBuilders.post("/api/subject/teacher/1/addStudent/1")
                        .header("Authorization", ""))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @Test
    @DisplayName("Test getSubject endpoint with valid user admin")
    void removeStudentFromSubject() throws Exception {
        when(subjectService.removeStudentFromSubject(anyString(), anyLong(), anyLong())).thenReturn("Removed");
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/subject/teacher/1/removeStudent/1")
                        .with(jwt().authorities(List.of(new SimpleGrantedAuthority("SCOPE_ADMIN")))
                                .jwt(jwt -> jwt.claim(StandardClaimNames.PREFERRED_USERNAME, "admin")))
                        .header("Authorization", ""))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("Test getSubject endpoint with valid user teacher")
    void removeStudentFromSubjectTeacher() throws Exception {
        when(subjectService.removeStudentFromSubject(anyString(), anyLong(), anyLong())).thenReturn("Removed");
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/subject/teacher/1/removeStudent/1")
                        .with(jwt().authorities(List.of(new SimpleGrantedAuthority("SCOPE_TEACHER")))
                                .jwt(jwt -> jwt.claim(StandardClaimNames.PREFERRED_USERNAME, "teacher")))
                        .header("Authorization", ""))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("Test getSubject endpoint with valid user ")
    void removeStudentFromSubjectUser() throws Exception {
        when(subjectService.removeStudentFromSubject(anyString(), anyLong(), anyLong())).thenReturn("Removed");
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/subject/teacher/1/removeStudent/1")
                        .with(jwt().authorities(List.of(new SimpleGrantedAuthority("SCOPE_USER")))
                                .jwt(jwt -> jwt.claim(StandardClaimNames.PREFERRED_USERNAME, "user")))
                        .header("Authorization", ""))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    @DisplayName("Test getSubject endpoint with no creds")
    void removeStudentFromSubjectNoCreds() throws Exception {
        when(subjectService.removeStudentFromSubject(anyString(), anyLong(), anyLong())).thenReturn("Removed");
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/subject/teacher/1/removeStudent/1")
                        .header("Authorization", ""))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @Test
    @DisplayName("Test getSubject endpoint with valid user admin")
    void getStudentsFromSubject() throws Exception {
        when(subjectService.getStudentsFromSubject(anyString(), anyLong())).thenReturn(List.of(UserDtoUtils.generate()));
        mockMvc.perform(MockMvcRequestBuilders.get("/api/subject/teacher/1/students")
                        .with(jwt().authorities(List.of(new SimpleGrantedAuthority("SCOPE_ADMIN")))
                                .jwt(jwt -> jwt.claim(StandardClaimNames.PREFERRED_USERNAME, "admin")))
                        .header("Authorization", ""))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("Test getSubject endpoint with valid user teacher")
    void getStudentsFromSubjectTeacher() throws Exception {
        when(subjectService.getStudentsFromSubject(anyString(), anyLong())).thenReturn(List.of(UserDtoUtils.generate()));
        mockMvc.perform(MockMvcRequestBuilders.get("/api/subject/teacher/1/students")
                        .with(jwt().authorities(List.of(new SimpleGrantedAuthority("SCOPE_TEACHER")))
                                .jwt(jwt -> jwt.claim(StandardClaimNames.PREFERRED_USERNAME, "teacher")))
                        .header("Authorization", ""))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("Test getSubject endpoint with valid user")
    void getStudentsFromSubjectUser() throws Exception {
        when(subjectService.getStudentsFromSubject(anyString(), anyLong())).thenReturn(List.of(UserDtoUtils.generate()));
        mockMvc.perform(MockMvcRequestBuilders.get("/api/subject/teacher/1/students")
                        .with(jwt().authorities(List.of(new SimpleGrantedAuthority("SCOPE_USER")))
                                .jwt(jwt -> jwt.claim(StandardClaimNames.PREFERRED_USERNAME, "user")))
                        .header("Authorization", ""))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    @DisplayName("Test getSubject endpoint with no creds")
    void getStudentsFromSubjectNoCreds() throws Exception {
        when(subjectService.getStudentsFromSubject(anyString(), anyLong())).thenReturn(List.of(UserDtoUtils.generate()));
        mockMvc.perform(MockMvcRequestBuilders.get("/api/subject/teacher/1/students")
                        .header("Authorization", ""))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @Test
    @DisplayName("Test getSubject endpoint with valid user admin")
    void getSubject() throws Exception {
        when(subjectService.getSubject(anyString(), anyLong())).thenReturn(SubjectDtoUtil.generate());
        mockMvc.perform(MockMvcRequestBuilders.get("/api/subject/1")
                        .with(jwt().authorities(List.of(new SimpleGrantedAuthority("SCOPE_ADMIN")))
                                .jwt(jwt -> jwt.claim(StandardClaimNames.PREFERRED_USERNAME, "admin")))
                        .header("Authorization", ""))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("Test getSubject endpoint with valid user teacher")
    void getSubjectTeacher() throws Exception {
        when(subjectService.getSubject(anyString(), anyLong())).thenReturn(SubjectDtoUtil.generate());
        mockMvc.perform(MockMvcRequestBuilders.get("/api/subject/1")
                        .with(jwt().authorities(List.of(new SimpleGrantedAuthority("SCOPE_TEACHER")))
                                .jwt(jwt -> jwt.claim(StandardClaimNames.PREFERRED_USERNAME, "teacher")))
                        .header("Authorization", ""))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("Test getSubject endpoint with valid user")
    void getSubjectUser() throws Exception {
        when(subjectService.getSubject(anyString(), anyLong())).thenReturn(SubjectDtoUtil.generate());
        mockMvc.perform(MockMvcRequestBuilders.get("/api/subject/1")
                        .with(jwt().authorities(List.of(new SimpleGrantedAuthority("SCOPE_USER")))
                                .jwt(jwt -> jwt.claim(StandardClaimNames.PREFERRED_USERNAME, "user")))
                        .header("Authorization", ""))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("Test getSubject endpoint with no creds")
    void getSubjectNoCreds() throws Exception {
        when(subjectService.getSubject(anyString(), anyLong())).thenReturn(SubjectDtoUtil.generate());
        mockMvc.perform(MockMvcRequestBuilders.get("/api/subject/1")
                        .header("Authorization", ""))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }

    @Test
    @DisplayName("Test getSubject endpoint with valid user admin")
    void getSubjectsStudent() throws Exception {
        when(subjectService.getSubjects(anyString())).thenReturn(List.of(SubjectDtoUtil.generate()));
        mockMvc.perform(MockMvcRequestBuilders.get("/api/subject/")
                        .with(jwt().authorities(List.of(new SimpleGrantedAuthority("SCOPE_ADMIN")))
                                .jwt(jwt -> jwt.claim(StandardClaimNames.PREFERRED_USERNAME, "admin")))
                        .header("Authorization", ""))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("Test getSubject endpoint with valid user teacher")
    void getSubjectsStudentTeacher() throws Exception {
        when(subjectService.getSubjects(anyString())).thenReturn(List.of(SubjectDtoUtil.generate()));
        mockMvc.perform(MockMvcRequestBuilders.get("/api/subject/")
                        .with(jwt().authorities(List.of(new SimpleGrantedAuthority("SCOPE_TEACHER")))
                                .jwt(jwt -> jwt.claim(StandardClaimNames.PREFERRED_USERNAME, "teacher")))
                        .header("Authorization", ""))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("Test getSubject endpoint with valid user")
    void getSubjectsStudentUser() throws Exception {
        when(subjectService.getSubjects(anyString())).thenReturn(List.of(SubjectDtoUtil.generate()));
        mockMvc.perform(MockMvcRequestBuilders.get("/api/subject/")
                        .with(jwt().authorities(List.of(new SimpleGrantedAuthority("SCOPE_USER")))
                                .jwt(jwt -> jwt.claim(StandardClaimNames.PREFERRED_USERNAME, "user")))
                        .header("Authorization", ""))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("Test getSubject endpoint with no creds")
    void getSubjectsStudentnoCreds() throws Exception {
        when(subjectService.getSubjects(anyString())).thenReturn(List.of(SubjectDtoUtil.generate()));
        mockMvc.perform(MockMvcRequestBuilders.get("/api/subject/")
                        .header("Authorization", ""))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized());
    }
}