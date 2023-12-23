package com.carniware.aoc;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.carniware.aoc.day21.Day21;

class Day21Test {
    @Test
    void test() {
        assertEquals(16, new Day21("src/main/java/com/carniware/aoc/day21/sample.txt", 6).getPart1Result());
        assertEquals(3748, new Day21("src/main/java/com/carniware/aoc/day21/input.txt", 64).getPart1Result());
        assertEquals(0, new Day21("src/main/java/com/carniware/aoc/day21/sample.txt").getPart2Result());
        assertEquals(0, new Day21("src/main/java/com/carniware/aoc/day21/input.txt").getPart2Result());
    }
}