package controller;

import model.Colour;
import model.Grid;
import model.cards.*;
import model.cards.ammocards.*;
import model.cards.powerupcards.*;
import model.cards.weaponcards.*;
import model.player.AmmoCube;
import model.player.Player;
import org.junit.jupiter.api.Test;
import view.Server;
import view.ServerInterface;
import static org.mockito.Mockito.*;

import java.rmi.RemoteException;
import java.util.LinkedList;
import java.util.List;

import static model.Colour.*;
import static org.junit.jupiter.api.Assertions.*;

class GameAssertTests {

    private int iD = 1;
    private ServerInterface server = mock(Server.class);

    @Test
    void GameStartMoveShootEndTest() throws RemoteException {
        Game game = new Game(iD, server);
        Grid grid = game.getGrid();

        assertTrue(game.gameIsNotStarted());
        assertFalse(game.isValidAddPlayer("Player", YELLOW));

        game.gameStart("Player 1", BLUE);
        assertEquals(GameState.START, game.getGameState());

        Player p1 = grid.getPlayerObject("Player 1");
        assertEquals(BLUE, p1.getC());
        assertEquals("Player 1", p1.getNickName());

        assertFalse(game.isValidAddPlayer("Player 1", YELLOW));
        assertFalse(game.isValidAddPlayer("Player", BLUE));
        assertFalse(game.isValidAddPlayer("Player 1", BLUE));

        game.addPlayer("Player 2", YELLOW);
        Player p2 = grid.getPlayerObject("Player 2");

        assertEquals(2, game.getPlayers().size());
        assertTrue(game.getPlayers().contains("Player 1"));
        assertTrue(game.getPlayers().contains("Player 2"));

        game.addPlayer("Player 3", GREEN);
        Player p3 = grid.getPlayerObject("Player 3");

        assertEquals(3, game.getPlayers().size());
        assertTrue(game.getPlayers().contains("Player 1"));
        assertTrue(game.getPlayers().contains("Player 2"));
        assertTrue(game.getPlayers().contains("Player 3"));

        assertTrue(game.isValidReceiveType(1));
        game.receiveType(1);
        assertEquals(1, grid.getBoard().getaType());
        assertEquals(GameState.INITIALIZED, game.getGameState());


        game.giveTwoPUCard("Player 1");
        PowerUpCard discarded1 = p1.getPowerUpCards().get(1);
        System.out.print("PowerUpCard picked from the deck for Player 1: " + p1.getPowerUpCards().get(0).getCardName() + " coloured " + p1.getPowerUpCards().get(0).getC().getAbbreviation() + ", and " + p1.getPowerUpCards().get(1).getCardName() + " coloured " + p1.getPowerUpCards().get(1).getC().getAbbreviation());
        assertTrue(game.isValidPickAndDiscard("Player 1", p1.getPowerUpCards().get(0).getCardName(), p1.getPowerUpCards().get(0).getC().getAbbreviation()));
        game.pickAndDiscardCard("Player 1", p1.getPowerUpCards().get(0).getCardName(), p1.getPowerUpCards().get(0).getC().getAbbreviation());
        assertEquals(GameState.STARTTURN, game.getGameState());
        assertEquals(1, p1.getPowerUpCards().size());
        assertEquals(1, grid.getPowerUpDiscardPile().size());


        if (discarded1.getC().equals(YELLOW))
            assertEquals(p1.getCell(), grid.getBoard().getArena()[2][3]);
        else if (discarded1.getC().equals(Colour.RED))
            assertEquals(p1.getCell(), grid.getBoard().getArena()[1][0]);
        else if (discarded1.getC().equals(BLUE))
            assertEquals(p1.getCell(), grid.getBoard().getArena()[0][2]);

        game.giveTwoPUCard("Player 2");
        PowerUpCard discarded2 = p2.getPowerUpCards().get(0);
        System.out.print("\nPowerUpCard picked from the deck for Player 2: " + p2.getPowerUpCards().get(0).getCardName() + " coloured " + p2.getPowerUpCards().get(0).getC().getAbbreviation() + ", and " + p2.getPowerUpCards().get(1).getCardName() + " coloured " + p2.getPowerUpCards().get(1).getC().getAbbreviation());
        assertTrue(game.isValidPickAndDiscard("Player 2", p2.getPowerUpCards().get(1).getCardName(), p2.getPowerUpCards().get(1).getC().getAbbreviation()));
        game.pickAndDiscardCard("Player 2", p2.getPowerUpCards().get(1).getCardName(), p2.getPowerUpCards().get(1).getC().getAbbreviation());
        assertEquals(GameState.STARTTURN, game.getGameState());
        assertEquals(1, p2.getPowerUpCards().size());
        assertEquals(2, grid.getPowerUpDiscardPile().size());

        if (discarded2.getC().equals(YELLOW))
            assertEquals(p2.getCell(), grid.getBoard().getArena()[2][3]);
        else if (discarded2.getC().equals(Colour.RED))
            assertEquals(p2.getCell(), grid.getBoard().getArena()[1][0]);
        else if (discarded2.getC().equals(BLUE))
            assertEquals(p2.getCell(), grid.getBoard().getArena()[0][2]);

        game.giveTwoPUCard("Player 3");
        PowerUpCard discarded3 = p3.getPowerUpCards().get(1);
        System.out.print("\nPowerUpCard picked from the deck for Player 3: " + p3.getPowerUpCards().get(0).getCardName() + " coloured " + p3.getPowerUpCards().get(0).getC().getAbbreviation() + ", and " + p3.getPowerUpCards().get(1).getCardName() + " coloured " + p3.getPowerUpCards().get(1).getC().getAbbreviation());
        assertTrue(game.isValidPickAndDiscard("Player 3", p3.getPowerUpCards().get(0).getCardName(), p3.getPowerUpCards().get(0).getC().getAbbreviation()));
        game.pickAndDiscardCard("Player 3", p3.getPowerUpCards().get(0).getCardName(), p3.getPowerUpCards().get(0).getC().getAbbreviation());
        assertEquals(GameState.STARTTURN, game.getGameState());
        assertEquals(1, p3.getPowerUpCards().size());
        assertEquals(3, grid.getPowerUpDiscardPile().size());

        if (discarded3.getC().equals(YELLOW))
            assertEquals(p3.getCell(), grid.getBoard().getArena()[2][3]);
        else if (discarded3.getC().equals(Colour.RED))
            assertEquals(p3.getCell(), grid.getBoard().getArena()[1][0]);
        else if (discarded3.getC().equals(BLUE))
            assertEquals(p3.getCell(), grid.getBoard().getArena()[0][2]);


        //First Action: Move

        List<Integer> directions = new LinkedList<>();
        assertFalse(game.isValidFirstActionMove(p1.getNickName(), directions));

        int x = p1.getCell().getP().getX();
        int y = p1.getCell().getP().getY();

        if ((p1.getCell() == grid.getBoard().getArena()[2][3]) || (p1.getCell() == grid.getBoard().getArena()[1][0])) {
            directions.add(1);
            directions.add(2);
            directions.add(1);
            assertFalse(game.isValidFirstActionMove(p1.getNickName(), directions));
            directions.clear();
            directions.add(1);
            game.firstActionMove("Player 1", directions);
            assertEquals(x - 1, p1.getCell().getP().getX());
            assertEquals(y, p1.getCell().getP().getY());

            directions.clear();
            directions.add(2);
            assertFalse(game.isValidFirstActionMove(p1.getNickName(), directions));
        } else if (p1.getCell() == grid.getBoard().getArena()[0][2]) {
            directions.add(3);
            directions.add(4);
            directions.add(4);
            assertFalse(game.isValidFirstActionMove(p1.getNickName(), directions));
            directions.clear();
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

        p1.changeCell(grid.getBoard().getArena()[0][2]);
        p2.changeCell(grid.getBoard().getArena()[1][1]);
        p3.changeCell(grid.getBoard().getArena()[1][2]);

        WeaponCard machineGun = new MachineGun();
        assertFalse(game.isValidCard("Player 1", "Machine Gun"));
        p1.addWeaponCard(machineGun);
        assertFalse(game.isValidCard("Player 1", "Machine Gun"));
        p1.getWeaponCards().get(0).reload();
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
        assertEquals(BLUE, p2.getPlayerBoard().getDamage().getDamageTokens()[0].getC());
        assertEquals(BLUE, p3.getPlayerBoard().getDamage().getDamageTokens()[0].getC());
        assertEquals(BLUE, p3.getPlayerBoard().getDamage().getDamageTokens()[1].getC());
        assertEquals(BLUE, p2.getPlayerBoard().getDamage().getDamageTokens()[1].getC());

        assertEquals(GameState.ACTION2, game.getGameState());
        assertFalse(machineGun.isReloaded());
        assertNotNull(p1.getAmmoCubes()[0]);
        assertNull(p1.getAmmoCubes()[1]);
        assertNull(p1.getAmmoCubes()[2]);
        assertNull(p1.getAmmoCubes()[3]);
        assertNull(p1.getAmmoCubes()[4]);
        assertNull(p1.getAmmoCubes()[5]);
        assertNull(p1.getAmmoCubes()[6]);
        assertNull(p1.getAmmoCubes()[7]);
        assertNull(p1.getAmmoCubes()[8]);


        //Use PowerUpCards

        lI.clear();
        lS.clear();
        lA.clear();
        p1.getPowerUpCards().clear();
        p2.getPowerUpCards().clear();
        p3.getPowerUpCards().clear();

        PowerUpCard tagbackGrenade = new TagbackGrenade(BLUE);
        p2.addPowerUpCard(tagbackGrenade);
        lS.add("Player 1");
        assertTrue(game.isValidUsePowerUpCard("Player 2", "Tagback Grenade", "BLUE", lS, null));
        game.usePowerUpCard("Player 2", "Tagback Grenade", "BLUE", lS, null);
        assertEquals(YELLOW, p1.getPlayerBoard().getMarks().get(0).getC());
        assertFalse(p2.getPowerUpCards().contains(tagbackGrenade));
        assertEquals(4, grid.getPowerUpDiscardPile().size());

        lS.clear();

        PowerUpCard targetingScope = new TargetingScope(Colour.RED);
        p1.addPowerUpCard(targetingScope);
        lS.add("Player 2");
        assertTrue(game.isValidUsePowerUpCard("Player 1", "Targeting Scope", "RED", lS, Colour.valueOf("RED")));
        game.usePowerUpCard("Player 1", "Targeting Scope", "RED", lS, Colour.valueOf("RED"));
        assertEquals(BLUE, p2.getPlayerBoard().getDamage().getDamageTokens()[2].getC());
        for (AmmoCube ac : p1.getAmmoCubes()) {
            if (ac != null)
                assertNotEquals(Colour.RED, ac.getC());
        }
        assertFalse(p1.getPowerUpCards().contains(targetingScope));
        assertEquals(5, grid.getPowerUpDiscardPile().size());

        lS.clear();

        PowerUpCard newton = new Newton(YELLOW);
        p1.addPowerUpCard(newton);
        lS.add("Player 3");
        lS.add("4");
        lS.add("3");
        assertTrue(game.isValidUsePowerUpCard("Player 1", "Newton", "YELLOW", lS, null));
        game.usePowerUpCard("Player 1", "Newton", "YELLOW", lS, null);
        assertEquals(grid.getBoard().getArena()[2][1], p3.getCell());
        assertFalse(p1.getPowerUpCards().contains(newton));
        assertEquals(6, grid.getPowerUpDiscardPile().size());

        lS.clear();

        PowerUpCard teleporter = new Teleporter(BLUE);
        p1.addPowerUpCard(teleporter);
        lS.add("1");
        lS.add("1");
        assertTrue(game.isValidUsePowerUpCard("Player 1", "Teleporter", "BLUE", lS, null));
        game.usePowerUpCard("Player 1", "Teleporter", "BLUE", lS, null);
        assertEquals(grid.getBoard().getArena()[1][1], p1.getCell());
        assertFalse(p1.getPowerUpCards().contains(teleporter));
        assertEquals(7, grid.getPowerUpDiscardPile().size());

        lS.clear();


        //Reloading

        assertFalse(game.isValidReload("Player 1", "Machine Gun"));
        p1.addNewAC(new AmmoCube(BLUE));
        p1.addNewAC(new AmmoCube(Colour.RED));
        assertNotNull(p1.getAmmoCubes()[0]);
        assertNull(p1.getAmmoCubes()[1]);
        assertNull(p1.getAmmoCubes()[2]);
        assertNotNull(p1.getAmmoCubes()[3]);
        assertNull(p1.getAmmoCubes()[4]);
        assertNull(p1.getAmmoCubes()[5]);
        assertNull(p1.getAmmoCubes()[6]);
        assertNull(p1.getAmmoCubes()[7]);
        assertNull(p1.getAmmoCubes()[8]);


        assertFalse(p1.getWeaponCards().get(0).isReloaded());
        assertTrue(game.isValidReload("Player 1", "Machine Gun"));
        game.reload("Player 1", "Machine Gun", 1);
        assertTrue(p1.getWeaponCards().get(0).isReloaded());
        for (AmmoCube ac : p1.getAmmoCubes()) {
            assertNull(ac);
        }

        assertEquals(GameState.RELOADED, game.getGameState());


        //Scoring

        grid.damage(p3, p2, 1);
        grid.damage(p1, p2, 9);
        grid.damage(p1, p3, 9);

        assertEquals(2, p1.getPlayerBoard().getMarks().size());
        assertEquals(p2.getC(), p1.getPlayerBoard().getMarks().get(0).getC());
        assertEquals(p2.getC(), p1.getPlayerBoard().getMarks().get(1).getC());

        assertTrue(game.isValidScoring());
        game.scoring();
        assertEquals(2, game.getDeadList().size());
        assertTrue(game.getDeadList().contains(p2.getNickName()));
        assertTrue(game.getDeadList().contains(p3.getNickName()));
        assertEquals(19, p1.getScore());
        assertEquals(6, p3.getScore());

        assertEquals(2, grid.getBoard().getK().getSkulls()[0]);
        assertEquals(1, grid.getBoard().getK().getSkulls()[1]);
        assertEquals(0, grid.getBoard().getK().getSkulls()[2]);
        assertEquals(0, grid.getBoard().getK().getSkulls()[3]);
        assertEquals(0, grid.getBoard().getK().getSkulls()[4]);
        assertEquals(0, grid.getBoard().getK().getSkulls()[5]);
        assertEquals(0, grid.getBoard().getK().getSkulls()[6]);
        assertEquals(0, grid.getBoard().getK().getSkulls()[7]);
        assertEquals(p1.getC(), grid.getBoard().getK().getC()[0]);
        assertEquals(p1.getC(), grid.getBoard().getK().getC()[1]);

        assertEquals(6, p2.getPlayerBoard().getPoints().getPoints().size());
        assertEquals(6, p3.getPlayerBoard().getPoints().getPoints().size());

        for (int i = 0; i < 12; i++) {
            assertNull(p2.getPlayerBoard().getDamage().getDamageTokens()[i]);
            assertNull(p3.getPlayerBoard().getDamage().getDamageTokens()[i]);
        }

        assertEquals(1, p2.getPowerUpCards().size());
        assertEquals(1, p3.getPowerUpCards().size());

        //assertEquals(GameState.DEATH, game.getGameState());


        //Discard card for new spawn point

        p2.getPowerUpCards().clear();
        p3.getPowerUpCards().clear();

        PowerUpCard targetingScope2 = new TargetingScope(BLUE);

        p2.addPowerUpCard(targetingScope2);
        assertTrue(game.isValidDiscardCardForSpawnPoint("Player 2", "Targeting Scope", "BLUE"));
        game.discardCardForSpawnPoint("Player 2", "Targeting Scope", "BLUE");
        assertEquals(p2.getCell(), grid.getBoard().getArena()[0][2]);
        assertFalse(p2.getPowerUpCards().contains(targetingScope));
        assertEquals(8, grid.getPowerUpDiscardPile().size());
        assertTrue(grid.getPowerUpDiscardPile().contains(targetingScope2));

        //assertEquals(GameState.DEATH, game.getGameState());
        assertEquals(1, game.getDeadList().size());
        assertTrue(game.getDeadList().contains(p3.getNickName()));


        PowerUpCard tagbackGrenade2 = new TagbackGrenade(Colour.RED);
        p3.addPowerUpCard(tagbackGrenade2);
        assertTrue(game.isValidDiscardCardForSpawnPoint("Player 3", "Tagback Grenade", "RED"));
        game.discardCardForSpawnPoint("Player 3", "Tagback Grenade", "RED");
        assertEquals(p3.getCell(), grid.getBoard().getArena()[1][0]);
        assertFalse(p3.getPowerUpCards().contains(tagbackGrenade2));
        assertEquals(9, grid.getPowerUpDiscardPile().size());
        assertTrue(grid.getPowerUpDiscardPile().contains(tagbackGrenade2));

        //assertEquals(GameState.ENDTURN, game.getGameState());
        assertTrue(game.getDeadList().isEmpty());


        //Replace

        //assertTrue(game.isValidToReplace());
        game.replace();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                if (grid.getBoard().getArena()[i][j].getStatus() == 0)
                    assertNotNull(grid.getBoard().getArena()[i][j].getA());
            }
        }
        assertNotNull(grid.getBoard().getW1().getCard1());
        assertNotNull(grid.getBoard().getW1().getCard2());
        assertNotNull(grid.getBoard().getW1().getCard3());
        assertNotNull(grid.getBoard().getW2().getCard1());
        assertNotNull(grid.getBoard().getW2().getCard2());
        assertNotNull(grid.getBoard().getW2().getCard3());
        assertNotNull(grid.getBoard().getW3().getCard1());
        assertNotNull(grid.getBoard().getW3().getCard2());
        assertNotNull(grid.getBoard().getW3().getCard3());

