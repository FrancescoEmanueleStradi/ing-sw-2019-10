package model.cards.weaponcards;

import model.Colour;
import model.Grid;
import model.InvalidColourException;
import model.cards.WeaponCard;
import model.player.Player;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HeatseekerAssertTests {
    @Test
    void HeatseekerCorrectConstructor() throws InvalidColourException {
        WeaponCard h = new Heatseeker();

        assertEquals("Heatseeker", h.getCardName());
        assertEquals(Colour.RED, h.getReloadCost()[0].getC());
        assertEquals(Colour.RED, h.getReloadCost()[1].getC());
        assertEquals(Colour.YELLOW, h.getReloadCost()[2].getC());
        assertEquals("effect: Choose 1 target you cannot see and deal 3 damage to it.\n" +
                        "Notes: Yes, this can only hit targets you cannot see.\n",
                h.getDescription());

        assertEquals(0, h.getNumOptionalEffect());
        assertFalse(h.hasAlternateFireMode());
    }

    @Test
    void HeatseekerMethods() throws InvalidColourException {
        Heatseeker h = new Heatseeker();

        h.reload();
        assertTrue(h.isReloaded());
        h.unload();
        assertFalse(h.isReloaded());

        Grid grid = new Grid();
        Player player = new Player("Myself", Colour.BLUE, true);
        Player enemy1 = new Player("Enemy 1", Colour.GREEN, false);

        h.applyEffect(grid, player, enemy1);
        assertEquals(Colour.BLUE, enemy1.getpB().getDamages().getDamageTr()[2].getC());

    }
}