package com.carniware.aoc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.carniware.aoc.day17.Day17;

class Day17Test {
    @Test
    void test() {
        assertEquals(102, new Day17("src/main/java/com/carniware/aoc/day17/sample.txt").getPart1Result());
        assertTrue(664 < new Day17("src/main/java/com/carniware/aoc/day17/input.txt").getPart1Result());
        assertTrue(689 > new Day17("src/main/java/com/carniware/aoc/day17/input.txt").getPart1Result());
        assertEquals(665, new Day17("src/main/java/com/carniware/aoc/day17/input.txt").getPart1Result());
        assertEquals(94, new Day17("src/main/java/com/carniware/aoc/day17/sample.txt").getPart2Result());
        assertEquals(71, new Day17("src/main/java/com/carniware/aoc/day17/sample2.txt").getPart2Result());
        assertEquals(809, new Day17("src/main/java/com/carniware/aoc/day17/input.txt").getPart2Result());
    }
}