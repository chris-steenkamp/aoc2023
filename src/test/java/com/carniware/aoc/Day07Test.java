package com.carniware.aoc;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.carniware.aoc.day07.Day07;

class Day07Test {
    @Test
    void test() {
        assertEquals(6440, new Day07("src/main/java/com/carniware/aoc/day07/sample.txt").getPart1Result());
        assertEquals(248453531, new Day07("src/main/java/com/carniware/aoc/day07/input.txt").getPart1Result());
        assertEquals(5905, new Day07("src/main/java/com/carniware/aoc/day07/sample.txt").getPart2Result());
        assertEquals(248781813, new Day07("src/main/java/com/carniware/aoc/day07/input.txt").getPart2Result());
    }
}