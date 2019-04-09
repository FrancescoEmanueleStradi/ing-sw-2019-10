package model.cards.weaponcards;

import model.*;
import model.cards.WeaponCard;

public class Shotgun extends WeaponCard {

    private String alternateFireMode = "Long Barrel Mode";

    public Shotgun() throws InvalidColourException {
        super();
        this.cardName = "Shotgun";
        this.reloadCost = new AmmoCube[]{new AmmoCube(Colour.YELLOW), new AmmoCube(Colour.YELLOW)};
        this.numOptionalEffect = 0;
        super.alternateFireMode = true;
        String description = "basic mode: Deal 3 damage to 1 target on\n" +
                "your square. If you want, you may then move\n" +
                "the target 1 square.\n" +
                "in long barrel mode: Deal 2 damage to\n" +
                "1 target on any square exactly one move\n" +
                "away.";
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
