package model.cards.weaponcards;

import model.Colour;
import model.cards.WeaponCard;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FlamethrowerAssertTests {
    @Test
    void FlamethrowerCorrectConstructor() {
        WeaponCard f = new Flamethrower();

        assertEquals("Flamethrower", f.getCardName());
        assertEquals(Colour.RED, f.getReloadCost()[0].getC());
        assertEquals("basic mode: Choose a square 1 move away and possibly a second square 1 more move away in the same direction.\n" +
                        "On each square, you may choose 1 target and give it 1 damage.\n" +
                        "in barbecue mode: Choose 2 squares as above. Deal 2 damage to everyone on the first square and 1 damage to everyone on the second square.\n" +
                        "Notes: This weapon cannot damage anyone in your square. However, it can sometimes damage a target you can't see – the flame won't go through walls, but it will go through doors. Think of it as a straight-line\n" +
                        "blast of flame that can travel 2 squares in a cardinal direction.\n",
                f.getDescription());

        assertEquals(0, f.getNumOptionalEffect());
        assertTrue(f.hasAlternateFireMode());
    }
}
