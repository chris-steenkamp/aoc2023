package com.carniware.aoc;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.carniware.aoc.day10.Day10;

class Day10Test {
    @Test
    void test() {
        assertEquals(4, new Day10("src/main/java/com/carniware/aoc/day10/sample.txt", 'F').getPart1Result());
        assertEquals(8, new Day10("src/main/java/com/carniware/aoc/day10/sample2.txt", 'F').getPart1Result());
        assertEquals(6875, new Day10("src/main/java/com/carniware/aoc/day10/input.txt", 'L').getPart1Result());
        assertEquals(4, new Day10("src/main/java/com/carniware/aoc/day10/sample3.txt", 'F').getPart2Result());
        assertEquals(4, new Day10("src/main/java/com/carniware/aoc/day10/sample6.txt", 'F').getPart2Result());
        assertEquals(8, new Day10("src/main/java/com/carniware/aoc/day10/sample4.txt",'F').getPart2Result());
        assertEquals(10, new Day10("src/main/java/com/carniware/aoc/day10/sample5.txt", '7').getPart2Result());
        assertEquals(471, new Day10("src/main/java/com/carniware/aoc/day10/input.txt", 'L').getPart2Result());
    }
}