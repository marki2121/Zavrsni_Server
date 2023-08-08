package com.example.zavrsnirad.mapper;

import com.example.zavrsnirad.dto.response.UserResponseDTO;
import com.example.zavrsnirad.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;

@Service
public class UserResponseDtoMapper implements Function<User, UserResponseDTO> {
    @Override
    public UserResponseDTO apply(User user) {
        return new UserResponseDTO(
                user.getId(),
                user.getUsername(),
                user.getUserProfile().getFirstName(),
                user.getUserProfile().getLastName(),
                user.getUserProfile().getEmail(),
                user.getUserProfile().getPhoneNumber(),
                user.getUserProfile().getAddress(),
                user.getUserProfile().getCity(),
                user.getUserProfile().getZipCode(),
                user.getUserProfile().getCountry(),
                user.getUserProfile().getAboutMe(),
                user.getRole().name(),
                user.getEnabled().toString()
        );
    }

    public List<UserResponseDTO> map(List<User> users){
        return users.stream().map(this::apply).toList();
    }
}
