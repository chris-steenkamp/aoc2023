package com.carniware.aoc;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.carniware.aoc.day12.Day12;

class Day12Test {
    @Test
    void test() {
        assertEquals(21, new Day12("src/main/java/com/carniware/aoc/day12/sample.txt").getPart1Result());
        assertEquals(7674, new Day12("src/main/java/com/carniware/aoc/day12/input.txt").getPart1Result());
        assertEquals(525152, new Day12("src/main/java/com/carniware/aoc/day12/sample.txt").getPart2Result());
        assertEquals(4443895258186l, new Day12("src/main/java/com/carniware/aoc/day12/input.txt").getPart2Result());
    }
}