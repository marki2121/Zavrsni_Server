package com.example.zavrsnirad.util;

import com.example.zavrsnirad.appenum.Role;
import com.example.zavrsnirad.entity.Subject;
import com.example.zavrsnirad.entity.Test;
import com.example.zavrsnirad.entity.TestApplication;
import com.example.zavrsnirad.entity.User;

import java.util.Date;
import java.util.List;

public final class TestApplicationUtil {
    public static TestApplication generate() {
        return new TestApplication(
                1L,
                new Test(1L,
                        new Subject(1L, "sub", "des", 1, 1, 1,
                                new User(1L, "nes", "tam", Role.TEACHER, true,
                                        UserProfileUtil.generate(), List.of()), List.of(), List.of())
                        , new Date(), "note", List.of()),
                UserUtil.generate(),
                1,
                "note",
                true);
    }

    public static List<TestApplication> generateList() {
        return List.of(generate());
    }
}
