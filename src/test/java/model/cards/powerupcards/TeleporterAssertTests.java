package model.cards.powerupcards;

import model.Colour;
import model.Grid;
import model.cards.PowerUpCard;
import model.player.Player;
import org.junit.jupiter.api.Test;
import network.Server;
import network.ServerInterface;

import java.rmi.RemoteException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class TeleporterAssertTests {

    private int iD = 1;
    private ServerInterface server = (ServerInterface) mock(Server.class);

    @Test
    void TeleporterCorrectConstructor() {
        PowerUpCard pUC = new Teleporter(Colour.BLUE);

        assertEquals("Teleporter", pUC.getCardName());
        assertEquals(Colour.BLUE, pUC.getC());
        assertEquals("You may play this card on your turn before or after any action.\n" +
                "Pick up your figure and set it down on any square of the board.\n" +
                "(You can't use this after you see where someone respawns at the end of your turn. By then it is too late.)\n", pUC.getDescription());
        assertEquals(Colour.BLUE, pUC.getValue().getC());
    }

    @Test
    void TeleporterMethods() throws RemoteException {
        Teleporter teleporter = new Teleporter(Colour.YELLOW);
        Grid grid = new Grid(iD, server);
        try {
            grid.setType(4);
        } catch (java.rmi.RemoteException e) {
            e.printStackTrace();
        }
        Player p1 = new Player("Player", Colour.GREEN, true);
        p1.changeCell(grid.getBoard().getArena()[2][3]);
        String x = "0";
        String y = "2";

        teleporter.applyEffect(grid, p1, x, y);

        assertEquals(grid.getBoard().getArena()[0][2], p1.getCell());
    }
}