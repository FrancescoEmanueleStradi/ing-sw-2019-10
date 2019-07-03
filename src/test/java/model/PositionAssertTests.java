package model;

import model.board.Position;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PositionAssertTests {
    @Test
    void PositionTest() {
        Position pos1 = new Position(0, 0);

        assertEquals(0, pos1.getX());
        assertEquals(0, pos1.getY());

        Position pos2 = new Position(1, 3);

        assertEquals(1, pos2.getX());
        assertEquals(3, pos2.getY());
    }
}