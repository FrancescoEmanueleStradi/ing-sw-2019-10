package model.player;

import model.Colour;
import model.InvalidColourException;
import model.Position;
import model.board.Cell;
import model.cards.PowerUpCard;
import model.cards.WeaponCard;
import model.cards.powerupcards.Newton;
import model.cards.powerupcards.TagbackGrenade;
import model.cards.powerupcards.TargetingScope;
import model.cards.weaponcards.Electroscythe;
import model.cards.weaponcards.Furnace;
import model.cards.weaponcards.THOR;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerAssertTests {
    @Test
    void PlayerTest() throws InvalidColourException {
        Player p1 = new Player("Test", Colour.BLUE, true);
        int[] pm = new int[]{0, 2};
        int[] pp = new int[]{1, 3};
        Position pos1 = new Position(0, 1);
        Position pos2 = new Position(0, 2);
        Cell cell1 = new Cell(1, Colour.BLUE, pm, pp, pos1);
        Cell cell2 = new Cell(0, Colour.BLUE, pm, pp, pos2);

        assertTrue(p1.isFirstPlayerCard());

        p1.setScore(42);
        assertEquals(42, p1.getScore());

        assertTrue(p1.getpB().mIsEmpty());

        assertEquals(9, p1.getaC().length);
        assertEquals(Colour.RED, p1.getaC()[0].getC());
        assertNull(p1.getaC()[1]);
        assertNull(p1.getaC()[2]);
        assertEquals(Colour.BLUE, p1.getaC()[3].getC());
        assertNull(p1.getaC()[4]);
        assertNull(p1.getaC()[5]);
        assertEquals(Colour.YELLOW, p1.getaC()[6].getC());
        assertNull(p1.getaC()[7]);
        assertNull(p1.getaC()[8]);

        //TODO test addAC() --> need method to remove an AmmoCube from p1.aC

        assertEquals(0, p1.getwC().size());

        WeaponCard w1 = new Electroscythe();
        WeaponCard w2 = new THOR();
        WeaponCard w3 = new Furnace();
        p1.addWeaponCard(w1);
        p1.addWeaponCard(w2);
        p1.addWeaponCard(w3);
        assertEquals(w1, p1.getwC().get(0));
        assertEquals(w2, p1.getwC().get(1));
        assertEquals(w3, p1.getwC().get(2));

        p1.removeWeaponCard(w3);
        assertEquals(2, p1.getwC().size());
        p1.removeWeaponCard(w2);
        assertEquals(1, p1.getwC().size());
        p1.removeWeaponCard(w1);
        assertEquals(0, p1.getwC().size());

        assertEquals(0, p1.getpC().size());

        PowerUpCard pu1 = new Newton(Colour.YELLOW);
        PowerUpCard pu2 = new TagbackGrenade(Colour.BLUE);
        PowerUpCard pu3 = new TargetingScope(Colour.RED);
        p1.addPowerUpCard(pu1);
        p1.addPowerUpCard(pu2);
        p1.addPowerUpCard(pu3);
        assertEquals(pu1, p1.getpC().get(0));
        assertEquals(pu2, p1.getpC().get(1));
        assertEquals(pu3, p1.getpC().get(2));

        p1.removePowerUpCard(pu3);
        assertEquals(2, p1.getpC().size());
        p1.removePowerUpCard(pu2);
        assertEquals(1, p1.getpC().size());
        p1.removePowerUpCard(pu1);
        assertEquals(0, p1.getpC().size());

        p1.setCell(cell1);
        assertEquals(pos1, p1.getCell().getP());

        p1.changeCell(cell2);
        assertSame(pos2, p1.getCell().getP());
    }
}