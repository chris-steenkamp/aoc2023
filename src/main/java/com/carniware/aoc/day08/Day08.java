package com.carniware.aoc.day08;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.carniware.aoc.common.AoCDayAbstract;

@Component
@Order(8)
public class Day08 extends AoCDayAbstract {
    public Day08() {
        this("src/main/java/com/carniware/aoc/day08/sample.txt");
    }

    public Day08(String filename) {
        super(filename);
        calculate();
    }

    private void calculate() {
        var directions = input.remove(0);
        Map<String, List<String>> graph = new HashMap<String, List<String>>();
        for (var i = 1; i < input.size(); ++i) {
            var line = input.get(i);
            var split = line.split(" = ");
            var values = split[1].replaceAll("\\(|\\)", "").split(", ");
            graph.put(split[0], List.of(values[0], values[1]));
        }

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
}