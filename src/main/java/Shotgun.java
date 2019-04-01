public class Shotgun extends WeaponCard{

    public Shotgun() throws InvalidColourException {
        super();
        this.cardName = "Shotgun";
        this.reloadCost = new AmmoCube[]{new AmmoCube(Colour.YELLOW), new AmmoCube(Colour.YELLOW)};
        this.numSpecialEffect = 0;                                    //has alternate fire  mode
        String description = "basic mode: Deal 3 damage to 1 target on\n" +
                "your square. If you want, you may then move\n" +
                "the target 1 square.\n" +
                "in long barrel mode: Deal 2 damage to\n" +
                "1 target on any square exactly one move\n" +
                "away.";
    }

    @Override
    public void applyEffect() {

    }

    @Override
    public void applySpecialEffect() {

    }
}
