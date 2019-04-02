package Model;

public class TargetingScope extends PowerUpCard {

    public TargetingScope(Colour c) throws InvalidColourException {
        super();
        this.cardName = "Targeting Scope";
        this.value = new AmmoCube(c);
        String description = "You may play this card when you are dealing\n" +
                "damage to one or more targets. Pay 1 ammo\n" +
                "cube of any color. Choose 1 of those targets\n" +
                "and give it an extra point of damage. Note: You\n" +
                "cannot use this to do 1 damage to a target that\n" +
                "is receiving only marks.";
    }

    @Override
    public void applyEffect() {

    }
}
