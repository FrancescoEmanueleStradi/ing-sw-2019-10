package model.decks;

import model.Colour;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AmmoDeckAssertTests {
    @Test
    void AmmoDeckTest()  {
        AmmoDeck aDeck = new AmmoDeck();

        assertEquals(36, aDeck.getDeck().size());

        assertFalse(aDeck.getDeck().get(0).ispC());
        assertEquals(Colour.BLUE, aDeck.getDeck().get(0).getaC().get(0).getC());
        assertEquals(Colour.RED, aDeck.getDeck().get(0).getaC().get(1).getC());
        assertEquals(Colour.RED, aDeck.getDeck().get(0).getaC().get(2).getC());
        assertFalse(aDeck.getDeck().get(1).ispC());
        assertEquals(Colour.BLUE, aDeck.getDeck().get(1).getaC().get(0).getC());
        assertEquals(Colour.RED, aDeck.getDeck().get(1).getaC().get(1).getC());
        assertEquals(Colour.RED, aDeck.getDeck().get(1).getaC().get(2).getC());
        assertFalse(aDeck.getDeck().get(2).ispC());
        assertEquals(Colour.BLUE, aDeck.getDeck().get(2).getaC().get(0).getC());
        assertEquals(Colour.RED, aDeck.getDeck().get(2).getaC().get(1).getC());
        assertEquals(Colour.RED, aDeck.getDeck().get(2).getaC().get(2).getC());

        assertFalse(aDeck.getDeck().get(3).ispC());
        assertEquals(Colour.BLUE, aDeck.getDeck().get(3).getaC().get(0).getC());
        assertEquals(Colour.YELLOW, aDeck.getDeck().get(3).getaC().get(1).getC());
        assertEquals(Colour.YELLOW, aDeck.getDeck().get(3).getaC().get(2).getC());
        assertFalse(aDeck.getDeck().get(4).ispC());
        assertEquals(Colour.BLUE, aDeck.getDeck().get(4).getaC().get(0).getC());
        assertEquals(Colour.YELLOW, aDeck.getDeck().get(4).getaC().get(1).getC());
        assertEquals(Colour.YELLOW, aDeck.getDeck().get(4).getaC().get(2).getC());
        assertFalse(aDeck.getDeck().get(5).ispC());
        assertEquals(Colour.BLUE, aDeck.getDeck().get(5).getaC().get(0).getC());
        assertEquals(Colour.YELLOW, aDeck.getDeck().get(5).getaC().get(1).getC());
        assertEquals(Colour.YELLOW, aDeck.getDeck().get(5).getaC().get(2).getC());

        assertTrue(aDeck.getDeck().get(6).ispC());
        assertEquals(Colour.BLUE, aDeck.getDeck().get(6).getaC().get(0).getC());
        assertEquals(Colour.BLUE, aDeck.getDeck().get(6).getaC().get(1).getC());
        assertTrue(aDeck.getDeck().get(7).ispC());
        assertEquals(Colour.BLUE, aDeck.getDeck().get(7).getaC().get(0).getC());
        assertEquals(Colour.BLUE, aDeck.getDeck().get(7).getaC().get(1).getC());

        assertTrue(aDeck.getDeck().get(8).ispC());
        assertEquals(Colour.RED, aDeck.getDeck().get(8).getaC().get(0).getC());
        assertEquals(Colour.BLUE, aDeck.getDeck().get(8).getaC().get(1).getC());
        assertTrue(aDeck.getDeck().get(9).ispC());
        assertEquals(Colour.RED, aDeck.getDeck().get(9).getaC().get(0).getC());
        assertEquals(Colour.BLUE, aDeck.getDeck().get(9).getaC().get(1).getC());
        assertTrue(aDeck.getDeck().get(10).ispC());
        assertEquals(Colour.RED, aDeck.getDeck().get(10).getaC().get(0).getC());
        assertEquals(Colour.BLUE, aDeck.getDeck().get(10).getaC().get(1).getC());
        assertTrue(aDeck.getDeck().get(11).ispC());
        assertEquals(Colour.RED, aDeck.getDeck().get(11).getaC().get(0).getC());
        assertEquals(Colour.BLUE, aDeck.getDeck().get(11).getaC().get(1).getC());

        assertTrue(aDeck.getDeck().get(12).ispC());
        assertEquals(Colour.RED, aDeck.getDeck().get(12).getaC().get(0).getC());
        assertEquals(Colour.RED, aDeck.getDeck().get(12).getaC().get(1).getC());
        assertTrue(aDeck.getDeck().get(13).ispC());
        assertEquals(Colour.RED, aDeck.getDeck().get(13).getaC().get(0).getC());
        assertEquals(Colour.RED, aDeck.getDeck().get(13).getaC().get(1).getC());

        assertTrue(aDeck.getDeck().get(14).ispC());
        assertEquals(Colour.YELLOW, aDeck.getDeck().get(14).getaC().get(0).getC());
        assertEquals(Colour.BLUE, aDeck.getDeck().get(14).getaC().get(1).getC());
        assertTrue(aDeck.getDeck().get(15).ispC());
        assertEquals(Colour.YELLOW, aDeck.getDeck().get(15).getaC().get(0).getC());
        assertEquals(Colour.BLUE, aDeck.getDeck().get(15).getaC().get(1).getC());
        assertTrue(aDeck.getDeck().get(16).ispC());
        assertEquals(Colour.YELLOW, aDeck.getDeck().get(16).getaC().get(0).getC());
        assertEquals(Colour.BLUE, aDeck.getDeck().get(16).getaC().get(1).getC());
        assertTrue(aDeck.getDeck().get(17).ispC());
        assertEquals(Colour.YELLOW, aDeck.getDeck().get(17).getaC().get(0).getC());
        assertEquals(Colour.BLUE, aDeck.getDeck().get(17).getaC().get(1).getC());

        assertTrue(aDeck.getDeck().get(18).ispC());
        assertEquals(Colour.YELLOW, aDeck.getDeck().get(18).getaC().get(0).getC());
        assertEquals(Colour.RED, aDeck.getDeck().get(18).getaC().get(1).getC());
        assertTrue(aDeck.getDeck().get(19).ispC());
        assertEquals(Colour.YELLOW, aDeck.getDeck().get(19).getaC().get(0).getC());
        assertEquals(Colour.RED, aDeck.getDeck().get(19).getaC().get(1).getC());
        assertTrue(aDeck.getDeck().get(20).ispC());
        assertEquals(Colour.YELLOW, aDeck.getDeck().get(20).getaC().get(0).getC());
        assertEquals(Colour.RED, aDeck.getDeck().get(20).getaC().get(1).getC());
        assertTrue(aDeck.getDeck().get(21).ispC());
        assertEquals(Colour.YELLOW, aDeck.getDeck().get(21).getaC().get(0).getC());
        assertEquals(Colour.RED, aDeck.getDeck().get(21).getaC().get(1).getC());

        assertTrue(aDeck.getDeck().get(22).ispC());
        assertEquals(Colour.YELLOW, aDeck.getDeck().get(22).getaC().get(0).getC());
        assertEquals(Colour.YELLOW, aDeck.getDeck().get(22).getaC().get(1).getC());
        assertTrue(aDeck.getDeck().get(23).ispC());
        assertEquals(Colour.YELLOW, aDeck.getDeck().get(23).getaC().get(0).getC());
        assertEquals(Colour.YELLOW, aDeck.getDeck().get(23).getaC().get(1).getC());

        assertFalse(aDeck.getDeck().get(24).ispC());
        assertEquals(Colour.RED, aDeck.getDeck().get(24).getaC().get(0).getC());
        assertEquals(Colour.BLUE, aDeck.getDeck().get(24).getaC().get(1).getC());
        assertEquals(Colour.BLUE, aDeck.getDeck().get(24).getaC().get(2).getC());
        assertFalse(aDeck.getDeck().get(25).ispC());
        assertEquals(Colour.RED, aDeck.getDeck().get(25).getaC().get(0).getC());
        assertEquals(Colour.BLUE, aDeck.getDeck().get(25).getaC().get(1).getC());
        assertEquals(Colour.BLUE, aDeck.getDeck().get(25).getaC().get(2).getC());
        assertFalse(aDeck.getDeck().get(26).ispC());
        assertEquals(Colour.RED, aDeck.getDeck().get(26).getaC().get(0).getC());
        assertEquals(Colour.BLUE, aDeck.getDeck().get(26).getaC().get(1).getC());
        assertEquals(Colour.BLUE, aDeck.getDeck().get(26).getaC().get(2).getC());

        assertFalse(aDeck.getDeck().get(27).ispC());
        assertEquals(Colour.RED, aDeck.getDeck().get(27).getaC().get(0).getC());
        assertEquals(Colour.YELLOW, aDeck.getDeck().get(27).getaC().get(1).getC());
        assertEquals(Colour.YELLOW, aDeck.getDeck().get(27).getaC().get(2).getC());
        assertFalse(aDeck.getDeck().get(28).ispC());
        assertEquals(Colour.RED, aDeck.getDeck().get(28).getaC().get(0).getC());
        assertEquals(Colour.YELLOW, aDeck.getDeck().get(28).getaC().get(1).getC());
        assertEquals(Colour.YELLOW, aDeck.getDeck().get(28).getaC().get(2).getC());
        assertFalse(aDeck.getDeck().get(29).ispC());
        assertEquals(Colour.RED, aDeck.getDeck().get(29).getaC().get(0).getC());
        assertEquals(Colour.YELLOW, aDeck.getDeck().get(29).getaC().get(1).getC());
        assertEquals(Colour.YELLOW, aDeck.getDeck().get(29).getaC().get(2).getC());

        assertFalse(aDeck.getDeck().get(30).ispC());
        assertEquals(Colour.YELLOW, aDeck.getDeck().get(30).getaC().get(0).getC());
        assertEquals(Colour.BLUE, aDeck.getDeck().get(30).getaC().get(1).getC());
        assertEquals(Colour.BLUE, aDeck.getDeck().get(30).getaC().get(2).getC());
        assertFalse(aDeck.getDeck().get(31).ispC());
        assertEquals(Colour.YELLOW, aDeck.getDeck().get(31).getaC().get(0).getC());
        assertEquals(Colour.BLUE, aDeck.getDeck().get(31).getaC().get(1).getC());
        assertEquals(Colour.BLUE, aDeck.getDeck().get(31).getaC().get(2).getC());
        assertFalse(aDeck.getDeck().get(32).ispC());
        assertEquals(Colour.YELLOW, aDeck.getDeck().get(32).getaC().get(0).getC());
        assertEquals(Colour.BLUE, aDeck.getDeck().get(32).getaC().get(1).getC());
        assertEquals(Colour.BLUE, aDeck.getDeck().get(32).getaC().get(2).getC());

        assertFalse(aDeck.getDeck().get(33).ispC());
        assertEquals(Colour.YELLOW, aDeck.getDeck().get(33).getaC().get(0).getC());
        assertEquals(Colour.RED, aDeck.getDeck().get(33).getaC().get(1).getC());
        assertEquals(Colour.RED, aDeck.getDeck().get(33).getaC().get(2).getC());
        assertFalse(aDeck.getDeck().get(34).ispC());
        assertEquals(Colour.YELLOW, aDeck.getDeck().get(34).getaC().get(0).getC());
        assertEquals(Colour.RED, aDeck.getDeck().get(34).getaC().get(1).getC());
        assertEquals(Colour.RED, aDeck.getDeck().get(34).getaC().get(2).getC());
        assertFalse(aDeck.getDeck().get(35).ispC());
        assertEquals(Colour.YELLOW, aDeck.getDeck().get(35).getaC().get(0).getC());
        assertEquals(Colour.RED, aDeck.getDeck().get(35).getaC().get(1).getC());
        assertEquals(Colour.RED, aDeck.getDeck().get(35).getaC().get(2).getC());

        assertFalse(aDeck.getDeck().get(0).ispC());
        assertEquals(Colour.BLUE, aDeck.getDeck().get(0).getaC().get(0).getC());
        assertEquals(Colour.RED, aDeck.getDeck().get(0).getaC().get(1).getC());
        assertEquals(Colour.RED, aDeck.getDeck().get(0).getaC().get(2).getC());
        assertEquals(36, aDeck.getDeck().size());
    }
}