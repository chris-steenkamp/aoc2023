package com.carniware.aoc.day10;

import static java.util.Map.entry;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
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
            entry('S', List.of(0, 0, 0, 0)));

    record Point(int x, int y) {
    }

    public Day10() {
        this("src/main/java/com/carniware/aoc/day10/input.txt", 'L');
    }

    public Day10(String filename, char sValue) {
        super(filename);
        calculate(sValue);
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

    private void calculate(char sValue) {
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

        // remove points that are not connected to S from S
        Set<Point> newPoints = new HashSet<>();
        for (var p : map.get(start)) {
            if (map.getOrDefault(p, new HashSet<>()).contains(start)) {
                newPoints.add(p);
            }
        }
        map.put(start, newPoints);
        input.set(start.y, input.get(start.y).replace('S', sValue));

        var path = traverse(map, start);
        part1Result = Math.ceilDiv(path.size(), 2);
        part2Result = countCrossings(path);
    }

    private Set<Point> traverse(Map<Point, Set<Point>> map, Point start) {
        Stack<Point> stack = new Stack<>();
        Set<Point> path = new LinkedHashSet<>();
        Boolean found = false;

        stack.push(map.get(start).stream().findFirst().get());
        path.add(start);

        while (!stack.isEmpty() && !found) {
            Point p = stack.pop();
            if (path.contains(p)) {
                continue;
            }

            path.add(p);

            if (p.equals(start)) {
                found = true;
            }

            map.get(p).forEach(x -> {
                stack.add(x);
            });
        }

        return path;
    }

    private int countCrossings(Set<Point> path) {
        Map<Character, Integer> pipeValues = Map.ofEntries(
                entry('|', 1),
                entry('-', 0),
                entry('L', 1),
                entry('J', 1),
                entry('7', 0),
                entry('F', 0)
        );

        int crossings = 0;

        for (var y = 0; y < input.size(); ++y) {
            var line = input.get(y);
            int counter = 0;
            for (var x = 0; x < line.length(); ++x) {
                char currentChar = line.charAt(x);
                if (path.contains(new Point(x, y))) {
                    counter += pipeValues.get(currentChar);
                } else {
                    crossings += counter % 2 == 0 ? 0 : 1;
                }
            }
        }

        return crossings;
    }

    private void drawPath(Set<Point> path) {
        var minX = path.stream().min((p1, p2) -> Integer.compare(p1.x, p2.x)).get().x;
        var minY = path.stream().min((p1, p2) -> Integer.compare(p1.y, p2.y)).get().y;

        var maxX = path.stream().max((p1, p2) -> Integer.compare(p1.x, p2.x)).get().x + 1;
        var maxY = path.stream().max((p1, p2) -> Integer.compare(p1.y, p2.y)).get().y + 1;

        char[][] board = new char[maxY][maxX];

        for (var y = 0; y < maxY; ++y) {
            for (var x = 0; x < maxX; ++x) {
                Point p = new Point(x + minX, y + minY);
                char character = path.contains(p) ? input.get(p.y).charAt(p.x) : '.';

                board[y][x] = character;
                System.out.print(character);
            }
            System.out.println();
        }

        int i = 0;
    }
}