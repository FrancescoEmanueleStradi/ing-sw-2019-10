package model.cards.powerupcards;

import model.Colour;
import model.Grid;
import model.cards.PowerUpCard;
import model.player.Player;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TargetingScopeAssertTests {
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
    void TargetingScopeMethods() {
        TargetingScope targetingScope = new TargetingScope(Colour.RED);
        Grid grid = new Grid();
        grid.setType(3);
        Player p1 = new Player("Player", Colour.YELLOW, true);
        Player p2 = new Player("Enemy", Colour.GREEN, false);

        targetingScope.applyEffect(grid, p1, p2);

        assertEquals(p1.getC(), p2.getpB().getDamages().getDamageTr()[0].getC());
    }
}
