package com.carniware.aoc.day01;

import static java.util.Map.entry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

import com.carniware.aoc.common.Helper;

@Component
public class Go {
    private static Map<String, Integer> values = Map.ofEntries(
            entry("1", 1),
            entry("2", 2),
            entry("3", 3),
            entry("4", 4),
            entry("5", 5),
            entry("6", 6),
            entry("7", 7),
            entry("8", 8),
            entry("9", 9),
            entry("one", 1),
            entry("two", 2),
            entry("three", 3),
            entry("four", 4),
            entry("five", 5),
            entry("six", 6),
            entry("seven", 7),
            entry("eight", 8),
            entry("nine", 9));
    private List<String> input;
    private List<Integer> part1Results;
    private List<Integer> part2Results;

    public Go() {
        this("src/main/java/com/carniware/aoc/day01/input.txt");
    }

    public Go(String filename) {
        input = Helper.readFile(filename);

        part1Results = processPart1Data();
        part2Results = processPart2Data();
    }

    public int getPart1Result() {
        return part1Results.stream().reduce(0, Integer::sum);

    }

    public int getPart2Result() {
        return part2Results.stream().reduce(0, Integer::sum);
    }

    private List<Integer> processPart1Data() {
        var results = new ArrayList<Integer>();

        for (var calibrationValue : input) {
            results.add(processCalibration(calibrationValue));
        }

        return results;
    }

    private int processCalibration(String value) {
        String reverse = new StringBuilder(value).reverse().toString();
        Pattern regex = Pattern.compile("\\d");

        var matcher = regex.matcher(value);
        var numberString = matcher.find() ? matcher.group() : "";

        matcher = regex.matcher(reverse);
        numberString += matcher.find() ? matcher.group() : "";

        return numberString.isBlank() ? 0 : Integer.parseInt(numberString);
    }

    private List<Integer> processPart2Data() {
        var results = new ArrayList<Integer>();

        for (var calibrationValue : input) {
            results.add(processCalibration2(calibrationValue));
        }

        return results;

    }

    private int processCalibration2(String value) {
        Pattern regex = Pattern
                .compile("(one|1)|(two|2)|(three|3)|(four|4)|(five|5)|(six|6)|(seven|7)|(eight|8)|(nine|9)");

        var matcher = regex.matcher(value.replaceAll("one", "one1one").replaceAll("two", "two2two").replaceAll("three", "three3three").replaceAll("four", "four4four").replaceAll("five", "five5five").replaceAll("six", "six6six").replaceAll("seven", "seven7seven").replaceAll("eight", "eight8eight").replaceAll("nine", "nine9nine"));
        
        var currentValue = matcher.find() ? matcher.group() : 0;
        int calibrationValue = values.get(currentValue) * 10;
        while (matcher.find()) {
            currentValue = matcher.group();
        }

        calibrationValue += values.get(currentValue);

        return calibrationValue;
    }
}
