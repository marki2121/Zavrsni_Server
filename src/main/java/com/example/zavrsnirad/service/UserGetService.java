package com.example.zavrsnirad.service;

import com.example.zavrsnirad.dto.request.UserDTO;
import com.example.zavrsnirad.entity.CostumeErrorException;
import com.example.zavrsnirad.entity.User;

import java.util.List;

public interface UserGetService {

    User getUserById(Long id) throws CostumeErrorException;
    List<User> findAllUsers();

    User getUserFromToken(String bearer) throws CostumeErrorException;

    List<UserDTO> getUsersByUsername(String username);
}
