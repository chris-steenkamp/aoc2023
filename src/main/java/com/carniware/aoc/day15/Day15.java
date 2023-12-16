package com.carniware.aoc.day15;

import java.util.HashMap;
import java.util.LinkedHashMap;
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

    private void calculate() {
        Map<Integer, Map<String, Integer>> boxes = new HashMap<>();
        for (var line : input) {
            for (var step : line.split(",")) {
                part1Result += getHash(step);
                
                var stepParts = step.splitWithDelimiters("=|-", 0);

                var stepHash = getHash(stepParts[0]);
                // Luckily java has a LinkedHashMap which uses a linked list in the background
                // to manage the hash keys. This ensures that the order lenses are added and removed
                // from each box remains consistent. The regular HashMap type does not maintain insertion
                // order which means that our final list of lenses could be in the incorrect order.
                var lensesInBox = boxes.getOrDefault(stepHash, new LinkedHashMap<>());
                if (stepParts[1].equals("-")) {
                    lensesInBox.remove(stepParts[0]);
                } else {
                    lensesInBox.put(stepParts[0], Integer.parseInt(stepParts[2]));
                }
                boxes.put(stepHash, lensesInBox);
            }
        }

        for (var box : boxes.keySet()) {
            int lensePosition = 1;
            for (var lense : boxes.get(box).keySet()) {
                part2Result += (box + 1) * (lensePosition++) * boxes.get(box).get(lense);
            }
        }
    }
}