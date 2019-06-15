package model.cards.weaponcards;

import model.Colour;
import model.cards.WeaponCard;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ShockwaveAssertTests {
    @Test
    void ShockwaveCorrectConstructor() {
        WeaponCard s = new Shockwave();

        assertEquals("Shockwave", s.getCardName());
        assertEquals(Colour.YELLOW, s.getReloadCost()[0].getC());
        assertEquals("basic mode: Choose up to 3 targets on different squares, each exactly 1 move away. Deal 1 damage to each target.\n" +
                        "in tsunami mode: Deal 1 damage to all targets that are exactly 1 move away.\n",
                s.getDescription());
    }
}