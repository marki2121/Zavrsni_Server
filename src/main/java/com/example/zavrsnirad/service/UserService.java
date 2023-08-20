package com.example.zavrsnirad.service;

import com.example.zavrsnirad.config.CostumeErrorException;
import com.example.zavrsnirad.dto.request.SignupDTO;
import org.springframework.security.core.Authentication;

public interface UserService {
    String login(Authentication authentication);

    String signup(SignupDTO data) throws CostumeErrorException;

    String deleteUserById(Long id) throws CostumeErrorException;

    String disableUserById(Long id) throws CostumeErrorException;

    String enableUserById(Long id) throws CostumeErrorException;

    String promoteUserById(Long id) throws CostumeErrorException;

    String demoteUserById(Long id) throws CostumeErrorException;
}
