package model.cards.weaponcards;

import model.Colour;
import model.cards.WeaponCard;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TractorBeamAssertTests {
    @Test
    void TractorBeamCorrectConstructor() {
        WeaponCard tb = new TractorBeam();

        assertEquals("Tractor Beam", tb.getCardName());
        assertEquals(Colour.BLUE, tb.getReloadCost()[0].getC());
        assertEquals("basic mode: Move a target 0, 1, or 2 squares to a square you can see, and give it 1 damage.\n" +
                        "in punisher mode: Choose a target 0, 1, or 2 moves away from you. Move the target to your square and deal 3 damage to it.\n" +
                        "Notes: You can move a target even if you can't see it. The target ends up in a place where you can see and damage it.\n" +
                        "The moves do not have to be in the same direction.\n",
                tb.getDescription());

        assertEquals(0, tb.getNumOptionalEffect());
        assertTrue(tb.hasAlternateFireMode());
    }
}
