package model.cards.weaponcards;

import model.Colour;
import model.Grid;
import model.InvalidColourException;
import model.Position;
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
        assertEquals("basic effect: Deal 2 damage to 1 target on your square.\n" +
                "with shadowstep: Move 1 square before or after the basic effect.\n" +
                "with slice and dice: Deal 2 damage to a different target on your square.\n" +
                "The shadowstep may be used before or after this effect.\n" +
                "Notes: Combining all effects allows you to move onto a square and whack 2 people; or whack somebody, move, and whack somebody else; or whack 2 people and then move.\n",
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
        grid.setType(1);

        Player player = new Player("Myself", Colour.YELLOW, true);
        Position oldPos = new Position(0,1);
        grid.move(player, oldPos);

        Player enemy1 = new Player("Enemy 1", Colour.BLACK, false);
        c.applyEffect(grid, player, enemy1);
        assertEquals(Colour.YELLOW, enemy1.getpB().getDamages().getDamageTr()[1].getC());

        String x = "0";
        String y = "2";
        c.applySpecialEffect(grid, player, x, y);
        assertTrue(grid.distance(player, oldPos) == 1);

        Player enemy2 = new Player("Enemy 2", Colour.RED, false);
        Position enemyPos = new Position(0,2);
        grid.move(enemy2, enemyPos);
        //grid.moveInMyCell doesn't work
        assertEquals(player.getCell(), enemy2.getCell());

        c.applySpecialEffect2(grid, player, enemy2);
        assertEquals(Colour.YELLOW, enemy2.getpB().getDamages().getDamageTr()[1].getC());
    }
}
