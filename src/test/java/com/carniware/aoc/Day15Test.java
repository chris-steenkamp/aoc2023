package com.carniware.aoc;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.carniware.aoc.day15.Day15;

class Day15Test {
    @Test
    void test() {
        assertEquals(1320, new Day15("src/main/java/com/carniware/aoc/day15/sample.txt").getPart1Result());
        assertEquals(513214, new Day15("src/main/java/com/carniware/aoc/day15/input.txt").getPart1Result());
        assertEquals(145, new Day15("src/main/java/com/carniware/aoc/day15/sample.txt").getPart2Result());
        assertEquals(258826, new Day15("src/main/java/com/carniware/aoc/day15/input.txt").getPart2Result());
    }
}