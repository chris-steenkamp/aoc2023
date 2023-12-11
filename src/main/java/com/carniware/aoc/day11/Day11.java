package com.carniware.aoc.day11;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.carniware.aoc.common.AoCDayAbstract;

@Component
@Order(11)
public class Day11 extends AoCDayAbstract {

    record Point(int x, int y) {
    };

    public Day11() {
        this("src/main/java/com/carniware/aoc/day11/sample.txt");
    }

    public Day11(String filename) {
        super(filename);
        calculate();
    }

    private void calculate() {
        List<Point> galaxies = getExpandedInput();
        List<Integer> distances = new ArrayList<>();

        for (var i = 0; i < galaxies.size() - 1; ++i) {
            for (var j = i + 1; j < galaxies.size(); ++j) {
                distances.add(getManhattanDistance(galaxies.get(i), galaxies.get(j)));
            }
        }

        part1Result = distances.stream().reduce(Integer::sum).get();
        // part1Result = 0;
    }

    private int getManhattanDistance(Point p1, Point p2) {
        return Math.abs(p1.x - p2.x) + Math.abs(p1.y - p2.y);
    }

    private List<Point> getExpandedInput() {
        List<String> expanded = new ArrayList<>();
        List<Point> galaxies = new ArrayList<>();
        // Start by assuming all columns are empty, we will then remove any that contain
        // a galaxy.
        Set<Integer> emptyColumns = new HashSet<>(IntStream.range(0, input.size()).boxed().toList());
        Set<Integer> emptyRows = new HashSet<>();

        for (var i = 0; i < input.size(); ++i) {
            var line = input.get(i);
            // If all characters in the row are '.' (ascii 46) the the row is considered
            // empty.
            if (line.chars().allMatch(x -> x == 46)) {
                emptyRows.add(i);
                continue;
            }

            for (var c = 0; c < line.length(); ++c) {
                if (!emptyColumns.contains(c)) {
                    // We have already removed this column because it contains a galaxy
                    continue;
                }

                if (line.charAt(c) != '.') {
                    // Found a column that contains a galaxy, so remove it
                    emptyColumns.remove(c);
                    continue;
                }
            }
        }

        // each row should be increased to cater for any extra columns.
        int newRowLength = input.get(0).length() + emptyColumns.size();

        char[] emptyRow = new char[newRowLength];
        Arrays.fill(emptyRow, '.');

        for (var i = 0; i < input.size(); ++i) {
            StringBuilder sb = new StringBuilder(input.get(i));
            int offset = 0;
            for (var newCol : emptyColumns) {
                sb.insert((offset++) + newCol, '.');
            }
            expanded.add(sb.toString());

            if (emptyRows.contains(i)) {
                expanded.add(String.valueOf(emptyRow));
            }
        }

        for (int y = 0; y < expanded.size(); ++y) {
            for (var x = 0; x < expanded.get(y).length(); ++x) {
                if (expanded.get(y).charAt(x) == '#') {
                    galaxies.add(new Point(x + 1, y + 1));
                }
            }
        }

        return galaxies;
    }
}