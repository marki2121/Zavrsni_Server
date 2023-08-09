package com.example.zavrsnirad.service;

import com.example.zavrsnirad.dto.response.UserResponseDTO;
import com.example.zavrsnirad.entity.CostumeErrorException;

import java.util.List;

public interface AdminService {
    List<UserResponseDTO> getAllUsers(String authorization) throws CostumeErrorException;

    UserResponseDTO getUserById(String authorization, Long id) throws CostumeErrorException;

    String deleteUserById(String authorization, Long id) throws CostumeErrorException;

    String disableUserById(String authorization, Long id) throws CostumeErrorException;

    String enableUserById(String authorization, Long id) throws CostumeErrorException;

    String promoteUserById(String authorization, Long id) throws CostumeErrorException;

    String demoteUserById(String authorization, Long id) throws CostumeErrorException;
}
