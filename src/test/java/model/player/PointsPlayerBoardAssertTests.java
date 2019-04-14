package model.player;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PointsPlayerBoardAssertTests {
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
}