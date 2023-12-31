package com.carniware.aoc.day17;

import static com.carniware.aoc.common.Helper.addPoints;
import static com.carniware.aoc.common.Helper.withinBounds;
import static com.carniware.aoc.common.Helper.CardinalDirection.getPointTranslation;
import static com.carniware.aoc.common.Helper.CardinalDirection.turnLeft;
import static com.carniware.aoc.common.Helper.CardinalDirection.turnRight;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.carniware.aoc.common.AoCDayAbstract;
import com.carniware.aoc.common.Helper.CardinalDirection.HEADING;
import com.carniware.aoc.common.Helper.Point2;

@Component
@Order(17)
public class Day17 extends AoCDayAbstract {
    Map<DirectedPoint2, DirectedPoint2> previous;
    Map<DirectedPoint2, Integer> distances;

    public Day17() {
        this("src/main/java/com/carniware/aoc/day17/sample.txt");
    }

    public Day17(String filename) {
        super(filename);
        calculate();
    }

    record DirectedPoint2(int x, int y, HEADING heading, int moveCount, int cost) {
        Point2 toPoint2() {
            return new Point2(x, y);
        }
    }

    private void calculate() {
        var start = new DirectedPoint2(0, 0, null, 0, 0);
        var end = new Point2(input.getFirst().length() - 1, input.size() - 1);
        part1Result = search(start, end, 1, 3);
        part2Result = search(start, end, 4, 10);
    }

    private int search(DirectedPoint2 start, Point2 end, int minStraightMoves, int maxStraightMoves) {
        Map<DirectedPoint2, Integer> distances = new HashMap<>();
        PriorityQueue<DirectedPoint2> queue = new PriorityQueue<>(
                Comparator.comparingInt(x -> distances.getOrDefault(x, Integer.MAX_VALUE)));

        distances.put(start, 0);
        queue.add(start);

        while (!queue.isEmpty()) {
            var current = queue.poll();
            var distance = distances.get(current);

            if (end.equals(current.toPoint2())) {
                return distance;
            }

            for (var neighbour : getNeighbours(current, minStraightMoves, maxStraightMoves)) {
                var tentativeCost = distance + neighbour.cost;
                if (tentativeCost < distances.getOrDefault(neighbour, Integer.MAX_VALUE)) {
                    distances.put(neighbour, tentativeCost);
                    queue.add(neighbour);
                }
            }
        }

        return -1;
    }

    private int getCost(Point2 node) {
        return Character.getNumericValue(input.get(node.y()).charAt(node.x()));
    }

    private List<DirectedPoint2> getNeighbours(DirectedPoint2 node, int minStraightMoves, int maxStraightMoves) {
        List<DirectedPoint2> neighbours = new ArrayList<>();

        if (node.heading != null) {
            var newPos = addPoints(node.toPoint2(), getPointTranslation(node.heading));
            if (withinBounds(newPos, input.getFirst().length(), input.size())
                    && node.moveCount + 1 <= maxStraightMoves) {
                neighbours.add(new DirectedPoint2(newPos.x(), newPos.y(), node.heading, node.moveCount + 1, getCost(newPos)));
            }
        }

        for (var newHeading : node.heading == null ? List.of(HEADING.E, HEADING.S)
                : List.of(turnLeft(node.heading), turnRight(node.heading))) {
            var newPos = node.toPoint2();
            for (var i = 0; i < minStraightMoves; ++i) {
                newPos = addPoints(newPos, getPointTranslation(newHeading));
            }
            if (withinBounds(newPos, input.getFirst().length(), input.size())) {
                var totalCost = 0;
                for (var i = 1; i <= minStraightMoves; ++i) {
                    switch (newHeading) {
                        case HEADING.E: {
                            totalCost += getCost(new Point2(node.x + i, node.y));
                            break;
                        }
                        case HEADING.W: {
                            totalCost += getCost(new Point2(node.x - i, node.y));
                            break;
                        }
                        case HEADING.N: {
                            totalCost += getCost(new Point2(node.x, node.y - i));
                            break;
                        }
                        case HEADING.S: {
                            totalCost += getCost(new Point2(node.x, node.y + i));
                            break;
                        }
                    }
                }
                neighbours.add(new DirectedPoint2(newPos.x(), newPos.y(), newHeading, minStraightMoves, totalCost));
            }
        }

        return neighbours;
    }
}