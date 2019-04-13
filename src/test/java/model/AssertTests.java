package model;

import model.board.Board;
import model.board.Cell;
import model.board.KillTrack;
import model.board.WeaponSlot;
import model.cards.PowerUpCard;
import model.cards.WeaponCard;
import model.cards.powerupcards.*;
import model.cards.weaponcards.*;
import model.decks.PowerUpDeck;
import model.decks.WeaponDeck;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AssertTests {

    @Test
    void KillTrackTesting() {
        KillTrack k = new KillTrack();
        int[] skulls = k.getSkulls();

        Colour[] c = k.getC();

        assertEquals(0, skulls[0]);
        assertEquals(0, skulls[1]);
        assertEquals(0, skulls[2]);
        assertEquals(0, skulls[3]);
        assertEquals(0, skulls[4]);
        assertEquals(0, skulls[5]);
        assertEquals(0, skulls[6]);
        assertEquals(0, skulls[7]);

        assertNull(c[0]);
        assertNull(c[1]);
        assertNull(c[2]);
        assertNull(c[3]);
        assertNull(c[4]);
        assertNull(c[5]);
        assertNull(c[6]);
        assertNull(c[7]);

        c[0] = Colour.YELLOW;
        c[1] = Colour.BLUE;
        c[2] = Colour.GREEN;
        c[3] = Colour.WHITE;
        c[4] = Colour.BLACK;
        c[5] = Colour.RED;
        c[6] = Colour.PURPLE;
        c[7] = Colour.BLUE;
        k.setC(c);

        assertEquals(Colour.YELLOW, k.getC()[0]);
        assertEquals(Colour.BLUE, k.getC()[1]);
        assertEquals(Colour.GREEN, k.getC()[2]);
        assertEquals(Colour.WHITE, k.getC()[3]);
        assertEquals(Colour.BLACK, k.getC()[4]);
        assertEquals(Colour.RED, k.getC()[5]);
        assertEquals(Colour.PURPLE, k.getC()[6]);
        assertEquals(Colour.BLUE, k.getC()[7]);

        skulls[0] = 0;
        skulls[1] = 1;
        skulls[2] = 2;
        skulls[3] = 3;
        skulls[4] = 2;
        skulls[5] = 1;
        skulls[6] = 0;
        skulls[7] = 1;
        k.setSkulls(skulls);

        assertEquals(0, k.getSkulls()[0]);
        assertEquals(1, k.getSkulls()[1]);
        assertEquals(2, k.getSkulls()[2]);
        assertEquals(3, k.getSkulls()[3]);
        assertEquals(2, k.getSkulls()[4]);
        assertEquals(1, k.getSkulls()[5]);
        assertEquals(0, k.getSkulls()[6]);
        assertEquals(1, k.getSkulls()[7]);
    }


    @Test
    void DamageTokenTest() {
        DamageToken dt = new DamageToken(Colour.BLUE);

        assertEquals(Colour.BLUE, dt.getC());
    }


    @Test
    void CellExists() {
        int[] pw = {1, 2};
        int[] pd = {3, 4};
        Position p = new Position(0, 0);
        Cell cell = new Cell(1, Colour.RED, pw, pd, p);

        int[] posWall = cell.getPosWall();
        int[] posDoor = cell.getPosDoor();

        assertEquals(1, cell.getStatus());
        assertEquals(Colour.RED, cell.getC());

        for(int i = 0; i < posWall.length; i++)
            assertEquals(pw[i], posWall[i]);

        for(int i = 0; i < posDoor.length; i++)
            assertEquals(pd[i], posDoor[i]);
    }


    @Test
    void CellDoesNotExists() {
        Position p = new Position(0, 3);
        Cell cell = new Cell(-1, p);
        assertEquals(-1, cell.getStatus());
    }


    @Test
    void CellCorrectPositionTest() {
        Position pos = new Position(1, 3);
        Cell cell = new Cell(-1, pos);

        assertEquals(1, cell.getP().getX());
        assertEquals(3, cell.getP().getY());
    }


    @Test
    void DamageTrackTest() {
        DamageTrack dt = new DamageTrack();
        DamageToken dt1 = new DamageToken(Colour.RED);
        DamageToken dt2 = new DamageToken(Colour.GREEN);

        dt.addDamage(0, Colour.BLUE);   //useless, it just tests the "else if" without entering the "if"
        dt.addDamage(2, Colour.RED);

        assertEquals(dt1.getC(), dt.getDT(0).getC());
        assertEquals(dt1.getC(), dt.getDT(1).getC());

        dt.addDamage(10, Colour.GREEN);

        assertEquals(dt2.getC(), dt.getDT(2).getC());
        assertEquals(dt2.getC(), dt.getDT(3).getC());
        assertEquals(dt2.getC(), dt.getDT(4).getC());
        assertEquals(dt2.getC(), dt.getDT(5).getC());
        assertEquals(dt2.getC(), dt.getDT(6).getC());
        assertEquals(dt2.getC(), dt.getDT(7).getC());
        assertEquals(dt2.getC(), dt.getDT(8).getC());
        assertEquals(dt2.getC(), dt.getDT(9).getC());
        assertEquals(dt2.getC(), dt.getDT(10).getC());
        assertEquals(dt2.getC(), dt.getDT(11).getC());

        dt.clean();

        assertNull(dt.getDT(0));
        assertNull(dt.getDT(1));
        assertNull(dt.getDT(2));
        assertNull(dt.getDT(3));
        assertNull(dt.getDT(4));
        assertNull(dt.getDT(5));
        assertNull(dt.getDT(6));
        assertNull(dt.getDT(7));
        assertNull(dt.getDT(8));
        assertNull(dt.getDT(9));
        assertNull(dt.getDT(10));
        assertNull(dt.getDT(11));
    }


    @Test
    void AmmoCubeRightColour() throws InvalidColourException {
        AmmoCube cube1 = new AmmoCube(Colour.BLUE);
        AmmoCube cube2 = new AmmoCube(Colour.YELLOW);
        AmmoCube cube3 = new AmmoCube(Colour.RED);

        assertEquals(Colour.BLUE, cube1.getC());
        assertEquals("B", cube1.getC().getAbbreviation());

        assertEquals(Colour.YELLOW, cube2.getC());
        assertEquals("Y", cube2.getC().getAbbreviation());

        assertEquals(Colour.RED, cube3.getC());
        assertEquals("R", cube3.getC().getAbbreviation());
    }


    @Test
    void FigureRightColour() {
        Figure fig = new Figure(Colour.GREEN);
        Colour colour = fig.getC();

        assertEquals(Colour.GREEN, colour);
    }


    @Test
    void PositionTest() {
        Position pos1 = new Position(0, 0);

        assertEquals(0, pos1.getX());
        assertEquals(0, pos1.getY());

        Position pos2 = new Position(1, 3);

        assertEquals(1, pos2.getX());
        assertEquals(3, pos2.getY());
    }


    @Test
    void PlayerTest() throws InvalidColourException {
        Player p1 = new Player("Test", Colour.BLUE, true);
        int[] pm = new int[]{0, 2};
        int[] pp = new int[]{1, 3};
        Position pos1 = new Position(0, 1);
        Position pos2 = new Position(0, 2);
        Cell cell1 = new Cell(1, Colour.BLUE, pm, pp, pos1);
        Cell cell2 = new Cell(0, Colour.BLUE, pm, pp, pos2);

        assertTrue(p1.isFirstPlayerCard());

        p1.setScore(42);
        assertEquals(42, p1.getScore());

        assertTrue(p1.getpB().mIsEmpty());

        assertEquals(3, p1.getaC().length);
        assertEquals(Colour.RED, p1.getaC()[0].getC());
        //assertEquals(Colour.RED, p1.getaC()[1].getC());
        //assertEquals(Colour.RED, p1.getaC()[2].getC());
        assertEquals(Colour.BLUE, p1.getaC()[1].getC());
        //assertEquals(Colour.BLUE, p1.getaC()[4].getC());
        //assertEquals(Colour.BLUE, p1.getaC()[5].getC());
        assertEquals(Colour.YELLOW, p1.getaC()[2].getC());
        //assertEquals(Colour.YELLOW, p1.getaC()[7].getC());
        //assertEquals(Colour.YELLOW, p1.getaC()[8].getC());

        //TODO test addAC() --> need method to remove an AmmoCube from p1.aC

        assertEquals(0, p1.getwC().size());

        WeaponCard w1 = new Electroscythe();
        WeaponCard w2 = new THOR();
        WeaponCard w3 = new Furnace();
        p1.addWeaponCard(w1);
        p1.addWeaponCard(w2);
        p1.addWeaponCard(w3);
        assertEquals(w1, p1.getwC().get(0));
        assertEquals(w2, p1.getwC().get(1));
        assertEquals(w3, p1.getwC().get(2));

        p1.removeWeaponCard(w3);
        assertEquals(2, p1.getwC().size());
        p1.removeWeaponCard(w2);
        assertEquals(1, p1.getwC().size());
        p1.removeWeaponCard(w1);
        assertEquals(0, p1.getwC().size());

        assertEquals(0, p1.getpC().size());

        PowerUpCard pu1 = new Newton(Colour.YELLOW);
        PowerUpCard pu2 = new TagbackGrenade(Colour.BLUE);
        PowerUpCard pu3 = new TargetingScope(Colour.RED);
        p1.addPowerUpCard(pu1);
        p1.addPowerUpCard(pu2);
        p1.addPowerUpCard(pu3);
        assertEquals(pu1, p1.getpC().get(0));
        assertEquals(pu2, p1.getpC().get(1));
        assertEquals(pu3, p1.getpC().get(2));

        p1.removePowerUpCard(pu3);
        assertEquals(2, p1.getpC().size());
        p1.removePowerUpCard(pu2);
        assertEquals(1, p1.getpC().size());
        p1.removePowerUpCard(pu1);
        assertEquals(0, p1.getpC().size());

        p1.setCell(cell1);
        assertEquals(pos1, p1.getCell().getP());

        p1.changeCell(cell2);
        assertSame(pos2, p1.getCell().getP());
    }


    @Test
    void PointsPlayerBoardTest() {
        PointsPlayerBoard ppb = new PointsPlayerBoard();

        int i = ppb.getInt(0);
        assertEquals(1, i);

        i = ppb.getInt(1);
        assertEquals(8, i);

        i = ppb.getInt(2);
        assertEquals(6, i);

        i = ppb.getInt(3);
        assertEquals(4, i);

        i = ppb.getInt(4);
        assertEquals(2, i);

        i = ppb.getInt(5);
        assertEquals(1, i);

        i = ppb.getInt(6);
        assertEquals(1, i);

        ppb.remove();
        assertEquals(6, ppb.getInt(1));

        ppb.remove();
        assertEquals(4, ppb.getInt(1));

        ppb.remove();
        assertEquals(2, ppb.getInt(1));

        ppb.remove();
        assertEquals(1, ppb.getInt(1));

        ppb.remove();
        assertEquals(1, ppb.getInt(1));

        assertEquals(1, ppb.getInt(0));
    }


    @Test
    void PlayerBoardTest() {
        PlayerBoard pb = new PlayerBoard();

        assertNotNull(pb.getActions());
        Actions newActions = new Actions();
        pb.setActions(newActions);
        assertEquals(newActions, pb.getActions());

        assertNotNull(pb.getDamages());
        DamageTrack newDT = new DamageTrack();
        pb.setDamages(newDT);
        assertEquals(newDT, pb.getDamages());

        assertEquals(8, pb.getPoints().getInt(1));
        PointsPlayerBoard ppb = new PointsPlayerBoard();
        ppb.remove();
        pb.setPoints(ppb);
        assertEquals(6, pb.getPoints().getInt(1));

        assertTrue(pb.mIsEmpty());
        DamageToken dt1 = new DamageToken(Colour.RED);
        pb.addMark(dt1);
        DamageToken dt2 = new DamageToken(Colour.YELLOW);
        pb.addMark(dt2);
        DamageToken dt3 = new DamageToken(Colour.RED);
        pb.addMark(dt3);
        DamageToken dt4 = new DamageToken(Colour.RED);
        pb.addMark(dt4);
        DamageToken dt5 = new DamageToken(Colour.BLUE);
        pb.addMark(dt5);
        DamageToken dt6 = new DamageToken(Colour.RED);
        pb.addMark(dt6);
        assertEquals(5, pb.getMarks().size());

        pb.clearMark(Colour.RED);
        assertEquals(2, pb.getMarks().size());

        pb.clearMark(Colour.YELLOW);
        assertEquals(1, pb.getMarks().size());

        pb.clearMark(Colour.BLUE);
        assertTrue(pb.mIsEmpty());
    }


    @Test
    void BoardTest() throws InvalidColourException {
        WeaponCard w1 = new MachineGun();
        WeaponCard w2 = new MachineGun();
        WeaponCard w3 = new MachineGun();
        WeaponCard w4 = new MachineGun();
        WeaponCard w5 = new MachineGun();
        WeaponCard w6 = new MachineGun();
        WeaponCard w7 = new MachineGun();
        WeaponCard w8 = new MachineGun();
        WeaponCard w9 = new MachineGun();


        Board board1 = new Board(1, w1, w2, w3, w4, w5, w6, w7, w8, w9);
        Cell[][] arena1 = board1.getArena();

        assertEquals(Colour.RED, arena1[0][0].getC());
        assertEquals(Colour.BLUE, arena1[0][1].getC());
        assertEquals(Colour.BLUE, arena1[0][2].getC());
        assertEquals(-1, arena1[0][3].getStatus());
        assertEquals(Colour.RED, arena1[1][0].getC());
        assertEquals(Colour.PURPLE, arena1[1][1].getC());
        assertEquals(Colour.PURPLE, arena1[1][2].getC());
        assertEquals(Colour.YELLOW, arena1[1][3].getC());
        assertEquals(Colour.WHITE, arena1[2][0].getC());
        assertEquals(Colour.WHITE, arena1[2][1].getC());
        assertEquals(Colour.WHITE, arena1[2][2].getC());
        assertEquals(Colour.YELLOW, arena1[2][3].getC());

        Board board2 = new Board(2, w1, w2, w3, w4, w5, w6, w7, w8, w9);
        Cell[][] arena2 = board2.getArena();

        assertEquals(Colour.BLUE, arena2[0][0].getC());
        assertEquals(Colour.BLUE, arena2[0][1].getC());
        assertEquals(Colour.BLUE, arena2[0][2].getC());
        assertEquals(-1, arena2[0][3].getStatus());
        assertEquals(Colour.PURPLE, arena2[1][0].getC());
        assertEquals(Colour.PURPLE, arena2[1][1].getC());
        assertEquals(Colour.PURPLE, arena2[1][2].getC());
        assertEquals(Colour.YELLOW, arena2[1][3].getC());
        assertEquals(-1, arena2[2][0].getStatus());
        assertEquals(Colour.WHITE, arena2[2][1].getC());
        assertEquals(Colour.WHITE, arena2[2][2].getC());
        assertEquals(Colour.YELLOW, arena2[2][3].getC());

        Board board3 = new Board(3, w1, w2, w3, w4, w5, w6, w7, w8, w9);
        Cell[][] arena3 = board3.getArena();

        assertEquals(Colour.BLUE, arena3[0][0].getC());
        assertEquals(Colour.BLUE, arena3[0][1].getC());
        assertEquals(Colour.BLUE, arena3[0][2].getC());
        assertEquals(Colour.GREEN, arena3[0][3].getC());
        assertEquals(Colour.RED, arena3[1][0].getC());
        assertEquals(Colour.RED, arena3[1][1].getC());
        assertEquals(Colour.YELLOW, arena3[1][2].getC());
        assertEquals(Colour.YELLOW, arena3[1][3].getC());
        assertEquals(-1, arena3[2][0].getStatus());
        assertEquals(Colour.WHITE, arena3[2][1].getC());
        assertEquals(Colour.YELLOW, arena3[2][2].getC());
        assertEquals(Colour.YELLOW, arena3[2][3].getC());

        Board board4 = new Board(4, w1, w2, w3, w4, w5, w6, w7, w8, w9);
        Cell[][] arena4 = board4.getArena();

        assertEquals(Colour.RED, arena4[0][0].getC());
        assertEquals(Colour.BLUE, arena4[0][1].getC());
        assertEquals(Colour.BLUE, arena4[0][2].getC());
        assertEquals(Colour.GREEN, arena4[0][3].getC());
        assertEquals(Colour.RED, arena4[1][0].getC());
        assertEquals(Colour.PURPLE, arena4[1][1].getC());
        assertEquals(Colour.YELLOW, arena4[1][2].getC());
        assertEquals(Colour.YELLOW, arena4[1][3].getC());
        assertEquals(Colour.WHITE, arena4[2][0].getC());
        assertEquals(Colour.WHITE, arena4[2][1].getC());
        assertEquals(Colour.YELLOW, arena4[2][2].getC());
        assertEquals(Colour.YELLOW, arena4[2][3].getC());
    }


    @Test
    void GridTest() throws InvalidColourException {
        Grid grid = new Grid();
        Player p1 = new Player("Test", Colour.BLUE, true);
        grid.addPlayer(p1);
        List<Player> players = grid.getPlayers();

        assertEquals(players.get(0).getNickName(), "Test");
        assertEquals(players.get(0).getC(), Colour.BLUE);

        int numPlayers = grid.getNumPlayer();
        assertEquals(1, numPlayers);

        assertNull(grid.whereAmI(p1));
    }


    @Test
    void WeaponSlotCorrectConstructor() throws InvalidColourException {
        WeaponCard wc1 = new MachineGun();
        WeaponCard wc2 = new MachineGun();
        WeaponCard wc3 = new MachineGun();
        WeaponSlot w1 = new WeaponSlot(1, wc1, wc2, wc3);

        int n = w1.getN();
        WeaponCard wc11 = w1.getCard1();
        WeaponCard wc12 = w1.getCard2();
        WeaponCard wc13 = w1.getCard3();

        assertEquals(1, n);
        assertEquals(wc1, wc11);
        assertEquals(wc2, wc12);
        assertEquals(wc3, wc13);
    }


    @Test
    void WeaponSlotSetCard() throws InvalidColourException {
        WeaponCard wc1 = new MachineGun();
        WeaponCard wc2 = new MachineGun();
        WeaponCard wc3 = new MachineGun();
        WeaponSlot w1 = new WeaponSlot(1, wc1, wc2, wc3);

        WeaponCard wc11 = new Furnace();
        w1.setCard1(wc11);

        WeaponCard wc12 = new RocketLauncher();
        w1.setCard2(wc12);

        WeaponCard wc13 = new THOR();
        w1.setCard3(wc13);

        assertEquals(wc11, w1.getCard1());
        assertEquals(wc12, w1.getCard2());
        assertEquals(wc13, w1.getCard3());
    }


    @Test
    void MachineGunCorrectConstructor() throws InvalidColourException {
        MachineGun mg = new MachineGun();

        String name = mg.getCardName();
        AmmoCube[] colours = mg.getReloadCost();
        AmmoCube colour1 = colours[0];
        AmmoCube colour2 = colours[1];

        assertEquals("Machine Gun", name);
        assertEquals(Colour.BLUE, colour1.getC());
        assertEquals(Colour.RED, colour2.getC());
    }

    @Test
    void PowerUpDeckTest() throws InvalidColourException {
        PowerUpDeck puDeck = new PowerUpDeck();

        assertEquals("Targeting Scope", puDeck.getTopOfDeck().getCardName());
        assertEquals(Colour.RED, puDeck.getTopOfDeck().getC());
        assertEquals("Targeting Scope", puDeck.getDeck().get(1).getCardName());
        assertEquals(Colour.RED, puDeck.getDeck().get(1).getC());
        assertEquals("Targeting Scope", puDeck.getDeck().get(2).getCardName());
        assertEquals(Colour.BLUE, puDeck.getDeck().get(2).getC());
        assertEquals("Targeting Scope", puDeck.getDeck().get(3).getCardName());
        assertEquals(Colour.BLUE, puDeck.getDeck().get(3).getC());
        assertEquals("Targeting Scope", puDeck.getDeck().get(4).getCardName());
        assertEquals(Colour.YELLOW, puDeck.getDeck().get(4).getC());
        assertEquals("Targeting Scope", puDeck.getDeck().get(5).getCardName());
        assertEquals(Colour.YELLOW, puDeck.getDeck().get(5).getC());
        assertEquals("Newton", puDeck.getDeck().get(6).getCardName());
        assertEquals(Colour.RED, puDeck.getDeck().get(6).getC());
        assertEquals("Newton", puDeck.getDeck().get(7).getCardName());
        assertEquals(Colour.RED, puDeck.getDeck().get(7).getC());
        assertEquals("Newton", puDeck.getDeck().get(8).getCardName());
        assertEquals(Colour.BLUE, puDeck.getDeck().get(8).getC());
        assertEquals("Newton", puDeck.getDeck().get(9).getCardName());
        assertEquals(Colour.BLUE, puDeck.getDeck().get(9).getC());
        assertEquals("Newton", puDeck.getDeck().get(10).getCardName());
        assertEquals(Colour.YELLOW, puDeck.getDeck().get(10).getC());
        assertEquals("Newton", puDeck.getDeck().get(11).getCardName());
        assertEquals(Colour.YELLOW, puDeck.getDeck().get(11).getC());
        assertEquals("Tagback Grenade", puDeck.getDeck().get(12).getCardName());
        assertEquals(Colour.RED, puDeck.getDeck().get(12).getC());
        assertEquals("Tagback Grenade", puDeck.getDeck().get(13).getCardName());
        assertEquals(Colour.RED, puDeck.getDeck().get(13).getC());
        assertEquals("Tagback Grenade", puDeck.getDeck().get(14).getCardName());
        assertEquals(Colour.BLUE, puDeck.getDeck().get(14).getC());
        assertEquals("Tagback Grenade", puDeck.getDeck().get(15).getCardName());
        assertEquals(Colour.BLUE, puDeck.getDeck().get(15).getC());
        assertEquals("Tagback Grenade", puDeck.getDeck().get(16).getCardName());
        assertEquals(Colour.YELLOW, puDeck.getDeck().get(16).getC());
        assertEquals("Tagback Grenade", puDeck.getDeck().get(17).getCardName());
        assertEquals(Colour.YELLOW, puDeck.getDeck().get(17).getC());
        assertEquals("Teleporter", puDeck.getDeck().get(18).getCardName());
        assertEquals(Colour.RED, puDeck.getDeck().get(18).getC());
        assertEquals("Teleporter", puDeck.getDeck().get(19).getCardName());
        assertEquals(Colour.RED, puDeck.getDeck().get(19).getC());
        assertEquals("Teleporter", puDeck.getDeck().get(20).getCardName());
        assertEquals(Colour.BLUE, puDeck.getDeck().get(20).getC());
        assertEquals("Teleporter", puDeck.getDeck().get(21).getCardName());
        assertEquals(Colour.BLUE, puDeck.getDeck().get(21).getC());
        assertEquals("Teleporter", puDeck.getDeck().get(22).getCardName());
        assertEquals(Colour.YELLOW, puDeck.getDeck().get(22).getC());
        assertEquals("Teleporter", puDeck.getDeck().get(23).getCardName());
        assertEquals(Colour.YELLOW, puDeck.getDeck().get(23).getC());

        Newton fakeCard = new Newton(Colour.RED);

        puDeck.addCard(fakeCard);
        assertEquals(fakeCard, puDeck.getDeck().get(24));
    }


    @Test
    void WeaponDeckTest() throws InvalidColourException {
        WeaponDeck wDeck = new WeaponDeck();

        assertEquals("Cyberblade", wDeck.getDeck().get(0).getCardName());
        assertEquals("Electroscythe", wDeck.getDeck().get(1).getCardName());
        assertEquals("Flamethrower", wDeck.getDeck().get(2).getCardName());
        assertEquals("Furnace", wDeck.getDeck().get(3).getCardName());
        assertEquals("Grenade Launcher", wDeck.getDeck().get(4).getCardName());
        assertEquals("Heatseeker", wDeck.getDeck().get(5).getCardName());
        assertEquals("Hellion", wDeck.getDeck().get(6).getCardName());
        assertEquals("Lock Rifle", wDeck.getDeck().get(7).getCardName());
        assertEquals("Machine Gun", wDeck.getDeck().get(8).getCardName());
        assertEquals("Plasma Gun", wDeck.getDeck().get(9).getCardName());
        assertEquals("Power Glove", wDeck.getDeck().get(10).getCardName());
        assertEquals("Railgun", wDeck.getDeck().get(11).getCardName());
        assertEquals("Rocket Launcher", wDeck.getDeck().get(12).getCardName());
        assertEquals("Shockwave", wDeck.getDeck().get(13).getCardName());
        assertEquals("Shotgun", wDeck.getDeck().get(14).getCardName());
        assertEquals("Sledgehammer", wDeck.getDeck().get(15).getCardName());
        assertEquals("T.H.O.R.", wDeck.getDeck().get(16).getCardName());
        assertEquals("Tractor Beam", wDeck.getDeck().get(17).getCardName());
        assertEquals("Vortex Cannon", wDeck.getDeck().get(18).getCardName());
        assertEquals("Whisper", wDeck.getDeck().get(19).getCardName());
        assertEquals("ZX-2", wDeck.getDeck().get(20).getCardName());

        WeaponCard fakeCard = new MachineGun();

        wDeck.addCard(fakeCard);
        assertEquals("Machine Gun", wDeck.getDeck().get(21).getCardName());
    }
}