package model.cards.weaponcards;

import model.Colour;
import model.Grid;
import model.cards.WeaponCard;
import model.player.Player;
import network.ServerMethods;
import org.junit.jupiter.api.Test;
import network.ServerInterface;

import java.rmi.RemoteException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class CyberbladeAssertTests {

    private ServerInterface server = mock(ServerMethods.class);

    @Test
    void CyberbladeCorrectConstructor() {
        WeaponCard c = new Cyberblade();

        assertEquals("Cyberblade", c.getCardName());
        assertEquals(Colour.YELLOW, c.getReloadCost()[0].getC());
        assertEquals("basic effect: Deal 2 damage to 1 target on your square.\n" +
                "with shadowstep: Move 1 square before or after the basic effect.\n" +
                "with slice and dice: Deal 2 damage to a different target on your square.\n" +
                "The shadowstep may be used before or after this effect.\n" +
                "Notes: Combining all effects allows you to move onto a square and whack 2 people; or whack somebody, move, and whack somebody else; or whack 2 people and then move.\n",
                c.getDescription());
    }

    @Test
    void CyberbladeMethods() throws RemoteException {
        int iD = 1;

        Cyberblade c = new Cyberblade();

        c.reload();
        assertTrue(c.isReloaded());
        c.unload();
        assertFalse(c.isReloaded());

        Grid grid = new Grid(iD, server);
        try {
            grid.setType(1);
        } catch (java.rmi.RemoteException e) {
            e.printStackTrace();
        }

        Player player = new Player("Myself", Colour.YELLOW, true);
        player.changeCell(grid.getBoard().getArena()[0][1]);

        Player enemy1 = new Player("Enemy 1", Colour.BLACK, false);
        try {
            c.applyEffect(grid, player, enemy1);
        } catch (java.rmi.RemoteException e) {
            e.printStackTrace();
        }
        assertEquals(Colour.YELLOW, enemy1.getPlayerBoard().getDamage().getDamageTokens()[1].getC());

        c.applySpecialEffect(grid, player, "2");
        assertEquals(grid.getBoard().getArena()[0][2], player.getCell());

        Player enemy2 = new Player("Enemy 2", Colour.RED, false);
        grid.move(enemy2, 0, 2);
        assertEquals(player.getCell(), enemy2.getCell());

        c.applySpecialEffect2(grid, player, enemy2);
        assertEquals(Colour.YELLOW, enemy2.getPlayerBoard().getDamage().getDamageTokens()[1].getC());
    }
}