package com.carniware.aoc.day19;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.carniware.aoc.common.AoCDayAbstract;

@Component
@Order(19)
public class Day19 extends AoCDayAbstract {
    public Day19() {
        this("src/main/java/com/carniware/aoc/day19/sample.txt");
    }

    public Day19(String filename) {
        super(filename);
        calculate();
    }

    public Day19(String[] inputs) {
        super(inputs);
        calculate();
    }

    record Part(long x, long m, long a, long s) {
        public long tally() {
            return x + m + a + s;
        }
    };

    record Rule(char operand1, char operator, long operand2, String targetRule) {
    };

    record Range(long lowerBound, long upperBound) {
        public long size() {
            return upperBound + 1 - lowerBound;
        }
    }

    record RangedPart(Range x, Range m, Range a, Range s) {
        public long tally() {
            return x.size() * m.size() * a.size() * s.size();
        }
    }

    private long parsePartString(String partString) {
        return Long.parseLong(partString.split("=")[1]);
    }

    private List<Rule> parseRuleString(String ruleString) {
        List<Rule> rules = new ArrayList<>();
        var ruleParts = ruleString.split(",");
        for (var rulePart : ruleParts) {
            var r = rulePart.split(":");

            if (r.length == 1) {
                rules.add(new Rule('-', '-', -1, r[0]));
            } else {
                var operands = r[0].splitWithDelimiters(">|<", 0);
                rules.add(new Rule(operands[0].charAt(0), operands[1].charAt(0), Long.parseLong(operands[2]), r[1]));
            }
        }
        return rules;
    }

    private void calculate() {
        List<Part> partsList = new ArrayList<>();
        Map<String, List<Rule>> rules = new HashMap<>();

        Boolean loadingRuleSpec = true;
        for (var line : input) {
            if (line.isEmpty()) {
                loadingRuleSpec = false;
                continue;
            }

            if (loadingRuleSpec) {
                var parts = line.split("\\{");
                rules.put(parts[0], parseRuleString(parts[1].replace("}", "")));
            } else {
                var parts = line.replaceAll("\\{|}", "").split(",");
                partsList.add(new Part(
                        parsePartString(parts[0]),
                        parsePartString(parts[1]),
                        parsePartString(parts[2]),
                        parsePartString(parts[3])));
            }
        }

        calculatePart1(partsList, rules);
        calculatePart2(rules);
    }

    private Boolean compareOperands(long operand1, long operand2, char comparator) {
        switch (comparator) {
            case '>':
                return operand1 > operand2;
            default:
                return operand1 < operand2;
        }
    }

    private Rule findNextRule(Part part, List<Rule> rules) {
        for (var rule : rules) {
            switch (rule.operand1) {
                case '-': {
                    return rule;
                }
                case 'x': {
                    if (compareOperands(part.x, rule.operand2, rule.operator))
                        return rule;
                    else
                        continue;
                }
                case 'm': {
                    if (compareOperands(part.m, rule.operand2, rule.operator))
                        return rule;
                    else
                        continue;
                }
                case 'a': {
                    if (compareOperands(part.a, rule.operand2, rule.operator))
                        return rule;
                    else
                        continue;
                }
                case 's': {
                    if (compareOperands(part.s, rule.operand2, rule.operator))
                        return rule;
                    else
                        continue;
                }
            }
        }

        return null;
    }

    private Boolean processPart(Part part, Map<String, List<Rule>> rules) {
        Stack<Rule> stack = new Stack<>();
        Boolean accepted = false;

        stack.push(findNextRule(part, rules.get("in")));

        while (!stack.isEmpty()) {
            var rule = stack.pop();
            if (rule.targetRule.equals("R")) {
                break;
            }

            if (rule.targetRule.equals("A")) {
                accepted = true;
                break;
            }

            stack.push(findNextRule(part, rules.get(rule.targetRule)));
        }
        return accepted;
    }

    private void calculatePart1(List<Part> partsList, Map<String, List<Rule>> rules) {
        List<Part> acceptedParts = new ArrayList<>();

        partsList.stream().forEach(x -> {
            if (processPart(x, rules)) {
                acceptedParts.add(x);
            }
        });

        part1Result = acceptedParts.stream().map(x -> x.tally()).reduce(Long::sum).get();
    }

