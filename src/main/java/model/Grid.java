package model;

import model.board.*;
import model.decks.AmmoDeck;
import model.decks.PowerUpDeck;
import model.decks.WeaponDeck;
import model.cards.*;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.abs;

public class Grid {
    private ArrayList<Player> players;
    private Board board;
    private WeaponDeck weaponDeck;
    private PowerUpDeck powerUpDeck;
    private AmmoDeck ammoDeck;

    public Grid() throws InvalidColourException {
        this.players = new ArrayList<>();
        this.weaponDeck = new WeaponDeck();
        this.weaponDeck.startingShuffle();
        this.powerUpDeck = new PowerUpDeck();
        this.powerUpDeck.startingShuffle();
        this.ammoDeck = new AmmoDeck();
        //this.board = new Board(aType, pickWeaponCard(this.weaponDeck), pickWeaponCard(this.weaponDeck), pickWeaponCard(this.weaponDeck), pickWeaponCard(this.weaponDeck), pickWeaponCard(this.weaponDeck), pickWeaponCard(this.weaponDeck), pickWeaponCard(this.weaponDeck), pickWeaponCard(this.weaponDeck), pickWeaponCard(this.weaponDeck));

    }

    public void setType(int aType){
        this.board = new Board(aType, pickWeaponCard(this.weaponDeck), pickWeaponCard(this.weaponDeck), pickWeaponCard(this.weaponDeck), pickWeaponCard(this.weaponDeck), pickWeaponCard(this.weaponDeck), pickWeaponCard(this.weaponDeck), pickWeaponCard(this.weaponDeck), pickWeaponCard(this.weaponDeck), pickWeaponCard(this.weaponDeck));
    }

    public void addPlayer(Player p){
        this.players.add(p);
    }

    public void removePlayer(Player p){
        this.players.remove(p);
    }

    public List<Player> getPlayers() {
        return players;
    }

    public Player getPlayerObject(String name) {
        for (Player p : players){
            if(p.getNickName().equals(name))
                return p;
        }
        return null;
    }

    public int getNumPlayer(){
        return this.players.size();
    }

    public void damage(Player p, Player p1, int numDamages) { //p attacks, p1 is attacked
        p1.getpB().getDamages().addDamage(numDamages, p.getC());
    }

    public void clean(Player p){
        p.getpB().getDamages().clean();
    }

    public void addMark(Player p1, Player p2){
        p2.getpB().addMark(new DamageToken(p1.getC()));
    }

    public void removeMarkAndAdd(Player p1, Player p2){
        long x = p2.getpB().getMarks().stream().filter(a -> a.getC() == p1.getC()).count();
        int y = (int)x;
        this.damage(p2, p1, y);
        p2.getpB().clearMark(p2.getC());

    }

    public int distance(Player p1, Player p2){
        return abs(p1.getCell().getP().getX()-p2.getCell().getP().getX()) + abs(p1.getCell().getP().getY()-p2.getCell().getP().getY());
    }

    public Cell whereAmI(Player p){
        return p.getCell();
    }

    public void moveInMyCell(Player p, Player p2){
        p2.setCell(p.getCell());
    }

    public void move(Player p, int d){                                //1 up, 2 right, 3 down, 4 left

        int n = 0;

        for(int i =0; i<p.getCell().getPosWall().length; i++) {
            if (p.getCell().getPosWall()[i] == d) {
                //System.out.println("you can't move");
                n=1;
            }
        }
        if(n==0) {
            if((d==1) && (p.getCell().getP().getX()>0)){
                p.changeCell(board.getArena()[p.getCell().getP().getX()-1][p.getCell().getP().getY()]);
            }
            if((d==2) && (p.getCell().getP().getY()<3)){
                p.changeCell(board.getArena()[p.getCell().getP().getX()][p.getCell().getP().getY()+1]);
            }
            if((d==3) && (p.getCell().getP().getX()<2)){
                p.changeCell(board.getArena()[p.getCell().getP().getX()+1][p.getCell().getP().getY()]);
            }
            if((d==4) && (p.getCell().getP().getY()>0)){
                p.changeCell(board.getArena()[p.getCell().getP().getX()][p.getCell().getP().getY()-1]);
            }
        }

    }
    
