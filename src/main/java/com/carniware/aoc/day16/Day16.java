package com.carniware.aoc.day16;

import static com.carniware.aoc.common.Helper.CardinalDirection.turnLeft;
import static com.carniware.aoc.common.Helper.CardinalDirection.turnRight;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.carniware.aoc.common.AoCDayAbstract;
import com.carniware.aoc.common.Helper.CardinalDirection.HEADING;

@Component
@Order(16)
public class Day16 extends AoCDayAbstract {
    public Day16() {
        this("src/main/java/com/carniware/aoc/day16/input.txt");
    }

    public Day16(String filename) {
        super(filename);
        calculate();
    }

    public Day16(String[] inputs) {
        super(inputs);
        calculate();
    }

    record Point2(int x, int y) {
    }

    class Beam {
        int x;
        int y;
        HEADING heading;
        int moves;

        Beam(int x, int y, HEADING heading) {
            this(x, y, heading, 0);
        }

        Beam(int x, int y, HEADING heading, int moves) {
            this.x = x;
            this.y = y;
            this.heading = heading;
            this.moves = 0;
        }

        public void move() {
            // Move the point 1 point in its current heading
            if (heading == HEADING.E) {
                x += 1;
            } else if (heading == HEADING.W) {
                x -= 1;
            } else if (heading == HEADING.N) {
                y -= 1;
            } else if (heading == HEADING.S) {
                y += 1;
            }

            ++moves;
        }

        public Boolean withinBounds(int x, int y) {
            return withinBounds(0, 0, x, y);
        }

        public Boolean withinBounds(int x1, int y1, int x2, int y2) {
            if (x < x1 || x >= x2 || y < y1 || y >= y2) {
                return false;
            }

            return true;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }

            if (!(obj instanceof Beam)) {
                return false;
            }

            Beam o = (Beam) obj;

            return this.x == o.x && this.y == o.y && this.heading.equals(o.heading);
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y, heading);
        }
    }

    private void calculate() {
        Set<Beam> beams = new HashSet<>();
        Set<Point2> energised = new HashSet<>();
        Map<String, Integer> mem = new HashMap<>();
        Boolean isCycle = false;

        beams.add(new Beam(0, 0, HEADING.E));
        int maxX = input.getFirst().length();
        int maxY = input.size();
        while (!beams.isEmpty()) {
            Set<Beam> newBeams = new HashSet<>();
            // calculate next position of each beam
            if (!isCycle) {
                if (beams.stream().allMatch(x -> mem.containsKey(x.toString()))) {
                    isCycle = true;
                }
            }
            for (Beam b : beams) {
                if (!isCycle) {
                    if (!mem.containsKey(b.toString())) {
                        mem.put(b.toString(), b.moves);
                    }
                }
                energised.add(new Point2(b.x, b.y));
                // determine the new heading of the beam
                char current = input.get(b.y).charAt(b.x);
                Beam p1 = null;
                Beam p2 = null;

                switch (current) {
                    case '.': {
                        p1 = b;
                        break;
                    }
                    case '|': {
                        if (b.heading == HEADING.E || b.heading == HEADING.W) {
                            if (!isCycle) {
                                p1 = new Beam(b.x, b.y, turnLeft(b.heading));
                                p2 = new Beam(b.x, b.y, turnRight(b.heading));
                            }
                        } else {
                            p1 = b;
                        }
                        break;
                    }
                    case '-': {
                        if (b.heading == HEADING.N || b.heading == HEADING.S) {
                            if (!isCycle) {
                                p1 = new Beam(b.x, b.y, turnLeft(b.heading));
                                p2 = new Beam(b.x, b.y, turnRight(b.heading));
                            }
                        } else {
                            p1 = b;
                        }
                        break;
                    }
                    case '\\': {
                        if (b.heading == HEADING.E || b.heading == HEADING.W) {
                            b.heading = turnRight(b.heading);
                        } else if (b.heading == HEADING.N || b.heading == HEADING.S) {
                            b.heading = turnLeft(b.heading);
                        }
                        p1 = b;
                        break;
                    }
                    case '/': {
                        if (b.heading == HEADING.E || b.heading == HEADING.W) {
                            b.heading = turnLeft(b.heading);
                        } else if (b.heading == HEADING.N || b.heading == HEADING.S) {
                            b.heading = turnRight(b.heading);
                        }
                        p1 = b;
                        break;
                    }
                }

                if (p1 != null) {
                    p1.move();
                    if (p1.withinBounds(maxX, maxY)) {
                        newBeams.add(p1);
                    }
                }

                if (p2 != null) {
                    p2.move();
                    if (p2.withinBounds(maxX, maxY)) {
                        newBeams.add(p2);
                    }
                }
            }

            beams = newBeams;
        }

        part1Result = energised.size();
    }
}