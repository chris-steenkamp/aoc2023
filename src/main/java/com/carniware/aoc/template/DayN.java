package com.carniware.aoc.template;

import java.util.List;

import com.carniware.aoc.common.AoCDay;
import com.carniware.aoc.common.Helper;

public class DayN implements AoCDay {
    private List<String> input;

    public DayN() {
        this("src/main/java/com/carniware/aoc/dayn/input.txt");
    }

    public DayN(String filename) {
        input = Helper.readFile(filename);
    }

    public DayN(String[] inputs) {
        input = List.of(inputs);
    }

    public void runPart1() {
        System.out.println("Part1 output");
    }

    public void runPart2() {
        System.out.println("Part2 output");
    }
}
