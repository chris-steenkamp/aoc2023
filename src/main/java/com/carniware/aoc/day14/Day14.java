package com.carniware.aoc.day14;

import static com.carniware.aoc.common.Helper.transpose;

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

    private void calculate() {
        // transpose the input to allow treating the columns as rows.
        var transposed = transpose(input);
        for (var line : transposed) {
            // each round rock can move an amount equal to the number of spaces (.) that appear before it + 1
            int count = 0;
            for (var i = line.length() - 1; i > -1; --i) {
                switch (line.charAt(i)) {
                    case '.': {
                        ++count;
                        break;
                    }
                    case 'O': {
                        part1Result += count + i + 1;
                        break;
                    }
                    case '#': {
                        count = 0;
                        break;
                    }
                }
            }
        }
    }
}