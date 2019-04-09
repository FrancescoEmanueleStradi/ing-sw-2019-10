package model.cards.weaponcards;

import model.AmmoCube;
import model.Colour;
import model.InvalidColourException;
import model.cards.WeaponCard;

public class RocketLauncher extends WeaponCard {

    public RocketLauncher() throws InvalidColourException {
        super();
        this.cardName = "Rocket Launcher";
        this.reloadCost = new AmmoCube[]{new AmmoCube(Colour.RED), new AmmoCube(Colour.RED)};
        this.numSpecialEffect = 2;
        String description = "basic effect: Deal 2 damage to 1 target you can see that is not on your\n" +
                "square. Then you may move the target 1 square.\n" +
                "with rocket jump: Move 1 or 2 squares. This effect can be used either\n" +
                "before or after the basic effect.\n" +
                "with fragmenting warhead: During the basic effect, deal 1 damage to\n" +
                "every player on your target's original square – including the target,\n" +
                "even if you move it.\n" +
                "Notes: If you use the rocket jump before the basic effect, you consider\n" +
                "only your new square when determining if a target is legal. You can\n" +
                "even move off a square so you can shoot someone on it. If you use the\n" +
                "fragmenting warhead, you deal damage to everyone on the target's\n" +
                "square before you move the target – your target will take 3 damage total.";
    }

    @Override
    public void applyEffect() {

    }

    @Override
    public void applySpecialEffect() {

    }
}
