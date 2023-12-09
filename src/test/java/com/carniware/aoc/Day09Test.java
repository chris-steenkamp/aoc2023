package com.carniware.aoc;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.carniware.aoc.day09.Day09;

class Day09Test {
    @Test
    void test() {
        assertEquals(114, new Day09("src/main/java/com/carniware/aoc/day09/sample.txt").getPart1Result());
        assertEquals(1934898178, new Day09("src/main/java/com/carniware/aoc/day09/input.txt").getPart1Result());
        assertEquals(2, new Day09("src/main/java/com/carniware/aoc/day09/sample.txt").getPart2Result());
        assertEquals(1129, new Day09("src/main/java/com/carniware/aoc/day09/input.txt").getPart2Result());
    }
}