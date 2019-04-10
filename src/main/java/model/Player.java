package model;

import model.board.*;
import model.cards.PowerUpCard;
import model.cards.WeaponCard;

import java.util.LinkedList;
import java.util.List;

public class Player {

    private String nickName;
    private Colour c;
    private PlayerBoard pB;
    private Figure fig;
    private boolean firstPlayerCard;            //0 is not the first player, 1 is
    private int score;
    //private ArrayList<DamageToken> dT;
    private AmmoCube[] aC;                  //from 0 to 9; maximum 3 for each colour
    private LinkedList<WeaponCard> wC;
    private LinkedList<PowerUpCard> pC;
    private Cell cell;

    public Player(String name,Colour c, boolean f) throws InvalidColourException{

        this.nickName = name;
        this.c = c;
        this.pB = new PlayerBoard();
        this.fig = new Figure(c);
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

    public PlayerBoard getpB() {
        return pB;
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

        if (ac.getC() == Colour.RED) {
            for (int i = 0; i < 3; i++) {
                if (this.aC[i] == null) {
                    this.aC[i] = ac;
                    break;
                }

            }
        }

        if (ac.getC() == Colour.BLUE) {
            for (int i = 3; i < 6; i++) {
                if (this.aC[i] == null) {
                    this.aC[i] = ac;
                    break;
                }

            }
        }

        if (ac.getC() == Colour.YELLOW) {
            for (int i = 6; i < 9; i++) {
                if (this.aC[i] == null) {
                    this.aC[i] = ac;
                    break;
                }

            }

        }
    }

    public List<WeaponCard> getwC() {
        return wC;
    }

    public void addWeaponCard(WeaponCard w){
        this.wC.add(w);
    }

    public void removeWeaponCard(WeaponCard w){
        this.wC.remove(w);
    }

    public List<PowerUpCard> getpC() {
        return pC;
    }

    public void addPowerUpCard(PowerUpCard p){
        this.pC.add(p);
    }

    public void removePowerUpCard(PowerUpCard p){
        this.pC.remove(p);
    }

    public void setCell(Cell c){
        this.cell = new Cell(c.getStatus(), c.getC(), c.getPosWall(), c.getPosDoor(), c.getP());
    }

    public void changeCell(Cell c){
        this.cell = c;
    }

    public Cell getCell() {
        return cell;
    }
}




