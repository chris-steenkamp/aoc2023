package com.carniware.aoc.day04;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.carniware.aoc.common.AoCDay;
import com.carniware.aoc.common.Helper;

@Component
@Order(4)
public class Day04 implements AoCDay {
    private List<String> input;
    private int part1Result;
    private int part2Result;

    public Day04() {
        this("src/main/java/com/carniware/aoc/day04/input.txt");
    }

    public Day04(String filename) {
        input = Helper.readFile(filename);

        calculate();
    }

    private void calculate() {
        part1Result = 0;
        part2Result = 0;

        for (var line : input) {
            var numbers = line.split(":")[1].split("\\|");

            Set<Integer> winningNumbers = new HashSet<>(
                    Arrays.stream(numbers[0].split(" "))
                            .filter(x -> !x.isBlank())
                            .map(Integer::parseInt)
                            .toList());

            Set<Integer> cardNumbers = new HashSet<>(
                    Arrays.stream(numbers[1].split(" "))
                            .filter(x -> !x.isBlank())
                            .map(Integer::parseInt)
                            .toList());

            winningNumbers.retainAll(cardNumbers);
            part1Result += Math.pow(2, winningNumbers.size() - 1);
        }
    }

    public void runPart1() {
        System.out.println(String.format("%s part 1 answer: %d", this.getClass().getSimpleName(), part1Result));
    }

    public void runPart2() {
        System.out.println(String.format("%s part 2 answer: %d", this.getClass().getSimpleName(), part2Result));
    }
}
