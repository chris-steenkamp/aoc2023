package com.carniware.aoc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.carniware.aoc.day18.Day18;

class Day18Test {
    @Test
    void test() {
        assertEquals(62, new Day18("src/main/java/com/carniware/aoc/day18/sample.txt").getPart1Result());
        assertTrue(52882 > new Day18("src/main/java/com/carniware/aoc/day18/input.txt").getPart1Result());
        assertTrue(52024 < new Day18("src/main/java/com/carniware/aoc/day18/input.txt").getPart1Result());
        assertEquals(52035, new Day18("src/main/java/com/carniware/aoc/day18/input.txt").getPart1Result());
        assertEquals(952408144115l, new Day18("src/main/java/com/carniware/aoc/day18/sample.txt").getPart2Result());
        assertEquals(60612092439765l, new Day18("src/main/java/com/carniware/aoc/day18/input.txt").getPart2Result());
    }
}