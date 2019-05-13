package model.cards.weaponcards;

import model.Colour;
import model.Grid;
import model.Position;
import model.cards.WeaponCard;
import model.player.Player;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HeatseekerAssertTests {
    @Test
    void HeatseekerCorrectConstructor()  {
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
    void HeatseekerMethods()  {
        Heatseeker h = new Heatseeker();

        h.reload();
        assertTrue(h.isReloaded());
        h.unload();
        assertFalse(h.isReloaded());

        Grid grid = new Grid();
        grid.setType(1);

        Player player = new Player("Myself", Colour.BLUE, true);
        grid.move(player, 0, 1);

        Player enemy = new Player("Enemy 1", Colour.GREEN, false);
        grid.move(enemy, 2, 3);

        assertFalse(grid.isInViewZone(player, enemy));
        h.applyEffect(grid, player, enemy);
        assertEquals(Colour.BLUE, enemy.getpB().getDamages().getDamageTr()[2].getC());

    }
}