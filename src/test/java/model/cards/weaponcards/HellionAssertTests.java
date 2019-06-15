package model.cards.weaponcards;

import model.Colour;
import model.cards.WeaponCard;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HellionAssertTests {
    @Test
    void HellionCorrectConstructor() {
        WeaponCard h = new Hellion();

        assertEquals("Hellion", h.getCardName());
        assertEquals(Colour.RED, h.getReloadCost()[0].getC());
        assertEquals(Colour.YELLOW, h.getReloadCost()[1].getC());
        assertEquals("basic mode: Deal 1 damage to 1 target you can see at least 1 move away. Then give 1 mark to that target and everyone else on that square.\n" +
                        "in nano-tracer mode: Deal 1 damage to 1 target you can see at least 1 move away. Then give 2 marks to that target and everyone else on that square.\n",
                h.getDescription());
    }
}