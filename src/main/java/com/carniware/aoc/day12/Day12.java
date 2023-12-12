package com.carniware.aoc.day12;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.carniware.aoc.common.AoCDayAbstract;

@Component
@Order(12)
public class Day12 extends AoCDayAbstract {
    static Set<Character> options = Set.of('#', '.');

    record Range(int start, int end, char rangeOf) {
        int getLength() {
            return (end - start) + 1;
        }
    }

    public Day12() {
        this("src/main/java/com/carniware/aoc/day12/sample.txt");
    }

    public Day12(String filename) {
        super(filename);
        calculate();
    }

    public Day12(String[] inputs) {
        super(inputs);
        calculate();
    }

    private void calculate() {
        List<String> records = new ArrayList<>();
        List<Integer> numericRanges = new ArrayList<>();
        List<Integer> solutions = new ArrayList<>();

        for (var line : input) {
            var parts = line.split(" ");
            numericRanges = (Arrays.stream(parts[1].split(",")).map(Integer::parseInt).collect(Collectors.toList()));
            records.add(parts[0]);
            solutions.add(solve(parts[0], numericRanges));
        }

        part1Result = solutions.stream().reduce(Integer::sum).get();
    }

    private int count(String input, List<Integer> ranges) {
        // if there is no group to create, we can only create 0 groups.
        if (ranges.size() == 0) {
            return 0;
        }

        // get the size of the first group in the list
        var currentGroupSize = ranges.get(0);

        // if there are not enough characters to create a group the we can only create 0 groups.
        if (input.length() < currentGroupSize) {
            return 0;
        }

        // if the current group is not contiguous then we this group is invalid.
        if (input.substring(0, currentGroupSize).contains(".")) {
            return 0;
        }

        // if the char after the current group is a #, then the current group would be too big.
        if (input.length() > currentGroupSize && input.charAt(currentGroupSize) == '#') {
            return 0;
        }

        var r = ranges.subList(1, ranges.size());
        var max = Math.min(currentGroupSize+1, input.length());
        // solve the rest of the string with a reduced number of group sizes.
        return solve(input.substring(max), r);
    }

    private int solve(String input, List<Integer> ranges) {
        if (input.length() == 0) {
            if (ranges.size() == 0) {
                return 1;
            }
            return 0;
        }

        switch (input.charAt(0)) {
            case '?': {
                return solve(input.substring(1), ranges) + count(input, ranges);
            }
            case '#': {
                return count(input, ranges);
            }
            case '.': {
                return solve(input.substring(1), ranges);
            }
        }

        // shouldn't get here
        return -1;
    }
}