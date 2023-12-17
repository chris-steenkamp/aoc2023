package com.carniware.aoc;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.carniware.aoc.day16.Day16;

class Day16Test {
    @Test
    void test() {
        assertEquals(46, new Day16("src/main/java/com/carniware/aoc/day16/sample.txt").getPart1Result());
        assertEquals(8125, new Day16("src/main/java/com/carniware/aoc/day16/input.txt").getPart1Result());
        assertEquals(51, new Day16("src/main/java/com/carniware/aoc/day16/sample.txt").getPart2Result());
        assertEquals(0, new Day16("src/main/java/com/carniware/aoc/day16/input.txt").getPart2Result());
    }
}