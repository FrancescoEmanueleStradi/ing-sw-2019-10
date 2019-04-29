package model.cards.weaponcards;

import model.Colour;
import model.Grid;
import model.InvalidColourException;
import model.cards.WeaponCard;
import model.player.Player;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CyberbladeAssertTests {
    @Test
    void CyberbladeCorrectConstructor() throws InvalidColourException {
        WeaponCard c = new Cyberblade();

        assertEquals("Cyberblade", c.getCardName());
        assertEquals(Colour.YELLOW, c.getReloadCost()[0].getC());
        assertEquals(Colour.RED, c.getReloadCost()[1].getC());
        assertEquals("basic effect: Deal 2 damage and 1 mark to 1 target you can see.\n" +
                        "with second lock: Deal 1 mark to a different target you can see.\n",
                c.getDescription());

        assertEquals(2, c.getNumOptionalEffect());
        assertFalse(c.hasAlternateFireMode());
    }

    @Test
    void CyberbladeMethods() throws InvalidColourException {
        Cyberblade c = new Cyberblade();

        c.reload();
        assertTrue(c.isReloaded());
        c.unload();
        assertFalse(c.isReloaded());

        assertEquals("Shadowstep", c.getOptionalEffect1());
        assertEquals("Slice and Dice", c.getOptionalEffect2());

        Grid grid = new Grid();
        Player player = new Player("Myself", Colour.YELLOW, true);
        Player enemy1 = new Player("Enemy 1", Colour.BLACK, false);
        Player enemy2 = new Player("Enemy 2", Colour.RED, false);
        String x = "2";
        String y = "1";

        c.applyEffect(grid, player, enemy1);
        assertEquals(Colour.YELLOW, enemy1.getpB().getDamages().getDamageTr()[1].getC());

        //TODO
        c.applySpecialEffect(grid, player, x, y);

    }
}
