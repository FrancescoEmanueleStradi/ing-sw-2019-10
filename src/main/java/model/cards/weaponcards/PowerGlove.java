package model.cards.weaponcards;

import model.*;
import model.cards.WeaponCard;

public class PowerGlove extends WeaponCard {

    private String alternateFireMode = "Rocket Fist Mode";

    public PowerGlove() throws InvalidColourException {
        super();
        this.cardName = "Power Glove";
        this.reloadCost = new AmmoCube[]{new AmmoCube(Colour.YELLOW), new AmmoCube(Colour.BLUE)};
        this.numOptionalEffect = 0;
        super.alternateFireMode = true;
        String description = "basic mode: Choose 1 target on any square\n" +
                "exactly 1 move away. Move onto that square\n" +
                "and give the target 1 damage and 2 marks.\n" +
                "in rocket fist mode: Choose a square\n" +
                "exactly 1 move away. Move onto that square.\n" +
                "You may deal 2 damage to 1 target there.\n" +
                "If you want, you may move 1 more square in\n" +
                "that same direction (but only if it is a legal\n" +
                "move). You may deal 2 damage to 1 target\n" +
                "there, as well.\n" +
                "Notes: In rocket fist mode, you're flying\n" +
                "2 squares in a straight line, punching\n" +
                "1 person per square.";
    }

    public String getAlternateFireMode() {
        return alternateFireMode;
    }

    @Override
    public void applyEffect(Grid grid, Player p, Player p1) {

    }

    @Override
    public void applySpecialEffect(Grid grid, Player p1) {

    }
}
