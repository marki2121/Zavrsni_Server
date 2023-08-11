package com.example.zavrsnirad.service.impl;

import com.example.zavrsnirad.entity.CostumeErrorException;
import com.example.zavrsnirad.repository.TestRepository;
import com.example.zavrsnirad.service.TestGetService;
import com.example.zavrsnirad.service.UserGetService;
import com.example.zavrsnirad.util.TestUtil;
import com.example.zavrsnirad.util.UserUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.when;

@SpringBootTest
class TestGetServiceImplTest {

    @Autowired
    private TestGetService testGetService;
    @MockBean
    private TestRepository testRepository;
    @MockBean
    private UserGetService userGetService;

    @Test
    void getTestForUser() throws CostumeErrorException {
        //when
        when(userGetService.getUserFromToken("token")).thenReturn(UserUtil.generate());
        when(testRepository.findById(1L)).thenReturn(Optional.of(TestUtil.generate()));

        //then
        assertDoesNotThrow(() -> testGetService.getTestForUser("token", 1L));
    }
}