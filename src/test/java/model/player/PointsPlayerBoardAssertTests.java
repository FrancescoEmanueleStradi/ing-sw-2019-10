package model.player;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PointsPlayerBoardAssertTests {
    @Test
    void PointsPlayerBoardTest() throws RemoteException {
        PointsPlayerBoard ppb = new PointsPlayerBoard();

        assertEquals(1, ppb.getInt(0));
        assertEquals(1, ppb.getPoints().get(0));
        assertEquals(8, ppb.getInt(1));
        assertEquals(8, ppb.getPoints().get(1));
        assertEquals(6, ppb.getInt(2));
        assertEquals(6, ppb.getPoints().get(2));
        assertEquals(4, ppb.getInt(3));
        assertEquals(4, ppb.getPoints().get(3));
        assertEquals(2, ppb.getInt(4));
        assertEquals(2, ppb.getPoints().get(4));
        assertEquals(1, ppb.getInt(5));
        assertEquals(1, ppb.getPoints().get(5));
        assertEquals(1, ppb.getInt(6));
        assertEquals(1, ppb.getPoints().get(6));


        ppb.remove();
        assertEquals(6, ppb.getInt(1));
        assertEquals(6, ppb.getPoints().get(1));

        ppb.remove();
        assertEquals(4, ppb.getInt(1));
        assertEquals(4, ppb.getPoints().get(1));

        ppb.remove();
        assertEquals(2, ppb.getInt(1));
        assertEquals(2, ppb.getPoints().get(1));

        ppb.remove();
        assertEquals(1, ppb.getInt(1));
        assertEquals(1, ppb.getPoints().get(1));

        ppb.remove();
        assertEquals(1, ppb.getInt(1));
        assertEquals(1, ppb.getPoints().get(1));

        assertEquals(1, ppb.getInt(0));
        assertEquals(1, ppb.getPoints().get(1));
    }
}