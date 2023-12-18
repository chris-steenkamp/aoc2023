package com.carniware.aoc.day18;

import static com.carniware.aoc.common.Helper.addPoints;
import static com.carniware.aoc.common.Helper.CardinalDirection.fromDirection;
import static com.carniware.aoc.common.Helper.CardinalDirection.getPointTranslation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HexFormat;
import java.util.List;
import java.util.Map;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.carniware.aoc.common.AoCDayAbstract;
import com.carniware.aoc.common.Helper.CardinalDirection.HEADING;
import com.carniware.aoc.common.Helper.Point2l;

@Component
@Order(18)
public class Day18 extends AoCDayAbstract {
    public Day18() {
        this("src/main/java/com/carniware/aoc/day18/sample.txt");
    }

    public Day18(String filename) {
        super(filename);
        calculate();
    }

    public Day18(String[] inputs) {
        super(inputs);
        calculate();
    }

    record DigInstruction(HEADING heading, long size, String colour) {
    }

    private void adjustBorderCorner(Map<Point2l, HEADING> border, DigInstruction instruction,
            DigInstruction previousInstruction, Point2l previousPoint) {
        // similar to the logic used in day 10, assign all corners that are going up or
        // down a vertical heading and then assign all those going left or right a
        // horizontal heading. This is required when implementing the even odd or
        // non-zero winding rule where you check the number of vertical lines
        // encountered so far. If the number is odd then a non-edge point is considered
        // inside the curve.
        if (previousInstruction != null) {
            if ((previousInstruction.heading == HEADING.W && instruction.heading == HEADING.N) ||
                    (previousInstruction.heading == HEADING.E && instruction.heading == HEADING.N) ||
                    (previousInstruction.heading == HEADING.S && instruction.heading == HEADING.E) ||
                    (previousInstruction.heading == HEADING.S && instruction.heading == HEADING.W)) {
                border.put(previousPoint, HEADING.N);
            } else if ((previousInstruction.heading == HEADING.W && instruction.heading == HEADING.S) ||
                    (previousInstruction.heading == HEADING.E && instruction.heading == HEADING.S) ||
                    (previousInstruction.heading == HEADING.N && instruction.heading == HEADING.E) ||
                    (previousInstruction.heading == HEADING.N && instruction.heading == HEADING.W)) {
                border.put(previousPoint, HEADING.W);
            }
        }
    }

    private HEADING getRealHeading(String hexValue) {
        switch (hexValue) {
            case "0":
                return HEADING.E;
            case "1":
                return HEADING.S;
            case "2":
                return HEADING.W;
            case "3":
                return HEADING.N;
        }

        return null;
    }

    private DigInstruction parseColourString(String colourString) {
        colourString = colourString.replaceAll("\\(|\\)|#", "");

        long size = HexFormat.fromHexDigitsToLong(colourString, 0, 5);
        HEADING h = getRealHeading(colourString.substring(5));

        return new DigInstruction(h, size, "");
    }

    private void calculate() {
        List<DigInstruction> instructions = new ArrayList<>();
        Map<Point2l, HEADING> border = new HashMap<>();
        Point2l start = new Point2l(0, 0);
        var minY = Long.MAX_VALUE;
        var minX = Long.MAX_VALUE;
        var maxY = Long.MIN_VALUE;
        var maxX = Long.MIN_VALUE;
        DigInstruction previousInstruction = null;
        Point2l previousPoint = null;
        for (var line : input) {
            var parts = line.split(" ");
            var instruction = new DigInstruction(fromDirection(parts[0]), Integer.parseInt(parts[1]), parts[2]);
            instructions.add(instruction);
            adjustBorderCorner(border, instruction, previousInstruction, previousPoint);
            for (long i = 0; i < instruction.size; ++i) {
                start = addPoints(start, getPointTranslation(instruction.heading));
                border.put(start, instruction.heading);
                minY = Math.min(minY, start.y());
                maxY = Math.max(maxY, start.y());
                minX = Math.min(minX, start.x());
                maxX = Math.max(maxX, start.x());
            }
            previousInstruction = instruction;
            previousPoint = start;
        }

        // Adjust the corner where the end meets the start.
        adjustBorderCorner(border, instructions.getFirst(), previousInstruction, previousPoint);

        calculatePart1(border, minX, minY, maxX, maxY);
        // extract the real digging instruction from the "colour" string
        calculatePart2(instructions.stream().map(x -> parseColourString(x.colour)).toList());
    }

    private void calculatePart2(List<DigInstruction> instructions) {
        List<Point2l> points = new ArrayList<>();
        Point2l start = new Point2l(0, 0);
        var border = 0l;
        // generate the cartesian points for each digging instruction
        // also tally up the size of the perimeter while we are busy.
        for (var instruction : instructions) {
            // there is definitely a better way to do this, but we already have
            // the translation helper to move a single point, so we just apply that
            // movement a whole bunch of times move the point to the correct place.
            for (long i = 0; i < instruction.size; ++i) {
                start = addPoints(start, getPointTranslation(instruction.heading));
            }
            points.add(start);
            border += instruction.size;
        }

        var sum1 = 0l;
        var sum2 = 0l;

        // apply the shoelace formula to calculate the area of the polygon.
        for (int i = 0; i < points.size() - 1; ++i) {
            sum1 += points.get(i).x() * points.get(i + 1).y();
            sum2 += points.get(i).y() * points.get(i + 1).x();
        }

        part2Result = (Math.abs(sum1 - sum2) + border) / 2 + 1;
    }

    private void calculatePart1(Map<Point2l, HEADING> border, long minX, long minY, long maxX, long maxY) {
        var crossings = 0;
        // char[][] board = new char[(maxY - minY) + 1][(maxX - minX) + 1];
        for (var y = minY; y <= maxY; ++y) {
            var counter = 0;
            for (var x = minX; x <= maxX; ++x) {
                Point2l p = new Point2l(x, y);
                // For drawing we need to move the original coordinates to be in the positive
                // cartesian plane.
                // Point2l correctedPoint = new Point2l(x - minX, y - minY);
                if (border.containsKey(p)) {
                    // board[correctedPoint.y()][correctedPoint.x()] = (border.get(p) == HEADING.N
                    // || border.get(p) == HEADING.S) ? '|' : '-';
                    // if the point is vertical we increase the number of vertical points seen
                    // this number gets checked when we encounter a non-edge point and is used
                    // to determine the if the non-edge point is inside or outside the curve.
                    if (border.get(p) == HEADING.N || border.get(p) == HEADING.S) {
                        counter += 1;
                    }
                } else {
                    // board[correctedPoint.y()][correctedPoint.x()] = '.';
                    // if the number of vertical points encountered so far is odd, then the point
                    // is inside the curve, otherwise it outside.
                    crossings += counter % 2 == 0 ? 0 : 1;
                }
            }
        }

        // draw(board);
        part1Result = crossings + border.size();
    }

    // private void draw(char[][] board) {
    // for (var y = 0; y < board.length; ++y) {
    // for (var x = 0; x < board[0].length; ++x) {
    // System.out.print(board[y][x]);
    // }
    // System.out.println();
    // }
    // }
}