package com.carniware.aoc.day14;

import static com.carniware.aoc.common.Helper.rotate;
import static com.carniware.aoc.common.Helper.CardinalDirection.turnLeft;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.carniware.aoc.common.AoCDayAbstract;
import com.carniware.aoc.common.Helper.CardinalDirection.HEADING;

@Component
@Order(14)
public class Day14 extends AoCDayAbstract {
    public Day14() {
        this("src/main/java/com/carniware/aoc/day14/input.txt");
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
        part1Result = rotateInput(input, 1).result;
    }

    private void calculatePart2() {
        part2Result = tiltPlatform(input, 1000000000);
    }

    private long tiltPlatform(List<String> input, long cycles) {
        RotatedInput rotated = new RotatedInput(0, input);
        Map<String, Long> mem = new LinkedHashMap<>();
        for (long c = 0; c < cycles; ++c) {
            var key = String.join("", rotated.input);
            if (mem.containsKey(key)) {
                var start = mem.get(key);
                var length = c - start;
                // We can skip most of the loop as it just repeats over and over again.
                // We only need to process the portion which remains after dividing by the cycle
                // length.
                c = cycles - ((cycles - start) % length);
            } else {
                mem.put(key, c);
            }
            rotated = rotateInput(rotated.input, 4);
        }

        return rotated.result;
    }

    record RotatedInput(long result, List<String> input) {
    }

    private RotatedInput rotateInput(List<String> input, int numberOfTimes) {
        HEADING tiltDirection = HEADING.N;
        long result = 0;
        for (var r = 0; r < numberOfTimes; ++r) {
            if (tiltDirection == HEADING.N || tiltDirection == HEADING.S) {
                // transpose the input to allow treating the columns as rows.
                // this makes it easier to handle north or south tilts.
                input = rotate(input);

                // We need to recalculate the load whenever we tilt N or S;
                // E and W tilts do not affect the load distribution in the vertical axis.
                result = 0;
            }

            if (tiltDirection == HEADING.N || tiltDirection == HEADING.E) {
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
                                // the load should only change if we are tilting N or S
                                // E and W tilts don't change the load in the vertical axis.
                                if (tiltDirection == HEADING.N) {
                                    result += newPosition + 1;
                                }
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
                                if (tiltDirection == HEADING.S) {
                                    result += newPosition + 1;
                                }
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
            if (tiltDirection == HEADING.N || tiltDirection == HEADING.S) {
                // rotate the input back to its original orientation.
                input = rotate(input, true);
            }
            tiltDirection = turnLeft(tiltDirection);
        }
        return new RotatedInput(result, input);
    }
}