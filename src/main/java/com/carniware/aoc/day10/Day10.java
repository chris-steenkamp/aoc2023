package com.carniware.aoc.day10;

import static java.util.Map.entry;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.carniware.aoc.common.AoCDayAbstract;

@Component
@Order(10)
public class Day10 extends AoCDayAbstract {
    static Map<Character, List<Integer>> pipes = Map.ofEntries(
            entry('|', List.of(0, -1, 0, 1)),
            entry('-', List.of(-1, 0, 1, 0)),
            entry('L', List.of(0, -1, 1, 0)),
            entry('J', List.of(0, -1, -1, 0)),
            entry('7', List.of(-1, 0, 0, 1)),
            entry('F', List.of(1, 0, 0, 1)),
            entry('.', List.of(0, 0, 0, 0)));

    record Point(int x, int y) {}

    public Day10() {
        this("src/main/java/com/carniware/aoc/day10/sample2.txt");
    }

    public Day10(String filename) {
        super(filename);
        calculate();
    }

    public Day10(String[] inputs) {
        super(inputs);
        calculate();
    }

    private Set<Point> getAdjacentPoints(Point p, char character) {
        Set<Point> points = new HashSet<>();

        if (character == '.') {
            return points;
        }

        if (character == 'S') {
            return Set.of(
                    new Point(p.x - 1, p.y),
                    new Point(p.x + 1, p.y),
                    new Point(p.x, p.y - 1),
                    new Point(p.x, p.y + 1));
        }

        var links = pipes.get(character);
        points.add(new Point(p.x + links.get(0), p.y + links.get(1)));
        points.add(new Point(p.x + links.get(2), p.y + links.get(3)));

        return points;
    }

    private void calculate() {
        Map<Point, Set<Point>> map = new HashMap<>();
        Point start = null;

        for (int y = 0; y < input.size(); ++y) {
            for (int x = 0; x < input.get(y).length(); ++x) {
                char currentChar = input.get(y).charAt(x);
                Point p = new Point(x, y);
                map.put(p, getAdjacentPoints(p, currentChar));
                if (currentChar == 'S') {
                    start = p;
                }
            }
        }

        //remove points that are not connected to S from S
        Set<Point> newPoints = new HashSet<>();
        for (var p : map.get(start)) {
            if (map.getOrDefault(p, new HashSet<>()).contains(start)) {
                newPoints.add(p);
            }
        }
        map.put(start, newPoints);

        part1Result = traverse(map, start);
    }

    private long traverse(Map<Point, Set<Point>> map, Point start) {
        Set<Point> visited = new HashSet<>();
        Stack<Point> stack = new Stack<>();
        Boolean found = false;
        long steps = 1;

        stack.push(map.get(start).stream().findFirst().get());
        visited.add(start);

        while (!stack.isEmpty() && !found) {
            Point p = stack.pop();
            if (visited.contains(p)) {
                continue;
            }

            visited.add(p);
            ++steps;

            if (p.equals(start)) {
                found = true;
            }

            map.get(p).forEach(x -> { stack.add(x);});
        }

        return Math.ceilDiv(steps, 2);
    }
}