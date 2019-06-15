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

class LockRifleAssertTests {

    private ServerInterface server = mock(ServerMethods.class);

    @Test
    void LockRifleCorrectConstructor() {
        WeaponCard lr = new LockRifle();

        assertEquals("Lock Rifle", lr.getCardName());
        assertEquals(Colour.BLUE, lr.getReloadCost()[0].getC());
        assertEquals(Colour.BLUE, lr.getReloadCost()[1].getC());
        assertEquals("basic effect: Deal 2 damage and 1 mark to 1 target you can see.\n" +
                "with second lock: Deal 1 mark to a different target you can see.\n",
                lr.getDescription());
    }

    @Test
    void LockRifleMethods() throws RemoteException {
        int iD = 1;

        LockRifle lr = new LockRifle();

        lr.reload();
        assertTrue(lr.isReloaded());
        lr.unload();
        assertFalse(lr.isReloaded());

        Grid grid = new Grid(iD, server);
        Player player = new Player("Myself", Colour.BLUE, true);
        Player enemy1 = new Player("Enemy 1", Colour.GREEN, false);
        Player enemy2 = new Player("Enemy 2", Colour.RED, false);

        lr.applyEffect(grid, player, enemy1);
        assertEquals(Colour.BLUE, enemy1.getPlayerBoard().getDamage().getDamageTokens()[1].getC());
        assertEquals(1, enemy1.getPlayerBoard().getMarks().size());

        lr.applySpecialEffect(grid, player, enemy2);
        assertEquals(1, enemy2.getPlayerBoard().getMarks().size());
    }
}