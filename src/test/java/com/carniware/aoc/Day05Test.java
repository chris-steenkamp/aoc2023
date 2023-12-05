package com.carniware.aoc;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.carniware.aoc.day05.Day05;

class Day05Test {
    @Test
    void test() {
        assertEquals(35, new Day05("src/main/java/com/carniware/aoc/day05/sample.txt", false).getPart1Result());
        assertEquals(107430936, new Day05("src/main/java/com/carniware/aoc/day05/input.txt", false).getPart1Result());
        assertEquals(46, new Day05("src/main/java/com/carniware/aoc/day05/sample.txt", true).getPart2Result());
        //Takes around 6 minutes to run 🥲 - Uncomment only if you must. 
        // assertEquals(23738616, new Day05("src/main/java/com/carniware/aoc/day05/input.txt", true).getPart2Result());
    }
}
