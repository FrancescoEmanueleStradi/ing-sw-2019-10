package model.cards.weaponcards;

import model.Colour;
import model.cards.WeaponCard;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class VortexCannonAssertTests {
    @Test
    void VortexCannonCorrectConstructor() {
        WeaponCard vc = new VortexCannon();

        assertEquals("Vortex Cannon", vc.getCardName());
        assertEquals(Colour.RED, vc.getReloadCost()[0].getC());
        assertEquals(Colour.BLUE, vc.getReloadCost()[1].getC());
        assertEquals("basic effect: Choose a square you can see, but not your square. Call it \"the vortex\".\n" +
                        "Choose a target on the vortex or 1 move away from it. Move it onto the vortex and give it 2 damage.\n" +
                        "with black hole: Choose up to 2 other targets on the vortex or 1 move away from it. Move them onto the vortex and give them each 1 damage.\n" +
                        "Notes: The 3 targets must be different, but some might start on the same square.\n" +
                        "It is legal to choose targets on your square, on the vortex, or even on squares you can't see. They all end up on the vortex.\n",
                vc.getDescription());

        assertEquals(1, vc.getNumOptionalEffect());
        assertFalse(vc.hasAlternateFireMode());
    }
}
