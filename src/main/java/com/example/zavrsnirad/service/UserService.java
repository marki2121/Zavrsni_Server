package com.example.zavrsnirad.service;

import com.example.zavrsnirad.dto.request.SignupDTO;
import org.springframework.security.core.Authentication;

public interface UserService {
    String login(Authentication authentication);

    String signup(SignupDTO data);

    String deleteUserById(Long id);

    String disableUserById(Long id);

    String enableUserById(Long id);

    String promoteUserById(Long id);

    String demoteUserById(Long id);
}
