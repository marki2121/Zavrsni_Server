package com.example.zavrsnirad.service;

import com.example.zavrsnirad.dto.response.UserResponseDTO;

import java.util.List;

public interface AdminService {
    List<UserResponseDTO> getAllUsers(String authorization);

    UserResponseDTO getUserById(String authorization, Long id);

    String deleteUserById(String authorization, Long id);

    String disableUserById(String authorization, Long id);

    String enableUserById(String authorization, Long id);

    String promoteUserById(String authorization, Long id);

    String demoteUserById(String authorization, Long id);
}
