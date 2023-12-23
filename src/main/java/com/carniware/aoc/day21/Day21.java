package com.carniware.aoc.day21;

import static com.carniware.aoc.common.Helper.getNeighbours;
import static com.carniware.aoc.common.Helper.load2dGrid;
import static com.carniware.aoc.common.Helper.withinBounds;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.carniware.aoc.common.AoCDayAbstract;
import com.carniware.aoc.common.Helper.Point2;

@Component
@Order(21)
public class Day21 extends AoCDayAbstract {
    public Day21() {
        this("src/main/java/com/carniware/aoc/day21/sample.txt", 0);
    }

    public Day21(String filename) {
        super(filename);
        calculate(0);
    }

    public Day21(String filename, int numSteps) {
        super(filename);
        calculate(numSteps);
    }

    private void calculate(int numSteps) {
        Point2 startingPoint = null;
        var grid = load2dGrid(input);
        for (var y = 0; y < input.size(); ++y) {
            for (var x = 0; x < input.get(0).length(); ++x) {
                if (input.get(y).charAt(x) == 'S') {
                    startingPoint = new Point2(x, y);
                }
            }
        }
        grid[startingPoint.y()][startingPoint.x()] = '.';

        Map<Point2, Integer> depthMap = new HashMap<>();
        Queue<Point2> queue = new LinkedList<>();

        queue.add(startingPoint);
        depthMap.put(startingPoint, 0);

        while (!queue.isEmpty()) {
            var point = queue.poll();
            var newDepth = depthMap.get(point) + 1;
            // if the newest level to explore is more than numSteps deep then we have
            // exhausted all options at numSteps depth (breadth-first always explores
            // everything at depth n before exploring n + 1)
            if (newDepth > numSteps) {
                break;
            }
            getNeighbours(point).forEach(x -> {
                if (withinBounds(x, grid[0].length, grid.length) && grid[x.y()][x.x()] != '#') {
                    if (!queue.contains(x)) {
                        queue.add(x);
                        depthMap.put(x, newDepth);
                    }
                }
            });
        }

        part1Result = depthMap.values()
                .stream()
                .filter(x -> x == numSteps)
                .count();
    }
}