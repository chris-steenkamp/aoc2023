package com.carniware.aoc.day04;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.carniware.aoc.common.AoCDayAbstract;

@Component
@Order(4)
public class Day04 extends AoCDayAbstract {
    private List<Integer> extraCardsWon;
    private Map<Integer, Integer> mem;

    public Day04() {
        this("src/main/java/com/carniware/aoc/day04/input.txt");
    }

    public Day04(String filename) {
        super(filename);
        calculate();
    }

    private void calculate() {
        part1Result = 0;
        part2Result = 0;
        mem = new ConcurrentHashMap<>();

        extraCardsWon = new ArrayList<>();

        for (var line : input) {
            var numbers = line.split(":")[1].split("\\|");

            Set<Integer> winningNumbers = new HashSet<>(
                    Arrays.stream(numbers[0].split(" "))
                            .filter(x -> !x.isBlank())
                            .map(Integer::parseInt)
                            .toList());

            Set<Integer> cardNumbers = new HashSet<>(
                    Arrays.stream(numbers[1].split(" "))
                            .filter(x -> !x.isBlank())
                            .map(Integer::parseInt)
                            .toList());

            winningNumbers.retainAll(cardNumbers);
            extraCardsWon.add(winningNumbers.size());

            part1Result += Math.pow(2, winningNumbers.size() - 1);
        }

        for (int scratchCard = 0; scratchCard < input.size(); ++scratchCard) {
            part2Result += get_result(scratchCard);
        }
    }

    private int get_result(int scratchCard) {
        int cardsWon = extraCardsWon.get(scratchCard);

        if (cardsWon < 1){
            return 1;
        }
        if (mem.containsKey(scratchCard)) {
            return mem.get(scratchCard);
        }

        int result = 1;
        int cardsAdded = scratchCard + cardsWon;

        for (int i = cardsAdded; i > scratchCard; --i) {
            result += get_result(i);
        }

        mem.put(scratchCard, result);
        return result;
    }
}
