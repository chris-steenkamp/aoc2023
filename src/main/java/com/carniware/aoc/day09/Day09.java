package com.carniware.aoc.day09;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.carniware.aoc.common.AoCDayAbstract;

@Component
@Order(9)
public class Day09 extends AoCDayAbstract {
    public Day09() {
        this("src/main/java/com/carniware/aoc/day09/sample.txt");
    }

    public Day09(String filename) {
        super(filename);
        calculate();
    }

    private void calculate() {
        List<Long> nextValues = new ArrayList<>();
        List<Long> previousValues = new ArrayList<>();
        for (var inputs : input) {
            List<Long> numbers = Arrays.stream(inputs.split(" ")).map(Long::parseLong).toList();

            nextValues.add(predictNext(numbers));
            previousValues.add(predictPrevious(numbers));
        }

        part1Result = nextValues.stream().reduce(Long::sum).get();
        part2Result = previousValues.stream().reduce(Long::sum).get();
    }

    private Long predictNext(List<Long> numbers) {
        List<List<Long>> diffs = new ArrayList<>();
        List<Long> currentList = numbers;
        do {
            List<Long> diff = new ArrayList<>();
            for (var i = 0; i < currentList.size() - 1; i++) {
                diff.add(currentList.get(i + 1) - currentList.get(i));
            }
            diffs.add(diff);
            currentList = diff;
        } while (!currentList.stream().allMatch(x -> x == 0));

        long prediction = 0;
        // we can skip the list of only zeroes and start with the last
        // value of the second last list of numbers.
        for (var i = diffs.size() - 2; i >= 0; --i) {
            prediction += diffs.get(i).getLast();
        }

        return numbers.getLast() + prediction;
    }

    private Long predictPrevious(List<Long> numbers) {
        List<List<Long>> diffs = new ArrayList<>();
        List<Long> currentList = numbers;
        do {
            List<Long> diff = new ArrayList<>();
            for (var i = 0; i < currentList.size() - 1; i++) {
                diff.add(currentList.get(i + 1) - currentList.get(i));
            }
            diffs.add(diff);
            currentList = diff;
        } while (!currentList.stream().allMatch(x -> x == 0));

        long prediction = 0;
        for (var i = diffs.size() - 2; i >= 0; --i) {
            prediction = diffs.get(i).getFirst() - prediction;
        }

        return numbers.getFirst() - prediction;
    }
}