package com.carniware.aoc.template;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.carniware.aoc.common.AoCDayAbstract;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class DayN extends AoCDayAbstract {
    public DayN() {
        this("src/main/java/com/carniware/aoc/dayn/input.txt");
    }

    public DayN(String filename) {
        super(filename);
    }

    public DayN(String[] inputs) {
        super(inputs);
    }
}
