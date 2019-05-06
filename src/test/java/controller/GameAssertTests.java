package controller;

import model.Colour;
import model.Grid;
import model.cards.PowerUpCard;
import model.cards.WeaponCard;
import model.cards.weaponcards.MachineGun;
import model.player.Player;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GameAssertTests {
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
        Player p2 = game.getGrid().getPlayerObject("Player 2");

        assertEquals(2, game.getPlayers().size());
        assertTrue(game.getPlayers().contains("Player 1"));
        assertTrue(game.getPlayers().contains("Player 2"));

        assertTrue(game.isValidReceiveType(1));
        game.receiveType(1);
        assertEquals(1, grid.getBoard().getaType());
        assertEquals(GameState.INITIALIZED, game.getGameState());

        List<PowerUpCard> list = game.giveTwoPUCard("Player 1");
        System.out.print("PowerUpCard picked from the deck for Player 1: \n"+list);
        game.pickAndDiscardCard("Player 1", list.get(0), list.get(1));
        assertEquals(GameState.STARTTURN, game.getGameState());
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

        List<PowerUpCard> list2 = game.giveTwoPUCard("Player 2");
        System.out.print("\nPowerUpCard picked from the deck for Player 2: \n"+list2);
        game.pickAndDiscardCard("Player 2", list2.get(1), list2.get(0));
        assertEquals(GameState.STARTTURN, game.getGameState());
        assertEquals(1, p2.getpC().size());
        assertEquals(list2.get(1), p2.getpC().get(0));
        assertEquals(2, grid.getPowerUpDiscardPile().size());
        assertEquals(list2.get(0), grid.getPowerUpDiscardPile().get(1));

        if(list2.get(0).getC().equals(Colour.YELLOW))
            assertEquals(p2.getCell(), grid.getBoard().getArena()[2][3]);
        else if(list2.get(0).getC().equals(Colour.RED))
            assertEquals(p2.getCell(), grid.getBoard().getArena()[1][0]);
        else if(list2.get(0).getC().equals(Colour.BLUE))
            assertEquals(p2.getCell(), grid.getBoard().getArena()[0][2]);


        //ACTIONS
        //Action 1: Move

        List<Integer> directions = new LinkedList<>();
        assertFalse(game.isValidFirstActionMove(p1.getNickName(), directions));
        directions.add(1);
        directions.add(2);
        directions.add(3);
        directions.add(4);
        assertFalse(game.isValidFirstActionMove(p1.getNickName(), directions));
        directions.clear();
        directions.add(1);
        directions.add(0);
        directions.add(4);
        assertFalse(game.isValidFirstActionMove(p1.getNickName(), directions));
        directions.clear();

        int x = p1.getCell().getP().getX();
        int y = p1.getCell().getP().getY();

        if((p1.getCell() == grid.getBoard().getArena()[2][3]) || (p1.getCell() == grid.getBoard().getArena()[1][0])) {
            directions.add(1);
            game.firstActionMove("Player 1", directions);
            assertEquals(x - 1, p1.getCell().getP().getX());
            assertEquals(y, p1.getCell().getP().getY());

            directions.clear();
            directions.add(2);
            assertFalse(game.isValidFirstActionMove(p1.getNickName(), directions));
        }
        else if(p1.getCell() == grid.getBoard().getArena()[0][2]) {
            directions.add(3);
            directions.add(4);
            game.firstActionMove("Player 1", directions);
            assertEquals(x + 1, p1.getCell().getP().getX());
            assertEquals(y - 1, p1.getCell().getP().getY());

            directions.clear();
            directions.add(2);
            assertFalse(game.isValidFirstActionMove(p1.getNickName(), directions));
        }

        assertEquals(GameState.ACTION1, game.getGameState());


        //Action 2: Shoot

        p1.changeCell(game.getGrid().getBoard().getArena()[0][2]);
        p2.changeCell(game.getGrid().getBoard().getArena()[1][1]);

        WeaponCard machineGun = new MachineGun();
        assertFalse(game.isValidCard("Player 1", "Machine Gun"));
        p1.addWeaponCard(machineGun);
        assertFalse(game.isValidCard("Player 1", "Machine Gun"));
        p1.getwC().get(0).reload();
        assertTrue(game.isValidCard("Player 1", "Machine Gun"));

        List<Integer> lI = new LinkedList<>();
        lI.add(1);
        List<String> lS = new LinkedList<>();
        lS.add("Player 2");
        lS.add("");
        List<Colour> lA = new LinkedList<>();
        List<String> lP = new LinkedList<>();

        assertTrue(game.isValidSecondActionShoot("Player 1", "Machine Gun", lI, lS, 0, lA, lP));
    }
}