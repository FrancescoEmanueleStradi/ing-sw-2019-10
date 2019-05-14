package controller;

import model.Colour;
import model.Grid;
import model.cards.AmmoCard;
import model.cards.PowerUpCard;
import model.cards.WeaponCard;
import model.cards.ammocards.PRB;
import model.cards.powerupcards.Newton;
import model.cards.powerupcards.TagbackGrenade;
import model.cards.powerupcards.TargetingScope;
import model.cards.powerupcards.Teleporter;
import model.cards.weaponcards.*;
import model.player.AmmoCube;
import model.player.Player;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static model.Colour.*;
import static org.junit.jupiter.api.Assertions.*;

class GameAssertTests {
    @Test
    void GameStartMoveShootEndTest() {
        Game game = new Game();
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
        PowerUpCard discarded1 = p1.getpC().get(1);
        System.out.print("PowerUpCard picked from the deck for Player 1: " + p1.getpC().get(0).getCardName() + " coloured " + p1.getpC().get(0).getC().getAbbreviation() + ", and " + p1.getpC().get(1).getCardName() + " coloured " + p1.getpC().get(1).getC().getAbbreviation());
        assertTrue(game.isValidPickAndDiscard("Player 1", p1.getpC().get(0).getCardName(), p1.getpC().get(0).getC().getAbbreviation()));
        game.pickAndDiscardCard("Player 1", p1.getpC().get(0).getCardName(), p1.getpC().get(0).getC().getAbbreviation());
        assertEquals(GameState.STARTTURN, game.getGameState());
        assertEquals(1, p1.getpC().size());
        assertEquals(1, grid.getPowerUpDiscardPile().size());


        if (discarded1.getC().equals(YELLOW))
            assertEquals(p1.getCell(), grid.getBoard().getArena()[2][3]);
        else if (discarded1.getC().equals(Colour.RED))
            assertEquals(p1.getCell(), grid.getBoard().getArena()[1][0]);
        else if (discarded1.getC().equals(BLUE))
            assertEquals(p1.getCell(), grid.getBoard().getArena()[0][2]);

        game.giveTwoPUCard("Player 2");
        PowerUpCard discarded2 = p2.getpC().get(0);
        System.out.print("\nPowerUpCard picked from the deck for Player 2: " + p2.getpC().get(0).getCardName() + " coloured " + p2.getpC().get(0).getC().getAbbreviation() + ", and " + p2.getpC().get(1).getCardName() + " coloured " + p2.getpC().get(1).getC().getAbbreviation());
        assertTrue(game.isValidPickAndDiscard("Player 2", p2.getpC().get(1).getCardName(), p2.getpC().get(1).getC().getAbbreviation()));
        game.pickAndDiscardCard("Player 2", p2.getpC().get(1).getCardName(), p2.getpC().get(1).getC().getAbbreviation());
        assertEquals(GameState.STARTTURN, game.getGameState());
        assertEquals(1, p2.getpC().size());
        assertEquals(2, grid.getPowerUpDiscardPile().size());

        if (discarded2.getC().equals(YELLOW))
            assertEquals(p2.getCell(), grid.getBoard().getArena()[2][3]);
        else if (discarded2.getC().equals(Colour.RED))
            assertEquals(p2.getCell(), grid.getBoard().getArena()[1][0]);
        else if (discarded2.getC().equals(BLUE))
            assertEquals(p2.getCell(), grid.getBoard().getArena()[0][2]);

        game.giveTwoPUCard("Player 3");
        PowerUpCard discarded3 = p3.getpC().get(1);
        System.out.print("\nPowerUpCard picked from the deck for Player 3: " + p3.getpC().get(0).getCardName() + " coloured " + p3.getpC().get(0).getC().getAbbreviation() + ", and " + p3.getpC().get(1).getCardName() + " coloured " + p3.getpC().get(1).getC().getAbbreviation());
        assertTrue(game.isValidPickAndDiscard("Player 3", p3.getpC().get(0).getCardName(), p3.getpC().get(0).getC().getAbbreviation()));
        game.pickAndDiscardCard("Player 3", p3.getpC().get(0).getCardName(), p3.getpC().get(0).getC().getAbbreviation());
        assertEquals(GameState.STARTTURN, game.getGameState());
        assertEquals(1, p3.getpC().size());
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
        assertEquals(BLUE, p2.getpB().getDamages().getDamageTr()[0].getC());
        assertEquals(BLUE, p3.getpB().getDamages().getDamageTr()[0].getC());
        assertEquals(BLUE, p3.getpB().getDamages().getDamageTr()[1].getC());
        assertEquals(BLUE, p2.getpB().getDamages().getDamageTr()[1].getC());

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
        p1.getpC().clear();
        p2.getpC().clear();
        p3.getpC().clear();

        PowerUpCard tagbackGrenade = new TagbackGrenade(BLUE);
        p2.addPowerUpCard(tagbackGrenade);
        lS.add("Player 1");
        assertTrue(game.isValidUsePowerUpCard("Player 2", "Tagback Grenade", "BLUE", lS, null));
        game.usePowerUpCard("Player 2", "Tagback Grenade", "BLUE", lS, null);
        assertEquals(YELLOW, p1.getpB().getMarks().get(0).getC());
        assertFalse(p2.getpC().contains(tagbackGrenade));
        assertEquals(4, grid.getPowerUpDiscardPile().size());

        lS.clear();

        PowerUpCard targetingScope = new TargetingScope(Colour.RED);
        p1.addPowerUpCard(targetingScope);
        lS.add("Player 2");
        assertTrue(game.isValidUsePowerUpCard("Player 1", "Targeting Scope", "RED", lS, Colour.valueOf("RED")));
        game.usePowerUpCard("Player 1", "Targeting Scope", "RED", lS, Colour.valueOf("RED"));
        assertEquals(BLUE, p2.getpB().getDamages().getDamageTr()[2].getC());
        for (AmmoCube ac : p1.getaC()) {
            if (ac != null)
                assertNotEquals(Colour.RED, ac.getC());
        }
        assertFalse(p1.getpC().contains(targetingScope));
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
        assertFalse(p1.getpC().contains(newton));
        assertEquals(6, grid.getPowerUpDiscardPile().size());

        lS.clear();

        PowerUpCard teleporter = new Teleporter(BLUE);
        p1.addPowerUpCard(teleporter);
        lS.add("1");
        lS.add("1");
        assertTrue(game.isValidUsePowerUpCard("Player 1", "Teleporter", "BLUE", lS, null));
        game.usePowerUpCard("Player 1", "Teleporter", "BLUE", lS, null);
        assertEquals(grid.getBoard().getArena()[1][1], p1.getCell());
        assertFalse(p1.getpC().contains(teleporter));
        assertEquals(7, grid.getPowerUpDiscardPile().size());

        lS.clear();


        //Reloading

        assertFalse(game.isValidReload("Player 1", "Machine Gun"));
        p1.addNewAC(new AmmoCube(BLUE));
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
        for (AmmoCube ac : p1.getaC()) {
            assertNull(ac);
        }

        assertEquals(GameState.RELOADED, game.getGameState());


        //Scoring

        grid.damage(p3, p2, 1);
        grid.damage(p1, p2, 9);
        grid.damage(p1, p3, 9);

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

        assertEquals(6, p2.getpB().getPoints().getPoints().size());
        assertEquals(6, p3.getpB().getPoints().getPoints().size());

        for (int i = 0; i < 12; i++) {
            assertNull(p2.getpB().getDamages().getDamageTr()[i]);
            assertNull(p3.getpB().getDamages().getDamageTr()[i]);
        }

        assertEquals(1, p2.getpC().size());
        assertEquals(1, p3.getpC().size());

        //assertEquals(GameState.DEATH, game.getGameState());


        //Discard card for new spawn point

        p2.getpC().clear();
        p3.getpC().clear();

        PowerUpCard targetingScope2 = new TargetingScope(BLUE);

        p2.addPowerUpCard(targetingScope2);
        assertTrue(game.isValidDiscardCardForSpawnPoint("Player 2", "Targeting Scope", "BLUE"));
        game.discardCardForSpawnPoint("Player 2", "Targeting Scope", "BLUE");
        assertEquals(p2.getCell(), grid.getBoard().getArena()[0][2]);
        assertFalse(p2.getpC().contains(targetingScope));
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
        assertFalse(p3.getpC().contains(tagbackGrenade2));
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
    void GameGrabShootTest() {
        Game game = new Game();
        Grid grid = game.getGrid();

        game.gameStart("Player 1", BLUE);
        Player p1 = grid.getPlayerObject("Player 1");

        game.addPlayer("Player 2", YELLOW);
        Player p2 = grid.getPlayerObject("Player 2");

        game.addPlayer("Player 3", GREEN);
        Player p3 = grid.getPlayerObject("Player 3");

        game.receiveType(1);

        game.giveTwoPUCard("Player 1");
        System.out.print("\nPowerUpCard picked from the deck for Player 1: " + p1.getpC().get(0).getCardName() + " coloured " + p1.getpC().get(0).getC().getAbbreviation() + ", and " + p1.getpC().get(1).getCardName() + " coloured " + p1.getpC().get(1).getC().getAbbreviation());
        game.pickAndDiscardCard("Player 1", p1.getpC().get(0).getCardName(), p1.getpC().get(0).getC().getAbbreviation());

        game.giveTwoPUCard("Player 2");
        System.out.print("\nPowerUpCard picked from the deck for Player 2: " + p2.getpC().get(0).getCardName() + " coloured " + p2.getpC().get(0).getC().getAbbreviation() + ", and " + p2.getpC().get(1).getCardName() + " coloured " + p2.getpC().get(1).getC().getAbbreviation());
        game.pickAndDiscardCard("Player 2", p2.getpC().get(1).getCardName(), p2.getpC().get(1).getC().getAbbreviation());

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

        assertNotNull(p1.getaC()[0]);
        assertNotNull(p1.getaC()[1]);
        assertNull(p1.getaC()[2]);
        assertNotNull(p1.getaC()[3]);
        assertNotNull(p1.getaC()[4]);
        assertNull(p1.getaC()[5]);
        assertNotNull(p1.getaC()[6]);
        assertNull(p1.getaC()[7]);
        assertNull(p1.getaC()[8]);

        assertEquals(2, p1.getpC().size());

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
        assertEquals(p3.getC(), p1.getpB().getDamages().getDamageTr()[0].getC());
        assertEquals(p3.getC(), p1.getpB().getDamages().getDamageTr()[1].getC());
        assertEquals(p3.getC(), p2.getpB().getDamages().getDamageTr()[0].getC());
        assertEquals(p3.getC(), p2.getpB().getDamages().getDamageTr()[1].getC());
    }

    @Test
    void GameGrabShootTest2() {
        Game game = new Game();
        Grid grid = game.getGrid();

        game.gameStart("Player 1", BLUE);
        Player p1 = grid.getPlayerObject("Player 1");

        game.addPlayer("Player 2", YELLOW);
        Player p2 = grid.getPlayerObject("Player 2");

        game.addPlayer("Player 3", GREEN);
        Player p3 = grid.getPlayerObject("Player 3");

        game.receiveType(1);

        game.giveTwoPUCard("Player 1");
        System.out.print("\nPowerUpCard picked from the deck for Player 1: " + p1.getpC().get(0).getCardName() + " coloured " + p1.getpC().get(0).getC().getAbbreviation() + ", and " + p1.getpC().get(1).getCardName() + " coloured " + p1.getpC().get(1).getC().getAbbreviation());
        game.pickAndDiscardCard("Player 1", p1.getpC().get(0).getCardName(), p1.getpC().get(0).getC().getAbbreviation());

        game.giveTwoPUCard("Player 2");
        System.out.print("\nPowerUpCard picked from the deck for Player 2: " + p2.getpC().get(0).getCardName() + " coloured " + p2.getpC().get(0).getC().getAbbreviation() + ", and " + p2.getpC().get(1).getCardName() + " coloured " + p2.getpC().get(1).getC().getAbbreviation());
        game.pickAndDiscardCard("Player 2", p2.getpC().get(1).getCardName(), p2.getpC().get(1).getC().getAbbreviation());

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

        assertEquals(1, p1.getwC().size());
        assertTrue(p1.getwC().contains(lockRifle));

        assertNotNull(p1.getaC()[0]);
        assertNull(p1.getaC()[1]);
        assertNull(p1.getaC()[2]);
        assertNull(p1.getaC()[3]);
        assertNull(p1.getaC()[4]);
        assertNull(p1.getaC()[5]);
        assertNotNull(p1.getaC()[6]);
        assertNull(p1.getaC()[7]);
        assertNull(p1.getaC()[8]);*/

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
    void GameShootTHORTest() {
        Game game = new Game();
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
        System.out.print("\nPowerUpCard picked from the deck for Player 1: " + p1.getpC().get(0).getCardName() + " coloured " + p1.getpC().get(0).getC().getAbbreviation() + ", and " + p1.getpC().get(1).getCardName() + " coloured " + p1.getpC().get(1).getC().getAbbreviation());
        game.pickAndDiscardCard("Player 1", p1.getpC().get(0).getCardName(), p1.getpC().get(0).getC().getAbbreviation());

        game.giveTwoPUCard("Player 2");
        System.out.print("\nPowerUpCard picked from the deck for Player 2: " + p2.getpC().get(0).getCardName() + " coloured " + p2.getpC().get(0).getC().getAbbreviation() + ", and " + p2.getpC().get(1).getCardName() + " coloured " + p2.getpC().get(1).getC().getAbbreviation());
        game.pickAndDiscardCard("Player 2", p2.getpC().get(1).getCardName(), p2.getpC().get(1).getC().getAbbreviation());

        game.giveTwoPUCard("Player 3");
        System.out.print("\nPowerUpCard picked from the deck for Player 3: " + p3.getpC().get(0).getCardName() + " coloured " + p3.getpC().get(0).getC().getAbbreviation() + ", and " + p3.getpC().get(1).getCardName() + " coloured " + p3.getpC().get(1).getC().getAbbreviation());
        game.pickAndDiscardCard("Player 3", p3.getpC().get(1).getCardName(), p3.getpC().get(1).getC().getAbbreviation());

        game.giveTwoPUCard("Player 4");
        System.out.print("\nPowerUpCard picked from the deck for Player 4: " + p4.getpC().get(0).getCardName() + " coloured " + p4.getpC().get(0).getC().getAbbreviation() + ", and " + p4.getpC().get(1).getCardName() + " coloured " + p4.getpC().get(1).getC().getAbbreviation());
        game.pickAndDiscardCard("Player 4", p4.getpC().get(1).getCardName(), p4.getpC().get(1).getC().getAbbreviation());

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
        System.out.println("\n" + p1.getaC()[0].getC().toString());
        assertNull(p1.getaC()[1]);
        assertNull(p1.getaC()[2]);
        System.out.println("\n" + p1.getaC()[3].getC().toString());
        System.out.println("\n" + p1.getaC()[4].getC().toString());
        System.out.println("\n" + p1.getaC()[5].getC().toString());

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
    void GameShootZX2Test() {
        Game game = new Game();
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
        System.out.print("\nPowerUpCard picked from the deck for Player 1: " + p1.getpC().get(0).getCardName() + " coloured " + p1.getpC().get(0).getC().getAbbreviation() + ", and " + p1.getpC().get(1).getCardName() + " coloured " + p1.getpC().get(1).getC().getAbbreviation());
        game.pickAndDiscardCard("Player 1", p1.getpC().get(0).getCardName(), p1.getpC().get(0).getC().getAbbreviation());

        game.giveTwoPUCard("Player 2");
        System.out.print("\nPowerUpCard picked from the deck for Player 2: " + p2.getpC().get(0).getCardName() + " coloured " + p2.getpC().get(0).getC().getAbbreviation() + ", and " + p2.getpC().get(1).getCardName() + " coloured " + p2.getpC().get(1).getC().getAbbreviation());
        game.pickAndDiscardCard("Player 2", p2.getpC().get(1).getCardName(), p2.getpC().get(1).getC().getAbbreviation());

        game.giveTwoPUCard("Player 3");
        System.out.print("\nPowerUpCard picked from the deck for Player 3: " + p3.getpC().get(0).getCardName() + " coloured " + p3.getpC().get(0).getC().getAbbreviation() + ", and " + p3.getpC().get(1).getCardName() + " coloured " + p3.getpC().get(1).getC().getAbbreviation());
        game.pickAndDiscardCard("Player 3", p3.getpC().get(1).getCardName(), p3.getpC().get(1).getC().getAbbreviation());

        game.giveTwoPUCard("Player 4");
        System.out.print("\nPowerUpCard picked from the deck for Player 4: " + p4.getpC().get(0).getCardName() + " coloured " + p4.getpC().get(0).getC().getAbbreviation() + ", and " + p4.getpC().get(1).getCardName() + " coloured " + p4.getpC().get(1).getC().getAbbreviation());
        game.pickAndDiscardCard("Player 4", p4.getpC().get(1).getCardName(), p4.getpC().get(1).getC().getAbbreviation());

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
    void GameShootWhisperTest() {
        Game game = new Game();
        Grid grid = game.getGrid();

        game.gameStart("Player 1", BLUE);
        Player p1 = grid.getPlayerObject("Player 1");

        game.addPlayer("Player 2", YELLOW);
        Player p2 = grid.getPlayerObject("Player 2");

        game.receiveType(1);

        game.giveTwoPUCard("Player 1");
        System.out.print("\nPowerUpCard picked from the deck for Player 1: " + p1.getpC().get(0).getCardName() + " coloured " + p1.getpC().get(0).getC().getAbbreviation() + ", and " + p1.getpC().get(1).getCardName() + " coloured " + p1.getpC().get(1).getC().getAbbreviation());
        game.pickAndDiscardCard("Player 1", p1.getpC().get(0).getCardName(), p1.getpC().get(0).getC().getAbbreviation());

        game.giveTwoPUCard("Player 2");
        System.out.print("\nPowerUpCard picked from the deck for Player 2: " + p2.getpC().get(0).getCardName() + " coloured " + p2.getpC().get(0).getC().getAbbreviation() + ", and " + p2.getpC().get(1).getCardName() + " coloured " + p2.getpC().get(1).getC().getAbbreviation());
        game.pickAndDiscardCard("Player 2", p2.getpC().get(1).getCardName(), p2.getpC().get(1).getC().getAbbreviation());

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
    void GameShootVortexCannonTest() {
        Game game = new Game();
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
        System.out.print("\nPowerUpCard picked from the deck for Player 1: " + p1.getpC().get(0).getCardName() + " coloured " + p1.getpC().get(0).getC().getAbbreviation() + ", and " + p1.getpC().get(1).getCardName() + " coloured " + p1.getpC().get(1).getC().getAbbreviation());
        game.pickAndDiscardCard("Player 1", p1.getpC().get(0).getCardName(), p1.getpC().get(0).getC().getAbbreviation());

        game.giveTwoPUCard("Player 2");
        System.out.print("\nPowerUpCard picked from the deck for Player 2: " + p2.getpC().get(0).getCardName() + " coloured " + p2.getpC().get(0).getC().getAbbreviation() + ", and " + p2.getpC().get(1).getCardName() + " coloured " + p2.getpC().get(1).getC().getAbbreviation());
        game.pickAndDiscardCard("Player 2", p2.getpC().get(1).getCardName(), p2.getpC().get(1).getC().getAbbreviation());

        game.giveTwoPUCard("Player 3");
        System.out.print("\nPowerUpCard picked from the deck for Player 3: " + p3.getpC().get(0).getCardName() + " coloured " + p3.getpC().get(0).getC().getAbbreviation() + ", and " + p3.getpC().get(1).getCardName() + " coloured " + p3.getpC().get(1).getC().getAbbreviation());
        game.pickAndDiscardCard("Player 3", p3.getpC().get(1).getCardName(), p3.getpC().get(1).getC().getAbbreviation());

        game.giveTwoPUCard("Player 4");
        System.out.print("\nPowerUpCard picked from the deck for Player 4: " + p4.getpC().get(0).getCardName() + " coloured " + p4.getpC().get(0).getC().getAbbreviation() + ", and " + p4.getpC().get(1).getCardName() + " coloured " + p4.getpC().get(1).getC().getAbbreviation());
        game.pickAndDiscardCard("Player 4", p4.getpC().get(1).getCardName(), p4.getpC().get(1).getC().getAbbreviation());

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
    void GameShootTractorBeamCannonTest() {
        Game game = new Game();
        Grid grid = game.getGrid();

        game.gameStart("Player 1", BLUE);
        Player p1 = grid.getPlayerObject("Player 1");

        game.addPlayer("Player 2", YELLOW);
        Player p2 = grid.getPlayerObject("Player 2");

        game.receiveType(1);

        game.giveTwoPUCard("Player 1");
        System.out.print("\nPowerUpCard picked from the deck for Player 1: " + p1.getpC().get(0).getCardName() + " coloured " + p1.getpC().get(0).getC().getAbbreviation() + ", and " + p1.getpC().get(1).getCardName() + " coloured " + p1.getpC().get(1).getC().getAbbreviation());
        game.pickAndDiscardCard("Player 1", p1.getpC().get(0).getCardName(), p1.getpC().get(0).getC().getAbbreviation());

        game.giveTwoPUCard("Player 2");
        System.out.print("\nPowerUpCard picked from the deck for Player 2: " + p2.getpC().get(0).getCardName() + " coloured " + p2.getpC().get(0).getC().getAbbreviation() + ", and " + p2.getpC().get(1).getCardName() + " coloured " + p2.getpC().get(1).getC().getAbbreviation());
        game.pickAndDiscardCard("Player 2", p2.getpC().get(1).getCardName(), p2.getpC().get(1).getC().getAbbreviation());

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
    void GameShootSledgehammerTest() {
        Game game = new Game();
        Grid grid = game.getGrid();

        game.gameStart("Player 1", BLUE);
        Player p1 = grid.getPlayerObject("Player 1");

        game.addPlayer("Player 2", YELLOW);
        Player p2 = grid.getPlayerObject("Player 2");

        game.receiveType(1);

        game.giveTwoPUCard("Player 1");
        System.out.print("\nPowerUpCard picked from the deck for Player 1: " + p1.getpC().get(0).getCardName() + " coloured " + p1.getpC().get(0).getC().getAbbreviation() + ", and " + p1.getpC().get(1).getCardName() + " coloured " + p1.getpC().get(1).getC().getAbbreviation());
        game.pickAndDiscardCard("Player 1", p1.getpC().get(0).getCardName(), p1.getpC().get(0).getC().getAbbreviation());

        game.giveTwoPUCard("Player 2");
        System.out.print("\nPowerUpCard picked from the deck for Player 2: " + p2.getpC().get(0).getCardName() + " coloured " + p2.getpC().get(0).getC().getAbbreviation() + ", and " + p2.getpC().get(1).getCardName() + " coloured " + p2.getpC().get(1).getC().getAbbreviation());
        game.pickAndDiscardCard("Player 2", p2.getpC().get(1).getCardName(), p2.getpC().get(1).getC().getAbbreviation());

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
    void GameShootShotgunTest() {
        Game game = new Game();
        Grid grid = game.getGrid();

        game.gameStart("Player 1", BLUE);
        Player p1 = grid.getPlayerObject("Player 1");

        game.addPlayer("Player 2", YELLOW);
        Player p2 = grid.getPlayerObject("Player 2");

        game.receiveType(1);

        game.giveTwoPUCard("Player 1");
        System.out.print("\nPowerUpCard picked from the deck for Player 1: " + p1.getpC().get(0).getCardName() + " coloured " + p1.getpC().get(0).getC().getAbbreviation() + ", and " + p1.getpC().get(1).getCardName() + " coloured " + p1.getpC().get(1).getC().getAbbreviation());
        game.pickAndDiscardCard("Player 1", p1.getpC().get(0).getCardName(), p1.getpC().get(0).getC().getAbbreviation());

        game.giveTwoPUCard("Player 2");
        System.out.print("\nPowerUpCard picked from the deck for Player 2: " + p2.getpC().get(0).getCardName() + " coloured " + p2.getpC().get(0).getC().getAbbreviation() + ", and " + p2.getpC().get(1).getCardName() + " coloured " + p2.getpC().get(1).getC().getAbbreviation());
        game.pickAndDiscardCard("Player 2", p2.getpC().get(1).getCardName(), p2.getpC().get(1).getC().getAbbreviation());

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
    void GameShootShockwaveTest() {
        Game game = new Game();
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
        System.out.print("\nPowerUpCard picked from the deck for Player 1: " + p1.getpC().get(0).getCardName() + " coloured " + p1.getpC().get(0).getC().getAbbreviation() + ", and " + p1.getpC().get(1).getCardName() + " coloured " + p1.getpC().get(1).getC().getAbbreviation());
        game.pickAndDiscardCard("Player 1", p1.getpC().get(0).getCardName(), p1.getpC().get(0).getC().getAbbreviation());

        game.giveTwoPUCard("Player 2");
        System.out.print("\nPowerUpCard picked from the deck for Player 2: " + p2.getpC().get(0).getCardName() + " coloured " + p2.getpC().get(0).getC().getAbbreviation() + ", and " + p2.getpC().get(1).getCardName() + " coloured " + p2.getpC().get(1).getC().getAbbreviation());
        game.pickAndDiscardCard("Player 2", p2.getpC().get(1).getCardName(), p2.getpC().get(1).getC().getAbbreviation());

        game.giveTwoPUCard("Player 3");
        System.out.print("\nPowerUpCard picked from the deck for Player 3: " + p3.getpC().get(0).getCardName() + " coloured " + p3.getpC().get(0).getC().getAbbreviation() + ", and " + p3.getpC().get(1).getCardName() + " coloured " + p3.getpC().get(1).getC().getAbbreviation());
        game.pickAndDiscardCard("Player 3", p3.getpC().get(1).getCardName(), p3.getpC().get(1).getC().getAbbreviation());

        game.giveTwoPUCard("Player 4");
        System.out.print("\nPowerUpCard picked from the deck for Player 4: " + p4.getpC().get(0).getCardName() + " coloured " + p4.getpC().get(0).getC().getAbbreviation() + ", and " + p4.getpC().get(1).getCardName() + " coloured " + p4.getpC().get(1).getC().getAbbreviation());
        game.pickAndDiscardCard("Player 4", p4.getpC().get(1).getCardName(), p4.getpC().get(1).getC().getAbbreviation());

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
}