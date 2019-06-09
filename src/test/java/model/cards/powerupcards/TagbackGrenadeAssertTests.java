package model.cards.powerupcards;

import model.Colour;
import model.Grid;
import model.cards.PowerUpCard;
import model.player.Player;
import network.ServerMethods;
import org.junit.jupiter.api.Test;
import network.ServerInterface;

import java.rmi.RemoteException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class TagbackGrenadeAssertTests {

    private int iD = 1;
    private ServerInterface server = mock(ServerMethods.class);

    @Test
    void TagbackGrenadeCorrectConstructor() {
        PowerUpCard pUC = new TagbackGrenade(Colour.RED);

        assertEquals("Tagback Grenade", pUC.getCardName());
        assertEquals(Colour.RED, pUC.getC());
        assertEquals("You may play this card when you receive damage from a player you can see.\n" +
                "Give that player 1 mark.\n", pUC.getDescription());
        assertEquals(Colour.RED, pUC.getValue().getC());
    }

    @Test
    void TagbackGrenadeMethods() throws RemoteException {
        TagbackGrenade tagbackGrenade = new TagbackGrenade(Colour.YELLOW);
        Grid grid = new Grid(iD, server);
        try {
            grid.setType(2);
        } catch (java.rmi.RemoteException e) {
            e.printStackTrace();
        }
        Player p1 = new Player("Player", Colour.BLUE, true);
        Player p2 = new Player("Enemy", Colour.BLACK, false);

        tagbackGrenade.applyEffect(grid, p1, p2);

        assertEquals(1, p2.getPlayerBoard().getMarks().size());
        assertEquals(p1.getC(), p2.getPlayerBoard().getMarks().get(0).getC());
    }
}