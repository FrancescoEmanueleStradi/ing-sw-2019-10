package model.player;

import model.*;
import model.board.*;
import model.cards.PowerUpCard;
import model.cards.WeaponCard;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Player {

    private String nickName;
    private Colour c;
    private PlayerBoard pB;
    private Figure fig;
    private boolean firstPlayerCard;            //0 is not the first player, 1 is
    private int score;
    private AmmoCube[] aC;                  //from 0 to 9; maximum 3 for each colour
    private LinkedList<WeaponCard> wC;
    private LinkedList<PowerUpCard> pC;
    private Cell cell;
    private boolean adrenaline1;
    private boolean adrenaline2;

    public Player(String name,Colour c, boolean f) throws InvalidColourException {

        this.nickName = name;
        this.c = c;
        this.pB = new PlayerBoard();
        this.fig = new Figure(c);
        this.firstPlayerCard = f;
        this.score = 0;
        this.aC = new AmmoCube[]{new AmmoCube(Colour.RED), null, null, new AmmoCube(Colour.BLUE), null, null, new AmmoCube(Colour.YELLOW), null, null};
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

    public boolean checkAmmoCube(AmmoCube[] a){
        List<AmmoCube> l1 = Arrays.asList(this.aC);
        List<AmmoCube> l2 = Arrays.asList(a);
        return (l1.containsAll(l2));
    }

    public boolean checkAmmoCubeForPay(AmmoCube[] a){           //does it work on the original one? we hope not
        List<AmmoCube> l1 = Arrays.asList(this.aC);
        List<AmmoCube> l2 = Arrays.asList(a);
        l2.remove(0);
        return (l1.containsAll(l2));
    }

    public void addAC(AmmoCube ac) {    //initialization

        if (ac.getC() == Colour.RED) {
            this.aC[0] = ac;
        }

        if (ac.getC() == Colour.BLUE) {
            this.aC[3] = ac;
        }

        if (ac.getC() == Colour.YELLOW) {
            this.aC[6] = ac;
        }
    }

    public void addNewAC(AmmoCube ac) {
        if(ac.getC() == Colour.RED) {
            for (int i = 0; i < 3; i++) {
                if (this.aC[i] == null)
                    this.aC[i] = ac;
            }
        }
        else if(ac.getC() == Colour.BLUE) {
            for (int i = 3; i < 6; i++) {
                if (this.aC[i] == null)
                    this.aC[i] = ac;
            }
        }
        else if(ac.getC() == Colour.YELLOW) {
            for (int i = 6; i < 9; i++) {
                if (this.aC[i] == null)
                    this.aC[i] = ac;
            }
        }
    }

    private void removeAC(AmmoCube a){
        int i = 0;
        for(AmmoCube a1 : this.getaC()){
            if(a1.getC().equals(a.getC())){
                this.aC[i] = null;
                return;
            }
            i++;
        }
    }

    public void removeArrayAC(AmmoCube[] a){
        for(AmmoCube a1 : a)
            removeAC(a1);
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

    public WeaponCard getWeaponCardObject(String s){
        for (WeaponCard w: this.wC){
            if(w.getCardName().equals(s))
                return w;
        }
        return null;
    }

    public boolean canPay(WeaponCard wC){
        //TODO return (this.checkAmmoCubeForPay(wC.getReloadCost()) && )

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
        if(c.getStatus() != -1)
            this.cell = c;
    }

    public Cell getCell() {
        return cell;
    }

    public boolean isAdrenaline1() {
        return adrenaline1;
    }

    public void setTrueAdrenaline1() {
        this.adrenaline1 = true;
    }

    public void setFalseAdrenaline1() {
        this.adrenaline1 = false;
    }

    public boolean isAdrenaline2() {
        return adrenaline2;
    }

    public void setTrueAdrenaline2() {
        this.adrenaline2 = true;
    }

    public void setFalseAdrenaline2() {
        this.adrenaline2 = false;
    }

    public boolean isDead(){
        return (this.pB.getDamages().getDamageTr()[10]!=null);
    }

    public boolean isOverkilled(){
        return (this.pB.getDamages().getDamageTr()[11]!=null);
    }
}