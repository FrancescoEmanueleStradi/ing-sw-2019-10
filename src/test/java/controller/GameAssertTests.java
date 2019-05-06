package controller;

import model.Colour;
import model.Grid;
import model.cards.PowerUpCard;
import model.player.Player;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GameAssertTests {
    @Test
    void GameStartTest() {
        Game game = new Game();
        Grid grid = game.getGrid();

        assertTrue(game.gameIsNotStarted());
        assertFalse(game.isValidAddPlayer("Player", Colour.YELLOW));

        game.gameStart("Player 1", Colour.BLUE);
        assertEquals(GameState.START, game.getGameState());

        Player p1 = game.getGrid().getPlayerObject("Player 1");
        assertEquals(Colour.BLUE, p1.getC());
        assertEquals("Player 1", p1.getNickName());

        assertFalse(game.isValidAddPlayer("Player 1", Colour.YELLOW));
        assertFalse(game.isValidAddPlayer("Player", Colour.BLUE));
        assertFalse(game.isValidAddPlayer("Player 1", Colour.BLUE));

        game.addPlayer("Player 2", Colour.YELLOW);

        assertEquals(2, game.getPlayers().size());
        assertTrue(game.getPlayers().contains("Player 1"));
        assertTrue(game.getPlayers().contains("Player 2"));

        int type = 1;

        assertTrue(game.isValidReceiveType(1));
        game.receiveType(1);
        assertEquals(1, grid.getBoard().getaType());
        assertEquals(GameState.INITIALIZED, game.getGameState());

        List<PowerUpCard> list = game.giveTwoPUCard("Player 1");
        System.out.print("PowerUpCard picked from the deck: "+list);
        game.pickAndDiscardCard("Player 1", list.get(0), list.get(1));
        assertEquals(1, p1.getpC().size());
        assertEquals(list.get(0), p1.getpC().get(0));
        assertEquals(1, grid.getPowerUpDiscardPile().size());
        assertEquals(list.get(1), grid.getPowerUpDiscardPile().get(0));

        if(list.get(1).getC().equals(Colour.YELLOW))
            assertEquals(p1.getCell(), grid.getBoard().getArena()[2][3]);
        else if(list.get(1).getC().equals(Colour.RED))
            assertEquals(p1.getCell(), grid.getBoard().getArena()[1][0]);
        else if(list.get(1).getC().equals(Colour.BLUE))
            assertEquals(p1.getCell(), grid.getBoard().getArena()[0][2]);
    }
}
