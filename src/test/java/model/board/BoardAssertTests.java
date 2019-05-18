package model.board;

import model.Colour;
import model.Position;
import model.cards.AmmoCard;
import model.cards.WeaponCard;
import model.cards.ammocards.PRB;
import model.cards.ammocards.PYB;
import model.cards.weaponcards.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BoardAssertTests {
    @Test
    void BoardCreationTest()  {
        WeaponCard w1 = new MachineGun();
        WeaponCard w2 = new MachineGun();
        WeaponCard w3 = new MachineGun();
        WeaponCard w4 = new MachineGun();
        WeaponCard w5 = new MachineGun();
        WeaponCard w6 = new MachineGun();
        WeaponCard w7 = new MachineGun();
        WeaponCard w8 = new MachineGun();
        WeaponCard w9 = new MachineGun();

        WeaponSlot ws1 = new WeaponSlot(1, w1, w2, w3);
        WeaponSlot ws2 = new WeaponSlot(2, w4, w5, w6);
        WeaponSlot ws3 = new WeaponSlot(3, w7, w8, w9);

        Board board1 = new Board(1, ws1, ws2, ws3);
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

        Board board2 = new Board(2, ws1, ws2, ws3);
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

        Board board3 = new Board(3, ws1, ws2, ws3);
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

        Board board4 = new Board(4, ws1, ws2, ws3);
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
    void BoardKillTrackTest() throws RemoteException {
        WeaponCard w1 = new MachineGun();
        WeaponCard w2 = new MachineGun();
        WeaponCard w3 = new MachineGun();
        WeaponCard w4 = new MachineGun();
        WeaponCard w5 = new MachineGun();
        WeaponCard w6 = new MachineGun();
        WeaponCard w7 = new MachineGun();
        WeaponCard w8 = new MachineGun();
        WeaponCard w9 = new MachineGun();

        WeaponSlot ws1 = new WeaponSlot(1, w1, w2, w3);
        WeaponSlot ws2 = new WeaponSlot(2, w4, w5, w6);
        WeaponSlot ws3 = new WeaponSlot(3, w7, w8, w9);

        Board board = new Board(1, ws1, ws2, ws3);

        for(int i = 0; i < 8; i++)
            assertEquals(0, board.getK().getSkulls()[i]);

        board.substituteSkull(1);
        assertEquals(1, board.getK().getSkulls()[0]);
        for(int i = 1; i < 8; i++)
            assertEquals(0, board.getK().getSkulls()[i]);

        board.substituteSkull(2);
        assertEquals(1, board.getK().getSkulls()[0]);
        assertEquals(2, board.getK().getSkulls()[1]);
        for(int i = 2; i < 8; i++)
            assertEquals(0, board.getK().getSkulls()[i]);

        board.substituteSkull(1);
        assertEquals(1, board.getK().getSkulls()[0]);
        assertEquals(2, board.getK().getSkulls()[1]);
        assertEquals(1, board.getK().getSkulls()[2]);
        for(int i = 3; i < 8; i++)
            assertEquals(0, board.getK().getSkulls()[i]);

        board.substituteSkull(2);
        assertEquals(1, board.getK().getSkulls()[0]);
        assertEquals(2, board.getK().getSkulls()[1]);
        assertEquals(1, board.getK().getSkulls()[2]);
        assertEquals(2, board.getK().getSkulls()[3]);
        for(int i = 4; i < 8; i++)
            assertEquals(0, board.getK().getSkulls()[i]);

        board.substituteSkull(1);
        assertEquals(1, board.getK().getSkulls()[0]);
        assertEquals(2, board.getK().getSkulls()[1]);
        assertEquals(1, board.getK().getSkulls()[2]);
        assertEquals(2, board.getK().getSkulls()[3]);
        assertEquals(1, board.getK().getSkulls()[4]);
        for(int i = 5; i < 8; i++)
            assertEquals(0, board.getK().getSkulls()[i]);

        board.substituteSkull(2);
        assertEquals(1, board.getK().getSkulls()[0]);
        assertEquals(2, board.getK().getSkulls()[1]);
        assertEquals(1, board.getK().getSkulls()[2]);
        assertEquals(2, board.getK().getSkulls()[3]);
        assertEquals(1, board.getK().getSkulls()[4]);
        assertEquals(2, board.getK().getSkulls()[5]);
        for(int i = 6; i < 8; i++)
            assertEquals(0, board.getK().getSkulls()[i]);

        board.substituteSkull(1);
        assertEquals(1, board.getK().getSkulls()[0]);
        assertEquals(2, board.getK().getSkulls()[1]);
        assertEquals(1, board.getK().getSkulls()[2]);
        assertEquals(2, board.getK().getSkulls()[3]);
        assertEquals(1, board.getK().getSkulls()[4]);
        assertEquals(2, board.getK().getSkulls()[5]);
        assertEquals(1, board.getK().getSkulls()[6]);
        for(int i = 7; i < 8; i++)
            assertEquals(0, board.getK().getSkulls()[i]);

        board.substituteSkull(2);
        assertEquals(1, board.getK().getSkulls()[0]);
        assertEquals(2, board.getK().getSkulls()[1]);
        assertEquals(1, board.getK().getSkulls()[2]);
        assertEquals(2, board.getK().getSkulls()[3]);
        assertEquals(1, board.getK().getSkulls()[4]);
        assertEquals(2, board.getK().getSkulls()[5]);
        assertEquals(1, board.getK().getSkulls()[6]);
        assertEquals(2, board.getK().getSkulls()[7]);

        assertEquals(-1, board.substituteSkull(1));
        assertEquals(1, board.getK().getSkulls()[0]);
        assertEquals(2, board.getK().getSkulls()[1]);
        assertEquals(1, board.getK().getSkulls()[2]);
        assertEquals(2, board.getK().getSkulls()[3]);
        assertEquals(1, board.getK().getSkulls()[4]);
        assertEquals(2, board.getK().getSkulls()[5]);
        assertEquals(1, board.getK().getSkulls()[6]);
        assertEquals(2, board.getK().getSkulls()[7]);
    }

    @Test
    void BoardWeaponSlotTest() throws RemoteException {
        WeaponCard w1 = new Cyberblade();
        WeaponCard w2 = new Electroscythe();
        WeaponCard w3 = new Flamethrower();
        WeaponCard w4 = new Furnace();
        WeaponCard w5 = new GrenadeLauncher();
        WeaponCard w6 = new Heatseeker();
        WeaponCard w7 = new Hellion();
        WeaponCard w8 = new LockRifle();
        WeaponCard w9 = new MachineGun();

        WeaponSlot ws1 = new WeaponSlot(1, w1, w2, w3);
        WeaponSlot ws2 = new WeaponSlot(2, w4, w5, w6);
        WeaponSlot ws3 = new WeaponSlot(3, w7, w8, w9);

        Board board = new Board(2, ws1, ws2, ws3);

        assertEquals(w1, board.getW1().getCard1());
        assertEquals(w2, board.getW1().getCard2());
        assertEquals(w3, board.getW1().getCard3());
        assertEquals(w4, board.getW2().getCard1());
        assertEquals(w5, board.getW2().getCard2());
        assertEquals(w6, board.getW2().getCard3());
        assertEquals(w7, board.getW3().getCard1());
        assertEquals(w8, board.getW3().getCard2());
        assertEquals(w9, board.getW3().getCard3());

        WeaponCard neww1 = new PlasmaGun();
        WeaponCard neww2 = new PowerGlove();
        WeaponCard neww3 = new Railgun();
        WeaponCard neww4 = new RocketLauncher();
        WeaponCard neww5 = new Shockwave();
        WeaponCard neww6 = new Shotgun();
        WeaponCard neww7 = new Sledgehammer();
        WeaponCard neww8 = new THOR();
        WeaponCard neww9 = new TractorBeam();

        board.getW1().setCard1(null);
        board.changeWeaponCard(board.getW1(), neww1);
        board.getW1().setCard2(null);
        board.changeWeaponCard(board.getW1(), neww2);
        board.getW1().setCard3(null);
        board.changeWeaponCard(board.getW1(), neww3);
        board.getW2().setCard1(null);
        board.changeWeaponCard(board.getW2(), neww4);
        board.getW2().setCard2(null);
        board.changeWeaponCard(board.getW2(), neww5);
        board.getW2().setCard3(null);
        board.changeWeaponCard(board.getW2(), neww6);
        board.getW3().setCard1(null);
        board.changeWeaponCard(board.getW3(), neww7);
        board.getW3().setCard2(null);
        board.changeWeaponCard(board.getW3(), neww8);
        board.getW3().setCard3(null);
        board.changeWeaponCard(board.getW3(), neww9);

        //A cell in a WeaponSlots empty = a player have picked that card. During the game, every time a player
        //picks a card from a slot, we set that place in the slot to null and we immediately replace
        //that null with a new WeaponCard from the deck (if available).

        assertEquals(neww1, board.getW1().getCard1());
        assertEquals(neww2, board.getW1().getCard2());
        assertEquals(neww3, board.getW1().getCard3());
        assertEquals(neww4, board.getW2().getCard1());
        assertEquals(neww5, board.getW2().getCard2());
        assertEquals(neww6, board.getW2().getCard3());
        assertEquals(neww7, board.getW3().getCard1());
        assertEquals(neww8, board.getW3().getCard2());
        assertEquals(neww9, board.getW3().getCard3());
    }

    @Test
    void BoardChangeAmmoCardTest()  {
        WeaponCard w1 = new Cyberblade();
        WeaponCard w2 = new Electroscythe();
        WeaponCard w3 = new Flamethrower();
        WeaponCard w4 = new Furnace();
        WeaponCard w5 = new GrenadeLauncher();
        WeaponCard w6 = new Heatseeker();
        WeaponCard w7 = new Hellion();
        WeaponCard w8 = new LockRifle();
        WeaponCard w9 = new MachineGun();

        WeaponSlot ws1 = new WeaponSlot(1, w1, w2, w3);
        WeaponSlot ws2 = new WeaponSlot(2, w4, w5, w6);
        WeaponSlot ws3 = new WeaponSlot(3, w7, w8, w9);

        Board board = new Board(3, ws1, ws2, ws3);
        Position pos = new Position(0, 1);
        AmmoCard ammocard = new PRB();
        AmmoCard newammocard = new PYB();

        board.changeAmmoCard(pos, ammocard);
        assertEquals(ammocard, board.getArena()[0][1].getA());

        board.changeAmmoCard(pos, newammocard);
        assertEquals(newammocard, board.getArena()[0][1].getA());
    }
}