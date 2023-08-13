package com.example.zavrsnirad.service.impl;

import com.example.zavrsnirad.appenum.Role;
import com.example.zavrsnirad.dto.response.UserResponseDTO;
import com.example.zavrsnirad.entity.CostumeErrorException;
import com.example.zavrsnirad.mapper.UserResponseDtoMapper;
import com.example.zavrsnirad.service.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {
    private final UserService userService;
    private final UserGetService userGetService;
    private final SubjectService subjectService;
    private final TestApplicationService testApplicationService;
    private final UserProfileService userProfileService;
    private final UserResponseDtoMapper userResponseDtoMapper;

    public AdminServiceImpl(UserService userService, UserGetService userGetService, SubjectService subjectService, TestApplicationService testApplicationService, UserProfileService userProfileService, UserResponseDtoMapper userResponseDtoMapper) {
        this.userService = userService;
        this.userGetService = userGetService;
        this.subjectService = subjectService;
        this.testApplicationService = testApplicationService;
        this.userProfileService = userProfileService;
        this.userResponseDtoMapper = userResponseDtoMapper;
    }

    @Override
    public List<UserResponseDTO> getAllUsers(String authorization) throws CostumeErrorException {
        checkUser(authorization);

        return userResponseDtoMapper.map(userGetService.findAllUsers());
    }

    @Override
    public UserResponseDTO getUserById(String authorization, Long id) throws CostumeErrorException {
        checkUser(authorization);

        return userResponseDtoMapper.apply(userGetService.getUserById(id));
    }

    @Override
    public String deleteUserById(String authorization, Long id) throws CostumeErrorException {
        checkUser(authorization);

        testApplicationService.deleteAllUserApplications(userGetService.getUserById(id));

        return userService.deleteUserById(id);
    }

    @Override
    public String disableUserById(String authorization, Long id) throws CostumeErrorException {
        checkUser(authorization);

        return userService.disableUserById(id);
    }

    @Override
    public String enableUserById(String authorization, Long id) throws CostumeErrorException {
        checkUser(authorization);

        return userService.enableUserById(id);
    }

    @Override
    public String promoteUserById(String authorization, Long id) throws CostumeErrorException {
        checkUser(authorization);

        return userService.promoteUserById(id);
    }

    @Override
    public String demoteUserById(String authorization, Long id) throws CostumeErrorException {
        checkUser(authorization);

        return userService.demoteUserById(id);
    }

    public void checkUser(String auth) throws CostumeErrorException {
        if(!userGetService.getUserFromToken(auth).getRole().equals(Role.ADMIN)) throw new CostumeErrorException("User is not admin", HttpStatus.BAD_REQUEST);
    }
}
