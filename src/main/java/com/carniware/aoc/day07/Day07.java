package com.carniware.aoc.day07;

import static java.util.Map.entry;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.PriorityQueue;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.carniware.aoc.common.AoCDayAbstract;

@Component
@Order(07)
public class Day07 extends AoCDayAbstract {
    class Hand implements Comparable<Hand> {
        private static Map<Character, Integer> cardValues = Map.ofEntries(
                entry('A', 13),
                entry('K', 12),
                entry('Q', 11),
                entry('J', 10),
                entry('T', 9),
                entry('9', 8),
                entry('8', 7),
                entry('7', 6),
                entry('6', 5),
                entry('5', 4),
                entry('4', 3),
                entry('3', 2),
                entry('2', 1));

        private Map<Character, Integer> counter;
        private int[] faceValues;
        private int bid;
        private String rawData;

        Hand(String input, int bid) {
            counter = new LinkedHashMap<>();
            faceValues = new int[input.length()];
            this.bid = bid;
            rawData = input;

            Arrays.fill(faceValues, 0);
            int index = 0;
            for (char c : input.toCharArray()) {
                counter.put(c, counter.getOrDefault(c, 0) + 1);
                faceValues[index++] = cardValues.get(c);
            }
        }

        public int getBid() {
            return bid;
        }

        public int getSize() {
            return counter.size();
        }

        public String getRawData() {
            return rawData;
        }

        public int get(char c) {
            return counter.getOrDefault(c, 0);
        }

        public int getRank() {
            switch (counter.size()) {
                case 1:
                    return 6;
                case 2:
                    if (counter.values().contains(1) || counter.values().contains(4))
                        return 5;
                    else
                        return 4;
                case 3:
                    if (counter.values().contains(3) || counter.values().contains(4))
                        return 3;
                    else
                        return 2;
                case 4:
                    return 1;
                case 5:
                    return 0;
            }

            return -1;
        }

        @Override
        public int compareTo(Hand o) {
            var c = Integer.compare(getRank(), o.getRank());
            if (c != 0)
                return c;

            for (int i = 0; i < faceValues.length; i++) {
                if (faceValues[i] > o.faceValues[i])
                    return 1;
                if (faceValues[i] < o.faceValues[i])
                    return -1;
                if (faceValues[i] == o.faceValues[i])
                    continue;
            }
            return 0;
        }
    }

    public Day07() {
        this("src/main/java/com/carniware/aoc/day07/input.txt");
    }

    public Day07(String filename) {
        super(filename);
        calculate();
    }

    public Day07(String[] inputs) {
        super(inputs);
        calculate();
    }

    private void calculate() {
        PriorityQueue<Hand> hands = new PriorityQueue<>();
        for (var line : input) {
            var parts = line.split(" ");
            hands.add(new Hand(parts[0], Integer.parseInt(parts[1])));
        }

        int multiplier = 1;
        part1Result = 0;

        while (!hands.isEmpty())
            part1Result += hands.poll().getBid() * multiplier++;

    }
}