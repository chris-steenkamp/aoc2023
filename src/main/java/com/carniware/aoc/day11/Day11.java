package com.carniware.aoc.day11;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.carniware.aoc.common.AoCDayAbstract;

@Component
@Order(11)
public class Day11 extends AoCDayAbstract {

    record Point(long x, long y) {
    };

    public Day11() {
        this("src/main/java/com/carniware/aoc/day11/input.txt", 1000000);
    }

    public Day11(String filename, int scaleFactor) {
        super(filename);
        calculate(scaleFactor);
    }

    private void calculate(int scaleFactor) {
        part1Result = calculateDistances(getExpandedInput(1));
        part2Result = calculateDistances(getExpandedInput(scaleFactor));
    }

    private long getManhattanDistance(Point p1, Point p2) {
        return Math.abs(p1.x - p2.x) + Math.abs(p1.y - p2.y);
    }

    private long calculateDistances(List<Point> galaxies) {
        List<Long> distances = new ArrayList<>();

        for (var i = 0; i < galaxies.size() - 1; ++i) {
            for (var j = i + 1; j < galaxies.size(); ++j) {
                distances.add(getManhattanDistance(galaxies.get(i), galaxies.get(j)));
            }
        }

        return distances.stream().reduce(Long::sum).get();
    }

    private List<Point> getExpandedInput(int scaleFactor) {
        List<Point> galaxies = new ArrayList<>();
        scaleFactor = Math.max(1, scaleFactor - 1);
        // Start by assuming all columns are empty, we will then remove any that contain
        // a galaxy.
        Set<Integer> emptyColumns = new LinkedHashSet<>(IntStream.range(0, input.size()).boxed().toList());
        Set<Integer> emptyRows = new LinkedHashSet<>();

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

        for (var y = 0; y < input.size(); ++y) {
            var line = input.get(y);
            for (var x = 0; x < line.length(); ++x) {
                if (line.charAt(x) == '#') {
                    galaxies.add(new Point(calculateOffset(x, emptyColumns, scaleFactor), calculateOffset(y, emptyRows, scaleFactor)));
                }
            }
        }

        return galaxies;
    }

    private int calculateOffset(int coordinate, Set<Integer> offsetRanges, int scaleFactor) {
        int offsetCount = 0;
        for (var column : offsetRanges) {
            if (coordinate < column) {
                // we have found where the coordinate appears in the range so we can break to calculate the offset.
                break;
            } else {
                // we haven't yet found where the coordinate fits in the range so we increment the offset count by one
                ++offsetCount;
            }
        }

        return coordinate + (offsetCount * scaleFactor);
    }
}