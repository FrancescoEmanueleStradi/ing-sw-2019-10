package model.cards.weaponcards;

import model.Colour;
import model.cards.WeaponCard;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SledgehammerAssertTests {
    @Test
    void SledgehammerCorrectConstructor() {
        WeaponCard s = new Sledgehammer();

        assertEquals("Sledgehammer", s.getCardName());
        assertEquals(Colour.YELLOW, s.getReloadCost()[0].getC());
        assertEquals("basic mode: Deal 2 damage to 1 target on your square.\n" +
                        "in pulverize mode: Deal 3 damage to 1 target on your square, then move that target 0, 1, or 2 squares in one direction.\n" +
                        "Notes: Remember that moves go through doors, but not walls.\n",
                s.getDescription());

        assertEquals(0, s.getNumOptionalEffect());
        assertTrue(s.hasAlternateFireMode());
    }
}
