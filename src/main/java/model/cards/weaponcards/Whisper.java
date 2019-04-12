package model.cards.weaponcards;

import model.*;
import model.cards.WeaponCard;

public class Whisper extends WeaponCard {

    public Whisper () throws InvalidColourException {
        super();
        this.cardName = "Whisper";
        this.reloadCost = new AmmoCube[]{new AmmoCube(Colour.BLUE), new AmmoCube(Colour.BLUE), new AmmoCube(Colour.YELLOW)};
        this.numOptionalEffect = 0;
        this.alternateFireMode = false;
        this.description = "effect: Deal 3 damage and 1 mark to 1 target you can see. Your target must be at least 2 moves away from you.\n" +
                             "Notes: For example, in the 2-by-2 room, you cannot shoot a target on an adjacent square, but you can shoot a target on the diagonal.\n" +
                             "If you are beside a door, you can't shoot a target on the other side of the door, but you can shoot a target on a different square of that room.\n";
    }

    //before: let the player p choose 1 target p1 he can see: it has to be at least 2 moves away from player p, so check this.

    public void applyEffect(Grid grid, Player p, Player p1) {   //Player p deals 3 damages and adds 1 mark to player p1
        grid.damage(p, p1, 3);
        grid.addMark(p, p1);
    }
}
