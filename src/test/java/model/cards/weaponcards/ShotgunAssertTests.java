package model.cards.weaponcards;

import model.Colour;
import model.cards.WeaponCard;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ShotgunAssertTests {
    @Test
    void ShotgunCorrectConstructor() {
        WeaponCard s = new Shotgun();

        assertEquals("Shotgun", s.getCardName());
        assertEquals(Colour.YELLOW, s.getReloadCost()[0].getC());
        assertEquals("basic mode: Deal 3 damage to 1 target on your square. If you want, you may then move the target 1 square.\n" +
                        "in long barrel mode: Deal 2 damage to 1 target on any square exactly one move away.\n",
                s.getDescription());
    }
}