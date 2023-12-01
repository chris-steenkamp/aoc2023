package com.carniware.aoc.day01;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

import com.carniware.aoc.common.Helper;

@Component
public class Go {
    private List<String> input;
    private List<Integer> results;

    public Go() {
        this("src/main/java/com/carniware/aoc/day01/input.txt");
    }

    public Go(String filename) {
        input = Helper.readFile(filename);

        results = processData();
    }

    public List<Integer> getResults() {
        return results;
    }

    public List<String> getInput() {
        return input;
    }

    public int getResult() {
        return results.stream().reduce(0, Integer::sum);

    }

    private List<Integer> processData() {
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
}
