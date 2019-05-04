package model;

import model.board.Cell;
import model.cards.PowerUpCard;
import model.cards.WeaponCard;
import model.player.Player;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GridAssertTests {
    @Test
    void GridPlayersTest() {
        Grid grid = new Grid();
        assertTrue(grid.getPlayers().isEmpty());
        assertNull(grid.getPlayerObject("Player 1"));

        Player p1 = new Player("Player 1", Colour.YELLOW, true);
        grid.addPlayer(p1);
        assertEquals(1, grid.getPlayers().size());
        assertEquals("Player 1", grid.getPlayers().get(0).getNickName());
        assertEquals(Colour.YELLOW, grid.getPlayers().get(0).getC());
        assertTrue(grid.getPlayers().get(0).isFirstPlayerCard());

        assertEquals("Player 1", grid.getPlayersNickName().get(0));
        assertEquals(Colour.YELLOW, grid.getPlayersColour().get(0));

        assertEquals("Player 1", grid.getPlayerObject("Player 1").getNickName());
        assertEquals(Colour.YELLOW, grid.getPlayerObject("Player 1").getC());
        assertTrue(grid.getPlayerObject("Player 1").isFirstPlayerCard());

        assertEquals(1, grid.getNumPlayers());


        Player p2 = new Player("Player 2", Colour.BLUE, false);
        grid.addPlayer(p2);
        assertEquals(2, grid.getPlayers().size());
        assertEquals("Player 2", grid.getPlayers().get(1).getNickName());
        assertEquals(Colour.BLUE, grid.getPlayers().get(1).getC());
        assertFalse(grid.getPlayers().get(1).isFirstPlayerCard());

        assertEquals("Player 1", grid.getPlayersNickName().get(0));
        assertEquals("Player 2", grid.getPlayersNickName().get(1));
        assertEquals(Colour.YELLOW, grid.getPlayersColour().get(0));
        assertEquals(Colour.BLUE, grid.getPlayersColour().get(1));

        assertEquals("Player 2", grid.getPlayerObject("Player 2").getNickName());
        assertEquals(Colour.BLUE, grid.getPlayerObject("Player 2").getC());
        assertFalse(grid.getPlayerObject("Player 2").isFirstPlayerCard());

        assertEquals(2, grid.getNumPlayers());


        grid.removePlayer(p2);
        assertEquals(1, grid.getPlayers().size());
        assertEquals("Player 1", grid.getPlayers().get(0).getNickName());
        assertEquals(Colour.YELLOW, grid.getPlayers().get(0).getC());
        assertTrue(grid.getPlayers().get(0).isFirstPlayerCard());

        assertEquals("Player 1", grid.getPlayersNickName().get(0));
        assertEquals(Colour.YELLOW, grid.getPlayersColour().get(0));

        assertEquals("Player 1", grid.getPlayerObject("Player 1").getNickName());
        assertEquals(Colour.YELLOW, grid.getPlayerObject("Player 1").getC());
        assertTrue(grid.getPlayerObject("Player 1").isFirstPlayerCard());

        assertEquals(1, grid.getNumPlayers());
        assertNull(grid.getPlayerObject("Player 2"));

        grid.setType(1);
        assertEquals(1, grid.getBoard().getaType());

        grid.damage(p1, p2, 3);
        grid.damage(p2, p1, 1);
        grid.addMark(p2, p1);
        assertEquals(Colour.YELLOW, p2.getpB().getDamages().getDamageTr()[1].getC());
        assertEquals(Colour.BLUE, p1.getpB().getDamages().getDamageTr()[0].getC());
        assertEquals(p1.getpB().getMarks().size(), 1);

        grid.damage(p2, p1, 2);
        assertEquals(Colour.BLUE, p1.getpB().getDamages().getDamageTr()[3].getC());
        assertEquals(p1.getpB().getMarks().size(), 0);

        grid.clean(p2);
        assertNull(p2.getpB().getDamages().getDamageTr()[0]);


        Player p3 = new Player("Player 3", Colour.GREEN, false);
        grid.addPlayer(p2);
        grid.damage(p3, p1, 11);
        assertEquals(1, grid.whoIsDead().size());
        assertEquals(p1, grid.whoIsDead().get(0));
        grid.damage(p3, p2, 11);
        assertEquals(2, grid.whoIsDead().size());
        assertEquals(p2, grid.whoIsDead().get(1));
    }

    @Test
    void ScoringByColourTest() {
        Grid grid = new Grid();
        Player p1 = new Player("Player 1", Colour.BLUE, true);
        Player p2 = new Player("Player 2", Colour.YELLOW, false);
        Player p3 = new Player("Player 3", Colour.BLACK, false);
        Player p4 = new Player("Player 4", Colour.GREEN, false);
        Player p5 = new Player("Player 5", Colour.PURPLE, false);

        grid.addPlayer(p1);
        grid.addPlayer(p2);
        grid.addPlayer(p3);
        grid.addPlayer(p4);
        grid.addPlayer(p5);

        grid.scoringByColour(Colour.YELLOW, 8);
        grid.scoringByColour(Colour.BLACK, 6);
        grid.scoringByColour(Colour.PURPLE, 4);
        grid.scoringByColour(Colour.BLUE, 2);
        grid.scoringByColour(Colour.GREEN, 1);

        assertEquals(8, p2.getScore());
        assertEquals(6, p3.getScore());
        assertEquals(4, p5.getScore());
        assertEquals(2, p1.getScore());
        assertEquals(1, p4.getScore());

        grid.scoringByColour(Colour.GREEN, 8);
        grid.scoringByColour(Colour.BLUE, 6);
        grid.scoringByColour(Colour.BLACK, 4);
        grid.scoringByColour(Colour.YELLOW, 2);
        grid.scoringByColour(Colour.PURPLE, 1);

        assertEquals(8, p1.getScore());
        assertEquals(10, p2.getScore());
        assertEquals(10, p3.getScore());
        assertEquals(9, p4.getScore());
        assertEquals(5, p5.getScore());
    }

    @Test
    void DistanceTest() {
        Grid grid = new Grid();
        Player p1 = new Player("Player 1", Colour.BLUE, true);
        Player p2 = new Player("Player 2", Colour.YELLOW, false);

        int[] walls = new int[]{0, 1};
        int[] doors = new int[]{2};
        Cell c1 = new Cell(1, Colour.BLUE, walls, doors, new Position(0, 2));
        Cell c2 = new Cell(1, Colour.YELLOW, walls, doors, new Position(1, 3));

        p1.changeCell(c1);
        p2.changeCell(c1);
        assertEquals(0, grid.distance(p1, p2));
        p2.changeCell(c2);
        assertEquals(2, grid.distance(p1, p2));


        assertEquals(0, grid.distance(new Position(0, 2), new Position(0, 2)));
        assertEquals(2, grid.distance(new Position(0, 2), new Position(1, 3)));
        assertEquals(2, grid.distance(new Position(1, 3), new Position(0, 2)));
    }

    @Test
    void MovementTest() {
        Grid grid = new Grid();
        grid.setType(1);
        Player p1 = new Player("Player 1", Colour.BLUE, true);
        Cell c1 = grid.getBoard().getArena()[0][2];
        Cell c2 = grid.getBoard().getArena()[1][2];
        Cell c3 = grid.getBoard().getArena()[1][1];

        p1.changeCell(grid.getBoard().getArena()[0][2]);
        assertEquals(c1, grid.whereAmI(p1));

        assertFalse(grid.canMove(p1, 1));
        assertTrue(grid.isThereAWall(p1, new Position(-1, 2)));
        grid.move(p1, 1);                       //this should not move p1
        assertEquals(c1, grid.whereAmI(p1));

        assertFalse(grid.canMove(p1, 2));
        assertTrue(grid.isThereAWall(p1, new Position(0, 3)));
        grid.move(p1, 2);                       //this should not move p1
        assertEquals(c1, grid.whereAmI(p1));

        assertTrue(grid.canMove(p1, 3));
        assertFalse(grid.isThereAWall(p1, c2.getP()));
        grid.move(p1, 3);                       //this should move p1 one cell down (to c2) through a door
        assertEquals(c2, grid.whereAmI(p1));

        assertTrue(grid.canMove(p1, 4));
        assertFalse(grid.isThereAWall(p1, c3.getP()));
        grid.move(p1, 4);                       //this should move p1 one cell left (to c3) through a corridor
        assertEquals(c3, grid.whereAmI(p1));
    }

    @Test
    void ViewZoneRoomTest() {
        Grid grid = new Grid();
        grid.setType(1);
        Player p1 = new Player("Player 1", Colour.BLUE, true);
        Player p2 = new Player("Player 2", Colour.YELLOW, false);
        Player p3 = new Player("Player 3", Colour.GREEN, false);

        grid.addPlayer(p1);
        grid.addPlayer(p2);
        grid.addPlayer(p3);

        p1.changeCell(grid.getBoard().getArena()[0][0]);
        p2.changeCell(grid.getBoard().getArena()[1][0]);
        p3.changeCell(grid.getBoard().getArena()[0][1]);
        //p1 is in the same cell as p2, and he can see p3. p2 can not see p3 but p3 can see p2

        assertTrue(grid.isInTheRoom(p1, p2));
        assertTrue(grid.isInTheRoom(p2, p1));
        assertFalse(grid.isInTheRoom(p1, p3));
        assertFalse(grid.isInTheRoom(p3, p1));
        assertFalse(grid.isInTheRoom(p2, p3));
        assertFalse(grid.isInTheRoom(p3, p2));

        assertTrue(grid.isInViewZone(p1, p2));
        assertTrue(grid.isInViewZone(p2, p1));
        assertTrue(grid.isInViewZone(p1, p3));
        assertTrue(grid.isInViewZone(p3, p1));
        assertFalse(grid.isInViewZone(p2, p3));
        assertTrue(grid.isInViewZone(p3, p2));

        assertTrue(grid.isInViewZone(p1, p2.getCell().getP()));
        assertTrue(grid.isInViewZone(p2, p1.getCell().getP()));
        assertTrue(grid.isInViewZone(p1, p3.getCell().getP()));
        assertTrue(grid.isInViewZone(p3, p1.getCell().getP()));
        assertFalse(grid.isInViewZone(p2, p3.getCell().getP()));
        assertTrue(grid.isInViewZone(p3, p2.getCell().getP()));


        assertTrue(grid.whoIsInTheRoom(p1).contains(p2));
        assertTrue(grid.whoIsInTheRoom(p2).contains(p1));
        assertFalse(grid.whoIsInTheRoom(p1).contains(p3));
        assertFalse(grid.whoIsInTheRoom(p3).contains(p1));
        assertFalse(grid.whoIsInTheRoom(p2).contains(p3));
        assertFalse(grid.whoIsInTheRoom(p3).contains(p2));

        assertTrue(grid.whoIsInTheViewZone(p1).contains(p2));
        assertTrue(grid.whoIsInTheViewZone(p1).contains(p3));
        assertTrue(grid.whoIsInTheViewZone(p2).contains(p1));
        assertTrue(grid.whoIsInTheViewZone(p3).contains(p1));
        assertFalse(grid.whoIsInTheViewZone(p2).contains(p3));
        assertTrue(grid.whoIsInTheViewZone(p3).contains(p2));


        assertEquals(1, grid.colourOfOtherViewZone(p1).size());
        assertTrue(grid.colourOfOtherViewZone(p1).contains(Colour.BLUE));
        assertEquals(1, grid.colourOfOtherViewZone(p2).size());
        assertTrue(grid.colourOfOtherViewZone(p2).contains(Colour.WHITE));
        assertEquals(2, grid.colourOfOtherViewZone(p3).size());
        assertTrue(grid.colourOfOtherViewZone(p3).contains(Colour.RED));
        assertTrue(grid.colourOfOtherViewZone(p3).contains(Colour.PURPLE));
    }

    @Test
    void GridWeaponCardMethodsTest() {
        Grid grid = new Grid();
        grid.setType(2);

        assertNotNull(grid.getBoard().getW1().getCard1());
        assertNotNull(grid.getBoard().getW1().getCard2());
        assertNotNull(grid.getBoard().getW1().getCard3());
        assertNotNull(grid.getBoard().getW2().getCard1());
        assertNotNull(grid.getBoard().getW2().getCard2());
        assertNotNull(grid.getBoard().getW2().getCard3());
        assertNotNull(grid.getBoard().getW3().getCard1());
        assertNotNull(grid.getBoard().getW3().getCard2());
        assertNotNull(grid.getBoard().getW3().getCard3());

        grid.getBoard().getW1().setCard2(null);
        grid.getBoard().getW2().setCard3(null);
        grid.getBoard().getW3().setCard1(null);

        assertNotNull(grid.getBoard().getW1().getCard1());
        assertNull(grid.getBoard().getW1().getCard2());
        assertNotNull(grid.getBoard().getW1().getCard3());
        assertNotNull(grid.getBoard().getW2().getCard1());
        assertNotNull(grid.getBoard().getW2().getCard2());
        assertNull(grid.getBoard().getW2().getCard3());
        assertNull(grid.getBoard().getW3().getCard1());
        assertNotNull(grid.getBoard().getW3().getCard2());
        assertNotNull(grid.getBoard().getW3().getCard3());

        grid.replaceWeaponCard();

        assertNotNull(grid.getBoard().getW1().getCard1());
        assertNotNull(grid.getBoard().getW1().getCard2());
        assertNotNull(grid.getBoard().getW1().getCard3());
        assertNotNull(grid.getBoard().getW2().getCard1());
        assertNotNull(grid.getBoard().getW2().getCard2());
        assertNotNull(grid.getBoard().getW2().getCard3());
        assertNotNull(grid.getBoard().getW3().getCard1());
        assertNotNull(grid.getBoard().getW3().getCard2());
        assertNotNull(grid.getBoard().getW3().getCard3());


        WeaponCard wc1 = grid.getBoard().getW1().getCard2();
        String sWc1 = grid.getBoard().getW1().getCard2().getCardName();
        WeaponCard wc2 = grid.getBoard().getW2().getCard3();
        String sWc2 = grid.getBoard().getW2().getCard3().getCardName();
        WeaponCard wc3 = grid.getBoard().getW3().getCard1();
        String sWc3 = grid.getBoard().getW3().getCard1().getCardName();

        assertEquals(wc1, grid.getWeaponCardObject(sWc1));
        assertEquals(wc2, grid.getWeaponCardObject(sWc2));
        assertEquals(wc3, grid.getWeaponCardObject(sWc3));


        assertEquals(grid.getBoard().getW1(), grid.getWeaponSlotObject("1"));
        assertEquals(grid.getBoard().getW2(), grid.getWeaponSlotObject("2"));
        assertEquals(grid.getBoard().getW3(), grid.getWeaponSlotObject("3"));
    }

    @Test
    void GridPowerUpCardMethodsTest() {
        Grid grid = new Grid();
        grid.setType(3);
        Player p = new Player("Player", Colour.BLUE, true);

        grid.pickPowerUpCard(p);
        grid.pickPowerUpCard(p);
        grid.pickPowerUpCard(p);
        grid.pickPowerUpCard(p);

        assertEquals(3, p.getpC().size());

        PowerUpCard pUC = grid.pickPowerUpCard();
        p.addPowerUpCard(pUC);

        assertEquals(4, p.getpC().size());
        assertEquals(pUC, p.getpC().get(3));
    }

    @Test
    void GridAmmoCardMethodsTest() {
        Grid grid = new Grid();
        grid.setType(4);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                assertNull(grid.getBoard().getArena()[i][j].getA());
            }
        }

        grid.setUpAmmoCard();

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                if (grid.getBoard().getArena()[i][j].getStatus() == 0)
                    assertNotNull(grid.getBoard().getArena()[i][j].getA());
            }
        }

        grid.getBoard().getArena()[0][0].setA(null);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                if (grid.getBoard().getArena()[i][j].getStatus() == 0 && i != 0 && j != 0)
                    assertNotNull(grid.getBoard().getArena()[i][j].getA());
            }
        }

        grid.replaceAmmoCard();

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                if (grid.getBoard().getArena()[i][j].getStatus() == 0)
                    assertNotNull(grid.getBoard().getArena()[i][j].getA());
            }
        }
    }

    @Test
    void GridGhostMoveTest() {
        Grid grid = new Grid();
        Player p1 = new Player("Player 1", Colour.BLUE, true);
        grid.setType(1);
        p1.changeCell(grid.getBoard().getArena()[1][0]);

        List<Integer> directions = new LinkedList<>();
        directions.add(1);                                  //p1 moves one cell up
        directions.add(2);                                  //p1 moves one cell right
        directions.add(3);                                  //p1 moves one cell down
        directions.add(4);                                  //p1 want to move one cell left, but he can't

        assertFalse(grid.canGhostMove(p1, directions));
        assertEquals(grid.getBoard().getArena()[1][0], p1.getCell());   //p1 didn't move

        directions.remove(3);                           //remove last direction, as it's not valid

        assertTrue(grid.canGhostMove(p1, directions));
        assertEquals(grid.getBoard().getArena()[1][0], p1.getCell());   //p1 didn't move
        Player ghost = grid.ghostMove(p1, directions);                  //now p1's ghost player moves
        assertEquals(grid.getBoard().getArena()[1][1], ghost.getCell());//ghost has moved to [1][1]
    }
}