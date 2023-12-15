package com.carniware.aoc;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.carniware.aoc.day14.Day14;

class Day14Test {
    @Test
    void test() {
        assertEquals(136, new Day14("src/main/java/com/carniware/aoc/day14/sample.txt").getPart1Result());
        assertEquals(110128, new Day14("src/main/java/com/carniware/aoc/day14/input.txt").getPart1Result());
        assertEquals(64, new Day14("src/main/java/com/carniware/aoc/day14/sample.txt").getPart2Result());
        assertEquals(103861, new Day14("src/main/java/com/carniware/aoc/day14/input.txt").getPart2Result());
    }
}