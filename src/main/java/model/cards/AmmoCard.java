package model.cards;

import model.AmmoCube;
import model.cards.PowerUpCard;

import java.util.ArrayList;

public abstract class AmmoCard {
    private ArrayList<AmmoCube>  aC;
    private ArrayList<PowerUpCard>  pC;

    public abstract void AmmoCard();            //for each AmmoCard implement the arrays

}
