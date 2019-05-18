package model.player;

import model.Colour;
import model.Position;
import model.board.Cell;
import model.cards.PowerUpCard;
import model.cards.WeaponCard;
import model.cards.powerupcards.Newton;
import model.cards.powerupcards.TargetingScope;
import model.cards.weaponcards.*;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PlayerAssertTests {
    @Test
    void PlayerConstructorTest()  {
        Player p1 = new Player("Test", Colour.BLUE, true);

        assertEquals("Test", p1.getNickName());
        assertEquals(Colour.BLUE, p1.getC());
        assertTrue(p1.getpB().mIsEmpty());
        assertTrue(p1.isFirstPlayerCard());
        assertEquals(0, p1.getScore());

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

        assertTrue(p1.getpC().isEmpty());
        assertTrue(p1.getwC().isEmpty());

        assertNull(p1.getCell());
        assertFalse(p1.isAdrenaline1());
        assertFalse(p1.isAdrenaline2());
        assertFalse(p1.isDead());
        assertFalse(p1.isOverkilled());
    }

    @Test
    void PlayerAmmoCubeTest()  {
        Player p1 = new Player("Test", Colour.BLUE, false);

        AmmoCube[] price1 = new AmmoCube[]{new AmmoCube(Colour.RED), new AmmoCube(Colour.YELLOW), new AmmoCube(Colour.BLUE)};
        AmmoCube[] price2 = new AmmoCube[]{new AmmoCube(Colour.RED), new AmmoCube(Colour.BLUE)};
        AmmoCube[] price3 = new AmmoCube[]{new AmmoCube(Colour.RED), new AmmoCube(Colour.YELLOW), new AmmoCube(Colour.BLUE), new AmmoCube(Colour.RED)};
        assertTrue(p1.checkAmmoCube(price1));
        assertTrue(p1.checkAmmoCube(price2));
        assertFalse(p1.checkAmmoCube(price3));
        //assertTrue(p1.checkAmmoCubeForPay(price3));

        assertEquals(Colour.RED, price3[0].getC());     //to check if price3 did not get modified
        assertEquals(Colour.YELLOW, price3[1].getC());
        assertEquals(Colour.BLUE, price3[2].getC());
        assertEquals(Colour.RED, price3[3].getC());

        p1.addNewAC(new AmmoCube(Colour.RED));
        p1.addNewAC(new AmmoCube(Colour.RED));
        p1.addNewAC(new AmmoCube(Colour.BLUE));
        p1.addNewAC(new AmmoCube(Colour.BLUE));
        p1.addNewAC(new AmmoCube(Colour.YELLOW));
        p1.addNewAC(new AmmoCube(Colour.YELLOW));
        p1.addNewAC(new AmmoCube(Colour.RED));              //it should not be added
        p1.addNewAC(new AmmoCube(Colour.BLUE));             //it should not be added
        p1.addNewAC(new AmmoCube(Colour.YELLOW));           //it should not be added

        assertEquals(9, p1.getaC().length);
        assertEquals(Colour.RED, p1.getaC()[0].getC());
        assertEquals(Colour.RED, p1.getaC()[1].getC());
        assertEquals(Colour.RED, p1.getaC()[2].getC());
        assertEquals(Colour.BLUE, p1.getaC()[3].getC());
        assertEquals(Colour.BLUE, p1.getaC()[4].getC());
        assertEquals(Colour.BLUE, p1.getaC()[5].getC());
        assertEquals(Colour.YELLOW, p1.getaC()[6].getC());
        assertEquals(Colour.YELLOW, p1.getaC()[7].getC());
        assertEquals(Colour.YELLOW, p1.getaC()[8].getC());


        p1.removeArrayAC(price1);
        assertNull(p1.getaC()[0]);
        assertEquals(Colour.RED, p1.getaC()[1].getC());
        assertEquals(Colour.RED, p1.getaC()[2].getC());
        assertNull(p1.getaC()[3]);
        assertEquals(Colour.BLUE, p1.getaC()[4].getC());
        assertEquals(Colour.BLUE, p1.getaC()[5].getC());
        assertNull(p1.getaC()[6]);
        assertEquals(Colour.YELLOW, p1.getaC()[7].getC());
        assertEquals(Colour.YELLOW, p1.getaC()[8].getC());

        p1.removeArrayAC(price2);
        assertNull(p1.getaC()[0]);
        assertNull(p1.getaC()[1]);
        assertEquals(Colour.RED, p1.getaC()[2].getC());
        assertNull(p1.getaC()[3]);
        assertNull(p1.getaC()[4]);
        assertEquals(Colour.BLUE, p1.getaC()[5].getC());
        assertNull(p1.getaC()[6]);
        assertEquals(Colour.YELLOW, p1.getaC()[7].getC());
        assertEquals(Colour.YELLOW, p1.getaC()[8].getC());

        AmmoCube[] price4 = new AmmoCube[]{new AmmoCube(Colour.RED), new AmmoCube(Colour.RED)};
        p1.removeArrayAC(price4);
        assertNull(p1.getaC()[0]);
        assertNull(p1.getaC()[1]);
        assertNull(p1.getaC()[2]);
        assertNull(p1.getaC()[3]);
        assertNull(p1.getaC()[4]);
        assertEquals(Colour.BLUE, p1.getaC()[5].getC());
        assertNull(p1.getaC()[6]);
        assertEquals(Colour.YELLOW, p1.getaC()[7].getC());
        assertEquals(Colour.YELLOW, p1.getaC()[8].getC());
    }

    @Test
    void PlayerCardsTest()  {
        Player p1 = new Player("Test", Colour.BLUE, false);

        assertTrue(p1.getwC().isEmpty());
        assertNull(p1.getWeaponCardObject("ZX-2"));

        WeaponCard wc1 = new ZX2();
        p1.addWeaponCard(wc1);
        assertEquals(wc1, p1.getwC().get(0));
        assertEquals(1, p1.getwC().size());
        assertEquals(wc1, p1.getWeaponCardObject("ZX-2"));

        WeaponCard wc2 = new Whisper();
        p1.addWeaponCard(wc2);
        assertEquals(wc2, p1.getwC().get(1));
        assertEquals(2, p1.getwC().size());
        assertEquals(wc2, p1.getWeaponCardObject("Whisper"));

        p1.removeWeaponCard(wc1);
        assertEquals(wc2, p1.getwC().get(0));
        assertEquals(1, p1.getwC().size());

        p1.removeWeaponCard(wc2);
        assertTrue(p1.getwC().isEmpty());


        assertTrue(p1.getpC().isEmpty());
        assertNull(p1.getPowerUpCardObject("Newton", Colour.valueOf("YELLOW")));

        PowerUpCard pc1 = new Newton(Colour.YELLOW);
        p1.addPowerUpCard(pc1);
        assertEquals(pc1, p1.getpC().get(0));
        assertEquals(1, p1.getpC().size());
        assertEquals(pc1, p1.getPowerUpCardObject("Newton", Colour.valueOf("YELLOW")));

        PowerUpCard pc2 = new TargetingScope(Colour.RED);
        p1.addPowerUpCard(pc2);
        assertEquals(pc2, p1.getpC().get(1));
        assertEquals(2, p1.getpC().size());
        assertEquals(pc2, p1.getPowerUpCardObject("Targeting Scope", Colour.valueOf("RED")));

        p1.removePowerUpCard(pc1);
        assertEquals(pc2, p1.getpC().get(0));
        assertEquals(1, p1.getpC().size());

        p1.removePowerUpCard(pc2);
        assertTrue(p1.getwC().isEmpty());
    }

    @Test
    void PlayerCellTest()  {
        Player p1 = new Player("Test", Colour.RED, true);

        int[] walls = new int[]{0, 2};
        int[] doors = new int[]{1, 3};
        Position pos1 = new Position(0, 1);
        Position pos2 = new Position(0, 2);
        Position fakePos = new Position(0, 3);
        Cell cell1 = new Cell(1, Colour.BLUE, walls, doors, pos1);
        Cell cell2 = new Cell(0, Colour.BLUE, walls, doors, pos2);
        Cell fake = new Cell(-1, fakePos);

        assertNull(p1.getCell());

        p1.changeCell(cell1);
        p1.changeCell(fake);    //this should not change the cell because 'fake' cell does not exist in the board

        assertEquals(pos1, p1.getCell().getP());
        assertEquals(cell1.getC(), p1.getCell().getC());
        assertEquals(cell1.getStatus(), p1.getCell().getStatus());
        for(int i = 0; i < walls.length; i++)
            assertEquals(cell1.getPosWall()[i], p1.getCell().getPosWall()[i]);
        for(int i = 0; i < doors.length; i++)
            assertEquals(cell1.getPosDoor()[i], p1.getCell().getPosDoor()[i]);

        p1.changeCell(cell2);
        assertEquals(pos2, p1.getCell().getP());
        assertEquals(cell2.getC(), p1.getCell().getC());
        assertEquals(cell2.getStatus(), p1.getCell().getStatus());
        for(int i = 0; i < walls.length; i++)
            assertEquals(cell2.getPosWall()[i], p1.getCell().getPosWall()[i]);
        for(int i = 0; i < doors.length; i++)
            assertEquals(cell2.getPosDoor()[i], p1.getCell().getPosDoor()[i]);
    }

    @Test
    void PlayerScoreTest()  {
        Player p1 = new Player("Test", Colour.BLACK, false);

        assertEquals(0, p1.getScore());
        p1.addScore(6);
        assertEquals(6, p1.getScore());

        p1.addScore(7);
        assertEquals(13, p1.getScore());
    }

    @Test
    void PlayerAdrenalineTest()  {
        Player p1 = new Player("Test", Colour.YELLOW, true);

        assertFalse(p1.isAdrenaline1());
        assertFalse(p1.isAdrenaline2());
    }

    @Test
    void PlayerDeadOverkillTest()  {
        Player p1 = new Player("Test", Colour.GREEN, true);

        assertFalse(p1.isDead());
        assertFalse(p1.isOverkilled());

        p1.getpB().getDamages().addDamage(11, Colour.RED);

        assertTrue(p1.isDead());

        p1.getpB().getDamages().addDamage(1, Colour.RED);

        assertTrue(p1.isOverkilled());
    }

    @Test
    void PlayerPaymentTest()  {
        Player p1 = new Player("Test", Colour.BLACK, true);
        List<AmmoCube> cubes = new LinkedList<>();
        List<PowerUpCard> cards = new LinkedList<>();

        p1.payCard(cubes, cards);

        assertTrue(p1.getpC().isEmpty());
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

        PowerUpCard card1 = new Newton(Colour.BLUE);
        p1.addPowerUpCard(card1);

        cubes.add(p1.getaC()[6]);
        cards.add(card1);

        p1.payCard(cubes, cards);

        assertEquals(9, p1.getaC().length);
        assertEquals(Colour.RED, p1.getaC()[0].getC());
        assertNull(p1.getaC()[1]);
        assertNull(p1.getaC()[2]);
        assertEquals(Colour.BLUE, p1.getaC()[3].getC());
        assertNull(p1.getaC()[4]);
        assertNull(p1.getaC()[5]);
        assertNull(p1.getaC()[6]);
        assertNull(p1.getaC()[7]);
        assertNull(p1.getaC()[8]);

        assertTrue(p1.getpC().isEmpty());

        cubes.add(p1.getaC()[3]);
        p1.payCard(cubes, cards);

        assertEquals(9, p1.getaC().length);
        assertEquals(Colour.RED, p1.getaC()[0].getC());
        assertNull(p1.getaC()[1]);
        assertNull(p1.getaC()[2]);
        assertNull(p1.getaC()[3]);
        assertNull(p1.getaC()[4]);
        assertNull(p1.getaC()[5]);
        assertNull(p1.getaC()[6]);
        assertNull(p1.getaC()[7]);
        assertNull(p1.getaC()[8]);

        assertTrue(p1.getpC().isEmpty());
    }

    @Test
    void PlayerFinalFrenzyTest() {
        Player p1 = new Player("Player", Colour.YELLOW, true);
        p1.setTurnFinalFrenzy(0);
        assertEquals(0, p1.getTurnFinalFrenzy());
        p1.setTurnFinalFrenzy(1);
        assertEquals(1, p1.getTurnFinalFrenzy());
    }
}