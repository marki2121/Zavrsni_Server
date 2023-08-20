package com.example.zavrsnirad.service;

import com.example.zavrsnirad.config.CostumeErrorException;
import com.example.zavrsnirad.dto.response.UserResponseDTO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface AdminService {
    List<UserResponseDTO> getAllUsers(String authorization) throws CostumeErrorException;

    UserResponseDTO getUserById(String authorization, Long id) throws CostumeErrorException;

    @Transactional
    String deleteUserById(String authorization, Long id) throws CostumeErrorException;

    String disableUserById(String authorization, Long id) throws CostumeErrorException;

    String enableUserById(String authorization, Long id) throws CostumeErrorException;

    String promoteUserById(String authorization, Long id) throws CostumeErrorException;

    String demoteUserById(String authorization, Long id) throws CostumeErrorException;
}
