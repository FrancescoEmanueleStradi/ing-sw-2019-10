package model.player;

import model.Colour;
import model.player.damagetrack.DamageTrack;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerBoardAssertTests {
    @Test
    void PlayerBoardTest() {
        PlayerBoard pb = new PlayerBoard();

        assertNotNull(pb.getDamage());
        DamageTrack newDT = new DamageTrack();
        pb.setDamage(newDT);
        assertEquals(newDT, pb.getDamage());

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
}