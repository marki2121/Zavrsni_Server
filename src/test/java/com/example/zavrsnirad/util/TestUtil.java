package com.example.zavrsnirad.util;

import com.example.zavrsnirad.entity.Test;

import java.util.Date;

public final class TestUtil {
    public static Test generate() {
        return new Test(
                1L,
                SubjectUtil.generate(),
                new Date(),
                "note",
                TestApplicationUtil.generateList());
    }
}
