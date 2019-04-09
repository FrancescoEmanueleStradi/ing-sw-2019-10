package model.cards.weaponcards;

import model.*;
import model.cards.WeaponCard;

public class Cyberblade extends WeaponCard {

    private String optionalEffect1 = "Shadowstep";
    private String optionalEffect2 = "Slice and Dice";

    public Cyberblade() throws InvalidColourException {
        super();
        this.cardName = "Cyberblade";
        this.reloadCost = new AmmoCube[]{new AmmoCube(Colour.YELLOW), new AmmoCube(Colour.RED)};
        this.numOptionalEffect = 2;
        super.alternateFireMode = false;
        String description = "basic effect: Deal 2 damage to 1 target on your square.\n" +
                "with shadowstep: Move 1 square before or after the basic effect.\n" +
                "with slice and dice: Deal 2 damage to a different target on your square.\n" +
                "The shadowstep may be used before or after this effect.\n" +
                "Notes: Combining all effects allows you to move onto a square and\n" +
                "whack 2 people; or whack somebody, move, and whack somebody else;\n" +
                "or whack 2 people and then move.";
    }

    public String getOptionalEffect2() {
        return optionalEffect2;
    }

    public String getOptionalEffect1() {
        return optionalEffect1;
    }

    @Override
    public void applyEffect(Grid grid, Player p, Player p1) {

    }

    @Override
    public void applySpecialEffect(Grid grid, Player p1) {

    }

}
