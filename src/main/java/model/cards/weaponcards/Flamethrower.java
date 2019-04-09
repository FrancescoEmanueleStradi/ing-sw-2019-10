package model.cards.weaponcards;

import model.*;
import model.cards.WeaponCard;

public class Flamethrower extends WeaponCard {

    private String alternateFireMode = "Barbecue Mode";

    public Flamethrower() throws InvalidColourException {
        super();
        this.cardName = "Flamethrower";
        this.reloadCost = new AmmoCube[]{new AmmoCube(Colour.RED)};
        this.numOptionalEffect = 0;
        super.alternateFireMode = true;
        String description = "basic mode: Choose a square 1 move away and possibly a second square\n" +
                "1 more move away in the same direction. On each square, you may\n" +
                "choose 1 target and give it 1 damage.\n" +
                "in barbecue mode: Choose 2 squares as above. Deal 2 damage to\n" +
                "everyone on the first square and 1 damage to everyone on the second\n" +
                "square.\n" +
                "Notes: This weapon cannot damage anyone in your square. However,\n" +
                "it can sometimes damage a target you can't see – the flame won't go\n" +
                "through walls, but it will go through doors. Think of it as a straight-line\n" +
                "blast of flame that can travel 2 squares in a cardinal direction.";
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
