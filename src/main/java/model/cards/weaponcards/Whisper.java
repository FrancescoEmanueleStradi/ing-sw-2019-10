package model.cards.weaponcards;

import model.*;
import model.cards.WeaponCard;

public class Whisper extends WeaponCard {

    public Whisper () throws InvalidColourException {
        super();
        this.cardName = "Whisper";
        this.cardName = "model.cards.weaponcards.Whisper";
        this.reloadCost = new AmmoCube[]{new AmmoCube(Colour.BLUE), new AmmoCube(Colour.BLUE), new AmmoCube(Colour.YELLOW)};
        this.numOptionalEffect = 0;
        super.alternateFireMode = false;
        String description = "effect: Deal 3 damage and 1 mark to 1 target you can see.\n" +
                "Your target must be at least 2 moves away from you.\n" +
                "Notes: For example, in the 2-by-2 room, you cannot shoot\n" +
                "a target on an adjacent square, but you can shoot a target\n" +
                "on the diagonal. If you are beside a door, you can't shoot\n" +
                "a target on the other side of the door, but you can shoot\n" +
                "a target on a different square of that room.";
    }

    @Override
    public void applyEffect(Grid grid, Player p, Player p1) {

    }

    @Override
    public void applySpecialEffect(Grid grid, Player p1) {

    }
}
