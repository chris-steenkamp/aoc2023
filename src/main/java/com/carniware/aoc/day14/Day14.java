package com.carniware.aoc.day14;

import static com.carniware.aoc.common.Helper.transpose;

import java.util.List;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.carniware.aoc.common.AoCDayAbstract;

@Component
@Order(14)
public class Day14 extends AoCDayAbstract {
    public Day14() {
        this("src/main/java/com/carniware/aoc/day14/sample.txt");
    }

    public Day14(String filename) {
        super(filename);
        calculate();
    }

    public Day14(String[] inputs) {
        super(inputs);
        calculate();
    }

    private String swapChars(char[] input, int char1, int char2) {
        if (char1 != char2) {
            char temp = input[char2];
            input[char2] = input[char1];
            input[char1] = temp;
        }

        return new String(input);
    }

    private void calculate() {
        calculatePart1();
        calculatePart2();
    }

    private void calculatePart1() {
        part1Result = tiltPlatform(input, true, true, 0);
    }

    private long tiltPlatform(List<String> input, Boolean reverse, Boolean transpose, int cycles) {
        long result = 0;
        if (transpose) {
            // transpose the input to allow treating the columns as rows.
            // this makes it easier to handle north or south tilts.
            input = transpose(input);
        }

        if (reverse) {
            for (var y = 0; y < input.size(); ++y) {
                var line = input.get(y);
                // each round rock can move an amount equal to the number of spaces (.) that
                // appear before it + 1
                int count = 0;
                for (var i = line.length() - 1; i > -1; --i) {
                    switch (line.charAt(i)) {
                        case '.': {
                            ++count;
                            break;
                        }
                        case 'O': {
                            var newPosition = count + i;
                            line = swapChars(line.toCharArray(), i, newPosition);
                            result += newPosition + 1;
                            break;
                        }
                        case '#': {
                            count = 0;
                            break;
                        }
                    }
                }
                input.set(y, line);
            }
        } else {
            for (var y = 0; y < input.size(); ++y) {
                var line = input.get(y);
                // each round rock can move an amount equal to the number of spaces (.) that
                // appear before it + 1
                int count = 0;
                for (var i = 0; i < input.size(); ++i) {
                    switch (line.charAt(i)) {
                        case '.': {
                            ++count;
                            break;
                        }
                        case 'O': {
                            var newPosition = i - count;
                            line = swapChars(line.toCharArray(), i, newPosition);
                            result += newPosition + 1;
                            break;
                        }
                        case '#': {
                            count = 0;
                            break;
                        }
                    }
                }
                input.set(y, line);
            }
        }

        return result;
    }

    private void calculatePart2() {
        part2Result = tiltPlatform(input, false, true, 0);
    }
}