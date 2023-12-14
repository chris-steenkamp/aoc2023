package com.carniware.aoc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.carniware.aoc.day13.Day13;

class Day13Test {
    @Test
    void test() {
        assertEquals(405, new Day13("src/main/java/com/carniware/aoc/day13/sample.txt").getPart1Result());
        assertEquals(30535, new Day13("src/main/java/com/carniware/aoc/day13/input.txt").getPart1Result());
        assertEquals(400, new Day13("src/main/java/com/carniware/aoc/day13/sample.txt").getPart2Result());
        assertTrue(2217 < new Day13("src/main/java/com/carniware/aoc/day13/input.txt").getPart2Result());
        assertTrue(35975 > new Day13("src/main/java/com/carniware/aoc/day13/input.txt").getPart2Result());
        assertEquals(30844, new Day13("src/main/java/com/carniware/aoc/day13/input.txt").getPart2Result());
    }
}