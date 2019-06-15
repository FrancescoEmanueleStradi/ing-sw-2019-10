package model.cards.weaponcards;

import model.Colour;
import model.cards.WeaponCard;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FurnaceAssertTests {
    @Test
    void FurnaceCorrectConstructor() {
        WeaponCard f = new Furnace();

        assertEquals("Furnace", f.getCardName());
        assertEquals(Colour.RED, f.getReloadCost()[0].getC());
        assertEquals(Colour.BLUE, f.getReloadCost()[1].getC());
        assertEquals("basic mode: Choose a room you can see, but not the room you are in. Deal 1 damage to everyone in that room.\n" +
                        "in cozy fire mode: Choose a square exactly one move away. Deal 1 damage and 1 mark to everyone on that square.",
                f.getDescription());
    }
}