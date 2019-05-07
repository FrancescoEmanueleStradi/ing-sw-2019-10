package controller;

import model.Colour;
import model.Grid;
import model.cards.PowerUpCard;
import model.cards.WeaponCard;
import model.cards.powerupcards.Newton;
import model.cards.powerupcards.TagbackGrenade;
import model.cards.powerupcards.TargetingScope;
import model.cards.powerupcards.Teleporter;
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
        List<String> lPC = new LinkedList<>();

        assertTrue(game.isValidSecondActionShoot("Player 1", "Machine Gun", lI, lS, 0, lA, lP, lPC));
        game.secondActionShoot("Player 1", "Machine Gun", lI, lS, 0, lA, lP, lPC);
        assertEquals(Colour.BLUE, p2.getpB().getDamages().getDamageTr()[0].getC());
        assertEquals(Colour.BLUE, p3.getpB().getDamages().getDamageTr()[0].getC());
        assertEquals(Colour.BLUE, p3.getpB().getDamages().getDamageTr()[1].getC());
        assertEquals(Colour.BLUE, p2.getpB().getDamages().getDamageTr()[1].getC());

        assertEquals(GameState.ACTION2, game.getGameState());
        assertFalse(machineGun.isReloaded());
        assertNotNull(p1.getaC()[0]);
        assertNull(p1.getaC()[1]);
        assertNull(p1.getaC()[2]);
        assertNull(p1.getaC()[3]);
        assertNull(p1.getaC()[4]);
        assertNull(p1.getaC()[5]);
        assertNull(p1.getaC()[6]);
        assertNull(p1.getaC()[7]);
        assertNull(p1.getaC()[8]);


        //Use PowerUpCards

        lI.clear();
        lS.clear();
        lA.clear();

        PowerUpCard tagbackGrenade = new TagbackGrenade(Colour.BLUE);
        p2.addPowerUpCard(tagbackGrenade);
        lS.add("Player 1");
        assertTrue(game.isValidUsePowerUpCard("Player 2", "Tagback Grenade", "BLUE", lS, null));
        game.usePowerUpCard("Player 2", "Tagback Grenade", "BLUE", lS, null);
        assertEquals(Colour.YELLOW, p1.getpB().getMarks().get(0).getC());

        lS.clear();

        PowerUpCard targetingScope = new TargetingScope(Colour.RED);
        p1.addPowerUpCard(targetingScope);
        lS.add("Player 2");
        assertTrue(game.isValidUsePowerUpCard("Player 1", "Targeting Scope", "RED", lS, Colour.valueOf("RED")));
        game.usePowerUpCard("Player 1", "Targeting Scope", "RED", lS, Colour.valueOf("RED"));
        assertEquals(Colour.BLUE, p2.getpB().getDamages().getDamageTr()[2].getC());
        for(AmmoCube ac : p1.getaC()) {
            if(ac != null)
                assertNotEquals(Colour.RED, ac.getC());
        }

        lS.clear();

        PowerUpCard newton = new Newton(Colour.YELLOW);
        p1.addPowerUpCard(newton);
        lS.add("Player 3");
        lS.add("4");
        lS.add("3");
        assertTrue(game.isValidUsePowerUpCard("Player 1", "Newton", "YELLOW", lS, null));
        game.usePowerUpCard("Player 1", "Newton", "YELLOW", lS, null);
        assertEquals(game.getGrid().getBoard().getArena()[2][1], p3.getCell());

        lS.clear();

        PowerUpCard teleporter = new Teleporter(Colour.BLUE);
        p1.addPowerUpCard(teleporter);
        lS.add("1");
        lS.add("1");
        assertTrue(game.isValidUsePowerUpCard("Player 1", "Teleporter", "BLUE", lS, null));
        game.usePowerUpCard("Player 1", "Teleporter", "BLUE", lS, null);
        assertEquals(game.getGrid().getBoard().getArena()[1][1], p1.getCell());

        lS.clear();


        //Reloading

        assertFalse(game.isValidReload("Player 1", "Machine Gun"));
        p1.addNewAC(new AmmoCube(Colour.BLUE));
        p1.addNewAC(new AmmoCube(Colour.RED));
        assertNotNull(p1.getaC()[0]);
        assertNull(p1.getaC()[1]);
        assertNull(p1.getaC()[2]);
        assertNotNull(p1.getaC()[3]);
        assertNull(p1.getaC()[4]);
        assertNull(p1.getaC()[5]);
        assertNull(p1.getaC()[6]);
        assertNull(p1.getaC()[7]);
        assertNull(p1.getaC()[8]);


        assertFalse(p1.getwC().get(0).isReloaded());
        assertTrue(game.isValidReload("Player 1", "Machine Gun"));
        game.reload("Player 1", "Machine Gun", 1);
        assertTrue(p1.getwC().get(0).isReloaded());
        for(AmmoCube ac : p1.getaC()) {
            assertNull(ac);
        }

        assertEquals(GameState.RELOADED, game.getGameState());


        //Scoring

        game.getGrid().damage(p3, p2, 1);
        game.getGrid().damage(p1, p2, 9);
        game.getGrid().damage(p1, p3, 9);

        assertEquals(2, p1.getpB().getMarks().size());
        assertEquals(p2.getC(), p1.getpB().getMarks().get(0).getC());
        assertEquals(p2.getC(), p1.getpB().getMarks().get(1).getC());

        assertTrue(game.isValidScoring());
        game.scoring();
        assertEquals(2, game.getDeadList().size());
        assertTrue(game.getDeadList().contains(p2.getNickName()));
        assertTrue(game.getDeadList().contains(p3.getNickName()));
        assertEquals(19, p1.getScore());
        assertEquals(6, p3.getScore());

        assertEquals(2, game.getGrid().getBoard().getK().getSkulls()[0]);
        assertEquals(1, game.getGrid().getBoard().getK().getSkulls()[1]);
        assertEquals(0, game.getGrid().getBoard().getK().getSkulls()[2]);
        assertEquals(0, game.getGrid().getBoard().getK().getSkulls()[3]);
        assertEquals(0, game.getGrid().getBoard().getK().getSkulls()[4]);
        assertEquals(0, game.getGrid().getBoard().getK().getSkulls()[5]);
        assertEquals(0, game.getGrid().getBoard().getK().getSkulls()[6]);
        assertEquals(0, game.getGrid().getBoard().getK().getSkulls()[7]);
        assertEquals(p1.getC(), game.getGrid().getBoard().getK().getC()[0]);
        assertEquals(p1.getC(), game.getGrid().getBoard().getK().getC()[1]);

        assertEquals(6, p2.getpB().getPoints().getPoints().size());
        assertEquals(6, p3.getpB().getPoints().getPoints().size());

        for(int i = 0; i < 12; i++) {
            assertNull(p2.getpB().getDamages().getDamageTr()[i]);
            assertNull(p3.getpB().getDamages().getDamageTr()[i]);
        }

        assertEquals(3, p2.getpC().size());
        assertEquals(2, p3.getpC().size());

        assertEquals(GameState.DEATH, game.getGameState());


        //Discard card for new spawn point

        PowerUpCard targetingScope2 = new TargetingScope(Colour.BLUE);

        p2.addPowerUpCard(targetingScope2);
        assertTrue(game.isValidDiscardCardForSpawnPoint("Player 2", "Targeting Scope", "BLUE"));
        game.discardCardForSpawnPoint("Player 2", "Targeting Scope", "BLUE");
        assertEquals(p2.getCell(), grid.getBoard().getArena()[1][0]);
        assertFalse(p2.getpC().contains(targetingScope));
        assertEquals(1, game.getGrid().getPowerUpDiscardPile().size());
        assertTrue(game.getGrid().getPowerUpDiscardPile().contains(targetingScope));

        assertEquals(GameState.ENDTURN, game.getGameState());
        assertTrue(game.getDeadList().isEmpty());
    }
}