package com.carniware.aoc;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.carniware.aoc.day19.Day19;

class Day19Test {
    @Test
    void test() {
        assertEquals(19114, new Day19("src/main/java/com/carniware/aoc/day19/sample.txt").getPart1Result());
        assertEquals(352052, new Day19("src/main/java/com/carniware/aoc/day19/input.txt").getPart1Result());
        assertEquals(167409079868000l, new Day19("src/main/java/com/carniware/aoc/day19/sample.txt").getPart2Result());
        assertEquals(116606738659695l, new Day19("src/main/java/com/carniware/aoc/day19/input.txt").getPart2Result());
    }
}