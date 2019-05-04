package model;

import model.board.Cell;
import model.player.Player;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GridAssertTests {
    @Test
    void GridPlayersTest()  {
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
}