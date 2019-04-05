package Model;

import java.util.ArrayList;
import java.util.LinkedList;

public class Player {

    private String nickName;
    private Colour c;
    private boolean firstPlayerCard;            //0 is not the first player, 1 is
    private int score;
    //private ArrayList<DamageToken> dT;
    private AmmoCube[] aC;                  //from 0 to 9; maximum 3 for each colour
    private LinkedList<WeaponCard> wC;
    private LinkedList<PowerUpCard> pC;

    public Player(String name,Colour c, boolean f)throws InvalidColourException{

        this.nickName = name;
        this.c = c;
        this.firstPlayerCard = f;
        this.score = 0;
        //this.dT = new ArrayList<>();
        this.aC = new AmmoCube[]{new AmmoCube(Colour.RED), new AmmoCube(Colour.RED), new AmmoCube(Colour.RED), new AmmoCube(Colour.BLUE), new AmmoCube(Colour.BLUE), new AmmoCube(Colour.BLUE), new AmmoCube(Colour.YELLOW), new AmmoCube(Colour.YELLOW), new AmmoCube(Colour.YELLOW)};
        wC = new LinkedList<>();
        pC = new LinkedList<>();

    }


    public String getNickName() {
        return nickName;
    }

    public Colour getC() {
        return c;
    }

    public boolean isFirstPlayerCard() {
        return firstPlayerCard;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public AmmoCube[] getaC() {
        return aC;
    }

    public void addAC(AmmoCube ac) {

        //TODO
    }

    public LinkedList<WeaponCard> getwC() {
        return wC;
    }

    public void addWeaponCard(WeaponCard w){
        this.wC.add(w);
    }

    public void removeWeaponCard(WeaponCard w){
        this.wC.remove(w);
    }

    public LinkedList<PowerUpCard> getpC() {
        return pC;
    }

    public void addPowerUpCard(PowerUpCard p){
        this.pC.add(p);
    }

    public void removePowerUpCard(PowerUpCard p){
        this.pC.remove(p);
    }

    
}




