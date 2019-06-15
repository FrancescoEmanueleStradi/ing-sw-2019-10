package model.cards.weaponcards;

import model.Colour;
import model.cards.WeaponCard;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class THORAssertTests {
    @Test
    void THORCorrectConstructor() {
        WeaponCard t = new THOR();

        assertEquals("T.H.O.R.", t.getCardName());
        assertEquals(Colour.BLUE, t.getReloadCost()[0].getC());
        assertEquals(Colour.RED, t.getReloadCost()[1].getC());
        assertEquals("basic effect: Deal 2 damage to 1 target you can see.\n" +
                "with chain reaction: Deal 1 damage to a second target that your first target can see.\n" +
                "with high voltage: Deal 2 damage to a third target that your second target can see.\n" +
                "You cannot use this effect unless you first use the chain reaction.\n" +
                "Notes: This card constrains the order in which you can use its effects (most cards don't).\n" +
                "Also note that each target must be a different player.\n",
                t.getDescription());
    }
}