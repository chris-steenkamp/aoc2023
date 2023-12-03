package com.carniware.aoc;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.carniware.aoc.day03.Day03;

class Day03Test {
    @Test
    void test() {
        assertEquals(4361, new Day03("src/main/java/com/carniware/aoc/day03/sample.txt").getPart1Result());
        assertEquals(528799, new Day03("src/main/java/com/carniware/aoc/day03/input.txt").getPart1Result());
        assertEquals(467835, new Day03("src/main/java/com/carniware/aoc/day03/sample.txt").getPart2Result());
        assertEquals(84907174, new Day03("src/main/java/com/carniware/aoc/day03/input.txt").getPart2Result());
    }
}
