package model.cards.weaponcards;

import model.Colour;
import model.cards.WeaponCard;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ElectroscytheAssertTests {
    @Test
    void ElectroscytheCorrectConstructor() {
        WeaponCard e = new Electroscythe();

        assertEquals("Electroscythe", e.getCardName());
        assertEquals(Colour.BLUE, e.getReloadCost()[0].getC());
        assertEquals("basic mode: Deal 1 damage to every other player on your square.\n" +
                        "in reaper mode: Deal 2 damage to every other player on your square.\n",
                e.getDescription());
    }
}