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

        List<Long> winners = new ArrayList<>(Collections.nCopies(times.size(), 0l));

        IntStream.range(0, times.size()).parallel().forEach(x -> {
            winners.set(x, getDistanceTravelled(times.get(x), distances.get(x)));
        });

        part1Result = winners.stream().reduce(1l, (t, u) -> t * u);

        part2Result = 0;

        var raceTime = Integer.parseInt(input.get(0).replace("Time:", "").replace(" ", ""));
        var maxDistance = Long.parseLong(input.get(1).replace("Distance:", "").replace(" ", ""));

        part2Result = getDistanceTravelled(raceTime, maxDistance);
    }

    private long getDistanceTravelled(long raceTime, long maxDistance) {
        long result = 0;
        for (long i = 1; i < raceTime-1; ++i) {
            long distanceTravelled = (raceTime - i) * i;

            if (distanceTravelled > maxDistance) {
                result += 1;
            }

        }
        return result;
    }
}
