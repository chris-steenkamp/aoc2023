package com.carniware.aoc.day08;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.carniware.aoc.common.AoCDayAbstract;

@Component
@Order(8)
public class Day08 extends AoCDayAbstract {
    public Day08() {
        this("src/main/java/com/carniware/aoc/day08/input.txt", 2);
    }

    public Day08(String filename, int partToRun) {
        super(filename);
        calculate(partToRun);
    }

    private void calculate(int partToRun) {
        var directions = input.remove(0);
        Map<String, List<String>> graph = new HashMap<String, List<String>>();
        for (var i = 1; i < input.size(); ++i) {
            var line = input.get(i);
            var split = line.split(" = ");
            var values = split[1].replaceAll("\\(|\\)", "").split(", ");
            graph.put(split[0], List.of(values[0], values[1]));
        }

        switch (partToRun) {
            case 1:
                calculatePart1(graph, directions);
                break;
            default:
                calculatePart2(graph, directions);
                break;
        }
    }

    private void calculatePart1(Map<String, List<String>> graph, String directions) {
        Boolean found = false;
        var next = "AAA";
        var end = "ZZZ";

        while (!found) {
            for (var i = 0; i < directions.length(); ++i) {
                ++part1Result;
                var direction = directions.charAt(i);
                next = graph.get(next).get(direction == 'L' ? 0 : 1);
                if (next.equals(end)) {
                    found = true;
                    break;
                }
            }
        }
    }

    private static long lcm(long first, long second) {
        if (first == 0 || second == 0) {
            return 0;
        }

        var highest = Math.max(Math.abs(first), Math.abs(second));
        var smallest = Math.min(Math.abs(first), Math.abs(second));
        var lcm = highest;

        while (lcm % smallest != 0) {
            lcm += highest;
        }

        return lcm;
    }

    private void calculatePart2(Map<String, List<String>> graph, String directions) {
        Set<String> nextNodes = new HashSet<>(graph.keySet().stream().filter(x -> x.endsWith("A")).toList());
        Map<String, Long> counts = new HashMap<>();
        while (!nextNodes.isEmpty()) {;
            for (var i = 0; i < directions.length(); ++i) {
                ++part2Result;
                var direction = directions.charAt(i);
                Set<String> newNodes = new HashSet<>();

                nextNodes.forEach(x -> {
                    newNodes.add(graph.get(x).get(direction == 'L' ? 0 : 1));
                });

                for (var completed : newNodes.stream().filter(x -> x.endsWith("Z")).toList()) {
                    // if any of the new nodes end with a Z then we have found the path to that node.
                    // continuing from the end node will just loop back again in the same number of steps
                    // so we store the length of the path and remove the node from further processing.
                    counts.put(completed, part2Result);
                    newNodes.remove(completed);
                }

                nextNodes = newNodes;
            }
        }

        part2Result = counts.values().stream().reduce(Day08::lcm).get();
    }
}