package model.cards.weaponcards;

import model.Colour;
import model.cards.WeaponCard;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PlasmaGunAssertTests {
    @Test
    void PlasmaGunCorrectConstructor() {
        WeaponCard pg = new PlasmaGun();

        assertEquals("Plasma Gun", pg.getCardName());
        assertEquals(Colour.BLUE, pg.getReloadCost()[0].getC());
        assertEquals(Colour.YELLOW, pg.getReloadCost()[1].getC());
        assertEquals("basic effect: Deal 2 damage to 1 target you can see.\n" +
                        "with phase glide: Move 1 or 2 squares. This effect can be used either before or after the basic effect.\n" +
                        "with charged shot: Deal 1 additional damage to your target.\n" +
                        "Notes: The two moves have no ammo cost. You don't have to be able to see your target when you play the card.\n" +
                        "For example, you can move 2 squares and shoot a target you now see. You cannot use 1 move before shooting and 1 move after.\n",
                pg.getDescription());
    }
}