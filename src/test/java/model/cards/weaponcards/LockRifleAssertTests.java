package model.cards.weaponcards;

import model.Colour;
import model.Grid;
import model.InvalidColourException;
import model.cards.WeaponCard;
import model.player.Player;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LockRifleAssertTests {
    @Test
    void LockRifleCorrectConstructor() throws InvalidColourException {
        WeaponCard lr = new LockRifle();

        assertEquals("Lock Rifle", lr.getCardName());
        assertEquals(Colour.BLUE, lr.getReloadCost()[0].getC());
        assertEquals(Colour.BLUE, lr.getReloadCost()[1].getC());
        assertEquals("basic effect: Deal 2 damage and 1 mark to 1 target you can see.\n" +
                "with second lock: Deal 1 mark to a different target you can see.\n",
                lr.getDescription());

        assertEquals(1, lr.getNumOptionalEffect());
        assertFalse(lr.hasAlternateFireMode());
    }

    @Test
    void LockRifleMethods() throws InvalidColourException {
        LockRifle lr = new LockRifle();

        lr.reload();
        assertTrue(lr.isReloaded());
        lr.unload();
        assertFalse(lr.isReloaded());

        assertEquals("Second Lock", lr.getOptionalEffect());

        Grid grid = new Grid();
        Player player = new Player("Myself", Colour.BLUE, true);
        Player enemy1 = new Player("Enemy 1", Colour.GREEN, false);
        Player enemy2 = new Player("Enemy 2", Colour.RED, false);

        lr.applyEffect(grid, player, enemy1);
        assertEquals(Colour.BLUE, enemy1.getpB().getDamages().getDamageTr()[1].getC());
        assertTrue(enemy1.getpB().getMarks().size() == 1);

        lr.applySpecialEffect(grid, player, enemy2);
        assertTrue(enemy2.getpB().getMarks().size() == 1);
    }
}
