package com.example.zavrsnirad.controller;

import com.example.zavrsnirad.dto.SignupDTO;
import com.example.zavrsnirad.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = AuthController.class)
@RunWith(SpringRunner.class)
class AuthControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    @WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
    @DisplayName("Test login endpoint with valid credentials")
    public void loginTestValid() throws Exception {
        // when
        when(userService.login(Mockito.any(Authentication.class))).thenReturn(ResponseEntity.ok("OK"));

        // then
        mockMvc.perform(get("/api/auth/login"))
                .andExpect(status().isOk());

        // verify
        verify(userService).login(Mockito.any(Authentication.class));
    }

    @Test
    @WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
    @DisplayName("Test login endpoint with invalid credentials")
    public void loginTestInvalid() throws Exception {
        // when
        when(userService.login(Mockito.any(Authentication.class))).thenReturn(new ResponseEntity<>(HttpStatus.UNAUTHORIZED));

        // then
        mockMvc.perform(get("/api/auth/login"))
                .andExpect(status().isUnauthorized());

        // verify
        verify(userService).login(Mockito.any(Authentication.class));
    }

    @Test
    @DisplayName("Test signup endpoint with no credentials")
    public void loginTestNoCredentials() throws Exception {
        mockMvc.perform(get("/api/auth/login"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = "admin", password = "admin", roles = "ADMIN") // TODO: fix this test wont run without user
    @DisplayName("Test signup endpoint with valid request")
    void signupTestValidRequest() throws Exception {
        //given
        SignupDTO signupDTO = new SignupDTO("admin", "iloveyou", "iloveyou");
        ObjectMapper objectMapper = new ObjectMapper();

        //when
        when(userService.signup(Mockito.any(SignupDTO.class))).thenReturn(ResponseEntity.ok("OK"));

        //then
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/auth/signup")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(signupDTO)))
                    .andExpect(status().isOk());
    }
}

