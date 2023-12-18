package com.carniware.aoc.day17;

import static com.carniware.aoc.common.Helper.withinBounds;
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
import com.carniware.aoc.common.Helper.P2;
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

    record DirectedPoint2(int x, int y, HEADING heading) implements P2 {
        Point2 toPoint2() {
            return new Point2(x, y);
        }
    }

    class Node {
        Point2 coords;
        HEADING heading;
        int straighMovements;

        Node(int x, int y, HEADING heading) {
            this(x, y, heading, 0);
        }

        Node(int x, int y, HEADING heading, int straightMovements) {
            this.coords = new Point2(x, y);
            this.heading = heading;
            this.straighMovements = straightMovements;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this)
                return true;

            if (!(obj instanceof Node)) {
                return false;
            }

            Node o = (Node) obj;
            return coords.x() == o.coords.x() && coords.y() == o.coords.y();
        }

        @Override
        public int hashCode() {
            return coords.hashCode();
        }
    }

    private int distance(DirectedPoint2 node) {
        return Integer.parseInt(String.valueOf(input.get(node.y()).charAt(node.x())));
    }

    private int calculateHeatloss(DirectedPoint2 node) {
        StringBuilder sb = new StringBuilder();
        List<Integer> heatLoss = new ArrayList<>();
        while (node != null) {
            heatLoss.add(distance(node));
            sb.insert(0, String.format("%s\\", node.heading));
            node = previous.get(node);
        }

        // we don't count the heatloss of the initial starting position.
        heatLoss.removeLast();
        System.out.println(sb.toString());
        return heatLoss.stream().reduce(Integer::sum).get();
    }

    private DirectedPoint2 getLeftNeighbour(DirectedPoint2 node) {
        DirectedPoint2 newNode = null;
        switch (node.heading) {
            case HEADING.E: {
                newNode = new DirectedPoint2(node.x(), node.y() - 1, turnLeft(node.heading));
                break;
            }
            case HEADING.S: {
                newNode = new DirectedPoint2(node.x() + 1, node.y(), turnLeft(node.heading));
                break;
            }
            case HEADING.W: {
                newNode = new DirectedPoint2(node.x(), node.y() + 1, turnLeft(node.heading));
                break;
            }
            case HEADING.N: {
                newNode = new DirectedPoint2(node.x() - 1, node.y(), turnLeft(node.heading));
                break;
            }
        }

        if (newNode != null && !withinBounds(newNode, input.getFirst().length(), input.size())) {
            newNode = null;
        }

        return newNode;
    }

    private DirectedPoint2 getRightNeighbour(DirectedPoint2 node) {
        DirectedPoint2 newNode = null;
        switch (node.heading) {
            case HEADING.E: {
                newNode = new DirectedPoint2(node.x(), node.y() + 1, turnRight(node.heading));
                break;
            }
            case HEADING.S: {
                newNode = new DirectedPoint2(node.x() - 1, node.y(), turnRight(node.heading));
                break;
            }
            case HEADING.W: {
                newNode = new DirectedPoint2(node.x(), node.y() - 1, turnRight(node.heading));
                break;
            }
            case HEADING.N: {
                newNode = new DirectedPoint2(node.x() + 1, node.y(), turnRight(node.heading));
                break;
            }
        }

        if (newNode != null && !withinBounds(newNode, input.getFirst().length(), input.size())) {
            newNode = null;
        }

        return newNode;
    }

    private DirectedPoint2 getStraightNeighbour(DirectedPoint2 node) {
        DirectedPoint2 newNode = null;
        switch (node.heading) {
            case HEADING.E: {
                newNode = new DirectedPoint2(node.x() + 1, node.y(), node.heading);
                break;
            }
            case HEADING.S: {
                newNode = new DirectedPoint2(node.x(), node.y() + 1, node.heading);
                break;
            }
            case HEADING.W: {
                newNode = new DirectedPoint2(node.x() - 1, node.y(), node.heading);
                break;
            }
            case HEADING.N: {
                newNode = new DirectedPoint2(node.x(), node.y() - 1, node.heading);
                break;
            }
        }

        if (newNode != null) {
            int count = 0;
            DirectedPoint2 temp = node;
            while (temp != null && count < 3) {
                if (previous.get(temp) == null) {
                    break;
                }

                if (!temp.heading.equals(previous.get(temp).heading)) {
                    break;
                }

                count++;
                temp = previous.get(temp);
            }
            if (count > 2 || !withinBounds(newNode, input.getFirst().length(), input.size())) {
                newNode = null;
            }
        }

        return newNode;
    }

    private List<DirectedPoint2> generateNeighbours(DirectedPoint2 node) {
        var l = getLeftNeighbour(node);
        var s = getStraightNeighbour(node);
        var r = getRightNeighbour(node);

        List<DirectedPoint2> neighbours = new ArrayList<>();
        if (l != null)
            neighbours.add(l);
        if (r != null)
            neighbours.add(r);
        if (s != null)
            neighbours.add(s);
        return neighbours;
    }

    private void calculate() {
        PriorityQueue<DirectedPoint2> open = new PriorityQueue<>(Comparator.comparingInt(x -> distances.get(x)));
        previous = new HashMap<>();
        distances = new HashMap<>();

        var start = new DirectedPoint2(0, 0, HEADING.S);
        distances.put(start, 0);
        open.add(start);

        var end = new Point2(input.getFirst().length() - 1, input.size() - 1);

        while (!open.isEmpty()) {
            var node = open.poll();

            if (end.equals(node.toPoint2())) {
                part1Result = calculateHeatloss(node);
                break;
            }

            var neighbours = generateNeighbours(node);
            for (var nextNode : neighbours) {
                var score = distances.get(node) + distance(nextNode);

                if (score < distances.getOrDefault(nextNode, Integer.MAX_VALUE)) {
                    distances.put(nextNode, score);
                    previous.put(nextNode, node);
                    open.add(nextNode);
                }
            }
        }
    }
}