package model.cards.weaponcards;

import model.Colour;
import model.cards.WeaponCard;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ZX2AssertTests {
    @Test
    void ZX2CorrectConstructor() {
        WeaponCard z = new ZX2();

        assertEquals("ZX-2", z.getCardName());
        assertEquals(Colour.YELLOW, z.getReloadCost()[0].getC());
        assertEquals(Colour.RED, z.getReloadCost()[1].getC());
        assertEquals("basic mode: Deal 1 damage and 2 marks to 1 target you can see.\n" +
                "in scanner mode: Choose up to 3 targets you can see and deal 1 mark to each.\n" +
                "Notes: Remember that the 3 targets can be in 3 different rooms.\n",
                z.getDescription());
    }
}