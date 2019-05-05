package model.cards.powerupcards;

import model.Colour;
import model.Grid;
import model.cards.PowerUpCard;
import model.player.Player;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TagbackGrenadeAssertTests {
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
    void TagbackGrenadeMethods() {
        TagbackGrenade tagbackGrenade = new TagbackGrenade(Colour.YELLOW);
        Grid grid = new Grid();
        grid.setType(2);
        Player p1 = new Player("Player", Colour.BLUE, true);
        Player p2 = new Player("Enemy", Colour.BLACK, false);

        tagbackGrenade.applyEffect(grid, p1, p2);

        assertEquals(1, p2.getpB().getMarks().size());
        assertEquals(p1.getC(), p2.getpB().getMarks().get(0).getC());
    }
}