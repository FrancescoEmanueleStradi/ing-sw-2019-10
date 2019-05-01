package model;

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
    }
}