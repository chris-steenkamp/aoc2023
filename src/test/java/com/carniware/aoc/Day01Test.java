package com.carniware.aoc;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.carniware.aoc.day01.Day01;

class Day01Test {
    @Test
    void test() {
        assertEquals(142, new Day01("src/main/java/com/carniware/aoc/day01/sample.txt").getPart2Result());
        assertEquals(53080, new Day01("src/main/java/com/carniware/aoc/day01/input.txt").getPart1Result());
        assertEquals(281, new Day01("src/main/java/com/carniware/aoc/day01/sample2.txt").getPart2Result());
        assertEquals(53268, new Day01("src/main/java/com/carniware/aoc/day01/input.txt").getPart2Result());
        assertEquals(21, new Day01(new String[]{"twone"}).getPart2Result());
        assertEquals(67, new Day01(new String[]{"twone","oneight","twoneight"}).getPart2Result());
    }
}
