package com.carniware.aoc;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.carniware.aoc.day11.Day11;

class Day11Test {
    @Test
    void test() {
        assertEquals(374, new Day11("src/main/java/com/carniware/aoc/day11/sample.txt", 1).getPart1Result());
        assertEquals(9693756, new Day11("src/main/java/com/carniware/aoc/day11/input.txt", 1).getPart1Result());
        assertEquals(1030, new Day11("src/main/java/com/carniware/aoc/day11/sample.txt", 10).getPart2Result());
        assertEquals(8410, new Day11("src/main/java/com/carniware/aoc/day11/sample.txt", 100).getPart2Result());
        assertEquals(717878258016l, new Day11("src/main/java/com/carniware/aoc/day11/input.txt", 1000000).getPart2Result());
    }
}