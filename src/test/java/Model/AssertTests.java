package Model;

import Model.Board.Cell;
import Model.Board.KillTrack;
import Model.Board.WeaponSlot;
import Model.WeaponCards.MachineGun;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

public class AssertTests {

    @Test
    public void SkullsArrayCorrectInitialization() {
        KillTrack k = new KillTrack();
        int[] skulls = k.getSkulls();

        assertEquals(0, skulls[0]);
        assertEquals(0, skulls[1]);
        assertEquals(0, skulls[2]);
        assertEquals(0, skulls[3]);
        assertEquals(0, skulls[4]);
        assertEquals(0, skulls[5]);
        assertEquals(0, skulls[6]);
        assertEquals(0, skulls[7]);
    }



    @Test
    public void CellDoesNotExists() {
        Cell cell = new Cell(-1);
        assertEquals(-1, cell.getStatus());
    }


    @Test
    public void AmmoCubeRightColour() throws InvalidColourException {
        AmmoCube cube = new AmmoCube(Colour.BLUE);
        Colour colour = cube.getC();

        assertEquals(Colour.BLUE, colour);
    }

    @Test
    public void WeaponSlotCorrectConstructor() throws InvalidColourException {
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
    public void MachineGunCorrectConstructor() throws InvalidColourException {
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
