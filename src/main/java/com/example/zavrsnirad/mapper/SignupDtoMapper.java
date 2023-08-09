package com.example.zavrsnirad.mapper;

import com.example.zavrsnirad.appenum.Role;
import com.example.zavrsnirad.dto.request.SignupDTO;
import com.example.zavrsnirad.entity.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class SignupDtoMapper implements Function<SignupDTO, User> {
    private final BCryptPasswordEncoder passwordEncoder;
    public SignupDtoMapper(BCryptPasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User apply(SignupDTO signupDTO) {
        return new User(
                signupDTO.username(),
                passwordEncoder.encode(signupDTO.password()),
                Role.STUDENT,
                true
        );
    }
}
