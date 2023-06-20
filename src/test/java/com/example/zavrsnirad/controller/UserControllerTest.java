package com.example.zavrsnirad.controller;

import com.example.zavrsnirad.dto.UpdatePasswordDTO;
import com.example.zavrsnirad.dto.UpdateProfileDTO;
import com.example.zavrsnirad.service.UserProfileService;
import com.example.zavrsnirad.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private UserProfileService userProfileService;

    @Test
    @DisplayName("Test update password success")
    public void updatePasswordTest() throws Exception {
        //given
        UpdatePasswordDTO updatePasswordDTO = new UpdatePasswordDTO("iloveyou", "iloveyou2", "iloveyou2");
        ObjectMapper objectMapper = new ObjectMapper();

        //when
        when(userService.updatePassword(null, updatePasswordDTO)).thenReturn(ResponseEntity.ok("OK"));

        //then
        mockMvc.perform(MockMvcRequestBuilders.post("/api/user/password/update")
                        .with(jwt().authorities(List.of(new SimpleGrantedAuthority("SCOPE_ADMIN")))
                                .jwt(jwt -> jwt.claim(StandardClaimNames.PREFERRED_USERNAME, "admin")))
                        .header("Authorization", "")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatePasswordDTO)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Test update password fail no Authorization header")
    public void updatePasswordNoAuthorizationToken() throws Exception {
        UpdatePasswordDTO updatePasswordDTO = new UpdatePasswordDTO("iloveyou", "iloveyou2", "iloveyou2");
        ObjectMapper objectMapper = new ObjectMapper();

        //when
        when(userService.updatePassword("token", updatePasswordDTO)).thenReturn(ResponseEntity.ok("OK"));

        //then
        mockMvc.perform(MockMvcRequestBuilders.post("/api/user/password/update")
                        .with(jwt().authorities(List.of(new SimpleGrantedAuthority("SCOPE_ADMIN")))
                                .jwt(jwt -> jwt.claim(StandardClaimNames.PREFERRED_USERNAME, "admin")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatePasswordDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(status().reason("Required header 'Authorization' is not present."));
    }

    @Test
    @DisplayName("Test update password fail no Update Password DTO")
    public void updatePasswordNoUpdatePasswordDTO() throws Exception {
        //given
        UpdatePasswordDTO updatePasswordDTO = new UpdatePasswordDTO("iloveyou", "iloveyou2", "iloveyou2");
        ObjectMapper objectMapper = new ObjectMapper();

        //when
        when(userService.updatePassword(null, updatePasswordDTO)).thenReturn(ResponseEntity.ok("OK"));

        //then
        mockMvc.perform(MockMvcRequestBuilders.post("/api/user/password/update")
                        .with(jwt().authorities(List.of(new SimpleGrantedAuthority("SCOPE_ADMIN")))
                                .jwt(jwt -> jwt.claim(StandardClaimNames.PREFERRED_USERNAME, "admin")))
                        .header("Authorization", ""))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Test get self success")
    public void testGetSelf() throws Exception {
        //when
        when(userProfileService.getSelf(null)).thenReturn(ResponseEntity.ok().build());

        //then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/user/profile")
                        .with(jwt().authorities(List.of(new SimpleGrantedAuthority("SCOPE_ADMIN")))
                                .jwt(jwt -> jwt.claim(StandardClaimNames.PREFERRED_USERNAME, "admin")))
                .header("Authorization", ""))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Test get self fail no Authorization header")
    public void testGetSelfAndFailNoToken() throws Exception {
        //when
        when(userProfileService.getSelf("token")).thenReturn(ResponseEntity.ok().build());

        //then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/user/profile")
                        .with(jwt().authorities(List.of(new SimpleGrantedAuthority("SCOPE_ADMIN")))
                            .jwt(jwt -> jwt.claim(StandardClaimNames.PREFERRED_USERNAME, "admin"))))
                .andExpect(status().isBadRequest())
                .andExpect(status().reason("Required header 'Authorization' is not present."));

    }

    @Test
    @DisplayName("Test updateProfile success")
    public void testUpdateProfile() throws Exception {
        //given
        UpdateProfileDTO data = new UpdateProfileDTO("test", "test", "test", "test", "test", "test", "test", "test", "test");
        ObjectMapper objectMapper = new ObjectMapper();

        //when
        when(userProfileService.updateSelfProfile(null, data)).thenReturn(ResponseEntity.ok().build());

        //then
        mockMvc.perform(MockMvcRequestBuilders.put("/api/user/profile/update")
                        .with(jwt().authorities(List.of(new SimpleGrantedAuthority("SCOPE_ADMIN")))
                                .jwt(jwt -> jwt.claim(StandardClaimNames.PREFERRED_USERNAME, "admin")))
                .header("Authorization", "")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(data)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Test updateProfile fail no token")
    public void testUpdateProfileNoToken() throws Exception {
        //given
        UpdateProfileDTO data = new UpdateProfileDTO("test", "test", "test", "test", "test", "test", "test", "test", "test");
        ObjectMapper objectMapper = new ObjectMapper();

        //when
        when(userProfileService.updateSelfProfile("token", data)).thenReturn(ResponseEntity.ok().build());

        //then
        mockMvc.perform(MockMvcRequestBuilders.put("/api/user/profile/update")
                        .with(jwt().authorities(List.of(new SimpleGrantedAuthority("SCOPE_ADMIN")))
                                .jwt(jwt -> jwt.claim(StandardClaimNames.PREFERRED_USERNAME, "admin")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(data)))
                .andExpect(status().isBadRequest())
                .andExpect(status().reason("Required header 'Authorization' is not present."));
    }

    @Test
    @DisplayName("Test updateProfile fail no data")
    public void testUpdateProfileNoData() throws Exception {
        //given
        UpdateProfileDTO data = new UpdateProfileDTO("test", "test", "test", "test", "test", "test", "test", "test", "test");
        ObjectMapper objectMapper = new ObjectMapper();

        //when
        when(userProfileService.updateSelfProfile(null, data)).thenReturn(ResponseEntity.ok().build());

        //then
        mockMvc.perform(MockMvcRequestBuilders.put("/api/user/profile/update")
                        .with(jwt().authorities(List.of(new SimpleGrantedAuthority("SCOPE_ADMIN")))
                                .jwt(jwt -> jwt.claim(StandardClaimNames.PREFERRED_USERNAME, "admin")))
                        .header("Authorization", ""))
                .andExpect(status().isBadRequest());
    }
}

