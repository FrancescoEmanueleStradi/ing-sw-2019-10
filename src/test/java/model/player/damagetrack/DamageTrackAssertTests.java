package model.player.damagetrack;

import model.Colour;
import model.player.DamageToken;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class DamageTrackAssertTests {
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
}