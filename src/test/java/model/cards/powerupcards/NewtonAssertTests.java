package model.cards.powerupcards;

import model.Colour;
import model.Grid;
import model.cards.PowerUpCard;
import model.player.Player;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class NewtonAssertTests {
    @Test
    void NewtonCorrectConstructor() {
        PowerUpCard pUC = new Newton(Colour.BLUE);

        assertEquals("Newton", pUC.getCardName());
        assertEquals(Colour.BLUE, pUC.getC());
        assertEquals("You may play this card on your turn before or after any action.\n" +
                "Choose any other player's figure and move it 1 or 2 squares in one direction.\n" +
                "(You can't use this to move a figure after it respawns at the end of your turn. That would be too late.)\n", pUC.getDescription());
        assertEquals(Colour.BLUE, pUC.getValue().getC());
    }

    @Test
    void NewtonMethods() {
        Newton newton = new Newton(Colour.YELLOW);
        Grid grid = new Grid();
        grid.setType(1);
        Player p1 = new Player("Player", Colour.BLUE, true);
        p1.changeCell(grid.getBoard().getArena()[0][0]);
        List<Integer> directions = new LinkedList<>();
        directions.add(3);
        directions.add(3);
        directions.add(2);

        newton.applyEffect(grid, p1, directions);
        assertEquals(grid.getBoard().getArena()[2][1], p1.getCell());
    }
}