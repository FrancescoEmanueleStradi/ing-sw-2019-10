package controller;

import model.Colour;
import model.Grid;
import model.cards.PowerUpCard;
import model.cards.WeaponCard;
import model.cards.weaponcards.MachineGun;
import model.player.AmmoCube;
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

        game.addPlayer("Player 3", Colour.GREEN);
        Player p3 = game.getGrid().getPlayerObject("Player 3");

        assertEquals(3, game.getPlayers().size());
        assertTrue(game.getPlayers().contains("Player 1"));
        assertTrue(game.getPlayers().contains("Player 2"));
        assertTrue(game.getPlayers().contains("Player 3"));

        assertTrue(game.isValidReceiveType(1));
        game.receiveType(1);
        assertEquals(1, grid.getBoard().getaType());
        assertEquals(GameState.INITIALIZED, game.getGameState());

        List<PowerUpCard> list = game.giveTwoPUCard("Player 1");
        System.out.print("PowerUpCard picked from the deck for Player 1: " + list.get(0).getCardName() + " coloured " + list.get(0).getC() + ", and " + list.get(1).getCardName() + " coloured " + list.get(1).getC());
        System.out.print("\nDetails (Player 1): " + list);
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
        System.out.print("\nPowerUpCard picked from the deck for Player 2: " + list2.get(0).getCardName() + " coloured " + list2.get(0).getC() + ", and " + list2.get(1).getCardName() + " coloured " + list2.get(1).getC());
        System.out.print("\nDetails (Player 2): " + list2);
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

        List<PowerUpCard> list3 = game.giveTwoPUCard("Player 3");
        System.out.print("\nPowerUpCard picked from the deck for Player 3: " + list3.get(0).getCardName() + " coloured " + list3.get(0).getC() + ", and " + list3.get(1).getCardName() + " coloured " + list3.get(1).getC());
        System.out.print("\nDetails (Player 3): " + list3);
        game.pickAndDiscardCard("Player 3", list3.get(0), list3.get(1));
        assertEquals(GameState.STARTTURN, game.getGameState());
        assertEquals(1, p3.getpC().size());
        assertEquals(list3.get(0), p3.getpC().get(0));
        assertEquals(3, grid.getPowerUpDiscardPile().size());
        assertEquals(list3.get(1), grid.getPowerUpDiscardPile().get(2));

        if(list3.get(1).getC().equals(Colour.YELLOW))
            assertEquals(p3.getCell(), grid.getBoard().getArena()[2][3]);
        else if(list3.get(1).getC().equals(Colour.RED))
            assertEquals(p3.getCell(), grid.getBoard().getArena()[1][0]);
        else if(list3.get(1).getC().equals(Colour.BLUE))
            assertEquals(p3.getCell(), grid.getBoard().getArena()[0][2]);


        //First Action: Move

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

        //Second Action: Shoot

        p1.changeCell(game.getGrid().getBoard().getArena()[0][2]);
        p2.changeCell(game.getGrid().getBoard().getArena()[1][1]);
        p3.changeCell(game.getGrid().getBoard().getArena()[1][2]);

        WeaponCard machineGun = new MachineGun();
        assertFalse(game.isValidCard("Player 1", "Machine Gun"));
        p1.addWeaponCard(machineGun);
        assertFalse(game.isValidCard("Player 1", "Machine Gun"));
        p1.getwC().get(0).reload();
        assertTrue(game.isValidCard("Player 1", "Machine Gun"));

        List<Integer> lI = new LinkedList<>();
        lI.add(1);
        lI.add(2);
        lI.add(3);
        List<String> lS = new LinkedList<>();
        lS.add("Player 2");
        lS.add("Player 3");
        lS.add("Player 3");
        lS.add("Player 2");
        lS.add("");
        List<Colour> lA = new LinkedList<>();
        lA.add(Colour.valueOf("YELLOW"));
        lA.add(Colour.valueOf("BLUE"));
        List<String> lP = new LinkedList<>();

        assertTrue(game.isValidSecondActionShoot("Player 1", "Machine Gun", lI, lS, 0, lA, lP));
        game.secondActionShoot("Player 1", "Machine Gun", lI, lS, 0, lA, lP);
        assertEquals(Colour.BLUE, p2.getpB().getDamages().getDamageTr()[0].getC());
        assertEquals(Colour.BLUE, p3.getpB().getDamages().getDamageTr()[0].getC());
        assertEquals(Colour.BLUE, p3.getpB().getDamages().getDamageTr()[1].getC());
        assertEquals(Colour.BLUE, p2.getpB().getDamages().getDamageTr()[1].getC());

        assertEquals(GameState.ACTION2, game.getGameState());
        assertFalse(machineGun.isReloaded());
        for(AmmoCube ac : p1.getaC()) {
            if(ac != null) {
                assertNotEquals(Colour.BLUE, ac.getC());
                assertNotEquals(Colour.YELLOW, ac.getC());
            }
        }

        lI.clear();
        lS.clear();
        lA.clear();

        if(p2.getpC().get(0).getCardName().equals("Tagback Grenade")) {
            lS.add("Player 1");
            assertTrue(game.isValidUsePowerUpCard("Player 2", "Tagback Grenade", lS, null));
            game.usePowerUpCard("Player 2", "Tagback Grenade", lS, null);
            assertEquals(Colour.YELLOW, p1.getpB().getMarks().get(0).getC());
        }
        else if(p1.getpC().get(0).getCardName().equals("Targeting Scope")) {
            lS.add("Player 2");
            assertTrue(game.isValidUsePowerUpCard("Player 1", "Targeting Scope", lS, Colour.valueOf("RED")));
            game.usePowerUpCard("Player 1", "Targeting Scope", lS, Colour.valueOf("RED"));
            assertEquals(Colour.BLUE, p2.getpB().getDamages().getDamageTr()[2].getC());
            for(AmmoCube ac : p1.getaC()) {
                if(ac != null)
                    assertNotEquals(Colour.RED, ac.getC());
            }
        }
        else if(p1.getpC().get(0).getCardName().equals("Newton")) {
            lS.add("Player 3");
            lS.add("2");
            lS.add("3");
            assertTrue(game.isValidUsePowerUpCard("Player 1", "Newton", lS, null));
            game.usePowerUpCard("Player 1", "Newton", lS, null);
            assertEquals(game.getGrid().getBoard().getArena()[2][3], p3.getCell());
        }
        else if(p1.getpC().get(0).getCardName().equals("Teleporter")) {
            lS.add("2");
            lS.add("1");
            assertTrue(game.isValidUsePowerUpCard("Player 1", "Teleporter", lS, null));
            game.usePowerUpCard("Player 1", "Teleporter", lS, null);
            assertEquals(game.getGrid().getBoard().getArena()[2][1], p1.getCell());
        }
    }
}