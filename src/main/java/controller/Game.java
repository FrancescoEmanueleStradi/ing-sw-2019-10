package controller;

import model.*;
import model.board.Cell;
import model.cards.PowerUpCard;
import model.Position;
import view.View;

import java.util.LinkedList;
import java.util.List;

import static controller.GameState.*;

public class Game {                                 //Cli or Gui -- Rmi or Socket

    private GameState gameState;
    private boolean init = false;
    private Grid grid;

    //private View v;

   public boolean gameStart(String nickName, Colour c) throws InvalidColourException{
       if(!init) {
           init = true;
           this.grid = new Grid();
           Player p = new Player(nickName, c, true);
           this.grid.addPlayer(p);               //first state
           this.gameState = START;
           return true;
       }
       return false;
   }

    public boolean addPlayer(String nickName, Colour c) throws InvalidColourException{      //TODO control name
        if(init) {
            Player p = new Player(nickName, c, false);
            this.grid.addPlayer(p);
            return true;
        }
        return false;
    }

    public boolean removePlayer(String nickName){
        if(init) {
            this.grid.removePlayer(this.grid.getPlayerObject(nickName));
            return true;
        }
        return false;
    }

   public boolean receiveType(int type){
       if(this.gameState.equals(START)) {
           this.grid.setType(type);                 //find a condition
           this.grid.setUpAmmoCard();
           this.gameState = INITIALIZED;
           return true;
       }
       return false;
   }

   public List<PowerUpCard> giveTwoPUCard(Player p){
       if(this.gameState.equals(INITIALIZED) && (p.getCell() == null)) {
           List<PowerUpCard> L = new LinkedList<>();
           L.add(this.grid.pickPowerUpCard());
           L.add(this.grid.pickPowerUpCard());
           return L;
       }
                                            //View control -> empty list
       return new LinkedList<>();
   }

   public Colour pickAndDiscardCard(Player p, PowerUpCard p1, PowerUpCard p2){     //p1 choose, p2 discard
           p.addPowerUpCard(p1);
           return p2.getC();
   }


   public boolean chooseSpawnPoint(Colour c, Player p){
       if(this.gameState.equals(INITIALIZED)){
           if(c.equals(Colour.YELLOW))
               this.grid.move(p, new Position(2,3));
           if(c.equals(Colour.RED))
               this.grid.move(p, new Position(1,0));
           if(c.equals(Colour.BLUE))
               this.grid.move(p, new Position(0,2));
           this.gameState = STARTTURN;
            return true;
       }
       return false;
   }
                                                            //view ask the choice

   public boolean firstActionShoot(Player p){
       if(this.gameState.equals(STARTTURN)){
           //TODO

           this.gameState = ACTION1;
           return true;
       }
       return false;
   }


    public boolean firstActionMove(Player p, String s){
        if(this.gameState.equals(STARTTURN)){

            //TODO
            this.gameState = ACTION1;
            return true;
        }
        return false;
    }

    public boolean firstActionGrab(Player p, String s){
        if(this.gameState.equals(STARTTURN)){
            //TODO

            this.gameState = ACTION1;
            return true;
        }
        return false;
    }


}
