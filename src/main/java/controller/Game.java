package controller;

import model.*;
import view.View;

public class Game {                                 //Cli or Gui -- Rmi or Socket

    private boolean init = false;
    private Grid grid;
    //private View v;

   public boolean gameStart(String nickName, Colour c) throws InvalidColourException{
       if(!init) {
           init = true;
           this.grid = new Grid();
           Player p = new Player(nickName, c, true);
           this.grid.addPlayer(p);               //first state
           return true;
       }
       return false;
   }

   public boolean receiveType(int type){
       this.grid.setType(type);                 //find a condition
       this.grid.setUpAmmoCard();
       return true;
   }

   public boolean addPlayer(String nickName, Colour c) throws InvalidColourException{
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



    //public boolean chooseSpawnPoint()


}
