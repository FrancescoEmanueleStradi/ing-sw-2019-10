public abstract class WeaponCard implements Card {

    private String cardName;
    private AmmoCube[] reloadCost;      //first cube is the default one


    public String getCardName() {
        return cardName;
    }

    public AmmoCube[] getReloadCost() {
        return reloadCost;
    }

    public abstract void applySpecialEffect();



}
