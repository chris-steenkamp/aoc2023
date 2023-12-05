package com.carniware.aoc.common;

import java.util.List;

public abstract class AoCDayAbstract implements AoCDay {
    protected List<String> input;
    protected long part1Result;
    protected long part2Result;

    public AoCDayAbstract(String filename) {
        input = Helper.readFile(filename);
    }

    public AoCDayAbstract(String[] inputs) {
        input = List.of(inputs);
    }

    public long getPart1Result() {
        return part1Result;
    }

    public long getPart2Result() {
        return part2Result;
    }

    public void displayPart1() {
        System.out.println(String.format("%s part 1 answer: %d", this.getClass().getSimpleName(), part1Result));
    }

    public void displayPart2() {
        System.out.println(String.format("%s part 2 answer: %d", this.getClass().getSimpleName(), part2Result));
    }
}
