package model.board;

import model.Colour;
import model.Position;
import model.cards.AmmoCard;
import model.cards.ammocards.BRR;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class CellAssertTests {
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
    void CellAmmoCardTest() {
        AmmoCard ammocard = new BRR();
        int[] walls = new int[]{0, 1};
        int[] doors = new int[]{2, 3};
        Position pos = new Position(2, 2);
        Cell cell = new Cell(0, Colour.BLUE, walls, doors, pos);

        assertNull(cell.getA());
        cell.setA(ammocard);
        assertEquals(ammocard, cell.getA());

        Position posRespawn = new Position(1, 0);
        Cell respawn = new Cell(1, Colour.RED, walls, doors, posRespawn);

        assertNull(respawn.getA());
        respawn.setA(ammocard);
        assertNull(respawn.getA());
    }
}