    private long countRanges(RangedPart ranges, String ruleName, Map<String, List<Rule>> rules, int skip) {
        if (ruleName.equals("A")) {
            return ranges.tally();
        } else if (ruleName.equals("R")) {
            return 0;
        }

        int count = 0;
        for (var rule : rules.get(ruleName)) {
            if (count++ < skip) {
                continue;
            }
            Range rangeMatched = null;
            Range rangeNotMatched = null;
            switch (rule.operand1) {
                case '-': {
                    return countRanges(ranges, rule.targetRule, rules, 0);
                }
                case 'x': {
                    Range r = ranges.x;
                    if (rule.operator == '<') {
                        rangeMatched = new Range(r.lowerBound, rule.operand2 - 1);
                        rangeNotMatched = new Range(rule.operand2, r.upperBound);
                    } else {
                        rangeMatched = new Range(rule.operand2 + 1, r.upperBound);
                        rangeNotMatched = new Range(r.lowerBound, rule.operand2);
                    }

                    var matched = new RangedPart(rangeMatched, ranges.m, ranges.a, ranges.s);
                    var unmatched = new RangedPart(rangeNotMatched, ranges.m, ranges.a, ranges.s);
                    return countRanges(matched, rule.targetRule, rules, 0)
                            + countRanges(unmatched, ruleName, rules, skip + 1);
                }
                case 'm': {
                    Range r = ranges.m;
                    if (rule.operator == '<') {
                        rangeMatched = new Range(r.lowerBound, rule.operand2 - 1);
                        rangeNotMatched = new Range(rule.operand2, r.upperBound);
                    } else {
                        rangeMatched = new Range(rule.operand2 + 1, r.upperBound);
                        rangeNotMatched = new Range(r.lowerBound, rule.operand2);
                    }

                    var matched = new RangedPart(ranges.x, rangeMatched, ranges.a, ranges.s);
                    var unmatched = new RangedPart(ranges.x, rangeNotMatched, ranges.a, ranges.s);
                    return countRanges(matched, rule.targetRule, rules, 0) +
                            countRanges(unmatched, ruleName, rules, skip + 1);
                }
                case 'a': {
                    Range r = ranges.a;
                    if (rule.operator == '<') {
                        rangeMatched = new Range(r.lowerBound, rule.operand2 - 1);
                        rangeNotMatched = new Range(rule.operand2, r.upperBound);
                    } else {
                        rangeMatched = new Range(rule.operand2 + 1, r.upperBound);
                        rangeNotMatched = new Range(r.lowerBound, rule.operand2);
                    }

                    var matched = new RangedPart(ranges.x, ranges.m, rangeMatched, ranges.s);
                    var unmatched = new RangedPart(ranges.x, ranges.m, rangeNotMatched, ranges.s);
                    return countRanges(matched, rule.targetRule, rules, 0) +
                            countRanges(unmatched, ruleName, rules, skip + 1);
                }
                case 's': {
                    Range r = ranges.s;
                    if (rule.operator == '<') {
                        rangeMatched = new Range(r.lowerBound, rule.operand2 - 1);
                        rangeNotMatched = new Range(rule.operand2, r.upperBound);
                    } else {
                        rangeMatched = new Range(rule.operand2 + 1, r.upperBound);
                        rangeNotMatched = new Range(r.lowerBound, rule.operand2);
                    }

                    var matched = new RangedPart(ranges.x, ranges.m, ranges.a, rangeMatched);
                    var unmatched = new RangedPart(ranges.x, ranges.m, ranges.a, rangeNotMatched);
                    return countRanges(matched, rule.targetRule, rules, 0) +
                            countRanges(unmatched, ruleName, rules, skip + 1);
                }
            }
        }

        return -1;
    }

    private void calculatePart2(Map<String, List<Rule>> rules) {
        final int start = 1;
        final int end = 4000;

        var startingRange = new Range(start, end);

        RangedPart part = new RangedPart(startingRange, startingRange, startingRange, startingRange);

        part2Result = countRanges(part, "in", rules, 0);
    }
}