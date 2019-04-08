package model.cards;

import model.AmmoCube;
import model.cards.Card;

import java.util.ArrayList;

public abstract class WeaponCard implements Card {

    protected String cardName;
    protected AmmoCube[] reloadCost;      //first cube is the default one
    protected String description;
    protected int numSpecialEffect;      //number of special effects
    protected ArrayList<String> effect = new ArrayList<>();


    public String getCardName() {
        return this.cardName;
    }

    public AmmoCube[] getReloadCost() {
        return this.reloadCost;
    }

    public abstract void applySpecialEffect();

}
