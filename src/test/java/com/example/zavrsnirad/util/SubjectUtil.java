package com.example.zavrsnirad.util;

import com.example.zavrsnirad.entity.Subject;
import com.example.zavrsnirad.entity.Test;

import java.util.List;

public final class SubjectUtil {
    public static Subject generate() {
        return new Subject(
                1L,
                "name",
                "description",
                1,
                1,
                1,
                UserUtil.generateTeacher(),
                UserUtil.generateList(),
                List.of(new Test()));
    }

    public static Subject generateMaxTests() {
        return new Subject(
                1L,
                "name",
                "description",
                1,
                1,
                1,
                UserUtil.generateTeacher(),
                UserUtil.generateList(),
                List.of(new Test(), new Test(), new Test(), new Test()));
    }
}
