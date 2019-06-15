package model.cards.weaponcards;

import model.Colour;
import model.cards.WeaponCard;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GrenadeLauncherAssertTests {
    @Test
    void GrenadeLauncherCorrectConstructor() {
        WeaponCard gl = new GrenadeLauncher();

        assertEquals("Grenade Launcher", gl.getCardName());
        assertEquals(Colour.RED, gl.getReloadCost()[0].getC());
        assertEquals("basic effect: Deal 1 damage to 1 target you can see. Then you may move the target 1 square.\n" +
                        "with extra grenade: Deal 1 damage to every player on a square you can see. You can use this before or after the basic effect's move.\n" +
                        "Notes: For example, you can shoot a target, move it onto a square with other targets, then damage everyone including the first target.\n" +
                        "Or you can deal 2 to a main target, 1 to everyone else on that square, then move the main target.\n" +
                        "Or you can deal 1 to an isolated target and 1 to everyone on a different square.\n" +
                        "If you target your own square, you will not be moved or damaged.\n",
                gl.getDescription());
    }
}