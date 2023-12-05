package com.carniware.aoc;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.carniware.aoc.day02.Day02;

class Day02Test {
    @Test
    void test() {
        assertEquals(8, new Day02("src/main/java/com/carniware/aoc/day02/sample.txt").getPart1Result());
        assertEquals(2377, new Day02("src/main/java/com/carniware/aoc/day02/input.txt").getPart1Result());
        assertEquals(2286, new Day02("src/main/java/com/carniware/aoc/day02/sample.txt").getPart2Result());
        assertEquals(71220, new Day02("src/main/java/com/carniware/aoc/day02/input.txt").getPart2Result());
    }
}
