package model.board;

import model.Colour;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class KillTrackAssertTests {
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
    void KillTrackScoringTest() {
        KillTrack killtrack = new KillTrack();

        killtrack.getSkulls()[0] = 1;
        killtrack.getC()[0] = Colour.YELLOW;

        killtrack.getSkulls()[1] = 2;
        killtrack.getC()[1] = Colour.BLUE;

        killtrack.getSkulls()[2] = 2;
        killtrack.getC()[2] = Colour.GREEN;

        killtrack.getSkulls()[3] = 1;
        killtrack.getC()[3] = Colour.BLACK;

        killtrack.getSkulls()[4] = 1;
        killtrack.getC()[4] = Colour.BLUE;

        killtrack.getSkulls()[5] = 2;
        killtrack.getC()[5] = Colour.BLUE;

        killtrack.getSkulls()[6] = 1;
        killtrack.getC()[6] = Colour.YELLOW;

        killtrack.getSkulls()[7] = 2;
        killtrack.getC()[7] = Colour.GREEN;

        assertEquals(Colour.BLUE, killtrack.getColourPosition(0));
        assertEquals(Colour.GREEN, killtrack.getColourPosition(1));
        assertEquals(Colour.YELLOW, killtrack.getColourPosition(2));
        assertEquals(Colour.BLACK, killtrack.getColourPosition(3));
    }

    @Test
    void KilltrackScoringTieTest() {
        KillTrack killtrack = new KillTrack();

        killtrack.getSkulls()[0] = 2;
        killtrack.getC()[0] = Colour.YELLOW;

        killtrack.getSkulls()[1] = 2;
        killtrack.getC()[1] = Colour.BLUE;

        killtrack.getSkulls()[2] = 2;
        killtrack.getC()[2] = Colour.GREEN;

        killtrack.getSkulls()[3] = 1;
        killtrack.getC()[3] = Colour.BLACK;

        killtrack.getSkulls()[4] = 1;
        killtrack.getC()[4] = Colour.BLUE;

        killtrack.getSkulls()[5] = 1;
        killtrack.getC()[5] = Colour.BLUE;

        killtrack.getSkulls()[6] = 2;
        killtrack.getC()[6] = Colour.YELLOW;

        killtrack.getSkulls()[7] = 2;
        killtrack.getC()[7] = Colour.GREEN;

        assertEquals(Colour.YELLOW, killtrack.getColourPosition(0));
        assertEquals(Colour.BLUE, killtrack.getColourPosition(1));
        assertEquals(Colour.GREEN, killtrack.getColourPosition(2));
        assertEquals(Colour.BLACK, killtrack.getColourPosition(3));
    }
}