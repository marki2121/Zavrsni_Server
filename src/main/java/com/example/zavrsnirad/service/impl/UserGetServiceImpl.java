package com.example.zavrsnirad.service.impl;

import com.example.zavrsnirad.config.CostumeErrorException;
import com.example.zavrsnirad.dto.request.UserDTO;
import com.example.zavrsnirad.entity.User;
import com.example.zavrsnirad.mapper.UserDtoMapper;
import com.example.zavrsnirad.repository.UserRepository;
import com.example.zavrsnirad.service.TokenService;
import com.example.zavrsnirad.service.UserGetService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserGetServiceImpl implements UserGetService {

    private final UserRepository userRepository;
    private final TokenService tokenService;
    private final UserDtoMapper userDtoMapper;

    public UserGetServiceImpl(UserRepository userRepository, TokenService tokenService, UserDtoMapper userDtoMapper) {
        this.userRepository = userRepository;
        this.tokenService = tokenService;
        this.userDtoMapper = userDtoMapper;
    }

    @Override
    public User getUserById(Long id) throws CostumeErrorException {
        Optional<User> user = userRepository.findById(id);

        if(user.isEmpty()) {
            throw new CostumeErrorException("User not found", HttpStatus.NOT_FOUND);
        }

        return user.get();
    }

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserFromToken(String bearer) throws CostumeErrorException {
        return userRepository.findByUsername(tokenService.getUsernameFromToken(bearer)).orElseThrow(() -> new CostumeErrorException("User not found", HttpStatus.BAD_REQUEST));
    }

    @Override
    public List<UserDTO> getUsersByUsername(String username) {
        return userDtoMapper.map(userRepository.findAllByUsernameContaining(username));
    }
}
