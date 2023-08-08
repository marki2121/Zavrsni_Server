package com.example.zavrsnirad.service;

import com.example.zavrsnirad.dto.request.UserDTO;
import com.example.zavrsnirad.entity.User;

import java.util.List;

public interface UserGetService {

    User getUserById(Long id);
    List<User> findAllUsers();

    User getUserFromToken(String bearer);

    List<UserDTO> getUsersByUsername(String username);
}
