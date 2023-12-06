package com.carniware.aoc;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.carniware.aoc.day06.Day06;

class Day06Test {
    @Test
    void test() {
        assertEquals(288, new Day06("src/main/java/com/carniware/aoc/day06/sample.txt").getPart1Result());
        assertEquals(800280, new Day06("src/main/java/com/carniware/aoc/day06/input.txt").getPart1Result());
        assertEquals(71503, new Day06("src/main/java/com/carniware/aoc/day06/sample.txt").getPart2Result());
        assertEquals(45128024, new Day06("src/main/java/com/carniware/aoc/day06/input.txt").getPart2Result());
    }
}
