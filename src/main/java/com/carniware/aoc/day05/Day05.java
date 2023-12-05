package com.carniware.aoc.day05;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.carniware.aoc.common.AoCDayAbstract;

@Component
@Order(5)
public class Day05 extends AoCDayAbstract {
    private Map<String, List<List<Long>>> maps;

    public Day05() {
        this("src/main/java/com/carniware/aoc/day05/input.txt");
    }

    public Day05(String filename) {
        super(filename);
        calculate();
    }

    private void calculate() {
        maps = new LinkedHashMap<>();
        var sectionName = "seeds";

        var seeds = Arrays.stream(
                input.get(0)
                        .split(":")[1]
                        .split(" "))
                .filter(x -> !x.isEmpty())
                .map(Long::parseLong)
                .toList();

        for (int i = 1; i < input.size(); ++i) {
            var line = input.get(i);
            if (line.isEmpty()) {
                sectionName = input.get(i + 1);
                maps.put(sectionName, new ArrayList<>());
                ++i;
                continue;
            }
            maps.get(sectionName).add(Arrays.stream(input.get(i)
                    .split(" "))
                    .filter(x -> !x.isEmpty())
                    .map(Long::parseLong)
                    .toList());

        }

        part1Result = seeds.stream()
                .map(x -> translate(x))
                .min(Long::compareTo)
                .get();
    }

    private long translate(long initialValue) {
        long translatedValue = initialValue;
        for (var map : maps.entrySet()) {
            for (var range : map.getValue()) {
                if (translatedValue >= range.get(1) && translatedValue < range.get(1) + range.get(2)) {
                    translatedValue = translatedValue - range.get(1) + range.get(0);
                    break;
                }
            }
        }

        return translatedValue;
    }
}
