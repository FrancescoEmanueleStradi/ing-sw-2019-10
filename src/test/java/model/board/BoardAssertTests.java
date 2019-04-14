package model.board;

import model.Colour;
import model.InvalidColourException;
import model.cards.WeaponCard;
import model.cards.weaponcards.MachineGun;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BoardAssertTests {
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
}