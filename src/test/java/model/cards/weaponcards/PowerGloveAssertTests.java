package model.cards.weaponcards;

import model.Colour;
import model.cards.WeaponCard;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PowerGloveAssertTests {
    @Test
    void PowerGloveCorrectConstructor() {
        WeaponCard pg = new PowerGlove();

        assertEquals("Power Glove", pg.getCardName());
        assertEquals(Colour.YELLOW, pg.getReloadCost()[0].getC());
        assertEquals(Colour.BLUE, pg.getReloadCost()[1].getC());
        assertEquals("basic mode: Choose 1 target on any square exactly 1 move away. Move onto that square and give the target 1 damage and 2 marks.\n" +
                        "in rocket fist mode: Choose a square exactly 1 move away. Move onto that square. You may deal 2 damage to 1 target there.\n" +
                        "If you want, you may move 1 more square in that same direction (but only if it is a legal move). You may deal 2 damage to 1 target there, as well.\n" +
                        "Notes: In rocket fist mode, you're flying 2 squares in a straight line, punching 1 person per square.\n",
                pg.getDescription());

        assertEquals(0, pg.getNumOptionalEffect());
        assertTrue(pg.hasAlternateFireMode());
    }
}
