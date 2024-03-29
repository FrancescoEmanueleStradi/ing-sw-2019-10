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

class MachineGunAssertTests {

    private ServerInterface server = mock(ServerMethods.class);

    @Test
    void MachineGunCorrectConstructor()  {
        WeaponCard mg = new MachineGun();

        assertEquals("Machine Gun", mg.getCardName());
        assertEquals(Colour.BLUE, mg.getReloadCost()[0].getC());
        assertEquals(Colour.RED, mg.getReloadCost()[1].getC());
        assertEquals("basic effect: Choose 1 or 2 targets you can see and deal 1 damage to each.\n" +
                             "with focus shot: Deal 1 additional damage to one of those targets.\n" +
                             "with turret tripod: Deal 1 additional damage to the other of those targets and/or deal 1 damage to a different target you can see.\n" +
                             "Notes: If you deal both additional points of damage, they must be dealt to 2 different targets. If you see only\n" +
                             "2 targets, you deal 2 to each if you use both optional effects. If you use the basic effect on only 1 target, you can still use the the turret tripod to give it 1 additional damage.\n",
                    mg.getDescription());
    }

    @Test
    void MachineGunMethods() throws RemoteException {
        int iD = 1;

        MachineGun mg = new MachineGun();

        mg.reload();
        assertTrue(mg.isReloaded());
        mg.unload();
        assertFalse(mg.isReloaded());

        Grid grid = new Grid(iD, server);
        Player player = new Player("Test", Colour.BLUE, true);
        Player enemy1 = new Player("Enemy 1", Colour.RED, false);
        Player enemy2 = new Player("Enemy 2", Colour.YELLOW, false);

        mg.applyEffect(grid, player, enemy1, enemy2);
        assertEquals(Colour.BLUE, enemy1.getPlayerBoard().getDamage().getDamageTokens()[0].getC());
        assertEquals(Colour.BLUE, enemy2.getPlayerBoard().getDamage().getDamageTokens()[0].getC());

        mg.applySpecialEffect(grid, player, enemy1);
        assertEquals(Colour.BLUE, enemy1.getPlayerBoard().getDamage().getDamageTokens()[1].getC());
    }
}