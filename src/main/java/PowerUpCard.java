public abstract class PowerUpCard implements Card{

    protected String cardName;
    protected AmmoCube value;

    public String getCardName() {
        return cardName;
    }

    public AmmoCube getValue() {
        return value;
    }
}
