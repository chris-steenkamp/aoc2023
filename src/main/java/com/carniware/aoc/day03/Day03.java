package com.carniware.aoc.day03;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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

    public Day03() {
        this("src/main/java/com/carniware/aoc/day03/input.txt");
    }

    public Day03(String filename) {
        input = Helper.readFile(filename);
        this.symbolPositions = new ArrayList<>();
    }

    public Day03(String[] inputs) {
        input = List.of(inputs);
        this.symbolPositions = new ArrayList<>();
    }

    public void runPart1() {
        int height = input.size();

        Pattern digits = Pattern.compile("\\d+");
        Matcher m = digits.matcher("");

        findSymbols();

        ArrayList<Integer> validPartNumbers = new ArrayList<>();

        for (int y_ = 0; y_ < height; ++y_) {
            final int y = y_;
            m.reset(input.get(y));

            while (m.find()) {
                if (IntStream.range(m.start(), m.end()).anyMatch(x -> findAdjacentSymbols(x, y))) {
                    validPartNumbers.add(Integer.parseInt(m.group()));
                }
            }
        }

        System.out.println(validPartNumbers.stream().reduce(0,(t,u) -> t + u));
    }

    private void findSymbols() {
        Pattern symbols = Pattern.compile("[^\\w\\s\\.]");
        Matcher m = symbols.matcher("");
        for (var line : input) {
            m.reset(line);
            Set<Integer> symbolPosition = new HashSet<>();
            while (m.find()) {
                symbolPosition.add(m.start());
            }
            symbolPositions.add(symbolPosition);
        }
    }

    private Boolean findAdjacentSymbols(int x, int y) {
        int start = y == 0 ? 0 : y - 1;
        int end = y == input.size() - 1 ? input.size() - 1 : y + 1;

        for (int i = start; i <= end; ++i) {
            if (symbolPositions.get(i).contains(x) || symbolPositions.get(i).contains(x - 1)
                    || symbolPositions.get(i).contains(x + 1)) {
                return true;
            }
        }

        return false;
    }

    public void runPart2() {
        System.out.println("Part2 output");
    }
}
