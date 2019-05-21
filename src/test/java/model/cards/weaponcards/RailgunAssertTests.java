package model.cards.weaponcards;

import model.Colour;
import model.cards.WeaponCard;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RailgunAssertTests {
    @Test
    void RailgunCorrectConstructor() {
        WeaponCard r = new Railgun();

        assertEquals("Railgun", r.getCardName());
        assertEquals(Colour.YELLOW, r.getReloadCost()[0].getC());
        assertEquals(Colour.YELLOW, r.getReloadCost()[1].getC());
        assertEquals(Colour.BLUE, r.getReloadCost()[2].getC());
        assertEquals(r.getReloadCost().length, 3);
        assertEquals("basic mode: Choose a cardinal direction and 1 target in that direction. Deal 3 damage to it.\n" +
                        "in piercing mode: Choose a cardinal direction and 1 or 2 targets in that direction. Deal 2 damage to each.\n" +
                        "Notes: Basically, you're shooting in a straight line and ignoring walls.\n" +
                        "You don't have to pick a target on the other side of a wall – it could even be someone on your own square – but shooting through walls sure is fun.\n" +
                        "There are only 4 cardinal directions. You imagine facing one wall or door, square-on, and firing in that direction. Anyone on a square in that direction (including yours) is a valid target.\n" +
                        "In piercing mode, the 2 targets can be on the same square or on different squares.\n",
                r.getDescription());

        assertEquals(0, r.getNumOptionalEffect());
        assertTrue(r.hasAlternateFireMode());
    }
}