    public void collectCard(Player p){
          if(p.getCell().getA().ispC())
              pickPowerUpCard(this.powerUpDeck, p);
          for(int i = 0; i < p.getCell().getA().getaC().size(); i++)
              p.addAC(p.getCell().getA().getaC().get(i));
    }

    public boolean isIntheRoom(Player p, Player p2){
        return (p.getCell().getC() == p2.getCell().getC());
    }

    public boolean isIntheRoom(Player p, Colour c){
        return (p.getCell().getC() == c);
    }

    public boolean isInViewZone(Player p, Player p2){
        boolean b = false;
        for(int i=0; i<p.getCell().getPosDoor().length; i++){
            if(!b) {
                if (p.getCell().getPosDoor()[i] == 1)
                    b = isIntheRoom(p2, this.board.getArena()[p2.getCell().getP().getX() - 1][p2.getCell().getP().getY()].getC());
                if (p.getCell().getPosDoor()[i] == 2)
                    b = isIntheRoom(p2, this.board.getArena()[p2.getCell().getP().getX()][p2.getCell().getP().getY() + 1].getC());
                if (p.getCell().getPosDoor()[i] == 3)
                    b = isIntheRoom(p2, this.board.getArena()[p2.getCell().getP().getX() + 1][p2.getCell().getP().getY()].getC());
                if (p.getCell().getPosDoor()[i] == 4)
                    b = isIntheRoom(p2, this.board.getArena()[p2.getCell().getP().getX()][p2.getCell().getP().getY() - 1].getC());

            }
        }

        return (isIntheRoom(p, p2) || ((p.getCell().getPosDoor()!=null) && b));
    }

    public List<Player> wichiIsInTheRoom(Player p){
        ArrayList<Player> pRoom = new ArrayList<>();
        for(Player px : players){
            if(isIntheRoom(p, px) && p!=px)         //Does it work?
                pRoom.add(px);
        }
        return pRoom;
    }

    public List<Player> whoIsInTheViewZone(Player p) {
        ArrayList<Player> pViewZone = new ArrayList<>();
        for (Player px : players) {
            if (isInViewZone(p, px) && p != px)         //Does it work?
                pViewZone.add(px);
        }
        return pViewZone;
    }

    public void pickWeaponCard(WeaponDeck d, Player p){
            p.addWeaponCard(d.getWeaponDeck().get(0));
            d.getWeaponDeck().remove(0);
    }

    public WeaponCard pickWeaponCard(WeaponDeck d){
        WeaponCard w = d.getWeaponDeck().get(0);
        d.getWeaponDeck().remove(0);
        return w;
    }


    public void pickPowerUpCard(PowerUpDeck d, Player p){
            p.addPowerUpCard(d.getPowerUpDeck().get(0));
            d.getPowerUpDeck().remove(0);
    }

    public AmmoCard pickAmmoCard(){
        AmmoCard a = this.ammoDeck.getAmmoDeck().get(0);
        this.ammoDeck.getAmmoDeck().remove(0);
        return a;
    }


    public void setUpAmmoCard(){

        this.board.getArena()[0][0].setA(this.pickAmmoCard());
        this.board.getArena()[0][1].setA(this.pickAmmoCard());
        this.board.getArena()[0][2].setA(this.pickAmmoCard());
        this.board.getArena()[0][3].setA(this.pickAmmoCard());
        this.board.getArena()[1][0].setA(this.pickAmmoCard());
        this.board.getArena()[1][1].setA(this.pickAmmoCard());
        this.board.getArena()[1][2].setA(this.pickAmmoCard());
        this.board.getArena()[1][3].setA(this.pickAmmoCard());
        this.board.getArena()[2][0].setA(this.pickAmmoCard());
        this.board.getArena()[2][1].setA(this.pickAmmoCard());
        this.board.getArena()[2][2].setA(this.pickAmmoCard());
        this.board.getArena()[2][3].setA(this.pickAmmoCard());

    }

    public void changeAmmoCard(Position p){
        this.board.getArena()[p.getX()][p.getY()].setA(this.pickAmmoCard());
    }
}
