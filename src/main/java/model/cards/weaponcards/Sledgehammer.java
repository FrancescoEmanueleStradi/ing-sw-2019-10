package model.cards.weaponcards;

import model.*;
import model.cards.WeaponCard;

public class Sledgehammer extends WeaponCard {

    public Sledgehammer() throws InvalidColourException {
        super();
        this.cardName = "Sledgehammer";
        this.reloadCost = new AmmoCube[]{new AmmoCube(Colour.YELLOW)};
        this.numSpecialEffect = 0;                                    //has alternate fire mode
        String description = "basic mode: Deal 2 damage to 1 target on\n" +
                "your square.\n" +
                "in pulverize mode: Deal 3 damage to 1 target\n" +
                "on your square, then move that target 0, 1,\n" +
                "or 2 squares in one direction.\n" +
                "Notes: Remember that moves go through\n" +
                "doors, but not walls.";
    }

    @Override
    public void applyEffect(Grid grid, Player p, Player p1) {

    }

    @Override
    public void applySpecialEffect(Grid grid, Player p1) {

    }
}
