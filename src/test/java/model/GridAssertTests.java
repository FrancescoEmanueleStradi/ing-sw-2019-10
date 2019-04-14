package model;

import model.player.Player;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class GridAssertTests {
    @Test
    void GridTest() throws InvalidColourException {
        Grid grid = new Grid();
        Player p1 = new Player("Test", Colour.BLUE, true);
        grid.addPlayer(p1);
        List<Player> players = grid.getPlayers();

        assertEquals(players.get(0).getNickName(), "Test");
        assertEquals(players.get(0).getC(), Colour.BLUE);

        int numPlayers = grid.getNumPlayers();
        assertEquals(1, numPlayers);

        assertNull(grid.whereAmI(p1));
    }
}