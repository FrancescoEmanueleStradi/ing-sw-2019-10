package model.player;

import model.Colour;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DamageTokenAssertTests {
    @Test
    void DamageTokenTest() {
        DamageToken dt = new DamageToken(Colour.BLUE);
        assertEquals(Colour.BLUE, dt.getC());
    }
}