package com.carniware.aoc;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.carniware.aoc.day08.Day08;

class Day08Test {
    @Test
    void test() {
        assertEquals(2, new Day08("src/main/java/com/carniware/aoc/day08/sample.txt",1).getPart1Result());
        assertEquals(6, new Day08("src/main/java/com/carniware/aoc/day08/sample2.txt",1).getPart1Result());
        assertEquals(16579, new Day08("src/main/java/com/carniware/aoc/day08/input.txt",1).getPart1Result());
        assertEquals(6, new Day08("src/main/java/com/carniware/aoc/day08/sample3.txt",2).getPart2Result());
        assertEquals(12927600769609l, new Day08("src/main/java/com/carniware/aoc/day08/input.txt",2).getPart2Result());
    }
}