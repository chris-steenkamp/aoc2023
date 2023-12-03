package com.carniware.aoc.day03;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.carniware.aoc.common.AoCDay;
import com.carniware.aoc.common.Helper;

@Component
@Order(3)
public class Day03 implements AoCDay {
    private List<String> input;
    private List<Set<Integer>> symbolPositions;
    private List<Map<Integer, Set<Integer>>> potentialGears;
    private int part1Result;
    private int part2Result;

    public Day03() {
        this("src/main/java/com/carniware/aoc/day03/input.txt");
    }

    public Day03(String filename) {
        input = Helper.readFile(filename);
        findSymbols();
        calculate();
    }

    public int getPart1Result() {
        return part1Result;
    }

    public int getPart2Result() {
        return part2Result;
    }

    public void runPart1() {
        System.out.println(part1Result);
    }

    public void runPart2() {
        System.out.println(part2Result);
    }

    private void findSymbols() {
        this.symbolPositions = new ArrayList<>();
        this.potentialGears = new ArrayList<>();

        Pattern symbols = Pattern.compile("[^\\w\\s\\.]");
        Matcher m = symbols.matcher("");
        for (var line : input) {
            m.reset(line);
            Set<Integer> symbolPosition = new HashSet<>();
            Map<Integer, Set<Integer>> potentialGearPosition = new HashMap<>();
            while (m.find()) {
                symbolPosition.add(m.start());
                if ("*".equals(m.group())) {
                    potentialGearPosition.put(m.start(), new HashSet<>());
                }
            }
            symbolPositions.add(symbolPosition);
            potentialGears.add(potentialGearPosition);
        }
    }

    private Boolean findAdjacentSymbols(int x, int y, int partNumber) {
        int start = y == 0 ? 0 : y - 1;
        int end = y == input.size() - 1 ? input.size() - 1 : y + 1;

        for (int i = start; i <= end; ++i) {
            for (int j = x - 1; j <= x + 1; ++j) {
                if (symbolPositions.get(i).contains(j)) {
                    if (potentialGears.get(i).containsKey(j)) {
                        potentialGears.get(i).get(j).add(partNumber);
                    }
                    return true;
                }
            }
        }

        return false;
    }

    private void calculate() {
        int height = input.size();

        Pattern digits = Pattern.compile("\\d+");
        Matcher m = digits.matcher("");

        ArrayList<Integer> validPartNumbers = new ArrayList<>();

        for (int y_ = 0; y_ < height; ++y_) {
            final int y = y_;
            m.reset(input.get(y));

            while (m.find()) {
                var partNumber = Integer.parseInt(m.group());

                if (IntStream.range(m.start(), m.end()).anyMatch(x -> findAdjacentSymbols(x, y, partNumber))) {
                    validPartNumbers.add(partNumber);
                }
            }
        }

        part1Result = validPartNumbers.stream()
                .reduce(0, (t, u) -> t + u);

        part2Result = potentialGears.stream()
                .filter(k -> k.values().size() > 0)
                .flatMap(t -> t.values().stream())
                .filter(l -> l.size() == 2)
                .map(l -> l.stream().reduce(1, (t, u) -> t * u))
                .reduce(0, (t, u) -> t + u);
    }
}
