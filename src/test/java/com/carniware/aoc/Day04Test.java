package com.carniware.aoc;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.carniware.aoc.day04.Day04;

class Day04Test {
    @Test
    void test() {
        assertEquals(13, new Day04("src/main/java/com/carniware/aoc/day04/sample.txt").getPart1Result());
        assertEquals(20829, new Day04("src/main/java/com/carniware/aoc/day04/input.txt").getPart1Result());
        assertEquals(30, new Day04("src/main/java/com/carniware/aoc/day04/sample.txt").getPart2Result());
        assertEquals(12648035, new Day04("src/main/java/com/carniware/aoc/day04/input.txt").getPart2Result());
    }
}
