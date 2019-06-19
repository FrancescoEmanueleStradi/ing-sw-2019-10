package model.player;

import model.Colour;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AmmoCubeAssertTests {
    @Test
    void AmmoCubeRightColour()  {
        AmmoCube cube1 = new AmmoCube(Colour.BLUE);
        AmmoCube cube2 = new AmmoCube(Colour.YELLOW);
        AmmoCube cube3 = new AmmoCube(Colour.RED);

        assertEquals(Colour.BLUE, cube1.getC());
        assertEquals("BLUE", cube1.getC().getColourId());

        assertEquals(Colour.YELLOW, cube2.getC());
        assertEquals("YELLOW", cube2.getC().getColourId());

        assertEquals(Colour.RED, cube3.getC());
        assertEquals("RED", cube3.getC().getColourId());
    }
}