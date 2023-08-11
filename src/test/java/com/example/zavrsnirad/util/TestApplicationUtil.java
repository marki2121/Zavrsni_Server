package com.example.zavrsnirad.util;

import com.example.zavrsnirad.entity.Test;
import com.example.zavrsnirad.entity.TestApplication;

import java.util.List;

public final class TestApplicationUtil {
    public static TestApplication generate() {
        return new TestApplication(
                1L,
                new Test(),
                UserUtil.generate(),
                1,
                "note",
                true);
    }

    public static List<TestApplication> generateList() {
        return List.of(generate());
    }
}
