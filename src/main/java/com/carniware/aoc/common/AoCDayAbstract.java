package com.carniware.aoc.common;

import java.util.List;

public abstract class AoCDayAbstract {
    protected List<String> input;
    protected int part1Result;
    protected int part2Result;

    public AoCDayAbstract(String filename) {
        input = Helper.readFile(filename);
    }

    public AoCDayAbstract(String[] inputs) {
        input = List.of(inputs);
    }

    public int getPart1Result() {
        return part1Result;
    }

    public int getPart2Result() {
        return part2Result;
    }

    public void displayPart1() {
        System.out.println(String.format("%s part 1 answer: %d", this.getClass().getSimpleName(), part1Result));
    }

    public void displayPart2() {
        System.out.println(String.format("%s part 2 answer: %d", this.getClass().getSimpleName(), part2Result));
    }
}
