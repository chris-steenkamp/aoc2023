package com.carniware.aoc.day13;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.carniware.aoc.common.AoCDayAbstract;

@Component
@Order(13)
public class Day13 extends AoCDayAbstract {
    private Map<String, Integer> hammingMem;
    public Day13() {
        this("src/main/java/com/carniware/aoc/day13/sample.txt");
    }

    public Day13(String filename) {
        super(filename);
        calculate();
    }

    private void calculate() {
        List<Long> rowCounts = new ArrayList<>();
        List<Long> colCounts = new ArrayList<>();
        hammingMem = new HashMap<>();
        int start = 0;
        // add an empty line to the end of input to make the data consistent.
        input.addLast("");
        for (var i = 0; i < input.size(); ++i) {
            if (input.get(i).isEmpty()) {
                var rows = countMirroredRows(input.subList(start, i));
                if (rows > 0) {
                    rowCounts.add(rows);
                } else {
                    //Only perform the more expensive tranpose check if the row count is 0.
                    colCounts.add(countMirroredRows(transpose(input.subList(start, i))));
                }
                start = i + 1;
            }
        }
        var rows = rowCounts.stream().reduce(Long::sum).get();
        var cols = colCounts.stream().reduce(Long::sum).get();

        part1Result = cols + (100 * rows);
    }

    private Boolean isValid(List<String> inputRows, int reflectionPoint) {
        // reflection point already equals reflection point + 1 (that's how we got here)
        // so we start comparing the remaining input lines to see if they all match, or
        // until one side runs out.
        int l = reflectionPoint - 1;
        int r = reflectionPoint + 2;

        while (r < inputRows.size() && l >= 0) {
            if (!inputRows.get(l).equals(inputRows.get(r))) {
                return false;
            }
            --l;
            ++r;
        }
        return true;
    }

    private long countMirroredRows(List<String> inputRows) {
        long rows = 0;

        for (var i = 0; i < inputRows.size() - 1; ++i) {
            if (inputRows.get(i).equals(inputRows.get(i + 1))) {
                if (isValid(inputRows, i)) {
                    // greatest mirror image found so far is the point between this line and the
                    // next
                    rows = i + 1;
                    // test if the first reflection found is sufficient, otherwise search for a
                    // deeper one
                    break;
                }
            }
        }

        return rows;
    }

    private List<String> transpose(List<String> inputRows) {
        List<String> transposed = new ArrayList<>();

        int rows = inputRows.getFirst().length();
        int cols = inputRows.size();
        for (var i = 0; i < rows; ++i) {
            StringBuilder sb = new StringBuilder();
            for (var j = cols - 1; j >= 0; --j) {
                sb.append(inputRows.get(j).charAt(i));
            }

            transposed.add(sb.toString());
        }

        return transposed;
    }

    private int hammingDistance(String input1, String input2) {
        if (input1.length() != input2.length()) {
            // Can't compute the hamming distance of unequal length strings
            return -1;
        }

        var key = String.format("%s`%s", input1,input2);
        if (hammingMem.containsKey(key)) {
            return hammingMem.get(String.format("%s`%s", input1,input2));
        }

        var distance = 0;
        for (var i = 0; i < input1.length(); ++i) {
            if (input1.charAt(i) != input2.charAt(i)) {
                ++distance;
            }
        }

        hammingMem.put(key, distance);
        return distance;
    }
}