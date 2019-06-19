package model.player;

import model.*;
import model.board.*;
import model.cards.PowerUpCard;
import model.cards.WeaponCard;

import java.util.*;

public class Player {

    private String nickName;
    private Colour c;
    private PlayerBoard playerBoard;
    private boolean firstPlayerCard;            //0 is not the first player, 1 is
    private int score;
    private AmmoCube[] ammoCubes;                  //from 0 to 9; maximum 3 for each colour
    private LinkedList<WeaponCard> weaponCards;
    private LinkedList<PowerUpCard> powerUpCards;
    private Cell cell;
    private int turnFinalFrenzy;        //0 if your final frenzy turn is before the first player, 1 if you are the first player, 2 if you play after

    public Player(String name, Colour c, boolean f)  {

        this.nickName = name;
        this.c = c;
        this.playerBoard = new PlayerBoard();
        this.firstPlayerCard = f;
        this.score = 0;
        this.ammoCubes = new AmmoCube[]{new AmmoCube(Colour.RED), null, null, new AmmoCube(Colour.BLUE), null, null, new AmmoCube(Colour.YELLOW), null, null};
        weaponCards = new LinkedList<>();
        powerUpCards = new LinkedList<>();
    }

    public String getNickName() {
        return nickName;
    }

    public Colour getC() {
        return c;
    }

    public PlayerBoard getPlayerBoard() {
        return playerBoard;
    }

    public boolean isFirstPlayerCard() {
        return firstPlayerCard;
    }

    public int getScore() {
        return score;
    }

    public void addScore(int score) {
        this.score = this.score + score;
    }

    public AmmoCube[] getAmmoCubes() {
        return ammoCubes;
    }

    public int getTurnFinalFrenzy() {
        return turnFinalFrenzy;
    }

    public void setTurnFinalFrenzy(int turnFinalFrenzy) {
        this.turnFinalFrenzy = turnFinalFrenzy;
    }

    public boolean checkAmmoCube(AmmoCube[] a) {
        List<AmmoCube> l2 = new ArrayList<>(Arrays.asList(a));          //this way the original array is not modified

        if(l2.isEmpty())
            return true;

        List<Colour> lInput = new LinkedList<>();
        for(AmmoCube aCPlayer : this.ammoCubes) {
            if(aCPlayer != null)
                lInput.add(aCPlayer.getC());
        }

        List<Colour> lCost = new LinkedList<>();
        for(AmmoCube aCost : l2)
            lCost.add(aCost.getC());

        for(Colour colour : lCost) {
            if(Collections.frequency(lInput, colour) < Collections.frequency(lCost, colour))
                return false;
        }

        return lInput.containsAll(lCost);
    }

    public void addNewAC(AmmoCube ac) {
        if(ac.getC() == Colour.RED)
            this.addNewACRED(ac);
        else if(ac.getC() == Colour.BLUE)
            this.addNewACBLUE(ac);
        else if(ac.getC() == Colour.YELLOW)
            this.addNewACYELLOW(ac);
    }

    private void addNewACRED(AmmoCube ac) {
        for(int i = 0; i < 3; i++) {
            if(this.ammoCubes[i] == null) {
                this.ammoCubes[i] = ac;
                break;
            }
        }
    }

    private void addNewACBLUE(AmmoCube ac) {
        for(int i = 3; i < 6; i++) {
            if(this.ammoCubes[i] == null) {
                this.ammoCubes[i] = ac;
                break;
            }
        }
    }

    private void addNewACYELLOW(AmmoCube ac) {
        for(int i = 6; i < 9; i++) {
            if(this.ammoCubes[i] == null) {
                this.ammoCubes[i] = ac;
                break;
            }
        }
    }

    private void removeAC(AmmoCube a) {
        int i = 0;
        for(AmmoCube a1 : this.getAmmoCubes()) {
            if((a1 != null) && a1.getC().equals(a.getC())) {
                this.ammoCubes[i] = null;
                return;
            }
            i++;
        }
    }

    public void removeArrayAC(AmmoCube[] a) {
        for(AmmoCube a1 : a)
            removeAC(a1);
    }

    public void payCard(List<AmmoCube> lA, List<PowerUpCard> lP) {
        if(!lA.isEmpty()) {
            for(AmmoCube a : lA)
                this.removeAC(a);
        }
        if(!lP.isEmpty()) {
            for(PowerUpCard p : lP)
                this.powerUpCards.remove(p);
        }
    }

    public List<WeaponCard> getWeaponCards() {
        return weaponCards;
    }

    public void addWeaponCard(WeaponCard w) {
        this.weaponCards.add(w);
    }

    public void removeWeaponCard(WeaponCard w) {
        this.weaponCards.remove(w);
    }

    public WeaponCard getWeaponCardObject(String s) {
        for(WeaponCard w: this.weaponCards) {
            if(w.getCardName().equals(s))
                return w;
        }
        return null;
    }

    public List<PowerUpCard> getPowerUpCards() {
        return powerUpCards;
    }

    public void addPowerUpCard(PowerUpCard p) {
        this.powerUpCards.add(p);
    }

    public void removePowerUpCard(PowerUpCard p) {
        this.powerUpCards.remove(p);
    }

    public PowerUpCard getPowerUpCardObject(String s, Colour colour) {
        for(PowerUpCard p: this.powerUpCards) {
            if(p.getCardName().equals(s) && p.getC().equals(colour))
                return p;
        }
        return null;
    }

    public void changeCell(Cell c) {
        if(c != null && c.getStatus() != -1)
            this.cell = c;
        else if(c == null)
            this.cell = null;
    }

    public Cell getCell() {
        return cell;
    }

    public boolean isAdrenaline1() {
        return (this.playerBoard.getDamage().getDamageTokens()[2]!=null);
    }

    public boolean isAdrenaline2() {
        return (this.playerBoard.getDamage().getDamageTokens()[5]!=null);
    }

    public boolean isDead() {
        return (this.playerBoard.getDamage().getDamageTokens()[10]!=null);
    }

    public boolean isOverkilled() {
        return (this.playerBoard.getDamage().getDamageTokens()[11]!=null);
    }
}