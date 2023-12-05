package com.carniware.aoc.day02;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.carniware.aoc.common.AoCDayAbstract;

@Component
@Order(2)
public class Day02 extends AoCDayAbstract {
    private static int[] VALID_GAMES = { 12, 13, 14 };

    public Day02() {
        this("src/main/java/com/carniware/aoc/day02/input.txt");
    }

    public Day02(String filename) {
        super(filename);
        calculate();
    }

    public Day02(String[] inputs) {
        super(inputs);
        calculate();
    }

    private void calculate() {
        part1Result = 0;
        part2Result = 0;

        for (String line : input) {
            part1Result += parseLine(line);
            part2Result += parseLineV2(line);
        }
    }

    private int parseLine(String line) {
        var parts = line.split(":");

        var gameId = parseGameId(parts[0]);
        var selections = parseSelections(parts[1]);

        return areAllSelectionsValid(selections) ? gameId : 0;
    }

    private int parseLineV2(String line) {
        var parts = line.split(":");

        var selections = parseSelections(parts[1]);

        return Arrays.stream(getMinimumCubes(selections)).reduce(1, (x, y) -> x * y);
    }

    private Boolean areAllSelectionsValid(List<int[]> selections) {
        for (var set: selections) {
            if (set[0] == 0)
                return false;
        }
        return true;
    }

    private int[] getMinimumCubes(List<int[]> selections) {
        int[] results = new int[3];

        for (var set: selections) {
            if (set[1] > results[0])
                results[0] = set[1]; // red (1)
            if (set[2] > results[1])
                results[1] = set[2]; // green (2)
            if (set[3] > results[2])
                results[2] = set[3]; // blue (3)
        }

        return results;
    }

    private List<int[]> parseSelections(String string) {
        List<int[]> selections = new ArrayList<int[]>();

        for (var selection : string.split(";")) {
            selections.add(parseSelectedSet(selection));
        }
        return selections;
    }

    private int[] parseSelectedSet(String input) {
        // store results in a 4-tuple, where 1 indicates if the set is valid,
        // 2 indicates the number of red, 3 is green and 4 is blue.
        int[] results = new int[4];

        for (var sets : input.split(",")) {
            var set = sets.trim().split(" ");
            switch (set[1].trim()) {
                case "red":
                    results[1] = Integer.parseInt(set[0]);
                    break;
                case "green":
                    results[2] = Integer.parseInt(set[0]);
                    break;
                case "blue":
                    results[3] = Integer.parseInt(set[0]);
                    break;
            }
        }

        if (results[1] <= VALID_GAMES[0] && results[2] <= VALID_GAMES[1] && results[3] <= VALID_GAMES[2]) {
            results[0] = 1;
        }

        return results;
    }

    private int parseGameId(String string) {
        return Integer.parseInt(string.split(" ")[1]);
    }
}
