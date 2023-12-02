package com.carniware.aoc.day01;

import static java.util.Map.entry;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import com.carniware.aoc.common.AoCDay;
import com.carniware.aoc.common.Helper;

public class Day01 implements AoCDay {
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

    public Day01() {
        this("src/main/java/com/carniware/aoc/day01/input.txt");
    }

    public Day01(String filename) {
        input = Helper.readFile(filename);

        part1Results = processPart1Data();
        part2Results = processPart2Data();
    }

    public Day01(String[] inputs) {
        input = List.of(inputs);

        part1Results = processPart1Data();
        part2Results = processPart2Data();
    }

    public void runPart1() {
        System.out.println(getPart1Result());
    }

    public void runPart2() {
        System.out.println(getPart2Result());
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
        Pattern regex = Pattern
                .compile("(one|1)|(two|2)|(three|3)|(four|4)|(five|5)|(six|6)|(seven|7)|(eight|8)|(nine|9)");
        var results = new ArrayList<Integer>();

        for (var calibrationInput : input) {
            /*For two overlapping numbers (like twone), the regex will consume the letters t, w, o leaving only n,e remaining to be parsed.
              This means that 21 will be interpreted as 22 (i.e. as if there was only a single digit in the input).
              To get around this, we can replace all the letters of each number word with their numeric representation surrounded by their
              first and last letters. This allows the number to be parsed correctly while ensuring any words that end before or start after
              remain intact. i.e. twone becomes t2one (which is correct already) but will be further reduced to t2o1e, which yields the same.
              The word twoneight becomes t2oneight -> t2o1eight -> t2o1e8t. Essentially only the last replacement can be ignored.
            */
            var matcher = regex.matcher(calibrationInput.replaceAll("one", "o1e").replaceAll("two", "t2o")
                    .replaceAll("three", "t3e").replaceAll("four", "f4r").replaceAll("five", "f5e")
                    .replaceAll("six", "s6x").replaceAll("seven", "s7n").replaceAll("eight", "e8t")
                    .replaceAll("nine", "n9e"));

            var currentValue = matcher.find() ? matcher.group() : 0;
            int calibrationValue = values.get(currentValue) * 10;
            while (matcher.find()) {
                currentValue = matcher.group();
            }

            calibrationValue += values.get(currentValue);

            results.add(calibrationValue);
        }

        return results;

    }
}
