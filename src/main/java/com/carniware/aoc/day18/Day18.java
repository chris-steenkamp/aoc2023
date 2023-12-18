package com.carniware.aoc.day18;

import static com.carniware.aoc.common.Helper.addPoints;
import static com.carniware.aoc.common.Helper.CardinalDirection.fromDirection;
import static com.carniware.aoc.common.Helper.CardinalDirection.getPointTranslation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.carniware.aoc.common.AoCDayAbstract;
import com.carniware.aoc.common.Helper.CardinalDirection.HEADING;
import com.carniware.aoc.common.Helper.Point2;

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

    record DigInstruction(HEADING heading, int size, String colour) {
    }

    private void adjustBorderCorner(Map<Point2, HEADING> border, DigInstruction instruction,
            DigInstruction previousInstruction, Point2 previousPoint) {
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

    private void calculate() {
        List<DigInstruction> instructions = new ArrayList<>();
        Map<Point2, HEADING> border = new HashMap<>();
        Point2 start = new Point2(0, 0);
        var minY = Integer.MAX_VALUE;
        var minX = Integer.MAX_VALUE;
        var maxY = Integer.MIN_VALUE;
        var maxX = Integer.MIN_VALUE;
        DigInstruction previousInstruction = null;
        Point2 previousPoint = null;
        for (var line : input) {
            var parts = line.split(" ");
            var instruction = new DigInstruction(fromDirection(parts[0]), Integer.parseInt(parts[1]), parts[2]);
            instructions.add(instruction);
            adjustBorderCorner(border, instruction, previousInstruction, previousPoint);
            for (int i = 0; i < instruction.size; ++i) {
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

        var crossings = 0;
        // char[][] board = new char[(maxY - minY) + 1][(maxX - minX) + 1];
        for (var y = minY; y <= maxY; ++y) {
            var counter = 0;
            for (var x = minX; x <= maxX; ++x) {
                Point2 p = new Point2(x, y);
                // For drawing we need to move the original coordinates to be in the positive cartesian plane.
                // Point2 correctedPoint = new Point2(x - minX, y - minY);
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