package model.cards.weaponcards;

import model.Colour;
import model.cards.WeaponCard;
import org.junit.jupiter.api.Test;

import java.rmi.RemoteException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ElectroscytheAssertTests {
    @Test
    void ElectroscytheCorrectConstructor() throws RemoteException {
        WeaponCard e = new Electroscythe();

        assertEquals("Electroscythe", e.getCardName());
        assertEquals(Colour.BLUE, e.getReloadCost()[0].getC());
        assertEquals("basic mode: Deal 1 damage to every other player on your square.\n" +
                        "in reaper mode: Deal 2 damage to every other player on your square.\n",
                e.getDescription());

        assertEquals(0, e.getNumOptionalEffect());
        assertTrue(e.hasAlternateFireMode());
    }
}
