package model.cards.weaponcards;

import model.Colour;
import model.cards.WeaponCard;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class RocketLauncherAssertTests {
    @Test
    void RocketLauncherCorrectConstructor() {
        WeaponCard rl = new RocketLauncher();

        assertEquals("Rocket Launcher", rl.getCardName());
        assertEquals(Colour.RED, rl.getReloadCost()[0].getC());
        assertEquals(Colour.RED, rl.getReloadCost()[1].getC());
        assertEquals("basic effect: Deal 2 damage to 1 target you can see that is not on your square. Then you may move the target 1 square.\n" +
                        "with rocket jump: Move 1 or 2 squares. This effect can be used either before or after the basic effect.\n" +
                        "with fragmenting warhead: During the basic effect, deal 1 damage to every player on your target's original square – including the target, even if you move it.\n" +
                        "Notes: If you use the rocket jump before the basic effect, you consider only your new square when determining if a target is legal.\n" +
                        "You can even move off a square so you can shoot someone on it.\n" +
                        "If you use the fragmenting warhead, you deal damage to everyone on the target's square before you move the target – your target will take 3 damage total.\n",
                rl.getDescription());

        assertEquals(2, rl.getNumOptionalEffect());
        assertFalse(rl.hasAlternateFireMode());
    }
}
