package model;

import model.board.Cell;
import model.board.KillTrack;
import model.board.WeaponSlot;
import model.cards.WeaponCard;
import model.cards.weaponCards.*;
import org.junit.jupiter.api.Test;
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
    void DamageTrackTest() {
        DamageTrack dt = new DamageTrack();
        DamageToken dt1 = new DamageToken(Colour.RED);

        dt.addDamage(2, Colour.RED);

        assertEquals(dt1.getC(), dt.getDT(0).getC());
        assertEquals(dt1.getC(), dt.getDT(1).getC());

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
}