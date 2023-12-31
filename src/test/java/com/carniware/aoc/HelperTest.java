package com.carniware.aoc;

import static com.carniware.aoc.common.Helper.rotate;
import static com.carniware.aoc.common.Helper.CardinalDirection.turnLeft;
import static com.carniware.aoc.common.Helper.CardinalDirection.turnRight;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.carniware.aoc.common.Helper.CardinalDirection.HEADING;

class HelperTest {
    @Test
    void test() {
        List<String> original = List.of("123", "456", "789");

        assertNotEquals(original,rotate(original));
        assertNotEquals(original,rotate(rotate(original)));
        assertNotEquals(original, rotate(rotate(rotate(original))));
        assertEquals(original, rotate(rotate(rotate(rotate(original)))));

        assertNotEquals(original,rotate(original, true));
        assertNotEquals(original,rotate(rotate(original, true), true));
        assertNotEquals(original, rotate(rotate(rotate(original, true), true), true));
        assertEquals(original, rotate(rotate(rotate(rotate(original, true), true), true), true));

        assertEquals(rotate(original, true), rotate(rotate(rotate(original))));

        assertEquals(HEADING.W, turnLeft(HEADING.N));
        assertEquals(HEADING.E, turnRight(HEADING.N));
        assertEquals(HEADING.N, turnLeft(turnLeft(turnLeft(turnLeft(HEADING.N)))));
        assertEquals(HEADING.N, turnRight(turnRight(turnRight(turnRight(HEADING.N)))));
    }
}
