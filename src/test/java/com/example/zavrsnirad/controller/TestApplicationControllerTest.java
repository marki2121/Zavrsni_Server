package com.example.zavrsnirad.controller;

import com.example.zavrsnirad.service.TestApplicationService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class TestApplicationControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TestApplicationService testApplicationService;

    @Test
    void applyForTest() {
    }

    @Test
    void getAllApplications() {
    }

    @Test
    void deleteApplication() {
    }
}