package com.carniware.aoc.day13;

import static com.carniware.aoc.common.Helper.rotate;

import java.util.ArrayList;
import java.util.List;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.carniware.aoc.common.AoCDayAbstract;

@Component
@Order(13)
public class Day13 extends AoCDayAbstract {
    // placeholder for hamming distance and the location of the first difference
    // we are only interested in strings with a hamming distance of 1.
    record Hamming1(int distance, int location) {
    }

    public Day13() {
        this("src/main/java/com/carniware/aoc/day13/sample3.txt");
    }

    public Day13(String filename) {
        super(filename);
        calculate();
    }

    private int sumList(List<Integer> rows, List<Integer> columns) {
        var r =  rows.size() == 0 ? 0 : rows.stream().reduce(Integer::sum).get();
        var c= columns.size() == 0 ? 0 : columns.stream().reduce(Integer::sum).get();

        return c + (100 * r);
    }

    private void calculate() {
        List<Integer> rowCounts = new ArrayList<>();
        List<Integer> colCounts = new ArrayList<>();

        int start = 0;
        // add an empty line to the end of input to make the data consistent.
        input.addLast("");
        for (var i = 0; i < input.size(); ++i) {
            if (input.get(i).isEmpty()) {
                var rows = countMirroredRows(input.subList(start, i));
                if (rows > 0) {
                    rowCounts.add(rows);
                } else {
                    // Only perform the more expensive tranpose check if the row count is 0.
                    colCounts.add(countMirroredRows(rotate(input.subList(start, i))));
                }
                start = i + 1;
            }
        }

        part1Result = sumList(rowCounts, colCounts);

        rowCounts = new ArrayList<>();
        colCounts = new ArrayList<>();

        start = 0;
        for (var i = 0; i < input.size(); ++i) {
            if (input.get(i).isEmpty()) {
                var rows = countMirroredRowsSmudged(input.subList(start, i));
                if (rows > 0) {
                    rowCounts.add(rows);
                } else {
                    // Only perform the more expensive tranpose check if the row count is 0.
                    colCounts.add(countMirroredRowsSmudged(rotate(input.subList(start, i))));
                }
                start = i + 1;
            }
        }

        part2Result = sumList(rowCounts, colCounts);
    }

    private Boolean isValid(List<String> inputRows, int reflectionPoint, Boolean isSmudged) {
        // start comparing the remaining input lines to see if they all match, or
        // until one side runs out.
        int l = reflectionPoint;
        int r = reflectionPoint + 1;

        while (r < inputRows.size() && l >= 0) {
            if (!inputRows.get(l).equals(inputRows.get(r))) {
                if (!isSmudged) {
                    // We can only alter 1 symbol so skip this block if the input has already been
                    // altered.
                    var hamming = modifiedHammingDistance(inputRows.get(l), inputRows.get(r));
                    if (hamming.distance == 1) {
                        // If one of the symbols is smudged, we can try flipping the symbol type
                        var newInput = swapCharacterInList(inputRows, l, hamming.location);
                        return isValid(newInput, reflectionPoint, true);
                    }
                }
                return false;
            }
            --l;
            ++r;
        }
        // We need to make sure a valid solution contains an smudge.
        return true & isSmudged;
    }

    private ArrayList<String> swapCharacterInList(List<String> inputRows, int rowIndex, int colIndex) {
        var newInput = new ArrayList<>(inputRows);
        var currentChar = newInput.get(rowIndex).charAt(colIndex);
        var replacedString = new StringBuilder(newInput.get(rowIndex));
        replacedString.setCharAt(colIndex, currentChar == '.' ? '#' : '.');
        newInput.set(rowIndex, replacedString.toString());
        return newInput;
    }

    private int countMirroredRows(List<String> inputRows) {
        for (var i = 0; i < inputRows.size() - 1; ++i) {
            if (inputRows.get(i).equals(inputRows.get(i + 1))) {
                if (isValid(inputRows, i, true)) {
                    // located reflection point is the point between this line and the next
                    return i + 1;
                }
            }
        }

        return 0;
    }

    private int countMirroredRowsSmudged(List<String> inputRows) {
        // Search forward
        for (var i = 1; i < inputRows.size(); ++i) {
            var hamming = modifiedHammingDistance(inputRows.getFirst(), inputRows.get(i));

            var mid = Math.floorDiv(i, 2);
            if (hamming.distance == 1) {
                if (isValid(swapCharacterInList(inputRows, i, hamming.location), mid, true)) {
                    return mid + 1;
                }
            } else if (hamming.distance == 0) {
                if (isValid(inputRows, mid, false)) {
                    return mid + 1;
                }
            }
        }

        // Search backwards
        for (var i = inputRows.size() - 2; i >= 0; --i) {
            var hamming = modifiedHammingDistance(inputRows.getLast(), inputRows.get(i));

            var mid = inputRows.size() - 1 - Math.ceilDiv(inputRows.size() - i, 2);
            if (hamming.distance == 1) {
                if (isValid(swapCharacterInList(inputRows, i, hamming.location), mid, true)) {
                    return mid + 1;
                }
            } else if (hamming.distance == 0) {
                if (isValid(inputRows, mid, false)) {
                    return mid + 1;
                }
            }
        }

        return 0;
    }

    private Hamming1 modifiedHammingDistance(String input1, String input2) {
        // This implementation is limited to strings with a difference of 1.
        // also returns the location of the character that differs.

        Hamming1 returnValue = new Hamming1(-1, -1);
        if (input1.length() != input2.length()) {
            // Can't compute the hamming distance of unequal length strings
            return returnValue;
        }

        int distance = 0;
        int location = -1;
        for (var i = 0; i < input1.length(); ++i) {
            if (input1.charAt(i) != input2.charAt(i)) {
                ++distance;
                if (distance > 1) {
                    // the difference is more than 1 so we return early.
                    return returnValue;
                } else {
                    location = i;
                }
            }
        }

        returnValue = new Hamming1(distance, location);

        return returnValue;
    }
}