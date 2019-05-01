package model.decks;

import model.Colour;
import model.cards.PowerUpCard;
import model.cards.powerupcards.Newton;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PowerUpDeckAssertTests {
    @Test
    void PowerUpDeckTest()  {
        PowerUpDeck puDeck = new PowerUpDeck();

        assertEquals("Targeting Scope", puDeck.getDeck().get(0).getCardName());
        assertEquals(Colour.RED, puDeck.getDeck().get(0).getC());
        assertEquals("Targeting Scope", puDeck.getDeck().get(1).getCardName());
        assertEquals(Colour.RED, puDeck.getDeck().get(1).getC());
        assertEquals("Targeting Scope", puDeck.getDeck().get(2).getCardName());
        assertEquals(Colour.BLUE, puDeck.getDeck().get(2).getC());
        assertEquals("Targeting Scope", puDeck.getDeck().get(3).getCardName());
        assertEquals(Colour.BLUE, puDeck.getDeck().get(3).getC());
        assertEquals("Targeting Scope", puDeck.getDeck().get(4).getCardName());
        assertEquals(Colour.YELLOW, puDeck.getDeck().get(4).getC());
        assertEquals("Targeting Scope", puDeck.getDeck().get(5).getCardName());
        assertEquals(Colour.YELLOW, puDeck.getDeck().get(5).getC());
        assertEquals("Newton", puDeck.getDeck().get(6).getCardName());
        assertEquals(Colour.RED, puDeck.getDeck().get(6).getC());
        assertEquals("Newton", puDeck.getDeck().get(7).getCardName());
        assertEquals(Colour.RED, puDeck.getDeck().get(7).getC());
        assertEquals("Newton", puDeck.getDeck().get(8).getCardName());
        assertEquals(Colour.BLUE, puDeck.getDeck().get(8).getC());
        assertEquals("Newton", puDeck.getDeck().get(9).getCardName());
        assertEquals(Colour.BLUE, puDeck.getDeck().get(9).getC());
        assertEquals("Newton", puDeck.getDeck().get(10).getCardName());
        assertEquals(Colour.YELLOW, puDeck.getDeck().get(10).getC());
        assertEquals("Newton", puDeck.getDeck().get(11).getCardName());
        assertEquals(Colour.YELLOW, puDeck.getDeck().get(11).getC());
        assertEquals("Tagback Grenade", puDeck.getDeck().get(12).getCardName());
        assertEquals(Colour.RED, puDeck.getDeck().get(12).getC());
        assertEquals("Tagback Grenade", puDeck.getDeck().get(13).getCardName());
        assertEquals(Colour.RED, puDeck.getDeck().get(13).getC());
        assertEquals("Tagback Grenade", puDeck.getDeck().get(14).getCardName());
        assertEquals(Colour.BLUE, puDeck.getDeck().get(14).getC());
        assertEquals("Tagback Grenade", puDeck.getDeck().get(15).getCardName());
        assertEquals(Colour.BLUE, puDeck.getDeck().get(15).getC());
        assertEquals("Tagback Grenade", puDeck.getDeck().get(16).getCardName());
        assertEquals(Colour.YELLOW, puDeck.getDeck().get(16).getC());
        assertEquals("Tagback Grenade", puDeck.getDeck().get(17).getCardName());
        assertEquals(Colour.YELLOW, puDeck.getDeck().get(17).getC());
        assertEquals("Teleporter", puDeck.getDeck().get(18).getCardName());
        assertEquals(Colour.RED, puDeck.getDeck().get(18).getC());
        assertEquals("Teleporter", puDeck.getDeck().get(19).getCardName());
        assertEquals(Colour.RED, puDeck.getDeck().get(19).getC());
        assertEquals("Teleporter", puDeck.getDeck().get(20).getCardName());
        assertEquals(Colour.BLUE, puDeck.getDeck().get(20).getC());
        assertEquals("Teleporter", puDeck.getDeck().get(21).getCardName());
        assertEquals(Colour.BLUE, puDeck.getDeck().get(21).getC());
        assertEquals("Teleporter", puDeck.getDeck().get(22).getCardName());
        assertEquals(Colour.YELLOW, puDeck.getDeck().get(22).getC());
        assertEquals("Teleporter", puDeck.getDeck().get(23).getCardName());
        assertEquals(Colour.YELLOW, puDeck.getDeck().get(23).getC());

        Newton fakeCard = new Newton(Colour.RED);

        puDeck.addCard(fakeCard);
        assertEquals(fakeCard, puDeck.getDeck().get(24));

        PowerUpCard firstCard = puDeck.getTopOfDeck();
        assertEquals("Targeting Scope", firstCard.getCardName());
        assertEquals(Colour.RED, firstCard.getC());
        assertEquals("Targeting Scope", puDeck.getDeck().get(0).getCardName());
        assertEquals(Colour.RED, puDeck.getDeck().get(0).getC());
    }
}