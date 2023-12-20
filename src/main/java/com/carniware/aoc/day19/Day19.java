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
            if (rule.targetRule.equals("R")){
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

        partsList.stream().parallel().forEach(x -> {
            if (processPart(x, rules)) {
                acceptedParts.add(x);
            }
        });

        part1Result = acceptedParts.stream().map(x -> x.tally()).reduce(Long::sum).get();
    }
}