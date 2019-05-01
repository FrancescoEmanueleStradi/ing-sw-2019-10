package model.decks;

import model.cards.WeaponCard;
import model.cards.weaponcards.MachineGun;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class WeaponDeckAssertTests {
    @Test
    void WeaponDeckTest()  {
        WeaponDeck wDeck = new WeaponDeck();

        assertEquals("Cyberblade", wDeck.getDeck().get(0).getCardName());
        assertEquals("Electroscythe", wDeck.getDeck().get(1).getCardName());
        assertEquals("Flamethrower", wDeck.getDeck().get(2).getCardName());
        assertEquals("Furnace", wDeck.getDeck().get(3).getCardName());
        assertEquals("Grenade Launcher", wDeck.getDeck().get(4).getCardName());
        assertEquals("Heatseeker", wDeck.getDeck().get(5).getCardName());
        assertEquals("Hellion", wDeck.getDeck().get(6).getCardName());
        assertEquals("Lock Rifle", wDeck.getDeck().get(7).getCardName());
        assertEquals("Machine Gun", wDeck.getDeck().get(8).getCardName());
        assertEquals("Plasma Gun", wDeck.getDeck().get(9).getCardName());
        assertEquals("Power Glove", wDeck.getDeck().get(10).getCardName());
        assertEquals("Railgun", wDeck.getDeck().get(11).getCardName());
        assertEquals("Rocket Launcher", wDeck.getDeck().get(12).getCardName());
        assertEquals("Shockwave", wDeck.getDeck().get(13).getCardName());
        assertEquals("Shotgun", wDeck.getDeck().get(14).getCardName());
        assertEquals("Sledgehammer", wDeck.getDeck().get(15).getCardName());
        assertEquals("T.H.O.R.", wDeck.getDeck().get(16).getCardName());
        assertEquals("Tractor Beam", wDeck.getDeck().get(17).getCardName());
        assertEquals("Vortex Cannon", wDeck.getDeck().get(18).getCardName());
        assertEquals("Whisper", wDeck.getDeck().get(19).getCardName());
        assertEquals("ZX-2", wDeck.getDeck().get(20).getCardName());

        WeaponCard fakeCard = new MachineGun();

        wDeck.addCard(fakeCard);
        assertEquals("Machine Gun", wDeck.getDeck().get(21).getCardName());
    }
}