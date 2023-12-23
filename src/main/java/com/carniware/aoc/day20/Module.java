package com.carniware.aoc.day20;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

enum PulseState {
    LOW,
    HIGH
}

record Pulse(Module sender, Module receiver, long id, PulseState state) {
}

public abstract class Module {
    String name;

    public Module(String name) {
        this.name = name;
    }

    public static Module fromString(String input) {
        var parts = input.splitWithDelimiters("&|%", 0);
        if (parts.length == 1) {
            switch (parts[0]) {
                case "broadcaster":
                    return new BroadcastModule();
                default:
                    return new OutputModule(parts[0]);
            }
        } else {
            switch (parts[1].charAt(0)) {
                case '%':
                    return new FlipFlopModule(parts[2]);
                case '&':
                    return new ConjunctionModule(parts[2]);
            }
        }
        return null;
    }

    public PulseState handlePulse(Pulse pulse) {
        // System.out.println(
        //         String.format("%s -%s-> %s", pulse.sender(), pulse.state().toString().toLowerCase(), pulse.receiver()));
        return pulse.state();
    }

    @Override
    public String toString() {
        return this.name;
    }
}

class ButtonModule extends Module {
    public ButtonModule() {
        super("button");
    }
}

class BroadcastModule extends Module {
    public BroadcastModule() {
        super("broadcaster");
    }
}

class OutputModule extends Module {
    public OutputModule(String name) {
        super(name);
    }
}

class FlipFlopModule extends Module {
    private Boolean on;

    public FlipFlopModule(String name) {
        super(name);
        this.on = false;
    }

    @Override
    public PulseState handlePulse(Pulse pulse) {
        super.handlePulse(pulse);
        PulseState returnPulse = null;
        if (pulse.state().equals(PulseState.LOW)) {
            // if the module was on, turn off and send a low pulse
            if (this.on) {
                returnPulse = PulseState.LOW;
            } else {
                returnPulse = PulseState.HIGH;
            }
            this.on = !this.on;
        }
        // System.out.println(String.format("%s state changed to %s", this, this.on ?
        // "on" : "off"));
        return returnPulse;
    }
}

class ConjunctionModule extends Module {
    private Map<String, PulseState> inputModules;

    public Map<String, PulseState> getInputModules() {
        return inputModules;
    }

    public ConjunctionModule(String name) {
        this(name, Set.of());
    }

    public ConjunctionModule(String name, Set<String> inputModules) {
        super(name);
        this.inputModules = new HashMap<>();
        inputModules.forEach(x -> this.inputModules.put(x, PulseState.LOW));
    }

    

    @Override
    public PulseState handlePulse(Pulse pulse) {
        super.handlePulse(pulse);
        inputModules.put(pulse.sender().name, pulse.state());
        boolean allHigh = inputModules.values().stream().allMatch(x -> x.equals(PulseState.HIGH));
        // System.out.println(String.format("%s %s all remembered inputs are high",
        // this, allHigh ? "" : "not "));
        return allHigh ? PulseState.LOW : PulseState.HIGH;
    }

}
