package com.example.zavrsnirad.service.impl;

import com.example.zavrsnirad.appenum.Role;
import com.example.zavrsnirad.dto.response.UserResponseDTO;
import com.example.zavrsnirad.entity.CostumeErrorException;
import com.example.zavrsnirad.entity.User;
import com.example.zavrsnirad.mapper.UserResponseDtoMapper;
import com.example.zavrsnirad.service.AdminService;
import com.example.zavrsnirad.service.UserGetService;
import com.example.zavrsnirad.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {
    private final UserService userService;
    private final UserGetService userGetService;
    private final UserResponseDtoMapper userResponseDtoMapper;

    public AdminServiceImpl(UserService userService, UserGetService userGetService, UserResponseDtoMapper userResponseDtoMapper) {
        this.userService = userService;
        this.userGetService = userGetService;
        this.userResponseDtoMapper = userResponseDtoMapper;
    }

    @Override
    public List<UserResponseDTO> getAllUsers(String authorization) {
        checkUser(authorization);

        return userResponseDtoMapper.map(userGetService.findAllUsers());
    }

    @Override
    public UserResponseDTO getUserById(String authorization, Long id) {
        checkUser(authorization);

        return userResponseDtoMapper.apply(userGetService.getUserById(id));
    }

    @Override
    public String deleteUserById(String authorization, Long id) {
        checkUser(authorization);

        return userService.deleteUserById(id);
    }

    @Override
    public String disableUserById(String authorization, Long id) {
        checkUser(authorization);

        return userService.disableUserById(id);
    }

    @Override
    public String enableUserById(String authorization, Long id) {
        checkUser(authorization);

        return userService.enableUserById(id);
    }

    @Override
    public String promoteUserById(String authorization, Long id) {
        checkUser(authorization);

        return userService.promoteUserById(id);
    }

    @Override
    public String demoteUserById(String authorization, Long id) {
        checkUser(authorization);

        return userService.demoteUserById(id);
    }

    public void checkUser(String auth) {
        User user = userGetService.getUserFromToken(auth);

        if(!user.getRole().equals(Role.ADMIN)) throw new CostumeErrorException("User is not admin", HttpStatus.BAD_REQUEST);
    }
}
