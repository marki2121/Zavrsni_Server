package com.example.zavrsnirad.util;

import com.example.zavrsnirad.appenum.Role;
import com.example.zavrsnirad.entity.User;

import java.util.List;

public final class UserUtil {
    public static User generate() {
        return User.builder()
                .id(1L)
                .username("username")
                .password("password")
                .role(Role.STUDENT)
                .enabled(true)
                .userProfile(UserProfileUtil.generate())
                .build();
    }

    public static User generateAdmin() {
        return User.builder()
                .id(2L)
                .username("username")
                .password("password")
                .role(Role.ADMIN)
                .enabled(true)
                .userProfile(UserProfileUtil.generate())
                .build();
    }

    public static User generateTeacher() {
        return User.builder()
                .id(3L)
                .username("username")
                .password("password")
                .role(Role.TEACHER)
                .enabled(true)
                .userProfile(UserProfileUtil.generate())
                .build();
    }

    public static List<User> generateList() {
        return List.of(generate());
    }

    public static List<User> generateListAdmin() {
        return List.of(generateAdmin());
    }
}
