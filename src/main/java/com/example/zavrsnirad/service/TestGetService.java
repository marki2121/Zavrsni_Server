package com.example.zavrsnirad.service;

import com.example.zavrsnirad.config.CostumeErrorException;
import com.example.zavrsnirad.entity.Test;

public interface TestGetService {
    Test getTestForUser(String authorization, Long testId) throws CostumeErrorException;
}
