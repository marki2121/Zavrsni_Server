package com.example.zavrsnirad.mapper;

import com.example.zavrsnirad.dto.UserDTO;
import com.example.zavrsnirad.entity.User;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class UserDtoMapper implements Function<User, UserDTO> {
    @Override
    public UserDTO apply(User user) {
        return new UserDTO(
                user.getUsername(),
                user.getUserProfile().getFirstName(),
                user.getUserProfile().getLastName(),
                user.getUserProfile().getEmail(),
                user.getUserProfile().getAddress(),
                user.getUserProfile().getCity(),
                user.getUserProfile().getZipCode(),
                user.getUserProfile().getCountry(),
                user.getUserProfile().getPhoneNumber(),
                user.getRole().name(),
                user.getUserProfile().getAboutMe()
        );
    }
}
