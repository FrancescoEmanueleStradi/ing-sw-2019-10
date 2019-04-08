package model;

import model.board.*;
import model.decks.AmmoDeck;
import model.decks.Deck;
import model.decks.PowerUpDeck;
import model.decks.WeaponDeck;
import model.cards.*;

import java.util.ArrayList;

public class Grid {
    private static ArrayList<Player> players;
    private Board board;
    private Deck weaponDeck;
    private Deck powerupDeck;
    private Deck ammoDeck;

    public Grid(int aType) throws InvalidColourException {
        this.players = new ArrayList<>();
        this.board = new Board(aType);
        this.weaponDeck = new WeaponDeck();
        this.powerupDeck = new PowerUpDeck();
        this.ammoDeck = new AmmoDeck();

    }

    public void addPlayer(Player p){
        this.players.add(p);
    }

    public void removePlayer(Player p){
        this.players.remove(p);
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public int getNumPlayer(){
        return this.players.size();
    }

    public static void damage(Player p, int numDamages) {
        p.getpB().getDamages().addDamage(numDamages, p.getC());
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
        this.damage(p2, y);
        p2.getpB().clearMark(p2.getC());

    }

    public Cell whereAmI(Player p){
        return p.getCell();
    }

    public void move(Player p, int d){                                //1 up, 2 right, 3 down, 4 left

        int n = 0;

        for(int i =0; i<p.getCell().getPosWall().length; i++) {
            if (p.getCell().getPosWall()[i] == d) {
                System.out.println("you can't move");
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
    
    /*public AmmoCard collectCard(Player p){
        //TODO return p.getCell().getA();
    }

    public boolean isIntheRoom(Player p, Player p2){
        //TODO return 1 if Player p2 is in the same room of Player p
    }*/

    public void pickWeaponCard(WeaponDeck d, Player p){
            p.addWeaponCard(d.getWeaponDeck().get(0));
            d.getWeaponDeck().remove(0);
    }

    public void pickPowerUpCard(PowerUpDeck d, Player p){
            p.addPowerUpCard(d.getPowerUpDeck().get(0));
            d.getPowerUpDeck().remove(0);
    }


}
