package model.player;

import model.Colour;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FigureAssertTests {
    @Test
    void FigureRightColour() {
        Figure fig = new Figure(Colour.GREEN);
        Colour colour = fig.getC();
        assertEquals(Colour.GREEN, colour);
    }
}