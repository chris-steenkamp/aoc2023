package com.carniware.aoc;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

import com.carniware.aoc.day01.Go;

class Day01Test {
    @Test
    void test() {
        var go = new Go("src/main/java/com/carniware/aoc/day01/sample.txt");
        assertEquals(go.getResult(), 142);
        go = new Go();
        assertEquals(go.getResult(), 53080);
    }
}
