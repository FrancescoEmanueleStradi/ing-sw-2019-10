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

class TargetingScopeAssertTests {

    private int iD = 1;
    private ServerInterface server = mock(ServerMethods.class);

    @Test
    void TargetingScopeCorrectConstructor() {
        PowerUpCard pUC = new TargetingScope(Colour.BLUE);

        assertEquals("Targeting Scope", pUC.getCardName());
        assertEquals(Colour.BLUE, pUC.getC());
        assertEquals("You may play this card when you are dealing damage to one or more targets.\n" +
                "Pay 1 ammo cube of any color. Choose 1 of those targets and give it an extra point of damage.\n" +
                "Note: You cannot use this to do 1 damage to a target that is receiving only marks.\n", pUC.getDescription());
        assertEquals(Colour.BLUE, pUC.getValue().getC());
    }

    @Test
    void TargetingScopeMethods() throws RemoteException {
        TargetingScope targetingScope = new TargetingScope(Colour.RED);
        Grid grid = new Grid(iD, server);
        try {
            grid.setType(3);
        } catch (java.rmi.RemoteException e) {
            e.printStackTrace();
        }
        Player p1 = new Player("Player", Colour.YELLOW, true);
        Player p2 = new Player("Enemy", Colour.GREEN, false);

        targetingScope.applyEffect(grid, p1, p2);

        assertEquals(p1.getC(), p2.getPlayerBoard().getDamage().getDamageTokens()[0].getC());
    }
}
