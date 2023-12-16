package com.carniware.aoc.day15;

import java.util.ArrayList;
import java.util.List;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.carniware.aoc.common.AoCDayAbstract;

@Component
@Order(15)
public class Day15 extends AoCDayAbstract {
    public Day15() {
        this("src/main/java/com/carniware/aoc/day15/input.txt");
    }

    public Day15(String filename) {
        super(filename);
        calculate();
    }

    public Day15(String[] inputs) {
        super(inputs);
        calculate();
    }

    private int getHash(String input) {
        // why java????
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
        List<Integer> hashes = new ArrayList<>();
        for (var line : input) {
            for (var step : line.split(",")) {
                hashes.add(getHash(step));
            }
        }
        part1Result = hashes.stream().reduce(Integer::sum).get();
    }
}