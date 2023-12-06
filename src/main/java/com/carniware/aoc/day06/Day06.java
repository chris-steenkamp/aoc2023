package com.carniware.aoc.day06;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.carniware.aoc.common.AoCDayAbstract;

@Component
@Order(6)
public class Day06 extends AoCDayAbstract {
    public Day06() {
        this("src/main/java/com/carniware/aoc/day06/input.txt");
    }

    public Day06(String filename) {
        super(filename);
        calculate();
    }

    private void calculate() {
        var times = Arrays.stream(
                input.get(0)
                        .replace("Time:", "")
                        .trim()
                        .split("\s+"))
                .filter(x -> !x.isEmpty())
                .map(x -> Integer.parseInt(x))
                .toList();

        var distances = Arrays.stream(
                input.get(1)
                        .replace("Distance:", "")
                        .trim()
                        .split("\s+"))
                .filter(x -> !x.isEmpty())
                .map(x -> Integer.parseInt(x))
                .toList();

        List<Integer> winners = new ArrayList<>(Collections.nCopies(times.size(), 0));

        IntStream.range(0, times.size()).parallel().forEach(x -> {
            var raceTime = times.get(x);
            for (var i = 0; i < raceTime; ++i) {
                var distanceTravelled = (raceTime - i) * i;

                if (distanceTravelled > distances.get(x)) {
                    winners.set(x, winners.get(x) + 1);
                }

            }
        });

        part1Result = winners.stream().reduce(1, (t, u) -> t * u);

        part2Result = 0;

        var raceTime = Integer.parseInt(input.get(0).replace("Time:", "").replace(" ", ""));
        var maxDistance = Long.parseLong(input.get(1).replace("Distance:", "").replace(" ", ""));
        for (long i = 0; i < raceTime; ++i) {
            long distanceTravelled = (raceTime - i) * i;

            if (distanceTravelled > maxDistance) {
                part2Result += 1;
            }

        }
    }
}
