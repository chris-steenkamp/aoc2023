package com.carniware.aoc.day20;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.carniware.aoc.common.AoCDayAbstract;
import com.carniware.aoc.common.Helper;

@Component
@Order(20)
public class Day20 extends AoCDayAbstract {
    public Day20() {
        this("src/main/java/com/carniware/aoc/day20/input.txt");
    }

    public Day20(String filename) {
        super(filename);
        calculate();
    }

    public Day20(String[] inputs) {
        super(inputs);
        calculate();
    }

    private void calculate() {
        Map<String, Set<String>> moduleConnections = new LinkedHashMap<>();
        Map<String, Module> modules = new HashMap<>();
        Map<String, Set<String>> moduleInputs = new HashMap<>();

        var button = new ButtonModule();
        modules.put(button.name, button);
        moduleConnections.put(button.name, Set.of("broadcaster"));

        for (var line : input) {
            var parts = line.split("( -> )");

            var module = Module.fromString(parts[0]);
            var connections = new HashSet<>(Arrays.stream(parts[1].split(",")).map(x -> x.trim()).toList());
            moduleConnections.put(module.name, connections);
            connections.stream().forEach(x -> {
                var currentInputs = moduleInputs.getOrDefault(x, new HashSet<>());
                currentInputs.add(module.name);
                moduleInputs.put(x, currentInputs);
                // if a module doesn't yet exist, add it as an output module.
                // If it exists on the LHS of a an input string then it will be
                // redefined as the correct type (flipflop, conjunction, broadcaster)
                modules.putIfAbsent(x, Module.fromString(x));
                moduleConnections.putIfAbsent(x, Set.of());
            });
            modules.put(module.name, module);
        }

        // Recreate each conjunction module with the correct list of input modules.
        // This will initialize the module's input memory to LOW for all inputs.
        for (var module : modules.keySet()) {
            var m = modules.get(module);
            if (m instanceof ConjunctionModule) {
                modules.put(m.name, new ConjunctionModule(m.name, moduleInputs.get(m.name)));
            }
        }

        // calculatePart1(modules, moduleConnections);
        calculatePart2(modules, moduleConnections, moduleInputs.get("nr"));
    }

    private void calculatePart1(Map<String, Module> modules, Map<String, Set<String>> moduleConnections) {
        Queue<Pulse> queue = new LinkedList<>();

        long totalHigh = 0;
        long totalLow = 0;
        for (var i = 0; i < 1000; ++i) {
            // System.out.println("--------------------------");
            queue.add(new Pulse(modules.get("button"), modules.get("broadcaster"), 0, PulseState.LOW));
            while (!queue.isEmpty()) {
                var pulse = queue.poll();
                var result = pulse.receiver().handlePulse(pulse);
                if (pulse.state().equals(PulseState.HIGH)) {
                    ++totalHigh;
                } else {
                    ++totalLow;
                }

                if (result != null) {
                    for (var connection : moduleConnections.get(pulse.receiver().name)) {
                        queue.add(new Pulse(pulse.receiver(), modules.get(connection), pulse.id() + 1, result));
                    }
                }
            }
        }

        part1Result = totalHigh * totalLow;
    }

    private void calculatePart2(Map<String, Module> modules, Map<String, Set<String>> moduleConnections,
            Set<String> modulesToWatch) {
        Queue<Pulse> queue = new LinkedList<>();
        Map<String, Long> cycles = new HashMap<>();

        long i = 0;
        if (modules.containsKey("rx")) {
            while (true) {
                ++i;
                queue.add(new Pulse(modules.get("button"), modules.get("broadcaster"), 0, PulseState.LOW));
                while (!queue.isEmpty()) {
                    var pulse = queue.poll();
                    var result = pulse.receiver().handlePulse(pulse);

                    if (result != null) {
                        for (var connection : moduleConnections.get(pulse.receiver().name)) {
                            queue.add(new Pulse(pulse.receiver(), modules.get(connection), pulse.id() + 1, result));
                        }
                    }
                    if (modulesToWatch.contains(pulse.sender().name)) {
                        if (pulse.state().equals(PulseState.HIGH)) {
                            if (!cycles.containsKey(pulse.sender().name)) {
                                System.out.println(String.format("Found minimum for %s (%d)", pulse.sender().name, i));
                                cycles.put(pulse.sender().name, i);
                            }
                        }
                    }
                }
                if (cycles.size() == modulesToWatch.size()) {
                    break;
                }
            }
        }
        part2Result = cycles.isEmpty() ? 0 : cycles.values().stream().reduce(Helper::lcm).get();
    }
}