        assertFalse(game.isFinalFrenzy());
        assertEquals(GameState.STARTTURN, game.getGameState());


        //Final Frenzy

        game.getGrid().getBoard().getK().setSkulls(new int[]{1, 2, 2, 1, 1, 2, 1, 1});
        game.replace();
        assertTrue(game.isFinalFrenzy());

        lS.clear();
        p1.setTurnFinalFrenzy(0);
        lS.add("1");
        lS.add("2");
        assertTrue(game.isValidFinalFrenzyAction(p1.getNickName(), lS));

        lS.clear();
        p2.setTurnFinalFrenzy(1);
        lS.add("4");
        assertTrue(game.isValidFinalFrenzyAction(p2.getNickName(), lS));

        lS.clear();
        p3.setTurnFinalFrenzy(1);
        lS.add("1");
        lS.add("3");
        assertFalse(game.isValidFinalFrenzyAction(p3.getNickName(), lS));
    }

    @Test
    void GameGrabShootTest() throws RemoteException {
        Game game = new Game(iD, server);
        Grid grid = game.getGrid();

        game.gameStart("Player 1", BLUE);
        Player p1 = grid.getPlayerObject("Player 1");

        game.addPlayer("Player 2", YELLOW);
        Player p2 = grid.getPlayerObject("Player 2");

        game.addPlayer("Player 3", GREEN);
        Player p3 = grid.getPlayerObject("Player 3");

        game.receiveType(1);

        game.giveTwoPUCard("Player 1");
        System.out.print("\nPowerUpCard picked from the deck for Player 1: " + p1.getPowerUpCards().get(0).getCardName() + " coloured " + p1.getPowerUpCards().get(0).getC().getAbbreviation() + ", and " + p1.getPowerUpCards().get(1).getCardName() + " coloured " + p1.getPowerUpCards().get(1).getC().getAbbreviation());
        game.pickAndDiscardCard("Player 1", p1.getPowerUpCards().get(0).getCardName(), p1.getPowerUpCards().get(0).getC().getAbbreviation());

        game.giveTwoPUCard("Player 2");
        System.out.print("\nPowerUpCard picked from the deck for Player 2: " + p2.getPowerUpCards().get(0).getCardName() + " coloured " + p2.getPowerUpCards().get(0).getC().getAbbreviation() + ", and " + p2.getPowerUpCards().get(1).getCardName() + " coloured " + p2.getPowerUpCards().get(1).getC().getAbbreviation());
        game.pickAndDiscardCard("Player 2", p2.getPowerUpCards().get(1).getCardName(), p2.getPowerUpCards().get(1).getC().getAbbreviation());

        //Initialization finished
        //First Action: Grab

        p1.changeCell(grid.getBoard().getArena()[1][0]);
        p2.changeCell(grid.getBoard().getArena()[0][2]);

        AmmoCard ammoCard1 = new PRB();
        grid.getBoard().getArena()[2][0].setA(ammoCard1);

        List<Integer> directions1 = new LinkedList<>();
        directions1.add(3);
        List<Colour> lA = new LinkedList<>();
        List<String> lP = new LinkedList<>();
        List<String> lPColourInput = new LinkedList<>();
        assertTrue(game.isValidFirstActionGrab(p1.getNickName(), directions1, "", "", lA, lP, lPColourInput));
        game.firstActionGrab(p1.getNickName(), directions1, "", lA, lP, lPColourInput);

        assertNotNull(p1.getAmmoCubes()[0]);
        assertNotNull(p1.getAmmoCubes()[1]);
        assertNull(p1.getAmmoCubes()[2]);
        assertNotNull(p1.getAmmoCubes()[3]);
        assertNotNull(p1.getAmmoCubes()[4]);
        assertNull(p1.getAmmoCubes()[5]);
        assertNotNull(p1.getAmmoCubes()[6]);
        assertNull(p1.getAmmoCubes()[7]);
        assertNull(p1.getAmmoCubes()[8]);

        assertEquals(2, p1.getPowerUpCards().size());

        assertEquals(GameState.ACTION1, game.getGameState());


        //Second Action: Shoot with Adrenaline

        grid.damage(p1, p3, 6);     //now p3 has unlocked Adrenaline 2
        p3.changeCell(grid.getBoard().getArena()[0][0]);
        p1.changeCell(grid.getBoard().getArena()[0][2]);
        p2.changeCell(grid.getBoard().getArena()[0][2]);

        WeaponCard cyberblade = new Cyberblade();
        p3.addWeaponCard(cyberblade);
        cyberblade.reload();
        PowerUpCard newton = new Newton(YELLOW);
        p3.addPowerUpCard(newton);

        List<Integer> lI = new LinkedList<>();
        lI.add(2);
        lI.add(1);
        lI.add(3);
        List<String> lS = new LinkedList<>();
        lS.add("Player 1");
        lS.add("2");
        lS.add("Player 2");

        lP.add("Newton");
        lPColourInput.add("YELLOW");

        assertTrue(game.isValidSecondActionShoot("Player 3", "Cyberblade", lI, lS, 2, lA, lP, lPColourInput));
        game.secondActionShoot("Player 3", "Cyberblade", lI, lS, 2, lA, lP, lPColourInput);
        assertEquals(p3.getC(), p1.getPlayerBoard().getDamage().getDamageTokens()[0].getC());
        assertEquals(p3.getC(), p1.getPlayerBoard().getDamage().getDamageTokens()[1].getC());
        assertEquals(p3.getC(), p2.getPlayerBoard().getDamage().getDamageTokens()[0].getC());
        assertEquals(p3.getC(), p2.getPlayerBoard().getDamage().getDamageTokens()[1].getC());
    }

    @Test
    void GameGrabShootTest2() throws RemoteException {
        Game game = new Game(iD, server);
        Grid grid = game.getGrid();

        game.gameStart("Player 1", BLUE);
        Player p1 = grid.getPlayerObject("Player 1");

        game.addPlayer("Player 2", YELLOW);
        Player p2 = grid.getPlayerObject("Player 2");

        game.addPlayer("Player 3", GREEN);
        Player p3 = grid.getPlayerObject("Player 3");

        game.receiveType(1);

        game.giveTwoPUCard("Player 1");
        System.out.print("\nPowerUpCard picked from the deck for Player 1: " + p1.getPowerUpCards().get(0).getCardName() + " coloured " + p1.getPowerUpCards().get(0).getC().getAbbreviation() + ", and " + p1.getPowerUpCards().get(1).getCardName() + " coloured " + p1.getPowerUpCards().get(1).getC().getAbbreviation());
        game.pickAndDiscardCard("Player 1", p1.getPowerUpCards().get(0).getCardName(), p1.getPowerUpCards().get(0).getC().getAbbreviation());

        game.giveTwoPUCard("Player 2");
        System.out.print("\nPowerUpCard picked from the deck for Player 2: " + p2.getPowerUpCards().get(0).getCardName() + " coloured " + p2.getPowerUpCards().get(0).getC().getAbbreviation() + ", and " + p2.getPowerUpCards().get(1).getCardName() + " coloured " + p2.getPowerUpCards().get(1).getC().getAbbreviation());
        game.pickAndDiscardCard("Player 2", p2.getPowerUpCards().get(1).getCardName(), p2.getPowerUpCards().get(1).getC().getAbbreviation());

        //Initialization finished
        //First Action: Grab

        /*p1.changeCell(grid.getBoard().getArena()[0][1]);
        WeaponCard lockRifle = new LockRifle();
        grid.getBoard().getW1().setCard1(lockRifle);
        assertNotNull(grid.getWeaponCardObject("Lock Rifle"));

        List<Integer> directions1 = new LinkedList<>();
        directions1.add(2);
        List<Colour> lA = new LinkedList<>();
        lA.add(Colour.BLUE);
        List<String> lP = new LinkedList<>();
        List<String> lPColourInput = new LinkedList<>();
        assertTrue(game.isValidFirstActionGrab(p1.getNickName(), directions1, "Lock Rifle", "1", lA, lP, lPColourInput));
        game.firstActionGrab(p1.getNickName(), directions1, "Lock Rifle", lA, lP, lPColourInput);

        assertEquals(1, p1.getWeaponCards().size());
        assertTrue(p1.getWeaponCards().contains(lockRifle));

        assertNotNull(p1.getAmmoCubes()[0]);
        assertNull(p1.getAmmoCubes()[1]);
        assertNull(p1.getAmmoCubes()[2]);
        assertNull(p1.getAmmoCubes()[3]);
        assertNull(p1.getAmmoCubes()[4]);
        assertNull(p1.getAmmoCubes()[5]);
        assertNotNull(p1.getAmmoCubes()[6]);
        assertNull(p1.getAmmoCubes()[7]);
        assertNull(p1.getAmmoCubes()[8]);*/

        p2.changeCell(grid.getBoard().getArena()[0][2]);
        WeaponCard railgun = new Railgun();
        grid.getBoard().getW1().setCard1(railgun);
        assertNotNull(grid.getWeaponCardObject("Railgun"));

        List<Integer> directions1 = new LinkedList<>();
        List<Colour> lA = new LinkedList<>();
        lA.add(BLUE);
        lA.add(YELLOW);
        List<String> lP = new LinkedList<>();
        List<String> lPColourInput = new LinkedList<>();
        assertTrue(game.isValidFirstActionGrab(p2.getNickName(), directions1, "Railgun", "1", lA, lP, lPColourInput));
        game.firstActionGrab(p2.getNickName(), directions1, "Railgun", lA, lP, lPColourInput);

        /*p3.changeCell(grid.getBoard().getArena()[0][2]);
        WeaponCard electroscythe = new Electroscythe();
        grid.getBoard().getW1().setCard1(electroscythe);

        List<Integer> directions1 = new LinkedList<>();
        List<Colour> lA = new LinkedList<>();
        List<String> lP = new LinkedList<>();
        List<String> lPColourInput = new LinkedList<>();
        assertEquals(GameState.STARTTURN, game.getGameState());

        assertTrue(game.isValidFirstActionGrab(p3.getNickName(), directions1, "Electroscythe", "1", lA, lP, lPColourInput));
        game.firstActionGrab(p3.getNickName(), directions1, "Electroscythe", lA, lP, lPColourInput);*/
    }

    @Test
    void GameShootTHORTest() throws RemoteException {
        Game game = new Game(iD, server);
        Grid grid = game.getGrid();

        game.gameStart("Player 1", BLUE);
        Player p1 = grid.getPlayerObject("Player 1");

        game.addPlayer("Player 2", YELLOW);
        Player p2 = grid.getPlayerObject("Player 2");

        game.addPlayer("Player 3", GREEN);
        Player p3 = grid.getPlayerObject("Player 3");

        game.addPlayer("Player 4", BLACK);
        Player p4 = grid.getPlayerObject("Player 4");

        game.receiveType(4);

        game.giveTwoPUCard("Player 1");
        System.out.print("\nPowerUpCard picked from the deck for Player 1: " + p1.getPowerUpCards().get(0).getCardName() + " coloured " + p1.getPowerUpCards().get(0).getC().getAbbreviation() + ", and " + p1.getPowerUpCards().get(1).getCardName() + " coloured " + p1.getPowerUpCards().get(1).getC().getAbbreviation());
        game.pickAndDiscardCard("Player 1", p1.getPowerUpCards().get(0).getCardName(), p1.getPowerUpCards().get(0).getC().getAbbreviation());

        game.giveTwoPUCard("Player 2");
        System.out.print("\nPowerUpCard picked from the deck for Player 2: " + p2.getPowerUpCards().get(0).getCardName() + " coloured " + p2.getPowerUpCards().get(0).getC().getAbbreviation() + ", and " + p2.getPowerUpCards().get(1).getCardName() + " coloured " + p2.getPowerUpCards().get(1).getC().getAbbreviation());
        game.pickAndDiscardCard("Player 2", p2.getPowerUpCards().get(1).getCardName(), p2.getPowerUpCards().get(1).getC().getAbbreviation());

        game.giveTwoPUCard("Player 3");
        System.out.print("\nPowerUpCard picked from the deck for Player 3: " + p3.getPowerUpCards().get(0).getCardName() + " coloured " + p3.getPowerUpCards().get(0).getC().getAbbreviation() + ", and " + p3.getPowerUpCards().get(1).getCardName() + " coloured " + p3.getPowerUpCards().get(1).getC().getAbbreviation());
        game.pickAndDiscardCard("Player 3", p3.getPowerUpCards().get(1).getCardName(), p3.getPowerUpCards().get(1).getC().getAbbreviation());

        game.giveTwoPUCard("Player 4");
        System.out.print("\nPowerUpCard picked from the deck for Player 4: " + p4.getPowerUpCards().get(0).getCardName() + " coloured " + p4.getPowerUpCards().get(0).getC().getAbbreviation() + ", and " + p4.getPowerUpCards().get(1).getCardName() + " coloured " + p4.getPowerUpCards().get(1).getC().getAbbreviation());
        game.pickAndDiscardCard("Player 4", p4.getPowerUpCards().get(1).getCardName(), p4.getPowerUpCards().get(1).getC().getAbbreviation());

        p1.changeCell(grid.getBoard().getArena()[1][0]);
        p2.changeCell(grid.getBoard().getArena()[2][1]);
        p3.changeCell(grid.getBoard().getArena()[1][1]);
        p4.changeCell(grid.getBoard().getArena()[0][2]);

        assertEquals(grid.getBoard().getArena()[1][0], p1.getCell());
        assertEquals(grid.getBoard().getArena()[2][1], p2.getCell());
        assertEquals(grid.getBoard().getArena()[1][1], p3.getCell());
        assertEquals(grid.getBoard().getArena()[0][2], p4.getCell());

        assertTrue(grid.isInViewZone(p1, p2));
        assertTrue(grid.isInViewZone(p2, p3));
        assertTrue(grid.isInViewZone(p3, p4));

        WeaponCard thor = new THOR();
        p1.addWeaponCard(thor);
        thor.reload();
        assertTrue(thor.isReloaded());
        AmmoCube aBlu = new AmmoCube(Colour.BLUE);
        p1.addNewAC(aBlu);
        p1.addNewAC(aBlu);
        System.out.println("\n" + p1.getAmmoCubes()[0].getC().toString());
        assertNull(p1.getAmmoCubes()[1]);
        assertNull(p1.getAmmoCubes()[2]);
        System.out.println("\n" + p1.getAmmoCubes()[3].getC().toString());
        System.out.println("\n" + p1.getAmmoCubes()[4].getC().toString());
        System.out.println("\n" + p1.getAmmoCubes()[5].getC().toString());

        List<Integer> lI = new LinkedList<>();
        lI.add(1);
        lI.add(2);
        lI.add(3);
        List<String> lS = new LinkedList<>();
        lS.add("Player 2");
        lS.add("Player 3");
        lS.add("Player 4");
        List<Colour> lA = new LinkedList<>();
        lA.add(Colour.valueOf("BLUE"));
        lA.add(Colour.valueOf("BLUE"));
        List<String> lP = new LinkedList<>();
        List<String> lPColourInput = new LinkedList<>();

        assertEquals(GameState.STARTTURN, game.getGameState());

        assertTrue(game.isValidFirstActionShoot("Player 1", "T.H.O.R.", lI, lS, 0, lA, lP, lPColourInput));
        game.firstActionShoot("Player 1", "T.H.O.R.", lI, lS, 0, lA, lP, lPColourInput);
    }

    @Test
    void GameShootZX2Test() throws RemoteException {
        Game game = new Game(iD, server);
        Grid grid = game.getGrid();

        game.gameStart("Player 1", BLUE);
        Player p1 = grid.getPlayerObject("Player 1");

        game.addPlayer("Player 2", YELLOW);
        Player p2 = grid.getPlayerObject("Player 2");

        game.addPlayer("Player 3", GREEN);
        Player p3 = grid.getPlayerObject("Player 3");

        game.addPlayer("Player 4", BLACK);
        Player p4 = grid.getPlayerObject("Player 4");

        game.receiveType(1);

        game.giveTwoPUCard("Player 1");
        System.out.print("\nPowerUpCard picked from the deck for Player 1: " + p1.getPowerUpCards().get(0).getCardName() + " coloured " + p1.getPowerUpCards().get(0).getC().getAbbreviation() + ", and " + p1.getPowerUpCards().get(1).getCardName() + " coloured " + p1.getPowerUpCards().get(1).getC().getAbbreviation());
        game.pickAndDiscardCard("Player 1", p1.getPowerUpCards().get(0).getCardName(), p1.getPowerUpCards().get(0).getC().getAbbreviation());

        game.giveTwoPUCard("Player 2");
        System.out.print("\nPowerUpCard picked from the deck for Player 2: " + p2.getPowerUpCards().get(0).getCardName() + " coloured " + p2.getPowerUpCards().get(0).getC().getAbbreviation() + ", and " + p2.getPowerUpCards().get(1).getCardName() + " coloured " + p2.getPowerUpCards().get(1).getC().getAbbreviation());
        game.pickAndDiscardCard("Player 2", p2.getPowerUpCards().get(1).getCardName(), p2.getPowerUpCards().get(1).getC().getAbbreviation());

        game.giveTwoPUCard("Player 3");
        System.out.print("\nPowerUpCard picked from the deck for Player 3: " + p3.getPowerUpCards().get(0).getCardName() + " coloured " + p3.getPowerUpCards().get(0).getC().getAbbreviation() + ", and " + p3.getPowerUpCards().get(1).getCardName() + " coloured " + p3.getPowerUpCards().get(1).getC().getAbbreviation());
        game.pickAndDiscardCard("Player 3", p3.getPowerUpCards().get(1).getCardName(), p3.getPowerUpCards().get(1).getC().getAbbreviation());

        game.giveTwoPUCard("Player 4");
        System.out.print("\nPowerUpCard picked from the deck for Player 4: " + p4.getPowerUpCards().get(0).getCardName() + " coloured " + p4.getPowerUpCards().get(0).getC().getAbbreviation() + ", and " + p4.getPowerUpCards().get(1).getCardName() + " coloured " + p4.getPowerUpCards().get(1).getC().getAbbreviation());
        game.pickAndDiscardCard("Player 4", p4.getPowerUpCards().get(1).getCardName(), p4.getPowerUpCards().get(1).getC().getAbbreviation());

        p1.changeCell(grid.getBoard().getArena()[1][1]);
        p2.changeCell(grid.getBoard().getArena()[1][2]);
        p3.changeCell(grid.getBoard().getArena()[0][1]);
        p4.changeCell(grid.getBoard().getArena()[0][2]);

        assertEquals(grid.getBoard().getArena()[1][1], p1.getCell());
        assertEquals(grid.getBoard().getArena()[1][2], p2.getCell());
        assertEquals(grid.getBoard().getArena()[0][1], p3.getCell());
        assertEquals(grid.getBoard().getArena()[0][2], p4.getCell());

        assertTrue(grid.isInViewZone(p1, p2));
        assertTrue(grid.isInViewZone(p1, p3));
        assertTrue(grid.isInViewZone(p1, p4));

        WeaponCard zx2 = new ZX2();
        p1.addWeaponCard(zx2);
        zx2.reload();
        assertTrue(zx2.isReloaded());


        List<Integer> lI = new LinkedList<>();
        //lI.add(1);
        lI.add(2);
        List<String> lS = new LinkedList<>();
        lS.add("Player 2");
        lS.add("Player 3");
        lS.add("Player 4");
        List<Colour> lA = new LinkedList<>();
        List<String> lP = new LinkedList<>();
        List<String> lPColourInput = new LinkedList<>();

        assertEquals(GameState.STARTTURN, game.getGameState());

        assertTrue(game.isValidFirstActionShoot("Player 1", "ZX-2", lI, lS, 0, lA, lP, lPColourInput));
        game.firstActionShoot("Player 1", "ZX-2", lI, lS, 0, lA, lP, lPColourInput);
    }

    @Test
    void GameShootWhisperTest() throws RemoteException {
        Game game = new Game(iD, server);
        Grid grid = game.getGrid();

        game.gameStart("Player 1", BLUE);
        Player p1 = grid.getPlayerObject("Player 1");

        game.addPlayer("Player 2", YELLOW);
        Player p2 = grid.getPlayerObject("Player 2");

        game.receiveType(1);

        game.giveTwoPUCard("Player 1");
        System.out.print("\nPowerUpCard picked from the deck for Player 1: " + p1.getPowerUpCards().get(0).getCardName() + " coloured " + p1.getPowerUpCards().get(0).getC().getAbbreviation() + ", and " + p1.getPowerUpCards().get(1).getCardName() + " coloured " + p1.getPowerUpCards().get(1).getC().getAbbreviation());
        game.pickAndDiscardCard("Player 1", p1.getPowerUpCards().get(0).getCardName(), p1.getPowerUpCards().get(0).getC().getAbbreviation());

        game.giveTwoPUCard("Player 2");
        System.out.print("\nPowerUpCard picked from the deck for Player 2: " + p2.getPowerUpCards().get(0).getCardName() + " coloured " + p2.getPowerUpCards().get(0).getC().getAbbreviation() + ", and " + p2.getPowerUpCards().get(1).getCardName() + " coloured " + p2.getPowerUpCards().get(1).getC().getAbbreviation());
        game.pickAndDiscardCard("Player 2", p2.getPowerUpCards().get(1).getCardName(), p2.getPowerUpCards().get(1).getC().getAbbreviation());

        p1.changeCell(grid.getBoard().getArena()[0][0]);
        p2.changeCell(grid.getBoard().getArena()[0][2]);

        assertEquals(grid.getBoard().getArena()[0][0], p1.getCell());
        assertEquals(grid.getBoard().getArena()[0][2], p2.getCell());

        assertTrue(grid.isInViewZone(p1, p2));

        WeaponCard whisper = new Whisper();
        p1.addWeaponCard(whisper);
        whisper.reload();
        assertTrue(whisper.isReloaded());


        List<Integer> lI = new LinkedList<>();
        List<String> lS = new LinkedList<>();
        lS.add("Player 2");
        List<Colour> lA = new LinkedList<>();
        List<String> lP = new LinkedList<>();
        List<String> lPColourInput = new LinkedList<>();

        assertEquals(GameState.STARTTURN, game.getGameState());

        assertTrue(game.isValidFirstActionShoot("Player 1", "Whisper", lI, lS, 0, lA, lP, lPColourInput));
        game.firstActionShoot("Player 1", "Whisper", lI, lS, 0, lA, lP, lPColourInput);
    }

    @Test
    void GameShootVortexCannonTest() throws RemoteException {
        Game game = new Game(iD, server);
        Grid grid = game.getGrid();

        game.gameStart("Player 1", BLUE);
        Player p1 = grid.getPlayerObject("Player 1");

        game.addPlayer("Player 2", YELLOW);
        Player p2 = grid.getPlayerObject("Player 2");

        game.addPlayer("Player 3", GREEN);
        Player p3 = grid.getPlayerObject("Player 3");

        game.addPlayer("Player 4", BLACK);
        Player p4 = grid.getPlayerObject("Player 4");

        game.receiveType(1);

        game.giveTwoPUCard("Player 1");
        System.out.print("\nPowerUpCard picked from the deck for Player 1: " + p1.getPowerUpCards().get(0).getCardName() + " coloured " + p1.getPowerUpCards().get(0).getC().getAbbreviation() + ", and " + p1.getPowerUpCards().get(1).getCardName() + " coloured " + p1.getPowerUpCards().get(1).getC().getAbbreviation());
        game.pickAndDiscardCard("Player 1", p1.getPowerUpCards().get(0).getCardName(), p1.getPowerUpCards().get(0).getC().getAbbreviation());

        game.giveTwoPUCard("Player 2");
        System.out.print("\nPowerUpCard picked from the deck for Player 2: " + p2.getPowerUpCards().get(0).getCardName() + " coloured " + p2.getPowerUpCards().get(0).getC().getAbbreviation() + ", and " + p2.getPowerUpCards().get(1).getCardName() + " coloured " + p2.getPowerUpCards().get(1).getC().getAbbreviation());
        game.pickAndDiscardCard("Player 2", p2.getPowerUpCards().get(1).getCardName(), p2.getPowerUpCards().get(1).getC().getAbbreviation());

        game.giveTwoPUCard("Player 3");
        System.out.print("\nPowerUpCard picked from the deck for Player 3: " + p3.getPowerUpCards().get(0).getCardName() + " coloured " + p3.getPowerUpCards().get(0).getC().getAbbreviation() + ", and " + p3.getPowerUpCards().get(1).getCardName() + " coloured " + p3.getPowerUpCards().get(1).getC().getAbbreviation());
        game.pickAndDiscardCard("Player 3", p3.getPowerUpCards().get(1).getCardName(), p3.getPowerUpCards().get(1).getC().getAbbreviation());

        game.giveTwoPUCard("Player 4");
        System.out.print("\nPowerUpCard picked from the deck for Player 4: " + p4.getPowerUpCards().get(0).getCardName() + " coloured " + p4.getPowerUpCards().get(0).getC().getAbbreviation() + ", and " + p4.getPowerUpCards().get(1).getCardName() + " coloured " + p4.getPowerUpCards().get(1).getC().getAbbreviation());
        game.pickAndDiscardCard("Player 4", p4.getPowerUpCards().get(1).getCardName(), p4.getPowerUpCards().get(1).getC().getAbbreviation());

        p1.changeCell(grid.getBoard().getArena()[0][0]);
        p2.changeCell(grid.getBoard().getArena()[0][2]);
        p3.changeCell(grid.getBoard().getArena()[1][1]);
        p4.changeCell(grid.getBoard().getArena()[1][1]);

        assertEquals(grid.getBoard().getArena()[0][0], p1.getCell());
        assertEquals(grid.getBoard().getArena()[0][2], p2.getCell());
        assertEquals(grid.getBoard().getArena()[1][1], p3.getCell());
        assertEquals(grid.getBoard().getArena()[1][1], p4.getCell());

        WeaponCard vortexCannon = new VortexCannon();
        p1.addWeaponCard(vortexCannon);
        vortexCannon.reload();
        assertTrue(vortexCannon.isReloaded());


        List<Integer> lI = new LinkedList<>();
        lI.add(1);
        lI.add(2);
        List<String> lS = new LinkedList<>();
        lS.add("Player 2");
        lS.add("0");
        lS.add("1");
        lS.add("Player 3");
        lS.add("Player 4");
        List<Colour> lA = new LinkedList<>();
        lA.add(RED);
        List<String> lP = new LinkedList<>();
        List<String> lPColourInput = new LinkedList<>();

        assertEquals(GameState.STARTTURN, game.getGameState());

        assertTrue(game.isValidFirstActionShoot("Player 1", "Vortex Cannon", lI, lS, 0, lA, lP, lPColourInput));
        game.firstActionShoot("Player 1", "Vortex Cannon", lI, lS, 0, lA, lP, lPColourInput);
    }

    @Test
    void GameShootTractorBeamCannonTest() throws RemoteException {
        Game game = new Game(iD, server);
        Grid grid = game.getGrid();

        game.gameStart("Player 1", BLUE);
        Player p1 = grid.getPlayerObject("Player 1");

        game.addPlayer("Player 2", YELLOW);
        Player p2 = grid.getPlayerObject("Player 2");

        game.receiveType(1);

        game.giveTwoPUCard("Player 1");
        System.out.print("\nPowerUpCard picked from the deck for Player 1: " + p1.getPowerUpCards().get(0).getCardName() + " coloured " + p1.getPowerUpCards().get(0).getC().getAbbreviation() + ", and " + p1.getPowerUpCards().get(1).getCardName() + " coloured " + p1.getPowerUpCards().get(1).getC().getAbbreviation());
        game.pickAndDiscardCard("Player 1", p1.getPowerUpCards().get(0).getCardName(), p1.getPowerUpCards().get(0).getC().getAbbreviation());

        game.giveTwoPUCard("Player 2");
        System.out.print("\nPowerUpCard picked from the deck for Player 2: " + p2.getPowerUpCards().get(0).getCardName() + " coloured " + p2.getPowerUpCards().get(0).getC().getAbbreviation() + ", and " + p2.getPowerUpCards().get(1).getCardName() + " coloured " + p2.getPowerUpCards().get(1).getC().getAbbreviation());
        game.pickAndDiscardCard("Player 2", p2.getPowerUpCards().get(1).getCardName(), p2.getPowerUpCards().get(1).getC().getAbbreviation());

        p1.changeCell(grid.getBoard().getArena()[0][0]);
        p2.changeCell(grid.getBoard().getArena()[1][1]);

        assertEquals(grid.getBoard().getArena()[0][0], p1.getCell());
        assertEquals(grid.getBoard().getArena()[1][1], p2.getCell());

        WeaponCard tractorBeam = new TractorBeam();
        p1.addWeaponCard(tractorBeam);
        tractorBeam.reload();
        assertTrue(tractorBeam.isReloaded());


        List<Integer> lI = new LinkedList<>();
        //lI.add(1);
        lI.add(2);
        List<String> lS = new LinkedList<>();
        lS.add("Player 2");
        lS.add("");
        lS.add("");
        List<Colour> lA = new LinkedList<>();
        lA.add(RED);
        lA.add(YELLOW);
        List<String> lP = new LinkedList<>();
        List<String> lPColourInput = new LinkedList<>();

        assertEquals(GameState.STARTTURN, game.getGameState());

        assertTrue(game.isValidFirstActionShoot("Player 1", "Tractor Beam", lI, lS, 0, lA, lP, lPColourInput));
        game.firstActionShoot("Player 1", "Tractor Beam", lI, lS, 0, lA, lP, lPColourInput);
    }

    @Test
    void GameShootSledgehammerTest() throws RemoteException {
        Game game = new Game(iD, server);
        Grid grid = game.getGrid();

        game.gameStart("Player 1", BLUE);
        Player p1 = grid.getPlayerObject("Player 1");

        game.addPlayer("Player 2", YELLOW);
        Player p2 = grid.getPlayerObject("Player 2");

        game.receiveType(1);

        game.giveTwoPUCard("Player 1");
        System.out.print("\nPowerUpCard picked from the deck for Player 1: " + p1.getPowerUpCards().get(0).getCardName() + " coloured " + p1.getPowerUpCards().get(0).getC().getAbbreviation() + ", and " + p1.getPowerUpCards().get(1).getCardName() + " coloured " + p1.getPowerUpCards().get(1).getC().getAbbreviation());
        game.pickAndDiscardCard("Player 1", p1.getPowerUpCards().get(0).getCardName(), p1.getPowerUpCards().get(0).getC().getAbbreviation());

        game.giveTwoPUCard("Player 2");
        System.out.print("\nPowerUpCard picked from the deck for Player 2: " + p2.getPowerUpCards().get(0).getCardName() + " coloured " + p2.getPowerUpCards().get(0).getC().getAbbreviation() + ", and " + p2.getPowerUpCards().get(1).getCardName() + " coloured " + p2.getPowerUpCards().get(1).getC().getAbbreviation());
        game.pickAndDiscardCard("Player 2", p2.getPowerUpCards().get(1).getCardName(), p2.getPowerUpCards().get(1).getC().getAbbreviation());

        p1.changeCell(grid.getBoard().getArena()[0][0]);
        p2.changeCell(grid.getBoard().getArena()[0][0]);

        assertEquals(grid.getBoard().getArena()[0][0], p1.getCell());
        assertEquals(grid.getBoard().getArena()[0][0], p2.getCell());

        WeaponCard sledgehammer = new Sledgehammer();
        p1.addWeaponCard(sledgehammer);
        sledgehammer.reload();
        assertTrue(sledgehammer.isReloaded());


        List<Integer> lI = new LinkedList<>();
        //lI.add(1);
        lI.add(2);
        List<String> lS = new LinkedList<>();
        lS.add("Player 2");
        lS.add("1");
        lS.add("2");
        List<Colour> lA = new LinkedList<>();
        lA.add(RED);
        List<String> lP = new LinkedList<>();
        List<String> lPColourInput = new LinkedList<>();

        assertEquals(GameState.STARTTURN, game.getGameState());

        assertTrue(game.isValidFirstActionShoot("Player 1", "Sledgehammer", lI, lS, 0, lA, lP, lPColourInput));
        game.firstActionShoot("Player 1", "Sledgehammer", lI, lS, 0, lA, lP, lPColourInput);

        assertEquals(grid.getBoard().getArena()[0][1], p2.getCell());
    }

    @Test
    void GameShootShotgunTest() throws RemoteException {
        Game game = new Game(iD, server);
        Grid grid = game.getGrid();

        game.gameStart("Player 1", BLUE);
        Player p1 = grid.getPlayerObject("Player 1");

        game.addPlayer("Player 2", YELLOW);
        Player p2 = grid.getPlayerObject("Player 2");

        game.receiveType(1);

        game.giveTwoPUCard("Player 1");
        System.out.print("\nPowerUpCard picked from the deck for Player 1: " + p1.getPowerUpCards().get(0).getCardName() + " coloured " + p1.getPowerUpCards().get(0).getC().getAbbreviation() + ", and " + p1.getPowerUpCards().get(1).getCardName() + " coloured " + p1.getPowerUpCards().get(1).getC().getAbbreviation());
        game.pickAndDiscardCard("Player 1", p1.getPowerUpCards().get(0).getCardName(), p1.getPowerUpCards().get(0).getC().getAbbreviation());

        game.giveTwoPUCard("Player 2");
        System.out.print("\nPowerUpCard picked from the deck for Player 2: " + p2.getPowerUpCards().get(0).getCardName() + " coloured " + p2.getPowerUpCards().get(0).getC().getAbbreviation() + ", and " + p2.getPowerUpCards().get(1).getCardName() + " coloured " + p2.getPowerUpCards().get(1).getC().getAbbreviation());
        game.pickAndDiscardCard("Player 2", p2.getPowerUpCards().get(1).getCardName(), p2.getPowerUpCards().get(1).getC().getAbbreviation());

        p1.changeCell(grid.getBoard().getArena()[0][0]);
        p2.changeCell(grid.getBoard().getArena()[1][0]);

        assertEquals(grid.getBoard().getArena()[0][0], p1.getCell());
        assertEquals(grid.getBoard().getArena()[1][0], p2.getCell());

        WeaponCard shotgun = new Shotgun();
        p1.addWeaponCard(shotgun);
        shotgun.reload();
        assertTrue(shotgun.isReloaded());


        List<Integer> lI = new LinkedList<>();
        //lI.add(1);
        //lI.add(2);
        lI.add(3);
        List<String> lS = new LinkedList<>();
        lS.add("Player 2");
        lS.add("3");
        List<Colour> lA = new LinkedList<>();
        List<String> lP = new LinkedList<>();
        List<String> lPColourInput = new LinkedList<>();

        assertEquals(GameState.STARTTURN, game.getGameState());

        assertTrue(game.isValidFirstActionShoot("Player 1", "Shotgun", lI, lS, 0, lA, lP, lPColourInput));
        game.firstActionShoot("Player 1", "Shotgun", lI, lS, 0, lA, lP, lPColourInput);

        assertEquals(grid.getBoard().getArena()[1][0], p2.getCell());
    }

    @Test
    void GameShootShockwaveTest() throws RemoteException {
        Game game = new Game(iD, server);
        Grid grid = game.getGrid();

        game.gameStart("Player 1", BLUE);
        Player p1 = grid.getPlayerObject("Player 1");

        game.addPlayer("Player 2", YELLOW);
        Player p2 = grid.getPlayerObject("Player 2");

        game.addPlayer("Player 3", GREEN);
        Player p3 = grid.getPlayerObject("Player 3");

        game.addPlayer("Player 4", BLACK);
        Player p4 = grid.getPlayerObject("Player 4");

        game.receiveType(1);

        game.giveTwoPUCard("Player 1");
        System.out.print("\nPowerUpCard picked from the deck for Player 1: " + p1.getPowerUpCards().get(0).getCardName() + " coloured " + p1.getPowerUpCards().get(0).getC().getAbbreviation() + ", and " + p1.getPowerUpCards().get(1).getCardName() + " coloured " + p1.getPowerUpCards().get(1).getC().getAbbreviation());
        game.pickAndDiscardCard("Player 1", p1.getPowerUpCards().get(0).getCardName(), p1.getPowerUpCards().get(0).getC().getAbbreviation());

        game.giveTwoPUCard("Player 2");
        System.out.print("\nPowerUpCard picked from the deck for Player 2: " + p2.getPowerUpCards().get(0).getCardName() + " coloured " + p2.getPowerUpCards().get(0).getC().getAbbreviation() + ", and " + p2.getPowerUpCards().get(1).getCardName() + " coloured " + p2.getPowerUpCards().get(1).getC().getAbbreviation());
        game.pickAndDiscardCard("Player 2", p2.getPowerUpCards().get(1).getCardName(), p2.getPowerUpCards().get(1).getC().getAbbreviation());

        game.giveTwoPUCard("Player 3");
        System.out.print("\nPowerUpCard picked from the deck for Player 3: " + p3.getPowerUpCards().get(0).getCardName() + " coloured " + p3.getPowerUpCards().get(0).getC().getAbbreviation() + ", and " + p3.getPowerUpCards().get(1).getCardName() + " coloured " + p3.getPowerUpCards().get(1).getC().getAbbreviation());
        game.pickAndDiscardCard("Player 3", p3.getPowerUpCards().get(1).getCardName(), p3.getPowerUpCards().get(1).getC().getAbbreviation());

        game.giveTwoPUCard("Player 4");
        System.out.print("\nPowerUpCard picked from the deck for Player 4: " + p4.getPowerUpCards().get(0).getCardName() + " coloured " + p4.getPowerUpCards().get(0).getC().getAbbreviation() + ", and " + p4.getPowerUpCards().get(1).getCardName() + " coloured " + p4.getPowerUpCards().get(1).getC().getAbbreviation());
        game.pickAndDiscardCard("Player 4", p4.getPowerUpCards().get(1).getCardName(), p4.getPowerUpCards().get(1).getC().getAbbreviation());

        p1.changeCell(grid.getBoard().getArena()[1][2]);
        p2.changeCell(grid.getBoard().getArena()[1][1]);
        p3.changeCell(grid.getBoard().getArena()[0][2]);
        p4.changeCell(grid.getBoard().getArena()[1][3]);

        assertEquals(grid.getBoard().getArena()[1][2], p1.getCell());
        assertEquals(grid.getBoard().getArena()[1][1], p2.getCell());
        assertEquals(grid.getBoard().getArena()[0][2], p3.getCell());
        assertEquals(grid.getBoard().getArena()[1][3], p4.getCell());

        WeaponCard shockwave = new Shockwave();
        p1.addWeaponCard(shockwave);
        shockwave.reload();
        assertTrue(shockwave.isReloaded());


        List<Integer> lI = new LinkedList<>();
        //lI.add(1);
        lI.add(2);
        List<String> lS = new LinkedList<>();
        lS.add("");
        lS.add("");
        lS.add("");
        List<Colour> lA = new LinkedList<>();
        lA.add(YELLOW);
        List<String> lP = new LinkedList<>();
        List<String> lPColourInput = new LinkedList<>();

        assertEquals(GameState.STARTTURN, game.getGameState());

        assertTrue(game.isValidFirstActionShoot("Player 1", "Shockwave", lI, lS, 0, lA, lP, lPColourInput));
        game.firstActionShoot("Player 1", "Shockwave", lI, lS, 0, lA, lP, lPColourInput);
    }

    @Test
    void GameShootRocketLauncherTest() throws RemoteException {
        Game game = new Game(iD, server);
        Grid grid = game.getGrid();

        game.gameStart("Player 1", BLUE);
        Player p1 = grid.getPlayerObject("Player 1");

        game.addPlayer("Player 2", YELLOW);
        Player p2 = grid.getPlayerObject("Player 2");

        game.addPlayer("Player 3", GREEN);
        Player p3 = grid.getPlayerObject("Player 3");

        game.addPlayer("Player 4", BLACK);
        Player p4 = grid.getPlayerObject("Player 4");

        game.receiveType(1);

        game.giveTwoPUCard("Player 1");
        System.out.print("\nPowerUpCard picked from the deck for Player 1: " + p1.getPowerUpCards().get(0).getCardName() + " coloured " + p1.getPowerUpCards().get(0).getC().getAbbreviation() + ", and " + p1.getPowerUpCards().get(1).getCardName() + " coloured " + p1.getPowerUpCards().get(1).getC().getAbbreviation());
        game.pickAndDiscardCard("Player 1", p1.getPowerUpCards().get(0).getCardName(), p1.getPowerUpCards().get(0).getC().getAbbreviation());

        game.giveTwoPUCard("Player 2");
        System.out.print("\nPowerUpCard picked from the deck for Player 2: " + p2.getPowerUpCards().get(0).getCardName() + " coloured " + p2.getPowerUpCards().get(0).getC().getAbbreviation() + ", and " + p2.getPowerUpCards().get(1).getCardName() + " coloured " + p2.getPowerUpCards().get(1).getC().getAbbreviation());
        game.pickAndDiscardCard("Player 2", p2.getPowerUpCards().get(1).getCardName(), p2.getPowerUpCards().get(1).getC().getAbbreviation());

        game.giveTwoPUCard("Player 3");
        System.out.print("\nPowerUpCard picked from the deck for Player 3: " + p3.getPowerUpCards().get(0).getCardName() + " coloured " + p3.getPowerUpCards().get(0).getC().getAbbreviation() + ", and " + p3.getPowerUpCards().get(1).getCardName() + " coloured " + p3.getPowerUpCards().get(1).getC().getAbbreviation());
        game.pickAndDiscardCard("Player 3", p3.getPowerUpCards().get(1).getCardName(), p3.getPowerUpCards().get(1).getC().getAbbreviation());

        game.giveTwoPUCard("Player 4");
        System.out.print("\nPowerUpCard picked from the deck for Player 4: " + p4.getPowerUpCards().get(0).getCardName() + " coloured " + p4.getPowerUpCards().get(0).getC().getAbbreviation() + ", and " + p4.getPowerUpCards().get(1).getCardName() + " coloured " + p4.getPowerUpCards().get(1).getC().getAbbreviation());
        game.pickAndDiscardCard("Player 4", p4.getPowerUpCards().get(1).getCardName(), p4.getPowerUpCards().get(1).getC().getAbbreviation());

        p1.changeCell(grid.getBoard().getArena()[1][2]);
        p2.changeCell(grid.getBoard().getArena()[1][1]);
        p3.changeCell(grid.getBoard().getArena()[0][2]);
        p4.changeCell(grid.getBoard().getArena()[1][3]);

        assertEquals(grid.getBoard().getArena()[1][2], p1.getCell());
        assertEquals(grid.getBoard().getArena()[1][1], p2.getCell());
        assertEquals(grid.getBoard().getArena()[0][2], p3.getCell());
        assertEquals(grid.getBoard().getArena()[1][3], p4.getCell());

        WeaponCard rocketLauncher = new RocketLauncher();
        p1.addWeaponCard(rocketLauncher);
        rocketLauncher.reload();
        assertTrue(rocketLauncher.isReloaded());


        List<Integer> lI = new LinkedList<>();
        lI.add(3);
        lI.add(1);
        lI.add(2);
        lI.add(4);
        List<String> lS = new LinkedList<>();
        lS.add("Player 2");
        lS.add("2");
        lS.add("2");
        lS.add("1");
        lS.add("4");
        List<Colour> lA = new LinkedList<>();
        lA.add(YELLOW);
        lA.add(BLUE);
        List<String> lP = new LinkedList<>();
        List<String> lPColourInput = new LinkedList<>();

        assertEquals(GameState.STARTTURN, game.getGameState());

        assertTrue(game.isValidFirstActionShoot("Player 1", "Rocket Launcher", lI, lS, 0, lA, lP, lPColourInput));
        game.firstActionShoot("Player 1", "Rocket Launcher", lI, lS, 0, lA, lP, lPColourInput);
    }

    @Test
    void GameShootRailgunTest() throws RemoteException {
        Game game = new Game(iD, server);
        Grid grid = game.getGrid();

        game.gameStart("Player 1", BLUE);
        Player p1 = grid.getPlayerObject("Player 1");

        game.addPlayer("Player 2", YELLOW);
        Player p2 = grid.getPlayerObject("Player 2");

        game.addPlayer("Player 3", GREEN);
        Player p3 = grid.getPlayerObject("Player 3");

        game.receiveType(1);

        game.giveTwoPUCard("Player 1");
        System.out.print("\nPowerUpCard picked from the deck for Player 1: " + p1.getPowerUpCards().get(0).getCardName() + " coloured " + p1.getPowerUpCards().get(0).getC().getAbbreviation() + ", and " + p1.getPowerUpCards().get(1).getCardName() + " coloured " + p1.getPowerUpCards().get(1).getC().getAbbreviation());
        game.pickAndDiscardCard("Player 1", p1.getPowerUpCards().get(0).getCardName(), p1.getPowerUpCards().get(0).getC().getAbbreviation());

        game.giveTwoPUCard("Player 2");
        System.out.print("\nPowerUpCard picked from the deck for Player 2: " + p2.getPowerUpCards().get(0).getCardName() + " coloured " + p2.getPowerUpCards().get(0).getC().getAbbreviation() + ", and " + p2.getPowerUpCards().get(1).getCardName() + " coloured " + p2.getPowerUpCards().get(1).getC().getAbbreviation());
        game.pickAndDiscardCard("Player 2", p2.getPowerUpCards().get(1).getCardName(), p2.getPowerUpCards().get(1).getC().getAbbreviation());

        game.giveTwoPUCard("Player 3");
        System.out.print("\nPowerUpCard picked from the deck for Player 3: " + p3.getPowerUpCards().get(0).getCardName() + " coloured " + p3.getPowerUpCards().get(0).getC().getAbbreviation() + ", and " + p3.getPowerUpCards().get(1).getCardName() + " coloured " + p3.getPowerUpCards().get(1).getC().getAbbreviation());
        game.pickAndDiscardCard("Player 3", p3.getPowerUpCards().get(1).getCardName(), p3.getPowerUpCards().get(1).getC().getAbbreviation());

        p1.changeCell(grid.getBoard().getArena()[1][3]);
        p2.changeCell(grid.getBoard().getArena()[1][2]);
        p3.changeCell(grid.getBoard().getArena()[1][0]);

        assertEquals(grid.getBoard().getArena()[1][3], p1.getCell());
        assertEquals(grid.getBoard().getArena()[1][2], p2.getCell());
        assertEquals(grid.getBoard().getArena()[1][0], p3.getCell());

        WeaponCard railgun = new Railgun();
        p1.addWeaponCard(railgun);
        railgun.reload();
        assertTrue(railgun.isReloaded());


        List<Integer> lI = new LinkedList<>();
        lI.add(2);
        List<String> lS = new LinkedList<>();
        lS.add("Player 2");
        lS.add("Player 3");
        List<Colour> lA = new LinkedList<>();
        List<String> lP = new LinkedList<>();
        List<String> lPColourInput = new LinkedList<>();

        assertEquals(GameState.STARTTURN, game.getGameState());

        assertTrue(game.isValidFirstActionShoot("Player 1", "Railgun", lI, lS, 0, lA, lP, lPColourInput));
        game.firstActionShoot("Player 1", "Railgun", lI, lS, 0, lA, lP, lPColourInput);
    }

    @Test
    void GameShootPowerGloveTest() throws RemoteException {
        Game game = new Game(iD, server);
        Grid grid = game.getGrid();

        game.gameStart("Player 1", BLUE);
        Player p1 = grid.getPlayerObject("Player 1");

        game.addPlayer("Player 2", YELLOW);
        Player p2 = grid.getPlayerObject("Player 2");

        game.addPlayer("Player 3", GREEN);
        Player p3 = grid.getPlayerObject("Player 3");

        game.receiveType(1);

        game.giveTwoPUCard("Player 1");
        System.out.print("\nPowerUpCard picked from the deck for Player 1: " + p1.getPowerUpCards().get(0).getCardName() + " coloured " + p1.getPowerUpCards().get(0).getC().getAbbreviation() + ", and " + p1.getPowerUpCards().get(1).getCardName() + " coloured " + p1.getPowerUpCards().get(1).getC().getAbbreviation());
        game.pickAndDiscardCard("Player 1", p1.getPowerUpCards().get(0).getCardName(), p1.getPowerUpCards().get(0).getC().getAbbreviation());

        game.giveTwoPUCard("Player 2");
        System.out.print("\nPowerUpCard picked from the deck for Player 2: " + p2.getPowerUpCards().get(0).getCardName() + " coloured " + p2.getPowerUpCards().get(0).getC().getAbbreviation() + ", and " + p2.getPowerUpCards().get(1).getCardName() + " coloured " + p2.getPowerUpCards().get(1).getC().getAbbreviation());
        game.pickAndDiscardCard("Player 2", p2.getPowerUpCards().get(1).getCardName(), p2.getPowerUpCards().get(1).getC().getAbbreviation());

        game.giveTwoPUCard("Player 3");
        System.out.print("\nPowerUpCard picked from the deck for Player 3: " + p3.getPowerUpCards().get(0).getCardName() + " coloured " + p3.getPowerUpCards().get(0).getC().getAbbreviation() + ", and " + p3.getPowerUpCards().get(1).getCardName() + " coloured " + p3.getPowerUpCards().get(1).getC().getAbbreviation());
        game.pickAndDiscardCard("Player 3", p3.getPowerUpCards().get(1).getCardName(), p3.getPowerUpCards().get(1).getC().getAbbreviation());

        p1.changeCell(grid.getBoard().getArena()[1][3]);
        p2.changeCell(grid.getBoard().getArena()[1][2]);
        p3.changeCell(grid.getBoard().getArena()[1][1]);

        assertEquals(grid.getBoard().getArena()[1][3], p1.getCell());
        assertEquals(grid.getBoard().getArena()[1][2], p2.getCell());
        assertEquals(grid.getBoard().getArena()[1][1], p3.getCell());

        WeaponCard powerGlove = new PowerGlove();
        p1.addWeaponCard(powerGlove);
        powerGlove.reload();
        assertTrue(powerGlove.isReloaded());


        List<Integer> lI = new LinkedList<>();
        lI.add(2);
        List<String> lS = new LinkedList<>();
        lS.add("1");
        lS.add("2");
        lS.add("Player 2");
        lS.add("1");
        lS.add("1");
        lS.add("Player 3");
        List<Colour> lA = new LinkedList<>();
        lA.add(BLUE);
        List<String> lP = new LinkedList<>();
        List<String> lPColourInput = new LinkedList<>();

        assertEquals(GameState.STARTTURN, game.getGameState());

        assertTrue(game.isValidFirstActionShoot("Player 1", "Power Glove", lI, lS, 0, lA, lP, lPColourInput));
        game.firstActionShoot("Player 1", "Power Glove", lI, lS, 0, lA, lP, lPColourInput);
    }

    @Test
    void GameShootPlasmaGunTest() throws RemoteException {
        Game game = new Game(iD, server);
        Grid grid = game.getGrid();

        game.gameStart("Player 1", BLUE);
        Player p1 = grid.getPlayerObject("Player 1");

        game.addPlayer("Player 2", YELLOW);
        Player p2 = grid.getPlayerObject("Player 2");

        game.receiveType(1);

        game.giveTwoPUCard("Player 1");
        System.out.print("\nPowerUpCard picked from the deck for Player 1: " + p1.getPowerUpCards().get(0).getCardName() + " coloured " + p1.getPowerUpCards().get(0).getC().getAbbreviation() + ", and " + p1.getPowerUpCards().get(1).getCardName() + " coloured " + p1.getPowerUpCards().get(1).getC().getAbbreviation());
        game.pickAndDiscardCard("Player 1", p1.getPowerUpCards().get(0).getCardName(), p1.getPowerUpCards().get(0).getC().getAbbreviation());

        game.giveTwoPUCard("Player 2");
        System.out.print("\nPowerUpCard picked from the deck for Player 2: " + p2.getPowerUpCards().get(0).getCardName() + " coloured " + p2.getPowerUpCards().get(0).getC().getAbbreviation() + ", and " + p2.getPowerUpCards().get(1).getCardName() + " coloured " + p2.getPowerUpCards().get(1).getC().getAbbreviation());
        game.pickAndDiscardCard("Player 2", p2.getPowerUpCards().get(1).getCardName(), p2.getPowerUpCards().get(1).getC().getAbbreviation());

        p1.changeCell(grid.getBoard().getArena()[0][0]);
        p2.changeCell(grid.getBoard().getArena()[1][0]);

        assertEquals(grid.getBoard().getArena()[0][0], p1.getCell());
        assertEquals(grid.getBoard().getArena()[1][0], p2.getCell());

        WeaponCard plasmaGun = new PlasmaGun();
        p1.addWeaponCard(plasmaGun);
        plasmaGun.reload();
        assertTrue(plasmaGun.isReloaded());


        List<Integer> lI = new LinkedList<>();
        lI.add(1);
        lI.add(2);
        lI.add(3);
        List<String> lS = new LinkedList<>();
        lS.add("Player 2");
        lS.add("2");
        lS.add("2");
        lS.add("4");
        List<Colour> lA = new LinkedList<>();
        lA.add(BLUE);
        List<String> lP = new LinkedList<>();
        List<String> lPColourInput = new LinkedList<>();

        assertEquals(GameState.STARTTURN, game.getGameState());

        assertTrue(game.isValidFirstActionShoot("Player 1", "Plasma Gun", lI, lS, 0, lA, lP, lPColourInput));
        game.firstActionShoot("Player 1", "Plasma Gun", lI, lS, 0, lA, lP, lPColourInput);
    }

    @Test
    void GameShootLockRifleTest() throws RemoteException {
        Game game = new Game(iD, server);
        Grid grid = game.getGrid();

        game.gameStart("Player 1", BLUE);
        Player p1 = grid.getPlayerObject("Player 1");

        game.addPlayer("Player 2", YELLOW);
        Player p2 = grid.getPlayerObject("Player 2");

        game.addPlayer("Player 3", GREEN);
        Player p3 = grid.getPlayerObject("Player 3");

        game.receiveType(1);

        game.giveTwoPUCard("Player 1");
        System.out.print("\nPowerUpCard picked from the deck for Player 1: " + p1.getPowerUpCards().get(0).getCardName() + " coloured " + p1.getPowerUpCards().get(0).getC().getAbbreviation() + ", and " + p1.getPowerUpCards().get(1).getCardName() + " coloured " + p1.getPowerUpCards().get(1).getC().getAbbreviation());
        game.pickAndDiscardCard("Player 1", p1.getPowerUpCards().get(0).getCardName(), p1.getPowerUpCards().get(0).getC().getAbbreviation());

        game.giveTwoPUCard("Player 2");
        System.out.print("\nPowerUpCard picked from the deck for Player 2: " + p2.getPowerUpCards().get(0).getCardName() + " coloured " + p2.getPowerUpCards().get(0).getC().getAbbreviation() + ", and " + p2.getPowerUpCards().get(1).getCardName() + " coloured " + p2.getPowerUpCards().get(1).getC().getAbbreviation());
        game.pickAndDiscardCard("Player 2", p2.getPowerUpCards().get(1).getCardName(), p2.getPowerUpCards().get(1).getC().getAbbreviation());

        game.giveTwoPUCard("Player 3");
        System.out.print("\nPowerUpCard picked from the deck for Player 3: " + p3.getPowerUpCards().get(0).getCardName() + " coloured " + p3.getPowerUpCards().get(0).getC().getAbbreviation() + ", and " + p3.getPowerUpCards().get(1).getCardName() + " coloured " + p3.getPowerUpCards().get(1).getC().getAbbreviation());
        game.pickAndDiscardCard("Player 3", p3.getPowerUpCards().get(1).getCardName(), p3.getPowerUpCards().get(1).getC().getAbbreviation());

        p1.changeCell(grid.getBoard().getArena()[1][3]);
        p2.changeCell(grid.getBoard().getArena()[1][2]);
        p3.changeCell(grid.getBoard().getArena()[1][1]);

        assertEquals(grid.getBoard().getArena()[1][3], p1.getCell());
        assertEquals(grid.getBoard().getArena()[1][2], p2.getCell());
        assertEquals(grid.getBoard().getArena()[1][1], p3.getCell());

        WeaponCard lockRifle = new LockRifle();
        p1.addWeaponCard(lockRifle);
        lockRifle.reload();
        assertTrue(lockRifle.isReloaded());


        List<Integer> lI = new LinkedList<>();
        lI.add(1);
        lI.add(2);
        List<String> lS = new LinkedList<>();
        lS.add("Player 2");
        lS.add("Player 3");
        List<Colour> lA = new LinkedList<>();
        lA.add(RED);
        List<String> lP = new LinkedList<>();
        List<String> lPColourInput = new LinkedList<>();

        assertEquals(GameState.STARTTURN, game.getGameState());

        assertTrue(game.isValidFirstActionShoot("Player 1", "Lock Rifle", lI, lS, 0, lA, lP, lPColourInput));
        game.firstActionShoot("Player 1", "Lock Rifle", lI, lS, 0, lA, lP, lPColourInput);
    }

    @Test
    void GameShootHellionTest() throws RemoteException {
        Game game = new Game(iD, server);
        Grid grid = game.getGrid();

        game.gameStart("Player 1", BLUE);
        Player p1 = grid.getPlayerObject("Player 1");

        game.addPlayer("Player 2", YELLOW);
        Player p2 = grid.getPlayerObject("Player 2");

        game.receiveType(1);

        game.giveTwoPUCard("Player 1");
        System.out.print("\nPowerUpCard picked from the deck for Player 1: " + p1.getPowerUpCards().get(0).getCardName() + " coloured " + p1.getPowerUpCards().get(0).getC().getAbbreviation() + ", and " + p1.getPowerUpCards().get(1).getCardName() + " coloured " + p1.getPowerUpCards().get(1).getC().getAbbreviation());
        game.pickAndDiscardCard("Player 1", p1.getPowerUpCards().get(0).getCardName(), p1.getPowerUpCards().get(0).getC().getAbbreviation());

        game.giveTwoPUCard("Player 2");
        System.out.print("\nPowerUpCard picked from the deck for Player 2: " + p2.getPowerUpCards().get(0).getCardName() + " coloured " + p2.getPowerUpCards().get(0).getC().getAbbreviation() + ", and " + p2.getPowerUpCards().get(1).getCardName() + " coloured " + p2.getPowerUpCards().get(1).getC().getAbbreviation());
        game.pickAndDiscardCard("Player 2", p2.getPowerUpCards().get(1).getCardName(), p2.getPowerUpCards().get(1).getC().getAbbreviation());

        p1.changeCell(grid.getBoard().getArena()[0][0]);
        p2.changeCell(grid.getBoard().getArena()[0][2]);

        assertEquals(grid.getBoard().getArena()[0][0], p1.getCell());
        assertEquals(grid.getBoard().getArena()[0][2], p2.getCell());

        WeaponCard hellion = new Hellion();
        p1.addWeaponCard(hellion);
        hellion.reload();
        assertTrue(hellion.isReloaded());


        List<Integer> lI = new LinkedList<>();
        lI.add(2);
        List<String> lS = new LinkedList<>();
        lS.add("Player 2");
        List<Colour> lA = new LinkedList<>();
        lA.add(RED);
        List<String> lP = new LinkedList<>();
        List<String> lPColourInput = new LinkedList<>();

        assertEquals(GameState.STARTTURN, game.getGameState());

        assertTrue(game.isValidFirstActionShoot("Player 1", "Hellion", lI, lS, 0, lA, lP, lPColourInput));
        game.firstActionShoot("Player 1", "Hellion", lI, lS, 0, lA, lP, lPColourInput);
    }

    @Test
    void GameShootHeatseekerTest() throws RemoteException {
        Game game = new Game(iD, server);
        Grid grid = game.getGrid();

        game.gameStart("Player 1", BLUE);
        Player p1 = grid.getPlayerObject("Player 1");

        game.addPlayer("Player 2", YELLOW);
        Player p2 = grid.getPlayerObject("Player 2");

        game.receiveType(1);

        game.giveTwoPUCard("Player 1");
        System.out.print("\nPowerUpCard picked from the deck for Player 1: " + p1.getPowerUpCards().get(0).getCardName() + " coloured " + p1.getPowerUpCards().get(0).getC().getAbbreviation() + ", and " + p1.getPowerUpCards().get(1).getCardName() + " coloured " + p1.getPowerUpCards().get(1).getC().getAbbreviation());
        game.pickAndDiscardCard("Player 1", p1.getPowerUpCards().get(0).getCardName(), p1.getPowerUpCards().get(0).getC().getAbbreviation());

        game.giveTwoPUCard("Player 2");
        System.out.print("\nPowerUpCard picked from the deck for Player 2: " + p2.getPowerUpCards().get(0).getCardName() + " coloured " + p2.getPowerUpCards().get(0).getC().getAbbreviation() + ", and " + p2.getPowerUpCards().get(1).getCardName() + " coloured " + p2.getPowerUpCards().get(1).getC().getAbbreviation());
        game.pickAndDiscardCard("Player 2", p2.getPowerUpCards().get(1).getCardName(), p2.getPowerUpCards().get(1).getC().getAbbreviation());

        p1.changeCell(grid.getBoard().getArena()[0][0]);
        p2.changeCell(grid.getBoard().getArena()[2][0]);

        assertEquals(grid.getBoard().getArena()[0][0], p1.getCell());
        assertEquals(grid.getBoard().getArena()[2][0], p2.getCell());

        WeaponCard heatseeker = new Heatseeker();
        p1.addWeaponCard(heatseeker);
        heatseeker.reload();
        assertTrue(heatseeker.isReloaded());


        List<Integer> lI = new LinkedList<>();
        lI.add(1);
        List<String> lS = new LinkedList<>();
        lS.add("Player 2");
        List<Colour> lA = new LinkedList<>();
        List<String> lP = new LinkedList<>();
        List<String> lPColourInput = new LinkedList<>();

        assertEquals(GameState.STARTTURN, game.getGameState());

        assertTrue(game.isValidFirstActionShoot("Player 1", "Heatseeker", lI, lS, 0, lA, lP, lPColourInput));
        game.firstActionShoot("Player 1", "Heatseeker", lI, lS, 0, lA, lP, lPColourInput);
    }

    @Test
    void GameShootGrenadeLauncherTest() throws RemoteException {
        Game game = new Game(iD, server);
        Grid grid = game.getGrid();

        game.gameStart("Player 1", BLUE);
        Player p1 = grid.getPlayerObject("Player 1");

        game.addPlayer("Player 2", YELLOW);
        Player p2 = grid.getPlayerObject("Player 2");

        game.receiveType(1);

        game.giveTwoPUCard("Player 1");
        System.out.print("\nPowerUpCard picked from the deck for Player 1: " + p1.getPowerUpCards().get(0).getCardName() + " coloured " + p1.getPowerUpCards().get(0).getC().getAbbreviation() + ", and " + p1.getPowerUpCards().get(1).getCardName() + " coloured " + p1.getPowerUpCards().get(1).getC().getAbbreviation());
        game.pickAndDiscardCard("Player 1", p1.getPowerUpCards().get(0).getCardName(), p1.getPowerUpCards().get(0).getC().getAbbreviation());

        game.giveTwoPUCard("Player 2");
        System.out.print("\nPowerUpCard picked from the deck for Player 2: " + p2.getPowerUpCards().get(0).getCardName() + " coloured " + p2.getPowerUpCards().get(0).getC().getAbbreviation() + ", and " + p2.getPowerUpCards().get(1).getCardName() + " coloured " + p2.getPowerUpCards().get(1).getC().getAbbreviation());
        game.pickAndDiscardCard("Player 2", p2.getPowerUpCards().get(1).getCardName(), p2.getPowerUpCards().get(1).getC().getAbbreviation());

        p1.changeCell(grid.getBoard().getArena()[0][0]);
        p2.changeCell(grid.getBoard().getArena()[1][0]);

        assertEquals(grid.getBoard().getArena()[0][0], p1.getCell());
        assertEquals(grid.getBoard().getArena()[1][0], p2.getCell());

        WeaponCard grenadeLauncher = new GrenadeLauncher();
        p1.addWeaponCard(grenadeLauncher);
        grenadeLauncher.reload();
        assertTrue(grenadeLauncher.isReloaded());


        List<Integer> lI = new LinkedList<>();
        lI.add(2);
        lI.add(1);
        List<String> lS = new LinkedList<>();
        lS.add("Player 2");
        lS.add("3");
        lS.add("0");
        lS.add("2");
        List<Colour> lA = new LinkedList<>();
        lA.add(RED);
        List<String> lP = new LinkedList<>();
        List<String> lPColourInput = new LinkedList<>();

        assertEquals(GameState.STARTTURN, game.getGameState());

        assertTrue(game.isValidFirstActionShoot("Player 1", "Grenade Launcher", lI, lS, 0, lA, lP, lPColourInput));
        game.firstActionShoot("Player 1", "Grenade Launcher", lI, lS, 0, lA, lP, lPColourInput);
    }

    @Test
    void GameShootFurnaceTest() throws RemoteException {
        Game game = new Game(iD, server);
        Grid grid = game.getGrid();

        game.gameStart("Player 1", BLUE);
        Player p1 = grid.getPlayerObject("Player 1");

        game.addPlayer("Player 2", YELLOW);
        Player p2 = grid.getPlayerObject("Player 2");

        game.receiveType(1);

        game.giveTwoPUCard("Player 1");
        System.out.print("\nPowerUpCard picked from the deck for Player 1: " + p1.getPowerUpCards().get(0).getCardName() + " coloured " + p1.getPowerUpCards().get(0).getC().getAbbreviation() + ", and " + p1.getPowerUpCards().get(1).getCardName() + " coloured " + p1.getPowerUpCards().get(1).getC().getAbbreviation());
        game.pickAndDiscardCard("Player 1", p1.getPowerUpCards().get(0).getCardName(), p1.getPowerUpCards().get(0).getC().getAbbreviation());

        game.giveTwoPUCard("Player 2");
        System.out.print("\nPowerUpCard picked from the deck for Player 2: " + p2.getPowerUpCards().get(0).getCardName() + " coloured " + p2.getPowerUpCards().get(0).getC().getAbbreviation() + ", and " + p2.getPowerUpCards().get(1).getCardName() + " coloured " + p2.getPowerUpCards().get(1).getC().getAbbreviation());
        game.pickAndDiscardCard("Player 2", p2.getPowerUpCards().get(1).getCardName(), p2.getPowerUpCards().get(1).getC().getAbbreviation());

        p1.changeCell(grid.getBoard().getArena()[0][0]);
        p2.changeCell(grid.getBoard().getArena()[1][0]);

        assertEquals(grid.getBoard().getArena()[0][0], p1.getCell());
        assertEquals(grid.getBoard().getArena()[1][0], p2.getCell());

        WeaponCard furnace = new Furnace();
        p1.addWeaponCard(furnace);
        furnace.reload();
        assertTrue(furnace.isReloaded());


        List<Integer> lI = new LinkedList<>();
        lI.add(2);
        List<String> lS = new LinkedList<>();
        lS.add("0");
        lS.add("1");
        List<Colour> lA = new LinkedList<>();
        List<String> lP = new LinkedList<>();
        List<String> lPColourInput = new LinkedList<>();

        assertEquals(GameState.STARTTURN, game.getGameState());

        assertTrue(game.isValidFirstActionShoot("Player 1", "Furnace", lI, lS, 0, lA, lP, lPColourInput));
        game.firstActionShoot("Player 1", "Furnace", lI, lS, 0, lA, lP, lPColourInput);
    }

    @Test
    void GameShootFlamethrowerTest() throws RemoteException {
        Game game = new Game(iD, server);
        Grid grid = game.getGrid();

        game.gameStart("Player 1", BLUE);
        Player p1 = grid.getPlayerObject("Player 1");

        game.addPlayer("Player 2", YELLOW);
        Player p2 = grid.getPlayerObject("Player 2");

        game.addPlayer("Player 3", GREEN);
        Player p3 = grid.getPlayerObject("Player 3");

        game.receiveType(1);

        game.giveTwoPUCard("Player 1");
        System.out.print("\nPowerUpCard picked from the deck for Player 1: " + p1.getPowerUpCards().get(0).getCardName() + " coloured " + p1.getPowerUpCards().get(0).getC().getAbbreviation() + ", and " + p1.getPowerUpCards().get(1).getCardName() + " coloured " + p1.getPowerUpCards().get(1).getC().getAbbreviation());
        game.pickAndDiscardCard("Player 1", p1.getPowerUpCards().get(0).getCardName(), p1.getPowerUpCards().get(0).getC().getAbbreviation());

        game.giveTwoPUCard("Player 2");
        System.out.print("\nPowerUpCard picked from the deck for Player 2: " + p2.getPowerUpCards().get(0).getCardName() + " coloured " + p2.getPowerUpCards().get(0).getC().getAbbreviation() + ", and " + p2.getPowerUpCards().get(1).getCardName() + " coloured " + p2.getPowerUpCards().get(1).getC().getAbbreviation());
        game.pickAndDiscardCard("Player 2", p2.getPowerUpCards().get(1).getCardName(), p2.getPowerUpCards().get(1).getC().getAbbreviation());

        game.giveTwoPUCard("Player 3");
        System.out.print("\nPowerUpCard picked from the deck for Player 3: " + p3.getPowerUpCards().get(0).getCardName() + " coloured " + p3.getPowerUpCards().get(0).getC().getAbbreviation() + ", and " + p3.getPowerUpCards().get(1).getCardName() + " coloured " + p3.getPowerUpCards().get(1).getC().getAbbreviation());
        game.pickAndDiscardCard("Player 3", p3.getPowerUpCards().get(1).getCardName(), p3.getPowerUpCards().get(1).getC().getAbbreviation());

        p1.changeCell(grid.getBoard().getArena()[1][3]);
        p2.changeCell(grid.getBoard().getArena()[1][2]);
        p3.changeCell(grid.getBoard().getArena()[1][1]);

        assertEquals(grid.getBoard().getArena()[1][3], p1.getCell());
        assertEquals(grid.getBoard().getArena()[1][2], p2.getCell());
        assertEquals(grid.getBoard().getArena()[1][1], p3.getCell());

        WeaponCard flamethrower = new Flamethrower();
        p1.addWeaponCard(flamethrower);
        flamethrower.reload();
        assertTrue(flamethrower.isReloaded());

        p1.addNewAC(new AmmoCube(YELLOW));


        List<Integer> lI = new LinkedList<>();
        lI.add(2);
        List<String> lS = new LinkedList<>();
        lS.add("1");
        lS.add("2");
        lS.add("1");
        lS.add("1");
        List<Colour> lA = new LinkedList<>();
        lA.add(YELLOW);
        lA.add(YELLOW);
        List<String> lP = new LinkedList<>();
        List<String> lPColourInput = new LinkedList<>();

        assertEquals(GameState.STARTTURN, game.getGameState());
        //TODO
        assertTrue(game.isValidFirstActionShoot("Player 1", "Flamethrower", lI, lS, 0, lA, lP, lPColourInput));
        game.firstActionShoot("Player 1", "Flamethrower", lI, lS, 0, lA, lP, lPColourInput);
    }

    @Test
    void GameShootElectroscytheTest() throws RemoteException {
        Game game = new Game(iD, server);
        Grid grid = game.getGrid();

        game.gameStart("Player 1", BLUE);
        Player p1 = grid.getPlayerObject("Player 1");

        game.addPlayer("Player 2", YELLOW);
        Player p2 = grid.getPlayerObject("Player 2");

        game.receiveType(1);

        game.giveTwoPUCard("Player 1");
        System.out.print("\nPowerUpCard picked from the deck for Player 1: " + p1.getPowerUpCards().get(0).getCardName() + " coloured " + p1.getPowerUpCards().get(0).getC().getAbbreviation() + ", and " + p1.getPowerUpCards().get(1).getCardName() + " coloured " + p1.getPowerUpCards().get(1).getC().getAbbreviation());
        game.pickAndDiscardCard("Player 1", p1.getPowerUpCards().get(0).getCardName(), p1.getPowerUpCards().get(0).getC().getAbbreviation());

        game.giveTwoPUCard("Player 2");
        System.out.print("\nPowerUpCard picked from the deck for Player 2: " + p2.getPowerUpCards().get(0).getCardName() + " coloured " + p2.getPowerUpCards().get(0).getC().getAbbreviation() + ", and " + p2.getPowerUpCards().get(1).getCardName() + " coloured " + p2.getPowerUpCards().get(1).getC().getAbbreviation());
        game.pickAndDiscardCard("Player 2", p2.getPowerUpCards().get(1).getCardName(), p2.getPowerUpCards().get(1).getC().getAbbreviation());

        p1.changeCell(grid.getBoard().getArena()[0][0]);
        p2.changeCell(grid.getBoard().getArena()[0][0]);

        assertEquals(grid.getBoard().getArena()[0][0], p1.getCell());
        assertEquals(grid.getBoard().getArena()[0][0], p2.getCell());

        WeaponCard electroscythe = new Electroscythe();
        p1.addWeaponCard(electroscythe);
        electroscythe.reload();
        assertTrue(electroscythe.isReloaded());


        List<Integer> lI = new LinkedList<>();
        lI.add(2);
        List<String> lS = new LinkedList<>();
        List<Colour> lA = new LinkedList<>();
        lA.add(BLUE);
        lA.add(RED);
        List<String> lP = new LinkedList<>();
        List<String> lPColourInput = new LinkedList<>();

        assertEquals(GameState.STARTTURN, game.getGameState());

        assertTrue(game.isValidFirstActionShoot("Player 1", "Electroscythe", lI, lS, 0, lA, lP, lPColourInput));
        game.firstActionShoot("Player 1", "Electroscythe", lI, lS, 0, lA, lP, lPColourInput);
    }



    @Test
    void FinalFrenzyScoringTest1() throws RemoteException {
        Game game = new Game(iD, server);
        Grid grid = game.getGrid();
        List<String> deadGuys = game.getDeadList();

        game.gameStart("Player 1", BLUE);
        Player p1 = grid.getPlayerObject("Player 1");

        game.addPlayer("Player 2", YELLOW);
        Player p2 = grid.getPlayerObject("Player 2");

        game.addPlayer("Player 3", GREEN);
        Player p3 = grid.getPlayerObject("Player 3");

        game.addPlayer("Player 4", BLACK);
        Player p4 = grid.getPlayerObject("Player 4");

        grid.damage(p3, p2, 3);
        grid.damage(p1, p2, 8);
        grid.damage(p3, p1, 5);
        grid.damage(p1, p3, 11);
        grid.damage(p1, p4, 2);
        grid.damage(p4, p1, 6);
        //p2, who has not dealt anyone damage, dies promptly at the hands of p3 and p1
        //p1 deals finishing blows to p2 and p3, and damages p4
        //p3 deals some damage to p2 and p1, who kills him
        //p4 receives damage from p1, who is then killed by him

        game.finalFrenzyTurnScoring();

        assertEquals(3, deadGuys.size());
        assertTrue(deadGuys.contains(p2.getNickName()));
        assertTrue(deadGuys.contains(p3.getNickName()));
        assertEquals(p1.getPlayerBoard().getDamage().scoreBoard().size(), 0);
        assertEquals(p2.getPlayerBoard().getDamage().scoreBoard().size(), 0);
        assertEquals(p3.getPlayerBoard().getDamage().scoreBoard().size(), 0);
        assertEquals(p4.getPlayerBoard().getDamage().scoreBoard().size(), 1);

        assertEquals(5, p1.getScore());
        assertEquals(0, p2.getScore());
        assertEquals(2, p3.getScore());
        assertEquals(2, p4.getScore());
    }

    @Test
    void FinalFrenzyScoringTest2() throws RemoteException {
        Game game = new Game(iD, server);
        Grid grid = game.getGrid();
        List<String> deadGuys = game.getDeadList();

        game.gameStart("Player 1", BLUE);
        Player p1 = grid.getPlayerObject("Player 1");

        game.addPlayer("Player 2", YELLOW);
        Player p2 = grid.getPlayerObject("Player 2");

        game.addPlayer("Player 3", GREEN);
        Player p3 = grid.getPlayerObject("Player 3");

        game.addPlayer("Player 4", BLACK);
        Player p4 = grid.getPlayerObject("Player 4");

        game.addPlayer("Player 5", PURPLE);
        Player p5 = grid.getPlayerObject("Player 5");

        grid.damage(p5, p2, 11);
        grid.damage(p5, p4, 1);
        grid.damage(p3, p4, 3);
        grid.damage(p4, p3, 11);
        grid.damage(p1, p4, 3);
        grid.damage(p5, p1, 6);
        grid.damage(p4, p1, 2);
        grid.damage(p1, p5, 2);
        grid.damage(p5, p4, 2);

        game.finalFrenzyTurnScoring();

        assertEquals(2, deadGuys.size());
        assertTrue(deadGuys.contains(p2.getNickName()));
        assertTrue(deadGuys.contains(p3.getNickName()));

        assertEquals(p1.getPlayerBoard().getDamage().scoreBoard().size(), 2);
        assertEquals(p2.getPlayerBoard().getDamage().scoreBoard().size(), 0);
        assertEquals(p3.getPlayerBoard().getDamage().scoreBoard().size(), 0);
        assertEquals(p4.getPlayerBoard().getDamage().scoreBoard().size(), 3);
        assertEquals(p5.getPlayerBoard().getDamage().scoreBoard().size(), 1);

        assertEquals(0, p1.getScore());
        assertEquals(0, p2.getScore());
        assertEquals(0, p3.getScore());
        assertEquals(3, p4.getScore());
        assertEquals(2, p5.getScore());

    }

    @Test
    void FinalFrenzyTest() throws RemoteException {
        Game game = new Game(iD, server);
        Grid grid = game.getGrid();

        game.gameStart("Player 1", BLUE);
        Player p1 = grid.getPlayerObject("Player 1");

        game.addPlayer("Player 2", YELLOW);
        Player p2 = grid.getPlayerObject("Player 2");

        game.addPlayer("Player 3", GREEN);
        Player p3 = grid.getPlayerObject("Player 3");

        game.receiveType(2);

        game.giveTwoPUCard("Player 1");
        System.out.print("\nPowerUpCard picked from the deck for Player 1: " + p1.getPowerUpCards().get(0).getCardName() + " coloured " + p1.getPowerUpCards().get(0).getC().getAbbreviation() + ", and " + p1.getPowerUpCards().get(1).getCardName() + " coloured " + p1.getPowerUpCards().get(1).getC().getAbbreviation());
        game.pickAndDiscardCard("Player 1", p1.getPowerUpCards().get(0).getCardName(), p1.getPowerUpCards().get(0).getC().getAbbreviation());

        game.giveTwoPUCard("Player 2");
        System.out.print("\nPowerUpCard picked from the deck for Player 2: " + p2.getPowerUpCards().get(0).getCardName() + " coloured " + p2.getPowerUpCards().get(0).getC().getAbbreviation() + ", and " + p2.getPowerUpCards().get(1).getCardName() + " coloured " + p2.getPowerUpCards().get(1).getC().getAbbreviation());
        game.pickAndDiscardCard("Player 2", p2.getPowerUpCards().get(1).getCardName(), p2.getPowerUpCards().get(1).getC().getAbbreviation());

        game.giveTwoPUCard("Player 3");
        System.out.print("\nPowerUpCard picked from the deck for Player 3: " + p3.getPowerUpCards().get(0).getCardName() + " coloured " + p3.getPowerUpCards().get(0).getC().getAbbreviation() + ", and " + p3.getPowerUpCards().get(1).getCardName() + " coloured " + p3.getPowerUpCards().get(1).getC().getAbbreviation());
        game.pickAndDiscardCard("Player 3", p3.getPowerUpCards().get(1).getCardName(), p3.getPowerUpCards().get(1).getC().getAbbreviation());

        int[] skulls = {0, 0, 0, 0, 0, 0, 0, 3};
        grid.getBoard().getK().setSkulls(skulls);
        assertEquals(game.getGameState(), GameState.STARTTURN);
        assertTrue(game.getDeadList().isEmpty());

        game.replace();
        assertTrue(game.isFinalFrenzy());

        WeaponCard whisper = new Whisper();
        grid.getBoard().getW1().setCard1(whisper);
        WeaponCard cyberblade = new Cyberblade();
        grid.getBoard().getW1().setCard2(cyberblade);
        WeaponCard electroscythe = new Electroscythe();
        grid.getBoard().getW1().setCard3(electroscythe);
        WeaponCard flamethrower = new Flamethrower();
        grid.getBoard().getW2().setCard1(flamethrower);
        WeaponCard shotgun = new Shotgun();
        grid.getBoard().getW2().setCard2(shotgun);
        WeaponCard furnace = new Furnace();
        grid.getBoard().getW2().setCard3(furnace);
        WeaponCard hellion = new Hellion();
        grid.getBoard().getW3().setCard1(hellion);
        WeaponCard shockwave = new Shockwave();
        grid.getBoard().getW3().setCard2(shockwave);
        WeaponCard railgun = new Railgun();
        grid.getBoard().getW3().setCard3(railgun);

        int p1Turn = 1;
        p1.setTurnFinalFrenzy(p1Turn);
        p1.changeCell(grid.getBoard().getArena()[0][0]);
        List<String> p1STrue = new LinkedList<>();
        List<String> p1SFalse = new LinkedList<>();
        p1STrue.add("4");
        p1SFalse.add("1");
        p1SFalse.add("3");
        assertTrue(game.isValidFinalFrenzyAction("Player 1", p1STrue));
        assertFalse(game.isValidFinalFrenzyAction("Player 1", p1SFalse));

        int p2Turn = 0;
        p2.setTurnFinalFrenzy(p2Turn);
        p2.changeCell(grid.getBoard().getArena()[1][2]);
        List<String> p2STrue = new LinkedList<>();
        List<String> p2SFalse = new LinkedList<>();
        p2STrue.add("1");
        p2STrue.add("3");
        p2SFalse.add("1");
        p2SFalse.add("4");
        assertTrue(game.isValidFinalFrenzyAction("Player 2", p2STrue));
        assertFalse(game.isValidFinalFrenzyAction("Player 2", p2SFalse));

        int p3Turn = 0;
        p3.setTurnFinalFrenzy(p3Turn);
        p3.changeCell(grid.getBoard().getArena()[2][2]);
        List<String> p3S = new LinkedList<>();
        p3S.add("3");
        p3S.add("2");
        assertTrue(game.isValidFinalFrenzyAction("Player 3", p3S));

        List<Integer> p2E = new LinkedList<>();
        List<String> p2S = new LinkedList<>();
        p2E.add(1);
        p2E.add(2);
        p2S.add("Player 1");
        p2.addWeaponCard(whisper);
        assertFalse(whisper.isReloaded());
        assertFalse(game.isValidFinalFrenzyAction1("Player 2", 1, "Whisper", p2E, p2S, new LinkedList<>(), new LinkedList<>(), new LinkedList<>()));
        whisper.reload();
        assertTrue(game.isValidFinalFrenzyAction1("Player 2", 1, "Whisper", p2E, p2S, new LinkedList<>(), new LinkedList<>(), new LinkedList<>()));
        game.finalFrenzyAction1("Player 2", 1, new LinkedList<>(),"Whisper", p2E, p2S, new LinkedList<>(), new LinkedList<>(), new LinkedList<>());
        assertEquals(YELLOW, p1.getPlayerBoard().getMarks().get(0).getC());
        assertNull(p2.getAmmoCubes()[4]);
        assertNull(p2.getAmmoCubes()[7]);

        List<Integer> p2D = new LinkedList<>();
        p2D.add(3);
        p2D.add(2);
        AmmoCard ammoCard1 = new PYB();
        grid.getBoard().getArena()[1][3].setA(ammoCard1);
        assertTrue(game.isValidFinalFrenzyAction3("Player 2", p2D, "","", new LinkedList<>(), new LinkedList<>(), new LinkedList<>()));
        game.finalFrenzyAction3("Player 2", p2D, "", new LinkedList<>(), new LinkedList<>(), new LinkedList<>());
        assertEquals(p2.getCell(), grid.getBoard().getArena()[1][3]);
        assertNotNull(p2.getAmmoCubes()[4]);
        assertNotNull(p2.getAmmoCubes()[7]);

        List<Integer> p3D = new LinkedList<>();
        List<Colour> p3A = new LinkedList<>();
        p3D.add(2);
        p3A.add(YELLOW);
        assertTrue(game.isValidFinalFrenzyAction3("Player 3", p3D, "Shotgun","2", p3A, new LinkedList<>(), new LinkedList<>()));
        game.finalFrenzyAction3("Player 3", p3D, "Shotgun", p3A, new LinkedList<>(), new LinkedList<>());
        assertNull(grid.getBoard().getW2().getCard2());
        assertEquals(p3.getCell(), grid.getBoard().getArena()[2][3]);
        assertEquals(p3.getWeaponCards().get(0), shotgun);
        assertTrue(p3.getWeaponCards().contains(shotgun));

        p3D.clear();
        p3D.add(1);
        p3D.add(4);
        p3D.add(1);
        p3D.add(4);
        assertTrue(game.isValidFinalFrenzyAction2("Player 3", p3D));
        game.finalFrenzyAction2("Player 3", p3D);
        assertEquals(p3.getCell(), grid.getBoard().getArena()[0][1]);

        List<Integer> p1D = new LinkedList<>();
        List<Integer> p1E = new LinkedList<>();
        List<Colour> p1A = new LinkedList<>();

        p1D.add(2);
        p1D.add(2);
        p1.addWeaponCard(shockwave);
        assertEquals(shockwave.getReloadCost()[0].getC(), YELLOW);
        shockwave.reload();
        assertTrue(shockwave.isReloaded());
        p1E.add(2);
        p1A.add(YELLOW);
        assertTrue(game.isValidFinalFrenzyAction4("Player 1", p1D, "Shockwave", p1E, new LinkedList<>(), p1A, new LinkedList<>(), new LinkedList<>()));
        game.finalFrenzyAction4("Player 1", p1D, new LinkedList<>(), "Shockwave", p1E, new LinkedList<>(), p1A, new LinkedList<>(), new LinkedList<>());
        assertEquals(BLUE, p3.getPlayerBoard().getDamage().getDamageTokens()[0].getC());
    }
}