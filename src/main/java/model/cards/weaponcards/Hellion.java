package model.cards.weaponcards;

import model.AmmoCube;
import model.Colour;
import model.InvalidColourException;
import model.cards.WeaponCard;

public class Hellion extends WeaponCard {

    public Hellion() throws InvalidColourException {
        super();
        this.cardName = "model.cards.weaponcards.Hellion";
        this.reloadCost = new AmmoCube[]{new AmmoCube(Colour.RED), new AmmoCube(Colour.YELLOW)};
        this.numSpecialEffect = 0;                                 //has alternate fire mode
        String description = "basic mode: Deal 1 damage to 1 target you can see at least\n" +
                "1 move away. Then give 1 mark to that target and everyone\n" +
                "else on that square.\n" +
                "in nano-tracer mode: Deal 1 damage to 1 target you can\n" +
                "see at least 1 move away. Then give 2 marks to that target\n" +
                "and everyone else on that square.";
    }

    @Override
    public void applyEffect() {

    }

    @Override
    public void applySpecialEffect() {

    }
}
