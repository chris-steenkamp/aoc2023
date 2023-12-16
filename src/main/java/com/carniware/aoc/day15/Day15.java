package com.carniware.aoc.day15;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.carniware.aoc.common.AoCDayAbstract;

@Component
@Order(15)
public class Day15 extends AoCDayAbstract {
    public Day15() {
        this("src/main/java/com/carniware/aoc/day15/sample.txt");
    }

    public Day15(String filename) {
        super(filename);
        calculate();
    }

    private int getHash(String input) {
        // why must you be like this java????
        var wrapper = new Object() {
            int current = 0;
        };
        input.chars().forEach(x -> {
            wrapper.current += x;
            wrapper.current *= 17;
            wrapper.current %= 256;
        });

        return wrapper.current;
    }

    record Step(String label, char operation, int lense) {
    }

    private void calculate() {
        List<Integer> hashes = new ArrayList<>();
        List<Step> steps = new ArrayList<>();
        Map<Integer, Map<String, Integer>> boxes = new LinkedHashMap<>();
        for (var line : input) {
            for (var step : line.split(",")) {
                hashes.add(getHash(step));
                var stepParts = step.splitWithDelimiters("=|-", 0);
                Step s = new Step(stepParts[0], stepParts[1].charAt(0),
                        stepParts.length > 2 ? Integer.parseInt(stepParts[2]) : -1);

                var stepHash = getHash(s.label);
                var lensesInBox = boxes.getOrDefault(stepHash, new LinkedHashMap<>());
                if (s.operation == '-') {
                    lensesInBox.remove(s.label);
                } else if (s.operation == '=') {
                    lensesInBox.put(s.label, s.lense);
                }
                boxes.put(stepHash, lensesInBox);
            }
        }
        part1Result = hashes.stream().reduce(Integer::sum).get();

        for (var box : boxes.keySet()) {
            int lensePosition = 1;
            for (var lense : boxes.get(box).keySet()) {
                part2Result += (box + 1) * (lensePosition++) * boxes.get(box).get(lense);
            }
        }
    }
}