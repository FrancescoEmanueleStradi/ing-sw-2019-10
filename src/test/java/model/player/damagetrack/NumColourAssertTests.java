package model.player.damagetrack;

import model.Colour;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class NumColourAssertTests {
    @Test
    void NumColourGetterSetterTest() {
        NumColour nc = new NumColour(Colour.BLUE);

        assertEquals(Colour.BLUE, nc.getC());
        assertEquals(0, nc.getNum());
        assertFalse(nc.isTie());
        assertTrue(nc.getcTie().isEmpty());

        nc.setC(Colour.YELLOW);
        assertEquals(Colour.YELLOW, nc.getC());
        nc.setNum(5);
        assertEquals(5, nc.getNum());
        nc.addNum();
        assertEquals(6, nc.getNum());
        nc.setTie(true);
        assertTrue(nc.isTie());

        List<Colour> colours = new LinkedList<>();
        colours.add(Colour.BLACK);
        colours.add(Colour.GREEN);
        colours.add(Colour.BLUE);
        nc.setcTie(colours);
        assertEquals(3, nc.getcTie().size());
        assertEquals(Colour.BLACK, nc.getcTie().get(0));
        assertEquals(Colour.GREEN, nc.getcTie().get(1));
        assertEquals(Colour.BLUE, nc.getcTie().get(2));
    }

    @Test
    void ColourDifferenceTest() {
        NumColour nc1 = new NumColour(Colour.BLUE);
        NumColour nc2 = new NumColour(Colour.YELLOW);

        nc1.setNum(3);
        nc2.setNum(2);
        assertEquals(3, nc1.colourDifference(nc2));
        assertEquals(3, nc2.colourDifference(nc1));

        nc2.addNum();
        assertEquals(3, nc1.colourDifference(nc2));
        assertEquals(3, nc2.colourDifference(nc1));
    }
}