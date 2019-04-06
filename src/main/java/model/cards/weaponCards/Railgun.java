package model.cards.weaponCards;

import model.AmmoCube;
import model.Colour;
import model.InvalidColourException;
import model.cards.WeaponCard;

public class Railgun extends WeaponCard {

    public Railgun() throws InvalidColourException {
        super();
        this.cardName = "Railgun";
        this.reloadCost = new AmmoCube[]{new AmmoCube(Colour.YELLOW), new AmmoCube(Colour.YELLOW), new AmmoCube(Colour.BLUE)};
        this.numSpecialEffect = 0;                                 //has alternate fire mode
        String description = "basic mode: Choose a cardinal direction and 1 target in that direction.\n" +
                "Deal 3 damage to it.\n" +
                "in piercing mode: Choose a cardinal direction and 1 or 2 targets in that\n" +
                "direction. Deal 2 damage to each.\n" +
                "Notes: Basically, you're shooting in a straight line and ignoring walls.\n" +
                "You don't have to pick a target on the other side of a wall – it could even\n" +
                "be someone on your own square – but shooting through walls sure is\n" +
                "fun. There are only 4 cardinal directions. You imagine facing one wall or\n" +
                "door, square-on, and firing in that direction. Anyone on a square in that\n" +
                "direction (including yours) is a valid target. In piercing mode,\n" +
                "the 2 targets can be on the same square or on different squares.";
    }

    @Override
    public void applyEffect() {

    }

    @Override
    public void applySpecialEffect() {

    }
}
