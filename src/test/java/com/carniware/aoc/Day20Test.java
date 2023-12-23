package com.carniware.aoc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.carniware.aoc.day20.Day20;

class Day20Test {
    @Test
    void test() {
        assertEquals(32000000, new Day20("src/main/java/com/carniware/aoc/day20/sample.txt").getPart1Result());
        assertEquals(11687500, new Day20("src/main/java/com/carniware/aoc/day20/sample2.txt").getPart1Result());
        assertEquals(814934624, new Day20("src/main/java/com/carniware/aoc/day20/input.txt").getPart1Result());
        assertTrue(19296496614487950l > new Day20("src/main/java/com/carniware/aoc/day20/input.txt").getPart2Result());
        assertTrue(69406901511717l < new Day20("src/main/java/com/carniware/aoc/day20/input.txt").getPart2Result());
        assertEquals(228282646835717l, new Day20("src/main/java/com/carniware/aoc/day20/input.txt").getPart2Result());
    }
}