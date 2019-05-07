package model.cards.weaponcards;

import model.Colour;
import model.cards.WeaponCard;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class WhisperAssertTests {
    @Test
    void WhisperCorrectConstructor() {
        WeaponCard w = new Whisper();

        assertEquals("Whisper", w.getCardName());
        assertEquals(Colour.BLUE, w.getReloadCost()[0].getC());
        assertEquals(Colour.BLUE, w.getReloadCost()[1].getC());
        assertEquals(Colour.YELLOW, w.getReloadCost()[2].getC());
        assertEquals("effect: Deal 3 damage and 1 mark to 1 target you can see. Your target must be at least 2 moves away from you.\n" +
                        "Notes: For example, in the 2-by-2 room, you cannot shoot a target on an adjacent square, but you can shoot a target on the diagonal.\n" +
                        "If you are beside a door, you can't shoot a target on the other side of the door, but you can shoot a target on a different square of that room.\n",
                w.getDescription());

        assertEquals(0, w.getNumOptionalEffect());
        assertFalse(w.hasAlternateFireMode());
    }
}
