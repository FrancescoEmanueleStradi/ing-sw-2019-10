package controller;

import model.*;
import model.board.WeaponSlot;
import model.cards.*;
import model.Position;
import model.cards.powerupcards.*;
import model.cards.weaponcards.*;
import model.player.AmmoCube;
import model.player.DamageToken;
import model.player.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import static controller.GameState.*;

public class Game {                                 //Cli or Gui -- Rmi or Socket

    private GameState gameState;
    private int numGame;
    private boolean init = false;
    private Grid grid = new Grid();
    private boolean discard = false;
    private List<String> deadList = new LinkedList<>();
    private boolean finalFrenzy = false;
    private PowerUpCard pUC1;
    private PowerUpCard pUC2;
    private int cardToPickAfterDeath;

    public Grid getGrid() {
        return grid;            //to use only for tests!!
    }

    public GameState getGameState() {
        return gameState;
    }

    public boolean gameIsNotStarted() {
        return this.grid.getPlayers().isEmpty();
    }

    public void gameStart(String nickName, Colour c) {
       if(!init) {
           init = true;
           //this.grid = new Grid();
           Player p = new Player(nickName, c, true);
           p.setTurnFinalFrenzy(1);
           this.grid.addPlayer(p);               //first state
           this.gameState = START;
       }
    }

    public void changeTurnFinalFrenzy(String nickName, int turn) {      //TODO attention to the server
        Player p = this.grid.getPlayerObject(nickName);
        if(!p.isFirstPlayerCard())
            p.setTurnFinalFrenzy(turn);
    }



//----------------------------------------------------------------------------------------------------




    public boolean isValidAddPlayer(String nickName, Colour c) {
       return ((init) && !(this.grid.getPlayersNickName().contains(nickName)) && !(this.grid.getPlayersColour().contains(c)));
    }


    public synchronized void addPlayer(String nickName, Colour c) {
        Player p = new Player(nickName, c, false);
        this.grid.addPlayer(p);
    }

    public boolean removePlayer(String nickName) {
        if(init) {
            this.grid.removePlayer(this.grid.getPlayerObject(nickName));
            return true;
        }
        return false;
    }

    public synchronized List<String> getPlayers() {
        return this.grid.getPlayers().stream().map(a -> a.getNickName()).collect(Collectors.toList());
    }

    public List<String> getWeaponCard(String nickName) {
        Player p = this.grid.getPlayerObject(nickName);
        return p.getwC().stream().map(a -> a.getCardName()).collect(Collectors.toList());
    }

    public List<String> getWeaponCardLoaded(String nickName){
        Player p = this.grid.getPlayerObject(nickName);
        return p.getwC().stream().filter(a -> a.isReloaded()).map(a -> a.getCardName()).collect(Collectors.toList());
    }

    public List<String> getWeaponCardUnloaded(String nickName) {
        Player p = this.grid.getPlayerObject(nickName);
        return p.getwC().stream().filter(a -> !a.isReloaded()).map(a -> a.getCardName()).collect(Collectors.toList());
    }

    public String getDescriptionWC(String s, String nickName) {
        Player p = this.grid.getPlayerObject(nickName);
        WeaponCard wC = p.getWeaponCardObject(s);
        return wC.getDescription();
    }

    public List<Colour> getReloadCost(String s, String nickName) {
        Player p = this.grid.getPlayerObject(nickName);
        WeaponCard wC = p.getWeaponCardObject(s);
        return Arrays.stream(wC.getReloadCost()).map(a -> a.getC()).collect(Collectors.toList());
    }

    public List<String> getPowerUpCard(String nickName) {
        Player p = this.grid.getPlayerObject(nickName);
        return p.getpC().stream().map(a -> a.getCardName()).collect(Collectors.toList());
    }

    public String getDescriptionPUC(String s, String colour, String nickName) {
        Player p = this.grid.getPlayerObject(nickName);
        Colour col = Colour.valueOf(colour);
        PowerUpCard pC = p.getPowerUpCardObject(s, col);
        return pC.getDescription();
    }

    public List<Integer> getScore(){
        return this.grid.getPlayers().stream().map(a -> a.getScore()).collect(Collectors.toList());
    }

//----------------------------------------------------------------------------------------------------




    public boolean isValidReceiveType(int type) {
        return this.gameState.equals(START) && (type == 1 || type == 2 || type == 3 || type == 4);
    }

   public void receiveType(int type) {
        this.grid.setType(type);                 //find a condition
        this.grid.setUpAmmoCard();
        this.gameState = INITIALIZED;
   }

   public synchronized List<String> giveTwoPUCard(String nickName) {
        Player p = this.grid.getPlayerObject(nickName);
        if(p.getCell() == null) {
            List<String> l = new LinkedList<>();
            pUC1 = this.grid.pickPowerUpCard();
            pUC2 = this.grid.pickPowerUpCard();
            l.add(pUC1.getCardName());
            l.add(pUC1.getC().getAbbreviation());
            l.add(pUC2.getCardName());
            l.add(pUC2.getC().getAbbreviation());
            return l;
        }
                                            //View control -> empty list
       return new LinkedList<>();
   }

   public boolean isValidPickAndDiscard(String nickName, String p1, String c1) {
        Player p = this.grid.getPlayerObject(nickName);
        return (p.getCell() == null &&
                (p1.equals(pUC1.getCardName()) && Colour.valueOf(c1).equals(pUC1.getC()) || p1.equals(pUC2.getCardName()) && Colour.valueOf(c1).equals(pUC2.getC())));
   }

   public synchronized void pickAndDiscardCard(String nickName, String p1, String c1) {     //p1 and c1 name and colour of the chosen card
       Player p = this.grid.getPlayerObject(nickName);
       if(p1.equals(pUC1.getCardName()) && Colour.valueOf(c1).equals(pUC1.getC())) {
           p.addPowerUpCard(pUC1);
           this.grid.getPowerUpDiscardPile().add(pUC2);
           chooseSpawnPoint(pUC2.getC(), p);
       }
       else if(p1.equals(pUC2.getCardName()) && Colour.valueOf(c1).equals(pUC2.getC())) {
           p.addPowerUpCard(pUC2);
           this.grid.getPowerUpDiscardPile().add(pUC1);
           chooseSpawnPoint(pUC1.getC(), p);
       }
   }


   private synchronized void chooseSpawnPoint(Colour c, Player p) {
       if(c.equals(Colour.YELLOW))
           p.changeCell(this.grid.getBoard().getArena()[2][3]);
       if(c.equals(Colour.RED))
           p.changeCell(this.grid.getBoard().getArena()[1][0]);            //view ask the choice
       if(c.equals(Colour.BLUE))
           p.changeCell(this.grid.getBoard().getArena()[0][2]);
       if(this.gameState == INITIALIZED)
           this.gameState = STARTTURN;
   }



   //----------------------------------------------------------------------------------------------------


    public boolean isValidCard(String nickName, String weaponCard) {
        return this.grid.getPlayerObject(nickName).getWeaponCardObject(weaponCard)!= null && this.grid.getPlayerObject(nickName).getWeaponCardObject(weaponCard).isReloaded();
    }


   private boolean isValidShootNotAdrenaline(Player p, String nameWC, List<Integer> lI, List<String> lS, List<AmmoCube> lA, List<PowerUpCard> lP) {
       boolean x = false;
       if(p.getWeaponCardObject(nameWC).isReloaded()){
           List<AmmoCube> l = choosePayment(lA, lP);
           List<Colour> lC = l.stream().map(a -> a.getC()).collect(Collectors.toList());
           switch(nameWC){
               case "Cyberblade":
                   if(lI.contains(1)){
                       if(this.grid.whereAmI(p).equals(this.grid.whereAmI(this.grid.getPlayerObject(lS.get(0)))))
                           x = true;
                       if(!x)
                           break;
                   }
                   if(lI.contains(2) && lI.contains(1)){
                       x = false;
                       if(this.grid.distance(p, new Position(Integer.parseInt(lS.get(1)), Integer.parseInt(lS.get(2)))) == 1 && !(this.grid.isThereAWall(p, new Position(Integer.parseInt(lS.get(1)), Integer.parseInt(lS.get(2))))))
                           x = true;
                   }
                   if(lI.contains(3) && lI.contains(1)){
                       x = false;
                       if(lI.contains(1) && this.grid.whereAmI(p).equals(this.grid.whereAmI(this.grid.getPlayerObject(lS.get(3)))) && !this.grid.getPlayerObject(lS.get(3)).equals(this.grid.getPlayerObject(lS.get(0))) && lC.contains(Colour.YELLOW))
                           x = true;
                   }
                   break;
               case "Electroscythe":
                   if(lI.contains(1) && !lI.contains(2))
                       x = true;
                   if(!lI.contains(1) && lI.contains(2) && lC.contains(Colour.RED) && lC.contains(Colour.BLUE))
                           x = true;
                   break;
               case "Flamethrower":
                   if(lI.contains(1) && !lI.contains(2) &&
                           this.grid.distance(p, this.grid.getPlayerObject(lS.get(0))) == 1 && !(this.grid.isThereAWall(p, new Position(this.grid.getPlayerObject(lS.get(0)).getCell().getP().getX(), this.grid.getPlayerObject(lS.get(0)).getCell().getP().getY())) &&
                           (lS.size()<2 || this.grid.distance(this.grid.getPlayerObject(lS.get(0)), this.grid.getPlayerObject(lS.get(1))) == 1 && ((this.grid.getPlayerObject(lS.get(0)).getCell().getP().getX() == this.grid.getPlayerObject(lS.get(1)).getCell().getP().getX()) || (this.grid.getPlayerObject(lS.get(0)).getCell().getP().getY() == this.grid.getPlayerObject(lS.get(1)).getCell().getP().getY())) && !(this.grid.isThereAWall(this.grid.getPlayerObject(lS.get(0)), new Position(this.grid.getPlayerObject(lS.get(1)).getCell().getP().getX(), this.grid.getPlayerObject(lS.get(1)).getCell().getP().getY()))))))
                           x = true;
                   if(!lI.contains(1) && lI.contains(2) &&
                           this.grid.distance(p, new Position(Integer.parseInt(lS.get(0)), Integer.parseInt(lS.get(1)))) == 1 && this.grid.distance(new Position(Integer.parseInt(lS.get(2)),Integer.parseInt(lS.get(3))), new Position(Integer.parseInt(lS.get(0)), Integer.parseInt(lS.get(1)))) == 1 && (Integer.parseInt(lS.get(0)) == Integer.parseInt(lS.get(2))||Integer.parseInt(lS.get(1)) == Integer.parseInt(lS.get(2))) && lC.containsAll(Arrays.asList(Colour.YELLOW, Colour.YELLOW)))
                           x = true;
                   break;
               case "Furnace":
                   if(lI.contains(1) && !lI.contains(2) && (!p.getCell().getC().equals(Colour.valueOf(lS.get(0))) && this.grid.colourOfOtherViewZone(p).contains(Colour.valueOf(lS.get(0)))))
                           x = true;
                   if(!lI.contains(1) && lI.contains(2) && (this.grid.distance(p, new Position(Integer.parseInt(lS.get(0)), Integer.parseInt(lS.get(1)))) == 1))
                           x = true;
                   break;
               case "Grenade Launcher":
                   if(lI.contains(1)){
                       if(this.grid.isInViewZone(p, this.grid.getPlayerObject(lS.get(0))) && (Integer.parseInt(lS.get(1)) == 0 || this.grid.canMove(this.grid.getPlayerObject(lS.get(0)), Integer.parseInt(lS.get(1))) && (Integer.parseInt(lS.get(1)) == 1 || Integer.parseInt(lS.get(1)) == 2 || Integer.parseInt(lS.get(1)) == 3 || Integer.parseInt(lS.get(1)) == 4)))
                           x = true;
                       if(!x)
                           break;
                   }
                   if(lI.contains(2) && lI.contains(1)){
                       x = false;
                       if(this.grid.isInViewZone(p, new Position(Integer.parseInt(lS.get(2)), Integer.parseInt(lS.get(3)))) && lC.contains(Colour.RED))
                           x = true;
                   }
                   break;
               case "Heatseeker":
                   if(lI.isEmpty() && (!this.grid.isInViewZone(p, this.grid.getPlayerObject(lS.get(0)))))
                            x = true;
                   break;
               case "Hellion":
                   if(lI.contains(1) && !lI.contains(2) &&
                           (this.grid.isInViewZone(p, this.grid.getPlayerObject(lS.get(0))) && !p.getCell().equals(this.grid.getPlayerObject(lS.get(0)).getCell())))
                            x = true;
                   if(!lI.contains(1) && lI.contains(2) &&
                           (this.grid.isInViewZone(p, this.grid.getPlayerObject(lS.get(0))) && !p.getCell().equals(this.grid.getPlayerObject(lS.get(0)).getCell()) && lC.contains(Colour.RED)))
                            x = true;
                   break;
               case "Lock Rifle":
                   if(lI.contains(1)) {
                        if(this.grid.isInViewZone(p, this.grid.getPlayerObject(lS.get(0))))
                            x = true;
                       if(!x)
                           break;
                   }
                   if(lI.contains(2) && lI.contains(1)) {
                        x = false;
                        if(this.grid.isInViewZone(p, this.grid.getPlayerObject(lS.get(1))) && !this.grid.getPlayerObject(lS.get(0)).equals(this.grid.getPlayerObject(lS.get(1))) && lC.contains(Colour.RED))
                            x = true;
                   }
                   break;
               case "Machine Gun":
                   if(!lI.isEmpty() && lI.get(0) == 1) {
                       if(this.grid.isInViewZone(p, this.grid.getPlayerObject(lS.get(0))) && (lS.get(1).equals("") || this.grid.isInViewZone(p, this.grid.getPlayerObject(lS.get(1)))))
                           x = true;
                       if(!x)
                           break;
                   }
                   if(lI.size() > 1 && lI.get(0) == 1 && lI.get(1) == 2) {
                       x = false;
                       if((this.grid.getPlayerObject(lS.get(2)).equals(this.grid.getPlayerObject(lS.get(0))) || (!lS.get(1).equals("") && (this.grid.getPlayerObject(lS.get(2)).equals(this.grid.getPlayerObject(lS.get(1)))) || this.grid.getPlayerObject(lS.get(2)).equals(this.grid.getPlayerObject(lS.get(0))))) && lC.contains(Colour.YELLOW))
                           x = true;
                       if(!x)
                           break;
                   }
                   if(lI.size() > 2 && lI.get(0) == 1 && lI.get(1) == 2 && lI.get(2) == 3) { //very difficult
                       x = false;
                       if((lS.get(1).equals("") || (!lS.get(1).equals("") && !this.grid.getPlayerObject(lS.get(3)).equals(this.grid.getPlayerObject(lS.get(2)))) || (!lS.get(1).equals("") && !this.grid.getPlayerObject(lS.get(3)).equals(this.grid.getPlayerObject(lS.get(0))) && !this.grid.getPlayerObject(lS.get(3)).equals(this.grid.getPlayerObject(lS.get(1))) && !this.grid.getPlayerObject(lS.get(3)).equals(this.grid.getPlayerObject(lS.get(2))) && this.grid.isInViewZone(p, this.grid.getPlayerObject(lS.get(3)))) ||
                               (!lS.get(1).equals("") && !this.grid.getPlayerObject(lS.get(3)).equals(this.grid.getPlayerObject(lS.get(2))) && (this.grid.getPlayerObject(lS.get(3)).equals(this.grid.getPlayerObject(lS.get(0))) || this.grid.getPlayerObject(lS.get(3)).equals(this.grid.getPlayerObject(lS.get(1)))) && !this.grid.getPlayerObject(lS.get(4)).equals(this.grid.getPlayerObject(lS.get(0))) && !this.grid.getPlayerObject(lS.get(4)).equals(this.grid.getPlayerObject(lS.get(1))) && !this.grid.getPlayerObject(lS.get(4)).equals(this.grid.getPlayerObject(lS.get(2))) && !this.grid.getPlayerObject(lS.get(4)).equals(this.grid.getPlayerObject(lS.get(3))) && this.grid.isInViewZone(p, this.grid.getPlayerObject(lS.get(4))))) && lC.contains(Colour.BLUE))
                           x = true;
                   }
                   break;
               case "Plasma Gun":
                   if((!lI.contains(2) || (lI.indexOf(2) > lI.indexOf(1)))) {
                       if (lI.contains(1)) {
                           if (this.grid.isInViewZone(p, this.grid.getPlayerObject(lS.get(0))))
                               x = true;
                           if (!x)
                               break;
                       }
                       if (lI.contains(2) && lI.contains(1)) {
                           x = false;
                           List<Integer> directions = new LinkedList<>();
                           if(lS.size() == 4) {
                               directions.add(Integer.parseInt(lS.get(2)));
                               directions.add(Integer.parseInt(lS.get(3)));
                           }
                           if (Integer.parseInt(lS.get(1)) < 3 && (Integer.parseInt(lS.get(2)) == 1 || Integer.parseInt(lS.get(2)) == 2 || Integer.parseInt(lS.get(2)) == 3 || Integer.parseInt(lS.get(2)) == 4) &&
                                   (lS.size() == 3 && this.grid.canMove(p, Integer.parseInt(lS.get(2))) || lS.size() == 4 && (this.grid.canGhostMove(p, directions)) && (Integer.parseInt(lS.get(3)) == 1 || Integer.parseInt(lS.get(3)) == 2 || Integer.parseInt(lS.get(3)) == 3 || Integer.parseInt(lS.get(3)) == 4)))
                               x = true;
                       }
                       if (lI.contains(3) && lI.contains(1)) {
                           x = false;
                           if(lC.contains(Colour.BLUE))
                               x = true;
                       }
                   }
                   else {
                       if (lI.contains(2) && lI.contains(1) &&
                               (Integer.parseInt(lS.get(1)) < 3 && this.grid.canMove(p, Integer.parseInt(lS.get(2))) && (lS.size() < 4 || this.grid.canMove(p, Integer.parseInt(lS.get(3))))))
                               x = true;
                       List<Integer> directions = new LinkedList<>();
                       for(int i = 0; i < Integer.parseInt(lS.get(1)); i++)
                           directions.add(Integer.parseInt(lS.get(2)));
                       if (lI.contains(1)) {
                           x = false;
                           if(this.grid.isInViewZone(this.grid.ghostMove(this.grid.ghostMove(p, directions), directions), this.grid.getPlayerObject(lS.get(0))))
                               x = true;
                           if (!x)
                               break;
                       }
                       if (lI.contains(3) && lI.contains(1)) {
                           x = false;
                           if(lC.contains(Colour.BLUE))
                               x = true;
                       }
                   }
                   break;
               case "Power Glove":
                   if(lI.contains(1) && !lI.contains(2) &&
                           (this.grid.distance(p, this.grid.getPlayerObject(lS.get(0))) == 1 && this.grid.isInViewZone(p,this.grid.getPlayerObject(lS.get(0)))))
                           x = true;
                   if(!lI.contains(1) && lI.contains(2) && !lI.contains(3) && !lI.contains(4) && !lI.contains(5) &&
                           (this.grid.distance(p, new Position(Integer.parseInt(lS.get(1)), Integer.parseInt(lS.get(2)))) == 1 && !this.grid.isThereAWall(p, new Position(Integer.parseInt(lS.get(1)), Integer.parseInt(lS.get(2)))) && lC.contains(Colour.BLUE)))
                           x = true;
                   if(!lI.contains(1) && lI.contains(2) && lI.contains(3) && !lI.contains(4) && !lI.contains(5) &&
                           (this.grid.distance(p, new Position(Integer.parseInt(lS.get(1)), Integer.parseInt(lS.get(2)))) == 1 && !this.grid.isThereAWall(p, new Position(Integer.parseInt(lS.get(1)), Integer.parseInt(lS.get(2)))) && this.grid.getPlayerObject(lS.get(3)).getCell().getP().equals(new Position(Integer.parseInt(lS.get(1)), Integer.parseInt(lS.get(2)))) && lC.contains(Colour.BLUE)))
                           x = true;
                   if(!lI.contains(1) && lI.contains(2) && lI.contains(3) && lI.contains(4) && !lI.contains(5) &&
                           (this.grid.distance(p, new Position(Integer.parseInt(lS.get(1)), Integer.parseInt(lS.get(2)))) == 1 && !this.grid.isThereAWall(p, new Position(Integer.parseInt(lS.get(1)), Integer.parseInt(lS.get(2)))) && this.grid.getPlayerObject(lS.get(3)).getCell().getP().equals(new Position(Integer.parseInt(lS.get(1)), Integer.parseInt(lS.get(2)))) &&
                               this.grid.distance(p, new Position(Integer.parseInt(lS.get(4)), Integer.parseInt(lS.get(5)))) == 1 && !this.grid.isThereAWall(p, new Position(Integer.parseInt(lS.get(4)), Integer.parseInt(lS.get(5)))) &&
                               (Integer.parseInt(lS.get(4)) == Integer.parseInt(lS.get(1)) || Integer.parseInt(lS.get(5)) == Integer.parseInt(lS.get(2)))&& lC.contains(Colour.BLUE)))
                           x = true;
                   if(!lI.contains(1) && lI.contains(2) && lI.contains(3) && lI.contains(4) && lI.contains(5) &&
                           (this.grid.distance(p, new Position(Integer.parseInt(lS.get(1)), Integer.parseInt(lS.get(2)))) == 1 && !this.grid.isThereAWall(p, new Position(Integer.parseInt(lS.get(1)), Integer.parseInt(lS.get(2)))) && this.grid.getPlayerObject(lS.get(3)).getCell().getP().equals(new Position(Integer.parseInt(lS.get(1)), Integer.parseInt(lS.get(2)))) &&
                               this.grid.distance(p, new Position(Integer.parseInt(lS.get(4)), Integer.parseInt(lS.get(5)))) == 1 && !this.grid.isThereAWall(p, new Position(Integer.parseInt(lS.get(4)), Integer.parseInt(lS.get(5)))) &&
                               (Integer.parseInt(lS.get(4)) == Integer.parseInt(lS.get(1)) || Integer.parseInt(lS.get(5)) == Integer.parseInt(lS.get(2))) &&
                               this.grid.getPlayerObject(lS.get(6)).getCell().getP().equals(new Position(Integer.parseInt(lS.get(4)), Integer.parseInt(lS.get(5)))) && lC.contains(Colour.BLUE)))
                           x = true;
                   break;
               case "Railgun":
                   if(lI.contains(1) && !lI.contains(2) &&
                           (p.getCell().getP().getX() == this.grid.getPlayerObject(lS.get(0)).getCell().getP().getX()|| p.getCell().getP().getY() == this.grid.getPlayerObject(lS.get(0)).getCell().getP().getY()))
                           x = true;
                   if(!lI.contains(1) && lI.contains(2)) {
                       if (lS.size() < 2) {
                           if (p.getCell().getP().getX() == this.grid.getPlayerObject(lS.get(0)).getCell().getP().getX() || p.getCell().getP().getY() == this.grid.getPlayerObject(lS.get(0)).getCell().getP().getY())
                               x = true;
                       } else if (lS.size() == 2 &&
                               (p.getCell().getP().getX() == this.grid.getPlayerObject(lS.get(0)).getCell().getP().getX() && p.getCell().getP().getX() == this.grid.getPlayerObject(lS.get(1)).getCell().getP().getX() && (p.getCell().getP().getY() <= this.grid.getPlayerObject(lS.get(0)).getCell().getP().getY() && p.getCell().getP().getY() <= this.grid.getPlayerObject(lS.get(1)).getCell().getP().getY() || p.getCell().getP().getY() >= this.grid.getPlayerObject(lS.get(0)).getCell().getP().getY() && p.getCell().getP().getY() >= this.grid.getPlayerObject(lS.get(1)).getCell().getP().getY()) ||
                                       p.getCell().getP().getY() == this.grid.getPlayerObject(lS.get(0)).getCell().getP().getY() && p.getCell().getP().getY() == this.grid.getPlayerObject(lS.get(1)).getCell().getP().getY() && (p.getCell().getP().getX() <= this.grid.getPlayerObject(lS.get(0)).getCell().getP().getX() && p.getCell().getP().getX() <= this.grid.getPlayerObject(lS.get(1)).getCell().getP().getX() || p.getCell().getP().getX() >= this.grid.getPlayerObject(lS.get(0)).getCell().getP().getX() && p.getCell().getP().getX() >= this.grid.getPlayerObject(lS.get(1)).getCell().getP().getX())))
                           x = true;
                   }
                   break;
               case "Rocket Launcher":
                   if(!lI.contains(3) || lI.indexOf(3) > lI.indexOf(1)) {
                       if (lI.contains(1)) {
                           if (this.grid.isInViewZone(p, this.grid.getPlayerObject(lS.get(0))) && !p.getCell().equals(this.grid.getPlayerObject(lS.get(0)).getCell()) && (!lS.contains("2") || lS.contains("2") && this.grid.canMove(p, Integer.parseInt(lS.get(1)))))
                               x = true;
                           if (!x)
                               break;
                       }
                       if (lI.contains(3) && lI.contains(1)) {
                           x = false;
                           List<Integer> directions = new LinkedList<>();
                           if(Integer.parseInt(lS.get(2)) == 2) {
                               directions.add(Integer.parseInt(lS.get(3)));
                           }
                           if ((Integer.parseInt(lS.get(2)) < 2 && this.grid.canMove(p, Integer.parseInt(lS.get(3))) && (Integer.parseInt(lS.get(3)) == 1 || Integer.parseInt(lS.get(3)) == 2 || Integer.parseInt(lS.get(3)) == 3 || Integer.parseInt(lS.get(3)) == 4)) ||
                                   (Integer.parseInt(lS.get(2)) == 2 && this.grid.canMove(p, Integer.parseInt(lS.get(3))) && Integer.parseInt(lS.get(3)) == 1 || Integer.parseInt(lS.get(3)) == 2 || Integer.parseInt(lS.get(3)) == 3 || Integer.parseInt(lS.get(3)) == 4 && this.grid.canMove(this.grid.ghostMove(p, directions), Integer.parseInt(lS.get(4))) && (Integer.parseInt(lS.get(4)) == 1 || Integer.parseInt(lS.get(4)) == 2 || Integer.parseInt(lS.get(4)) == 3 || Integer.parseInt(lS.get(4)) == 4)) && lC.contains(Colour.BLUE))
                               x = true;
                       }
                       if (lI.contains(4) && lI.contains(1)) {
                           x = false;
                           if (lC.contains(Colour.YELLOW))
                               x = true;
                           if(!x)
                               break;
                       }
                   }
                   else {
                       if (lI.contains(3) && lI.contains(1)) {
                           List<Integer> directions = new LinkedList<>();
                           if(Integer.parseInt(lS.get(2)) == 2) {
                               directions.add(Integer.parseInt(lS.get(3)));
                           }
                           if (((Integer.parseInt(lS.get(2)) < 2 && this.grid.canMove(p, Integer.parseInt(lS.get(3)))) && (Integer.parseInt(lS.get(3)) == 1 || Integer.parseInt(lS.get(3)) == 2 || Integer.parseInt(lS.get(3)) == 3 || Integer.parseInt(lS.get(3)) == 4)) ||
                                   (Integer.parseInt(lS.get(2)) == 2 && this.grid.canMove(p, Integer.parseInt(lS.get(3))) && (Integer.parseInt(lS.get(3)) == 1 || Integer.parseInt(lS.get(3)) == 2 || Integer.parseInt(lS.get(3)) == 3 || Integer.parseInt(lS.get(3)) == 4) && this.grid.canMove(this.grid.ghostMove(p, directions), Integer.parseInt(lS.get(4))) && (Integer.parseInt(lS.get(4)) == 1 || Integer.parseInt(lS.get(4)) == 2 || Integer.parseInt(lS.get(4)) == 3 || Integer.parseInt(lS.get(4)) == 4)) && lC.contains(Colour.BLUE))
                               x = true;
                       }
                       List<Integer> directions = new LinkedList<>();
                       for(int i = 0; i <= Integer.parseInt(lS.get(2)); i++)
                           directions.add((Integer.parseInt(lS.get(i + 3))));
                       if (lI.contains(1)) {
                           x = false;
                           if (this.grid.isInViewZone(this.grid.ghostMove(p, directions), this.grid.getPlayerObject(lS.get(0))) && !this.grid.ghostMove(p, directions).getCell().equals(this.grid.getPlayerObject(lS.get(0)).getCell()) && (!lS.contains("2") || lS.contains("2") && this.grid.canMove(this.grid.ghostMove(p, directions), Integer.parseInt(lS.get(1))) && (Integer.parseInt(lS.get(1)) == 1 || Integer.parseInt(lS.get(1)) == 2 || Integer.parseInt(lS.get(1)) == 3 || Integer.parseInt(lS.get(1)) == 4)))
                               x = true;
                           if (!x)
                               break;
                       }
                       if (lI.contains(4) && lI.contains(1)) {
                           x = false;
                           if (lC.contains(Colour.YELLOW))
                               x = true;
                           if(!x)
                               break;
                       }
                   }
                   break;
               case "Shockwave":
                   if(lI.contains(1) && !lI.contains(2) &&
                           (lS.size() == 1 && this.grid.distance(p, this.grid.getPlayerObject(lS.get(0))) <= 1 || lS.size() == 2 && this.grid.distance(p, this.grid.getPlayerObject(lS.get(0))) <= 1 && this.grid.distance(p, this.grid.getPlayerObject(lS.get(1))) <= 1|| lS.size() == 3 && this.grid.distance(p, this.grid.getPlayerObject(lS.get(0))) <= 1 && this.grid.distance(p, this.grid.getPlayerObject(lS.get(1))) <= 1 && this.grid.distance(p, this.grid.getPlayerObject(lS.get(2))) <= 1))
                           x = true;
                   if(!lI.contains(1) && lI.contains(2) &&
                           (lC.contains(Colour.YELLOW)))
                        x = true;
                   break;
               case "Shotgun":
                   if(lI.contains(1) && !lI.contains(3) &&
                           (p.getCell().equals(this.grid.getPlayerObject(lS.get(0)).getCell()) && (lS.size() < 2 || lS.size() == 2 && this.grid.canMove(this.grid.getPlayerObject(lS.get(0)), Integer.parseInt(lS.get(1))) && Integer.parseInt(lS.get(1)) == 1 || Integer.parseInt(lS.get(1)) == 2 || Integer.parseInt(lS.get(1)) == 3 || Integer.parseInt(lS.get(1)) == 4)))
                           x = true;
                   if(!lI.contains(1) && lI.contains(3) &&
                           (this.grid.distance(p, this.grid.getPlayerObject(lS.get(0))) == 1))
                           x = true;
                   break;
               case "Sledgehammer":
                   if(lI.contains(1) && !lI.contains(2) &&
                           (p.getCell().equals(this.grid.getPlayerObject(lS.get(0)).getCell())))
                           x = true;
                   if(!lI.contains(1) && lI.contains(2)) {
                       List<Integer> directions = new LinkedList<>();
                       directions.add(Integer.parseInt(lS.get(2)));
                       if(p.getCell().equals(this.grid.getPlayerObject(lS.get(0)).getCell()) &&
                               (Integer.parseInt(lS.get(1)) == 0) || ((Integer.parseInt(lS.get(1))) == 1 && this.grid.canGhostMove(p, directions) && Integer.parseInt(lS.get(2)) == 1 || Integer.parseInt(lS.get(2)) == 2 || Integer.parseInt(lS.get(2)) == 3 || Integer.parseInt(lS.get(2)) == 4) ||
                               (Integer.parseInt(lS.get(1)) == 2 && this.grid.canGhostMove(p, directions) && this.grid.canGhostMove(this.grid.ghostMove(p, directions), directions) && Integer.parseInt(lS.get(2)) == 1 || Integer.parseInt(lS.get(2)) == 2 || Integer.parseInt(lS.get(2)) == 3 || Integer.parseInt(lS.get(2)) == 4) && lC.contains(Colour.RED))
                           x = true;    //is the if correct?
                   }
                   break;
               case "T.H.O.R.":
                   if(lI.contains(1)) {
                       if(this.grid.isInViewZone(p, this.grid.getPlayerObject(lS.get(0))))
                           x = true;
                       if(!x)
                           break;
                   }
                   if(lI.contains(1) && lI.contains(2)) {
                       x = false;
                       if(this.grid.isInViewZone(this.grid.getPlayerObject(lS.get(0)), this.grid.getPlayerObject(lS.get(1))) && !this.grid.getPlayerObject(lS.get(0)).equals(this.grid.getPlayerObject(lS.get(1))) && lC.contains(Colour.BLUE))
                           x = true;
                       if(!x)
                           break;
                   }
                   if(lI.contains(1) && lI.contains(2) && lI.contains(3)) {
                       x = false;
                       if(this.grid.isInViewZone(this.grid.getPlayerObject(lS.get(1)), this.grid.getPlayerObject(lS.get(2))) && !this.grid.getPlayerObject(lS.get(1)).equals(this.grid.getPlayerObject(lS.get(2))) && !this.grid.getPlayerObject(lS.get(0)).equals(this.grid.getPlayerObject(lS.get(2))) && lC.contains(Colour.BLUE))
                           x = true;
                   }
                   break;
               case "Tractor Beam":
                   if(lI.contains(1) && !lI.contains(2)) {
                       List<Integer> directions = new LinkedList<>();
                       if(lS.size() == 2)
                           directions.add(Integer.parseInt(lS.get(1)));
                       if(lS.size() == 3) {
                           directions.add(Integer.parseInt(lS.get(1)));
                           directions.add(Integer.parseInt(lS.get(2)));
                       }
                       if((lS.size() == 1 && this.grid.isInViewZone(p, this.grid.getPlayerObject(lS.get(0))) || (lS.size() == 2 && this.grid.canGhostMove(this.grid.getPlayerObject(lS.get(0)), directions) && this.grid.isInViewZone(p, this.grid.ghostMove(this.grid.getPlayerObject(lS.get(0)), directions)) && Integer.parseInt(lS.get(1)) == 1 || Integer.parseInt(lS.get(1)) == 2 || Integer.parseInt(lS.get(1)) == 3 || Integer.parseInt(lS.get(1)) == 4) ||
                               (lS.size() == 3 && this.grid.canGhostMove(this.grid.getPlayerObject(lS.get(0)), directions) && this.grid.isInViewZone(p, this.grid.ghostMove(this.grid.getPlayerObject(lS.get(0)), directions))) && (Integer.parseInt(lS.get(1)) == 1 || Integer.parseInt(lS.get(1)) == 2 || Integer.parseInt(lS.get(1)) == 3 || Integer.parseInt(lS.get(1)) == 4) && (Integer.parseInt(lS.get(2)) == 1 || Integer.parseInt(lS.get(2)) == 2 || Integer.parseInt(lS.get(2)) == 3 || Integer.parseInt(lS.get(2)) == 4)) && lC.contains(Colour.YELLOW) && lC.contains(Colour.RED))
                            x = true;
                   }
                   if(!lI.contains(1) && lI.contains(2) &&
                           (this.grid.distance(p, this.grid.getPlayerObject(lS.get(0))) < 3 && lC.contains(Colour.RED) && lC.contains(Colour.YELLOW)))
                           x = true;
                   break;
               case "Vortex Cannon":
                   if(lI.contains(1)) {
                       if(this.grid.isInViewZone(p, new Position(Integer.parseInt(lS.get(1)), Integer.parseInt(lS.get(2)))) && !p.getCell().getP().equals(new Position(Integer.parseInt(lS.get(1)), Integer.parseInt(lS.get(2)))) && this.grid.distance(this.grid.getPlayerObject(lS.get(0)), (new Position(Integer.parseInt(lS.get(1)), Integer.parseInt(lS.get(2))))) < 2)
                           x = true;
                       if(!x)
                           break;
                   }
                   if(lI.contains(2) && lI.contains(1)) {
                       x = false;
                       if(this.grid.distance(this.grid.getPlayerObject(lS.get(3)), (new Position(Integer.parseInt(lS.get(1)), Integer.parseInt(lS.get(2))))) < 2 && this.grid.distance(this.grid.getPlayerObject(lS.get(4)), (new Position(Integer.parseInt(lS.get(1)), Integer.parseInt(lS.get(2))))) < 2 && lC.contains(Colour.RED))
                           x = true;
                   }
                   break;
               case "Whisper":
                   if(this.grid.isInViewZone(p, this.grid.getPlayerObject(lS.get(0))) && this.grid.distance(p, this.grid.getPlayerObject(lS.get(0))) >= 2)
                       x = true;
                   break;
               case "ZX-2":
                   if(lI.contains(1) && !lI.contains(2) &&
                           (this.grid.isInViewZone(p, this.grid.getPlayerObject(lS.get(0)))))
                            x = true;
                   if(!lI.contains(1) && lI.contains(2) &&
                           (lS.size() == 1 && this.grid.isInViewZone(p, this.grid.getPlayerObject(lS.get(0))) ||
                               lS.size() == 2 && this.grid.isInViewZone(p, this.grid.getPlayerObject(lS.get(0))) && this.grid.isInViewZone(p, this.grid.getPlayerObject(lS.get(1))) && !this.grid.getPlayerObject(lS.get(0)).getCell().getC().equals(this.grid.getPlayerObject(lS.get(1)).getCell().getC()) ||
                                lS.size() == 3 && this.grid.isInViewZone(p, this.grid.getPlayerObject(lS.get(0))) && this.grid.isInViewZone(p, this.grid.getPlayerObject(lS.get(1))) && this.grid.isInViewZone(p, this.grid.getPlayerObject(lS.get(2))) && !this.grid.getPlayerObject(lS.get(0)).getCell().getC().equals(this.grid.getPlayerObject(lS.get(1)).getCell().getC()) && !this.grid.getPlayerObject(lS.get(1)).getCell().getC().equals(this.grid.getPlayerObject(lS.get(2)).getCell().getC()) && !this.grid.getPlayerObject(lS.get(0)).getCell().getC().equals(this.grid.getPlayerObject(lS.get(2)).getCell().getC())))
                           x = true;
                   break;
               default: x = false;
           }
       }
       return x;
    }



   private void shootNotAdrenaline(Player p, String nameWC, List<Integer> lI, List<String> lS, List<AmmoCube> lA, List<PowerUpCard> lP) {                    //is better to use a file?
        switch(nameWC) {
            case "Cyberblade":
                int x = 0;
                for(Integer i : lI) {
                    if (i == 1) {
                        ((Cyberblade) p.getWeaponCardObject(nameWC)).applyEffect(this.grid, p, this.grid.getPlayerObject(lS.get(0)));
                        x = 1;
                    }
                    if (i == 2) {
                        ((Cyberblade) p.getWeaponCardObject(nameWC)).applySpecialEffect(this.grid, p, lS.get(1), lS.get(2));
                    }
                    if ((x == 1) && i == 3)
                        ((Cyberblade) p.getWeaponCardObject(nameWC)).applySpecialEffect2(this.grid, p , this.grid.getPlayerObject(lS.get(3)));
                }
                break;
            case "Electroscythe":
                if(lI.get(0) == 1)
                    ((Electroscythe) p.getWeaponCardObject(nameWC)).applyEffect(this.grid, p);
                if(lI.get(0) == 2)
                    ((Electroscythe) p.getWeaponCardObject(nameWC)).applySpecialEffect(this.grid, p);
                break;
            case "Flamethrower":
                if(lI.get(0) == 1)
                    ((Flamethrower) p.getWeaponCardObject(nameWC)).applyEffect(this.grid, p, this.grid.getPlayerObject(lS.get(0)), this.grid.getPlayerObject(lS.get(1)));
                if(lI.get(0) == 2)
                    ((Flamethrower) p.getWeaponCardObject(nameWC)).applySpecialEffect(this.grid, p, lS.get(0), lS.get(1), lS.get(2), lS.get(3));
                break;
            case "Furnace":
                if(lI.get(0) == 1)
                    ((Furnace) p.getWeaponCardObject(nameWC)).applyEffect(this.grid, p, Colour.valueOf(lS.get(0)));
                if(lI.get(0) == 2)
                    ((Furnace) p.getWeaponCardObject(nameWC)).applySpecialEffect(this.grid, p, lS.get(0), lS.get(1));
                break;
            case "Grenade Launcher":
                for(Integer i : lI){
                    if(i == 1){
                        ((GrenadeLauncher) p.getWeaponCardObject(nameWC)).applyEffect(this.grid, p, this.grid.getPlayerObject(lS.get(0)));
                        ((GrenadeLauncher) p.getWeaponCardObject(nameWC)).moveEnemy(this.grid, this.grid.getPlayerObject(lS.get(0)), Integer.parseInt(lS.get(1)));          //we give 0 if the player doesn't want to move
                    }
                    if(i == 2)
                        ((GrenadeLauncher) p.getWeaponCardObject(nameWC)).applySpecialEffect(this.grid, p, lS.get(2), lS.get(3));
                }
                break;
            case "Heatseeker":
                ((Heatseeker) p.getWeaponCardObject(nameWC)).applyEffect(this.grid, p, this.grid.getPlayerObject(lS.get(0)));
                break;
            case "Hellion":
                if(lI.get(0) == 1)
                    ((Hellion) p.getWeaponCardObject(nameWC)).applyEffect(this.grid, p, this.grid.getPlayerObject(lS.get(0)));
                if(lI.get(0) == 2)
                    ((Hellion) p.getWeaponCardObject(nameWC)).applySpecialEffect(this.grid, p, this.grid.getPlayerObject(lS.get(0)));
                break;
            case "Lock Rifle":
                int y = 0;
                if(lI.get(0) == 1) {
                    ((LockRifle) p.getWeaponCardObject(nameWC)).applyEffect(this.grid, p, this.grid.getPlayerObject(lS.get(0)));
                    y = 1;
                }
                if(lI.get(1) == 2 && y == 1)
                    ((LockRifle) p.getWeaponCardObject(nameWC)).applySpecialEffect(this.grid, p, this.grid.getPlayerObject(lS.get(1)));
                break;
            case "Machine Gun":
                if(lI.get(0) == 1)
                    ((MachineGun) p.getWeaponCardObject(nameWC)).applyEffect(this.grid, p, this.grid.getPlayerObject(lS.get(0)), this.grid.getPlayerObject(lS.get(1)));
                if(lI.size() > 1 && lI.get(1) == 2)
                    ((MachineGun) p.getWeaponCardObject(nameWC)).applySpecialEffect(this.grid, p, this.grid.getPlayerObject(lS.get(2)));
                if(lI.size() == 3 && lI.get(2) == 3)
                    ((MachineGun) p.getWeaponCardObject(nameWC)).applyEffect(this.grid, p,this.grid.getPlayerObject(lS.get(3)), this.grid.getPlayerObject(lS.get(4)));
                break;
            case "Plasma Gun":
                int z = 0;
                for(int i : lI){
                    if(i == 1) {
                        ((PlasmaGun) p.getWeaponCardObject(nameWC)).applyEffect(this.grid, p, this.grid.getPlayerObject(lS.get(0)));
                        z = 1;
                    }
                    if(i == 2)
                        ((PlasmaGun) p.getWeaponCardObject(nameWC)).applySpecialEffect(this.grid, p, Integer.parseInt(lS.get(1)), Integer.parseInt(lS.get(2)), Integer.parseInt(lS.get(3)));
                    if(i == 3 && z == 1)
                        ((PlasmaGun) p.getWeaponCardObject(nameWC)).applySpecialEffect2(this.grid, p, this.grid.getPlayerObject(lS.get(0)));
                }
                break;
            case "Power Glove":
                if (lI.get(0) == 1)
                    ((PowerGlove) p.getWeaponCardObject(nameWC)).applyEffect(this.grid, p, this.grid.getPlayerObject(lS.get(0)));
                if (lI.get(0) == 2) {
                    ((PowerGlove) p.getWeaponCardObject(nameWC)).applySpecialEffectPart1(p, this.grid, lS.get(1), lS.get(2));
                    if(lI.contains(3))
                        ((PowerGlove) p.getWeaponCardObject(nameWC)).applySpecialEffectPart2(this.grid, p, this.grid.getPlayerObject(lS.get(3)));
                    if(lI.contains(4))
                        ((PowerGlove) p.getWeaponCardObject(nameWC)).applySpecialEffectPart3(p, this.grid, lS.get(4), lS.get(5));
                    if(lI.contains(5))
                        ((PowerGlove) p.getWeaponCardObject(nameWC)).applySpecialEffectPart4(this.grid, p, this.grid.getPlayerObject(lS.get(6)));
                }
                break;
            case "Railgun":
                if(lI.get(0) == 1)
                    ((Railgun) p.getWeaponCardObject(nameWC)).applyEffect(this.grid, p, this.grid.getPlayerObject(lS.get(0)));
                if(lI.get(0) == 2)
                    ((Railgun) p.getWeaponCardObject(nameWC)).applySpecialEffect(this.grid, p, this.grid.getPlayerObject(lS.get(0)), this.grid.getPlayerObject(lS.get(1)));
                break;
            case "Rocket Launcher":
                int h = 0;
                for(int i : lI) {
                    if (i == 1) {
                        ((RocketLauncher) p.getWeaponCardObject(nameWC)).applyEffect(this.grid, p, this.grid.getPlayerObject(lS.get(0)));
                        h = 1;
                        if(lI.get(i+1) == 2)
                            ((RocketLauncher) p.getWeaponCardObject(nameWC)).movePlayer(this.grid,this.grid.getPlayerObject(lS.get(0)), Integer.parseInt(lS.get(1)));
                    }
                    if(i == 3)
                        ((RocketLauncher) p.getWeaponCardObject(nameWC)).applySpecialEffect(this.grid, p, Integer.parseInt(lS.get(2)), Integer.parseInt(lS.get(3)), Integer.parseInt(lS.get(4)));
                    if(i == 4 && h == 1)
                        ((RocketLauncher) p.getWeaponCardObject(nameWC)).applySpecialEffect2(this.grid, p);
                }
                break;
            case "Shockwave":
                if(lI.get(0) == 1)
                    ((Shockwave) p.getWeaponCardObject(nameWC)).applyEffect(this.grid, p, this.grid.getPlayerObject(lS.get(0)), this.grid.getPlayerObject(lS.get(1)), this.grid.getPlayerObject(lS.get(2)));
                if(lI.get(0) == 2)
                    ((Shockwave) p.getWeaponCardObject(nameWC)).applySpecialEffect(this.grid, p);
                break;
            case "Shotgun":
                if(lI.get(0) == 1 && lI.get(1) == 2) {
                    ((Shotgun) p.getWeaponCardObject(nameWC)).applyEffect(this.grid, p, this.grid.getPlayerObject(lS.get(0)));
                    ((Shotgun) p.getWeaponCardObject(nameWC)).movePlayer(this.grid, this.grid.getPlayerObject(lS.get(0)), Integer.parseInt(lS.get(1)));
                }
                if(lI.get(0) == 1 && lI.size()<2)
                    ((Shotgun) p.getWeaponCardObject(nameWC)).applyEffect(this.grid, p, this.grid.getPlayerObject(lS.get(0)));
                if(lI.get(0) == 3)
                    ((Shotgun) p.getWeaponCardObject(nameWC)).applySpecialEffect(this.grid, p, this.grid.getPlayerObject(lS.get(0)));
                break;
            case "Sledgehammer":
                if(lI.get(0) == 1)
                    ((Sledgehammer) p.getWeaponCardObject(nameWC)).applyEffect(this.grid, p, this.grid.getPlayerObject(lS.get(0)));
                if(lI.get(0) == 2) {
                    ((Sledgehammer) p.getWeaponCardObject(nameWC)).applyEffect(this.grid, p, this.grid.getPlayerObject(lS.get(0)));
                    ((Sledgehammer) p.getWeaponCardObject(nameWC)).moveEnemy(this.grid.getPlayerObject(lS.get(0)), this.grid, Integer.parseInt(lS.get(1)), Integer.parseInt(lS.get(2)));
                }
                break;
            case "T.H.O.R.":
                if(lI.get(0) == 1)
                    ((THOR) p.getWeaponCardObject(nameWC)).applyEffect(this.grid, p, this.grid.getPlayerObject(lS.get(0)));
                if(lI.get(1) == 2)
                    ((THOR) p.getWeaponCardObject(nameWC)).applySpecialEffect(this.grid, p, this.grid.getPlayerObject(lS.get(1)));
                if(lI.get(2) == 3)
                    ((THOR) p.getWeaponCardObject(nameWC)).applySpecialEffect2(this.grid, p, this.grid.getPlayerObject(lS.get(2)));
                break;
            case "Tractor Beam":
                if(lI.get(0) == 1) {
                    List<Integer> directions = new LinkedList<>();
                    if(lS.size() == 2)
                        directions.add(Integer.parseInt(lS.get(1)));
                    if(lS.size() == 3) {
                        directions.add(Integer.parseInt(lS.get(1)));
                        directions.add(Integer.parseInt(lS.get(2)));
                    }
                    ((TractorBeam) p.getWeaponCardObject(nameWC)).applyEffect(this.grid, p, this.grid.getPlayerObject(lS.get(0)), directions);
                }
                if(lI.get(0) == 2)
                    ((TractorBeam) p.getWeaponCardObject(nameWC)).applySpecialEffect(this.grid, p, this.grid.getPlayerObject(lS.get(0)));
                break;
            case "Vortex Cannon":
                if(lI.get(0) == 1){
                    ((VortexCannon) p.getWeaponCardObject(nameWC)).applyEffect(this.grid, p, this.grid.getPlayerObject(lS.get(0)), lS.get(1), lS.get(2));
                    if(lI.get(1) == 2)
                        ((VortexCannon) p.getWeaponCardObject(nameWC)).applySpecialEffect(this.grid, p, this.grid.getPlayerObject(lS.get(3)), this.grid.getPlayerObject(lS.get(4)));
                }
                break;
            case "Whisper":
                ((Whisper) p.getWeaponCardObject(nameWC)).applyEffect(this.grid, p, this.grid.getPlayerObject(lS.get(0)));
                break;
            case "ZX-2":
                if(lI.get(0) == 1)
                    ((ZX2) p.getWeaponCardObject(nameWC)).applyEffect(this.grid, p, this.grid.getPlayerObject(lS.get(0)));
                if(lI.get(0) == 2)
                    ((ZX2) p.getWeaponCardObject(nameWC)).applySpecialEffect(this.grid, p, this.grid.getPlayerObject(lS.get(0)), this.grid.getPlayerObject(lS.get(1)), this.grid.getPlayerObject(lS.get(2)));
                break;

            default: return;

        }
        p.getWeaponCardObject(nameWC).unload();
        p.payCard(lA, lP);
        this.grid.getPowerUpDiscardPile().addAll(lP);

    }

    private boolean isValidShootAdrenaline(Player p, String nameWC, List<Integer> lI, List<String> lS, int direction, List<AmmoCube> lA, List<PowerUpCard> lP) {
        if(!this.grid.canMove(p, direction))
            return false;
        else {
            Player future = new Player("?fUtUrE!", p.getC(), p.isFirstPlayerCard());
            future.changeCell(p.getCell());
            this.grid.move(future, direction);
            return this.isValidShootNotAdrenaline(future, nameWC, lI, lS, lA, lP);
        }
    }

    private void shootAdrenaline(Player p, String nameWC, List<Integer> lI, List<String> lS, int direction, List<AmmoCube> lA, List<PowerUpCard> lP) {
        this.grid.move(p, direction);
        this.shootNotAdrenaline(p, nameWC, lI, lS, lA, lP);
    }


    public boolean isValidFirstActionShoot(String nickName, String nameWC, List<Integer> lI, List<String> lS, int direction, List<Colour> lAInput, List<String> lPInput, List<String> lPColourInput) {
        Player p = this.grid.getPlayerObject(nickName);
        List<AmmoCube> lA= new LinkedList<>();
        if(!lAInput.isEmpty()) {
            for (Colour c : lAInput)
                lA.add(new AmmoCube(c));
            AmmoCube[] cubeArray = new AmmoCube[lA.size()];
            lA.toArray(cubeArray);
            if (!p.checkAmmoCube(cubeArray))
                return false;
        }
        List<PowerUpCard> lP= new LinkedList<>();
        if(!lPInput.isEmpty()) {
            for (int i = 0; i < lPInput.size(); i++) {
                if (p.getPowerUpCardObject(lPInput.get(i), Colour.valueOf(lPColourInput.get(i))) == null)
                    return false;
                lP.add(p.getPowerUpCardObject(lPInput.get(i), Colour.valueOf(lPColourInput.get(i))));
            }
        }
        if(this.gameState.equals(STARTTURN)) {
            if (!p.isAdrenaline2())
                return isValidShootNotAdrenaline(p, nameWC, lI, lS, lA, lP);
            else if (p.isAdrenaline2())
                return isValidShootAdrenaline(p, nameWC, lI, lS, direction, lA, lP);
        }
        return false;
    }

    public synchronized void firstActionShoot(String nickName, String nameWC, List<Integer> lI, List<String> lS, int direction, List<Colour> lAInput, List<String> lPInput, List<String> lPColourInput) {
        Player p = this.grid.getPlayerObject(nickName);
        List<AmmoCube> lA= new LinkedList<>();
        if(!lAInput.isEmpty()) {
            for (Colour c : lAInput)
                lA.add(new AmmoCube(c));
        }
        List<PowerUpCard> lP= new LinkedList<>();
        if(!lPInput.isEmpty()) {
            for (int i = 0; i < lPInput.size(); i++)
                lP.add(p.getPowerUpCardObject(lPInput.get(i), Colour.valueOf(lPColourInput.get(i))));
        }
        if(!p.isAdrenaline2())
            this.shootNotAdrenaline(p, nameWC, lI, lS, lA, lP);
        else if (p.isAdrenaline2())
            this.shootAdrenaline(p, nameWC, lI, lS, direction, lA, lP);
        this.gameState = ACTION1;
    }

//----------------------------------------------------------------------------------------------------



   private void move(Player p, List<Integer> directions) {
       for(int i = 0; i < directions.size(); i++) {
           this.grid.move(p, directions.get(i));    //view will tell player if there's a wall
       }
   }


   public boolean isValidFirstActionMove(String nickName, List<Integer> directions) {
        Player p = this.grid.getPlayerObject(nickName);
        return (this.gameState.equals(STARTTURN) && (directions.size() < 4) && (!directions.isEmpty()) && grid.canGhostMove(p, directions));
   }

    public synchronized void firstActionMove(String nickName, List<Integer> directions){ //player p moves 1,2,3 cells: directions contains every direction from cell to cell
        Player p = this.grid.getPlayerObject(nickName);
        move(p, directions);
        this.gameState = ACTION1;
    }





//----------------------------------------------------------------------------------------------------




    private void giveWhatIsOnAmmoCard(Player p, AmmoCard card) {
       if(card.ispC())
           this.grid.pickPowerUpCard(p);
       for(AmmoCube cube : card.getaC())
           p.addNewAC(cube);
       this.grid.getAmmoDiscardPile().add(card);
    }

    private boolean canPay(WeaponCard w, List<AmmoCube> l) {
        List<AmmoCube> l2 = new ArrayList<>(Arrays.asList(w.getReloadCost()));          //this way the original array is not modified
        l2.remove(0);                                             //containsAll does not work: AmmoCubes have not the same references!
        int count = 0;
        for(AmmoCube a : l) {
            for(AmmoCube aCost : l2) {
                if(a.getC().equals(aCost.getC())) {
                    count++;
                    break;
                }
                if(count == l2.size())
                    return true;
            }
        }
        return false;

    }

    private void grabNotAdrenaline(Player p, int direction, WeaponCard wCard, List<AmmoCube> lA, List<PowerUpCard> lP) {
        this.grid.move(p, direction);
        if(p.getCell().getStatus() == 0)
            giveWhatIsOnAmmoCard(p, p.getCell().getA());
        else if((p.getCell().getStatus() == 1) && wCard != null && (canPay(wCard, choosePayment(lA, lP)))) {
                p.payCard(lA, lP);
                p.getwC().add(wCard);
                p.getwC().get(p.getwC().size() - 1).reload();
        }
        if(p.getwC().size() > 3)
            this.discard = true;
    }

    public boolean isDiscard() {
        return discard;
    }

    public void discardWeaponCard(String nickName, String wSInput, String wCInput) {
       Player p = this.grid.getPlayerObject(nickName);
       WeaponCard wC = p.getWeaponCardObject(wCInput);
       WeaponSlot wS = this.grid.getWeaponSlotObject(wSInput);
       p.removeWeaponCard(wC);
       if(wS.getCard1() == null){
           wS.setCard1(wC);
           return;
       }
        if(wS.getCard2() == null){
            wS.setCard2(wC);
            return;
        }
        if(wS.getCard3() == null)
            wS.setCard3(wC);
    }

    private void grabAdrenaline(Player p, Integer[] d, WeaponCard w, List<AmmoCube> lA, List<PowerUpCard> lP) {
        this.grid.move(p, d[0]);
        if(d.length == 2)
            this.grid.move(p, d[1]);
        if(p.getCell().getStatus() == 0)
            giveWhatIsOnAmmoCard(p, p.getCell().getA());
        else if((p.getCell().getStatus() == 1) && w != null && (canPay(w, choosePayment(lA, lP)))) {
                p.payCard(lA, lP);
                p.getwC().add(w);
                p.getwC().get(p.getwC().size() - 1).reload();
        }
        if(p.getwC().size() > 3)
            this.discard = true;
    }

    private List<AmmoCube> choosePayment(List<AmmoCube> lA, List<PowerUpCard> lP) {
        List<AmmoCube> l = new LinkedList<>();
        if(!lA.isEmpty())
            l.addAll(lA);
        if(!lA.isEmpty()) {
            for (PowerUpCard p : lP)
                l.add(p.getValue());
        }
        return l;
    }

    public boolean isValidFirstActionGrab(String nickName, Integer[] directions, String wCardInput, String wSlotInput, List<Colour> lAInput, List<String> lPInput, List<String> lPColourInput) {
        Player p = this.grid.getPlayerObject(nickName);
        List<Integer> directionList = Arrays.asList(directions);
        WeaponCard wCard = this.grid.getWeaponCardObject(wCardInput);
        List<AmmoCube> lA = new LinkedList<>();
        if(!lAInput.isEmpty()) {
            for (Colour c : lAInput)
                lA.add(new AmmoCube(c));
            AmmoCube[] cubeArray = new AmmoCube[lA.size()];
            lA.toArray(cubeArray);
            if (!p.checkAmmoCube(cubeArray) && wCard != null)
                return false;
        }
        List<PowerUpCard> lP = new LinkedList<>();
        if(!lPInput.isEmpty()) {
            for (int i = 0; i < lPInput.size(); i++) {
                if (p.getPowerUpCardObject(lPInput.get(i), Colour.valueOf(lPColourInput.get(i))) == null)
                    return false;
                lP.add(p.getPowerUpCardObject(lPInput.get(i), Colour.valueOf(lPColourInput.get(i))));
            }
        }
        if(this.gameState.equals(STARTTURN) && (directions.length <= 2)) {
            if(!p.isAdrenaline1() && directions.length == 1 && this.grid.canMove(p, directions[0]) &&
                    (wCardInput.equals("") || ((!wCardInput.equals("") && wCard != null) && ((wSlotInput.equals("1") && (this.grid.ghostMove(p, directionList).getCell().getP().getX() == 0 && this.grid.ghostMove(p, directionList).getCell().getP().getY() == 2)) ||
                        (wSlotInput.equals("2") && (this.grid.ghostMove(p, directionList).getCell().getP().getX() == 2 && this.grid.ghostMove(p, directionList).getCell().getP().getY() == 3)) ||
                        (wSlotInput.equals("3") && (this.grid.ghostMove(p, directionList).getCell().getP().getX() == 1 && this.grid.ghostMove(p, directionList).getCell().getP().getY() == 0))))))
                    return true;
            if(p.isAdrenaline1() && directions.length == 2 && this.grid.canGhostMove(p, directionList) &&
                    (wCardInput.equals("") || ((!wCardInput.equals("") && wCard != null) && ((wSlotInput.equals("1") && (this.grid.ghostMove(p, directionList).getCell().getP().getX() == 0 && this.grid.ghostMove(p, directionList).getCell().getP().getY() == 2)) ||
                        (wSlotInput.equals("2") && (this.grid.ghostMove(p, directionList).getCell().getP().getX() == 2 && this.grid.ghostMove(p, directionList).getCell().getP().getY() == 3)) ||
                        (wSlotInput.equals("3") && (this.grid.ghostMove(p, directionList).getCell().getP().getX() == 1 && this.grid.ghostMove(p, directionList).getCell().getP().getY() == 0))))))
                    return true;
        }
        return false;
    }


    public synchronized void firstActionGrab(String nickName, Integer[] directions, String wCardInput, List<Colour> lAInput, List<String> lPInput, List<String> lPColourInput) { //directions contains where p wants to go. directions contains '0' if p doesn't want to move and only grab
        Player p = this.grid.getPlayerObject(nickName);
        WeaponCard wCard = this.grid.getWeaponCardObject(wCardInput);
        List<AmmoCube> l = new LinkedList<>();
        for(Colour c : lAInput)
            l.add(new AmmoCube(c));
        List<PowerUpCard> lP= new LinkedList<>();
        for(int i = 0; i < lPInput.size(); i++)
            lP.add(p.getPowerUpCardObject(lPInput.get(i), Colour.valueOf(lPColourInput.get(i))));
        if(!(p.isAdrenaline1()))
                grabNotAdrenaline(p, directions[0], wCard, l, lP);

        if(p.isAdrenaline1()||p.isAdrenaline2()){
                grabAdrenaline(p, directions, wCard, l, lP);
        }
        this.gameState = ACTION1;
    }





//----------------------------------------------------------------------------------------------------






    public boolean isValidSecondActionMove(String nickName, List<Integer> directions) {
        Player p = this.grid.getPlayerObject(nickName);
        return (this.gameState.equals(ACTION1) && (directions.size() < 4) && (!directions.isEmpty()) && grid.canGhostMove(p, directions));
    }



    public synchronized void secondActionMove(String nickName, List<Integer> directions ){ //player p moves 1,2,3 cells: directions contains every direction from cell to cell
        Player p = this.grid.getPlayerObject(nickName);
        move(p, directions);
        this.gameState = ACTION2;
    }





//----------------------------------------------------------------------------------------------------





    public boolean isValidSecondActionGrab(String nickName, Integer[] directions, String wCardInput, String wSlotInput, List<Colour> lAInput, List<String> lPInput, List<String> lPColourInput) {
        Player p = this.grid.getPlayerObject(nickName);
        List<Integer> directionList = Arrays.asList(directions);
        WeaponCard wCard = this.grid.getWeaponCardObject(wCardInput);
        List<AmmoCube> lA= new LinkedList<>();
        if(!lAInput.isEmpty()) {
            for (Colour c : lAInput)
                lA.add(new AmmoCube(c));
            AmmoCube[] cubeArray = new AmmoCube[lA.size()];
            lA.toArray(cubeArray);
            if (!p.checkAmmoCube(cubeArray) && wCard != null)
                return false;
        }
        List<PowerUpCard> lP= new LinkedList<>();
        if(!lPInput.isEmpty()) {
            for (int i = 0; i < lPInput.size(); i++) {
                if (p.getPowerUpCardObject(lPInput.get(i), Colour.valueOf(lPColourInput.get(i))) == null)
                    return false;
                lP.add(p.getPowerUpCardObject(lPInput.get(i), Colour.valueOf(lPColourInput.get(i))));
            }
        }
        if(this.gameState.equals(ACTION1) && (directions.length <= 2)) {
            if(!p.isAdrenaline1() && directions.length == 1 && this.grid.canMove(p, directions[0]) &&
                    (wCardInput.equals("") || ((!wCardInput.equals("") && wCard != null) && ((wSlotInput.equals("1") && (this.grid.ghostMove(p, directionList).getCell().getP().getX() == 0 && this.grid.ghostMove(p, directionList).getCell().getP().getY() == 2)) ||
                        (wSlotInput.equals("2") && (this.grid.ghostMove(p, directionList).getCell().getP().getX() == 2 && this.grid.ghostMove(p, directionList).getCell().getP().getY() == 3)) ||
                        (wSlotInput.equals("3") && (this.grid.ghostMove(p, directionList).getCell().getP().getX() == 1 && this.grid.ghostMove(p, directionList).getCell().getP().getY() == 0))))))
                    return true;
            if(p.isAdrenaline1() && directions.length == 2 && this.grid.canGhostMove(p, directionList) &&
                    (wCardInput.equals("") || ((!wCardInput.equals("") && wCard != null) && ((wSlotInput.equals("1") && (this.grid.ghostMove(p, directionList).getCell().getP().getX() == 0 && this.grid.ghostMove(p, directionList).getCell().getP().getY() == 2)) ||
                        (wSlotInput.equals("2") && (this.grid.ghostMove(p, directionList).getCell().getP().getX() == 2 && this.grid.ghostMove(p, directionList).getCell().getP().getY() == 3)) ||
                        (wSlotInput.equals("3") && (this.grid.ghostMove(p, directionList).getCell().getP().getX() == 1 && this.grid.ghostMove(p, directionList).getCell().getP().getY() == 0))))))
                    return true;
        }
        return false;
    }

    public synchronized void secondActionGrab(String nickName, Integer[] directions, String wCardInput, List<Colour> lAInput, List<String> lPInput, List<String> lPColourInput) { //directions contains where p wants to go. directions contains '0' if p doesn't want to move and only grab
        Player p = this.grid.getPlayerObject(nickName);
        WeaponCard wCard = this.grid.getWeaponCardObject(wCardInput);
        List<AmmoCube> l= new LinkedList<>();
        for(Colour c : lAInput)
            l.add(new AmmoCube(c));
        List<PowerUpCard> lP= new LinkedList<>();
        for(int i = 0; i < lPInput.size(); i++)
            lP.add(p.getPowerUpCardObject(lPInput.get(i), Colour.valueOf(lPColourInput.get(i))));
        if(!(p.isAdrenaline1()))
            grabNotAdrenaline(p, directions[0], wCard, l, lP);

        if(p.isAdrenaline1()||p.isAdrenaline2()){
            grabAdrenaline(p, directions, wCard, l, lP);
        }

        this.gameState = ACTION2;
    }

//----------------------------------------------------------------------------------------------------

    public boolean isValidSecondActionShoot(String nickName, String nameWC, List<Integer> lI, List<String> lS, int direction, List<Colour> lAInput, List<String> lPInput, List<String> lPColourInput) {
        Player p = this.grid.getPlayerObject(nickName);
        List<AmmoCube> lA = new LinkedList<>();
        if(!lAInput.isEmpty()) {
            for (Colour c : lAInput)
                lA.add(new AmmoCube(c));
            AmmoCube[] cubeArray = new AmmoCube[lA.size()];
            lA.toArray(cubeArray);
            if (!p.checkAmmoCube(cubeArray))
                return false;
        }
        List<PowerUpCard> lP = new LinkedList<>();
        if(!lPInput.isEmpty()) {
            for (int i = 0; i < lPInput.size(); i++) {
                if (p.getPowerUpCardObject(lPInput.get(i), Colour.valueOf(lPColourInput.get(i))) == null)
                    return false;
                lP.add(p.getPowerUpCardObject(lPInput.get(i), Colour.valueOf(lPColourInput.get(i))));
            }
        }
        if(this.gameState.equals(ACTION1))
            if(!p.isAdrenaline2())
                return isValidShootNotAdrenaline(p, nameWC, lI, lS, lA, lP);
            else if(p.isAdrenaline2())
                return isValidShootAdrenaline(p, nameWC, lI, lS, direction, lA, lP);
        return false;
    }

    public synchronized void secondActionShoot(String nickName, String nameWC, List<Integer> lI, List<String> lS, int direction, List<Colour> lAInput, List<String> lPInput, List<String> lPColourInput) {
        Player p = this.grid.getPlayerObject(nickName);
        List<AmmoCube> lA= new LinkedList<>();
        if(!lAInput.isEmpty()) {
            for (Colour c : lAInput)
                lA.add(new AmmoCube(c));
        }
        List<PowerUpCard> lP= new LinkedList<>();
        if(!lPInput.isEmpty()) {
            for (int i = 0; i < lPInput.size(); i++)
                lP.add(p.getPowerUpCardObject(lPInput.get(i), Colour.valueOf(lPColourInput.get(i))));
        }
        if(!p.isAdrenaline2())
            this.shootNotAdrenaline(p, nameWC, lI, lS, lA, lP);
        else if (p.isAdrenaline2())
            this.shootAdrenaline(p, nameWC, lI, lS, direction, lA, lP);
        this.gameState = ACTION2;
    }

//----------------------------------------------------------------------------------------------------


    public boolean isValidUsePowerUpCard(String nickName, String namePC, String colourPC, List<String> lS, Colour c) {
        Player p = this.grid.getPlayerObject(nickName);
        boolean x = false;
        if(this.gameState.equals(ACTION1) || this.gameState.equals(ACTION2)) {
            if(namePC.equals("Tagback Grenade")) {
                //player p can use this only when he is being attacked, i.e. while the attacker is taking his turn
                //TODO this should be implemented server-side
                if (p.getPowerUpCardObject(namePC, Colour.valueOf(colourPC)) != null && this.grid.isInViewZone(p, this.grid.getPlayerObject(lS.get(0))))
                    x = true;
            }
            else if(namePC.equals("Targeting Scope")) {
                    if (p.getPowerUpCardObject(namePC, Colour.valueOf(colourPC)) != null && p.checkAmmoCube(new AmmoCube[]{new AmmoCube(c)}))                //TODO this should be implemented server-side
                        x = true;
                }
        }
        if(this.gameState.equals(STARTTURN) || this.gameState.equals(ACTION1) || this.gameState.equals(ACTION2)) {
                if(namePC.equals("Newton")) {
                    List<Integer> directions = new LinkedList<>();
                    if (lS.size() == 2) {
                        if (p.getPowerUpCardObject(namePC, Colour.valueOf(colourPC)) != null && this.grid.canMove(this.grid.getPlayerObject(lS.get(0)), Integer.parseInt(lS.get(1))) &&
                                (Integer.parseInt(lS.get(1)) == 1 || Integer.parseInt(lS.get(1)) == 2 || Integer.parseInt(lS.get(1)) == 3 || Integer.parseInt(lS.get(1)) == 4))
                            x = true;
                    } else if (lS.size() == 3) {
                        directions.add(Integer.parseInt(lS.get(1)));
                        directions.add(Integer.parseInt(lS.get(2)));
                        if (p.getPowerUpCardObject(namePC, Colour.valueOf(colourPC)) != null && this.grid.canGhostMove(this.grid.getPlayerObject(lS.get(0)), directions) &&
                                (Integer.parseInt(lS.get(1)) == 1 || Integer.parseInt(lS.get(1)) == 2 || Integer.parseInt(lS.get(1)) == 3 || Integer.parseInt(lS.get(1)) == 4) && (Integer.parseInt(lS.get(2)) == 1 || Integer.parseInt(lS.get(2)) == 2 || Integer.parseInt(lS.get(2)) == 3 || Integer.parseInt(lS.get(2)) == 4))
                            x = true;
                    }
                }
                else if(namePC.equals("Teleporter") &&
                        (p.getPowerUpCardObject(namePC, Colour.valueOf(colourPC)) != null && this.grid.getBoard().getArena()[Integer.parseInt(lS.get(0))][Integer.parseInt(lS.get(1))].getStatus() != -1))
                        x = true;
                }
        return x;
    }

    public synchronized void usePowerUpCard(String nickName, String namePC, String colourPC, List<String> lS, Colour c) {
        Player p = this.grid.getPlayerObject(nickName);
        switch(namePC) {
            case "Newton" :
                List<Integer> directions = new LinkedList<>();
                if(lS.size() == 2)
                    directions.add(Integer.parseInt(lS.get(1)));
                if(lS.size() == 3) {
                    directions.add(Integer.parseInt(lS.get(1)));
                    directions.add(Integer.parseInt(lS.get(2)));
                }
                ((Newton) p.getPowerUpCardObject(namePC, Colour.valueOf(colourPC))).applyEffect(this.grid, this.grid.getPlayerObject(lS.get(0)), directions);
                break;
            case "Teleporter" :
                ((Teleporter) p.getPowerUpCardObject(namePC, Colour.valueOf(colourPC))).applyEffect(this.grid, p, lS.get(0), lS.get(1));
                break;
            case "Tagback Grenade" :
                ((TagbackGrenade) p.getPowerUpCardObject(namePC, Colour.valueOf(colourPC))).applyEffect(this.grid, p, this.grid.getPlayerObject(lS.get(0)));
                break;
            case "Targeting Scope" :
                for(String s : lS) {
                    ((TargetingScope) p.getPowerUpCardObject(namePC, Colour.valueOf(colourPC))).applyEffect(this.grid, p, this.grid.getPlayerObject(s));
                }
                List<AmmoCube> lA = new LinkedList<>();
                lA.add(new AmmoCube(c));
                List<PowerUpCard> lP = new LinkedList<>();
                p.payCard(lA, lP);
                break;
            default: return;
        }
        p.removePowerUpCard(p.getPowerUpCardObject(namePC, Colour.valueOf(colourPC)));
        this.grid.getPowerUpDiscardPile().add(p.getPowerUpCardObject(namePC, Colour.valueOf(colourPC)));
    }






//----------------------------------------------------------------------------------------------------





    public boolean isValidReload(String nickName, String s) {
        Player p = this.grid.getPlayerObject(nickName);
        return (p.checkAmmoCube(p.getWeaponCardObject(s).getReloadCost()) && this.gameState.equals(ACTION2));
    }

    public synchronized void reload(String nickName, String s, int end) {  // end is 1 if the player has finished to reload
           Player p = this.grid.getPlayerObject(nickName);
           p.getWeaponCardObject(s).reload();
           p.removeArrayAC(p.getWeaponCardObject(s).getReloadCost());
           if(end == 1)
               this.gameState = RELOADED;
    }


    private void death(Player p) {
       p.changeCell(null);
       if(p.getpB().getDamages().getDamageTr()[11] != null) {
           int n = this.grid.getBoard().substituteSkull(2);
           this.grid.getBoard().getK().getC()[n] = p.getpB().getDamages().getDT(11).getC();
           p.getpB().addMark(new DamageToken(p.getpB().getDamages().getDT(11).getC()));      //OverKill
        }
        else {
            int n = this.grid.getBoard().substituteSkull(1);
            this.grid.getBoard().getK().getC()[n] = p.getpB().getDamages().getDT(10).getC();
        }
        if(p.getpB().getPoints().getPoints().size()>1)
            p.getpB().getPoints().remove();
        p.getpB().getDamages().clean();
        p.addPowerUpCard(this.grid.getPowerUpDeck().getDeck().get(cardToPickAfterDeath));
        cardToPickAfterDeath++;     //TODO attention to this int, also used below
    }

    public boolean isValidScoring() {
        return this.gameState.equals(RELOADED) && (this.grid.whoIsDead()!=null);
    }

    public synchronized void scoring() {
        int c = 0;
        cardToPickAfterDeath = 0;
        for(Player p : this.grid.whoIsDead()) {
            this.deadList.add(p.getNickName());
            this.grid.scoringByColour(p.getpB().getDamages().getDamageTr()[0].getC(), 1);
            if(!p.getpB().getDamages().scoreBoard().isEmpty())
                this.grid.scoringByColour(p.getpB().getDamages().getColourPosition(0), p.getpB().getPoints().getInt(1));
            if(p.getpB().getDamages().scoreBoard().size() >= 2)
                this.grid.scoringByColour(p.getpB().getDamages().getColourPosition(1), p.getpB().getPoints().getInt(2));
            if(p.getpB().getDamages().scoreBoard().size() >= 3)
                this.grid.scoringByColour(p.getpB().getDamages().getColourPosition(2), p.getpB().getPoints().getInt(3));
            if(p.getpB().getDamages().scoreBoard().size() >= 4)
                this.grid.scoringByColour(p.getpB().getDamages().getColourPosition(3), p.getpB().getPoints().getInt(4));
            if(p.getpB().getDamages().scoreBoard().size() >= 5)
                this.grid.scoringByColour(p.getpB().getDamages().getColourPosition(4), p.getpB().getPoints().getInt(5));
            if(p.getpB().getDamages().scoreBoard().size() >= 6)
                this.grid.scoringByColour(p.getpB().getDamages().getColourPosition(5), p.getpB().getPoints().getInt(6));
            p.getpB().getDamages().cleanL();
            c++;
            if(c == 2)
                this.grid.scoringByColour(p.getpB().getDamages().getDamageTr()[10].getC(),1);        //Double Kill  //TODO attention to powerupcard: another player can kill you
            this.death(p);
            this.gameState = DEATH;
        }

    }

    public List<String> getDeadList() {
        return deadList;
    }

    public boolean isValidDiscardCardForSpawnPoint(String nickName, String s1, String c1) {
        Player p = this.grid.getPlayerObject(nickName);
        return (this.gameState == DEATH && p.getPowerUpCardObject(s1, Colour.valueOf(c1)) != null);
    }

    public synchronized void discardCardForSpawnPoint(String nickName, String s1, String c1) {      //Attention to the view
        Player p = this.grid.getPlayerObject(nickName);
        PowerUpCard p1 = p.getPowerUpCardObject(s1, Colour.valueOf(c1));
        chooseSpawnPoint(p1.getC(), p);
        p.removePowerUpCard(p1);
        this.grid.getPowerUpDeck().getDeck().remove(cardToPickAfterDeath);
        cardToPickAfterDeath--;
        this.grid.getPowerUpDiscardPile().add(p1);
        this.deadList.remove(nickName);
        if(this.deadList.isEmpty())
            this.gameState = ENDTURN;
    }

    public boolean isValidToReplace() {
        return this.gameState == ENDTURN;
    }

    public synchronized void replace() {
       this.grid.replaceAmmoCard();
       this.grid.replaceWeaponCard();
       if(this.grid.getBoard().getK().getSkulls()[7] != 0)
           finalFrenzy = true;
       this.gameState = STARTTURN;
    }



//-------------------------------------------------------------------------------------------------------------
    //FINAL FRENZY ACTIONS

    //player can do 2 actions (choosing between 1, 2, 3) if he takes his final turn before the first player
    //player can do 1 action (choosing between 4, 5) if he is the first player, or takes his final turn after the first player


    public boolean isFinalFrenzy() {
        return finalFrenzy;
    }

    public boolean isValidFinalFrenzyAction(String nickName, List<String> lS) {
        if(this.gameState == STARTTURN && finalFrenzy) {
            Player p = this.grid.getPlayerObject(nickName);
            if (p.getTurnFinalFrenzy() == 0 && lS.size() == 2) {
                for (String s : lS) {
                    if (s.equals("1") || s.equals("2") || s.equals("3"))
                        return true;
                }
            }
            else return (p.getTurnFinalFrenzy() != 0 && lS.size() == 1 &&
                    (lS.get(0).equals("4") || lS.get(0).equals("5")));
        }
        return false;
    }

    //first action available for players who take their final turn before the first player
    //player can move up to 1 cell, reload if he wants, and then shoot

    public boolean isValidFinalFrenzyAction1(String nickName, int direction, String weaponToUse, List<Integer> lI, List<String> lS, List<Colour> lAInput, List<String> lPInput, List<String> lPColourInput) {
        Player p = this.grid.getPlayerObject(nickName);
        List<AmmoCube> lA = new LinkedList<>();
        if(!lAInput.isEmpty()) {
            for (Colour c : lAInput)
                lA.add(new AmmoCube(c));
            AmmoCube[] cubeArray = new AmmoCube[lA.size()];
            lA.toArray(cubeArray);
            if (!p.checkAmmoCube(cubeArray) && !weaponToUse.equals(""))
                return false;
        }
        List<PowerUpCard> lP = new LinkedList<>();
        if(!lPInput.isEmpty()) {
            for (int i = 0; i < lPInput.size(); i++) {
                if (p.getPowerUpCardObject(lPInput.get(i), Colour.valueOf(lPColourInput.get(i))) == null)
                    return false;
                lP.add(p.getPowerUpCardObject(lPInput.get(i), Colour.valueOf(lPColourInput.get(i))));
            }
        }
        return (finalFrenzy  && this.grid.canMove(p, direction) && this.isValidShootNotAdrenaline(p, weaponToUse, lI, lS, lA, lP) &&
                    direction >= 1 && direction <= 4);

    }

    public synchronized void finalFrenzyAction1(String nickName, int direction, List<String> weaponToReload, String weaponToUse, List<Integer> lI, List<String> lS, List<Colour> lAInput, List<String> lPInput, List<String> lPColourInput) {
        Player p = this.grid.getPlayerObject(nickName);
        List<AmmoCube> lA = new LinkedList<>();
        if(!lAInput.isEmpty()) {
            for (Colour c : lAInput)
                lA.add(new AmmoCube(c));
        }
        List<PowerUpCard> lP = new LinkedList<>();
        if(!lPInput.isEmpty()) {
            for (int i = 0; i < lPInput.size(); i++) {
                lP.add(p.getPowerUpCardObject(lPInput.get(i), Colour.valueOf(lPColourInput.get(i))));
            }
        }
        if (direction != 0)
            this.grid.move(p, direction);
        if (!weaponToReload.isEmpty()) {
            for (String weapon : weaponToReload)
                this.reloadFrenzy(p, weapon);
        }
        this.shootNotAdrenaline(p, weaponToUse, lI, lS, lA, lP);
        this.gameState = ENDTURN;
    }


    //second action available for players who take their final turn before the first player
    //player can move up to 4 cells

    public boolean isValidFinalFrenzyAction2(String nickName, List<Integer> directions) {
        Player p = this.grid.getPlayerObject(nickName);
        for(Integer i : directions) {
            if(i < 0 || i > 4)
                return false;
        }
        return(finalFrenzy && directions.size() <= 4 && this.grid.canGhostMove(p, directions));
    }

    public synchronized void finalFrenzyAction2(String nickName, List<Integer> directions) {
        Player p = this.grid.getPlayerObject(nickName);
        for(int i : directions)
            this.grid.move(p, i);
        this.gameState = ENDTURN;
    }

    //third action available for players who take their final turn before the first player
    //player can move up to 2 cells and grab something there

    public boolean isValidFinalFrenzyAction3(String nickName, List<Integer> directions, String wCardInput, String wSlotInput, List<Colour> lAInput, List<String> lPInput, List<String> lPColourInput) {
        Player p = this.grid.getPlayerObject(nickName);
        for(Integer i : directions) {
            if(i < 0 || i > 4)
                return false;
        }
        return(finalFrenzy && directions.size() <= 2 && this.grid.canGhostMove(p, directions) && isValidFrenzyGrab(nickName, wCardInput, wSlotInput, lAInput, lPInput, lPColourInput));
    }

    public synchronized void finalFrenzyAction3(String nickName, List<Integer> directions, String wCardInput, List<Colour> lAInput, List<String> lPInput, List<String> lPColourInput) {
        Player p = this.grid.getPlayerObject(nickName);
        WeaponCard wCard = this.grid.getWeaponCardObject(wCardInput);
        List<AmmoCube> lA = new LinkedList<>();
        if(!lAInput.isEmpty()) {
            for (Colour c : lAInput)
                lA.add(new AmmoCube(c));
        }
        List<PowerUpCard> lP = new LinkedList<>();
        if(!lPInput.isEmpty()) {
            for (int i = 0; i < lPInput.size(); i++) {
                lP.add(p.getPowerUpCardObject(lPInput.get(i), Colour.valueOf(lPColourInput.get(i))));
            }
        }
        for(int i : directions)
            this.grid.move(p, i);
        this.frenzyGrab(p, wCard, lA, lP);
        this.gameState = ENDTURN;
    }


    //first action available for player who are the first player, or take their final turn after the first player
    //player can move up to two cells, reload if he wants, and then shoot

    public boolean isValidFinalFrenzyAction4(String nickName, List<Integer> directions, String weaponToUse, List<Integer> lI, List<String> lS, List<Colour> lAInput, List<String> lPInput, List<String> lPColourInput) {
        Player p = this.grid.getPlayerObject(nickName);
        List<AmmoCube> lA = new LinkedList<>();
        if(!lAInput.isEmpty()) {
            for (Colour c : lAInput)
                lA.add(new AmmoCube(c));
            AmmoCube[] cubeArray = new AmmoCube[lA.size()];
            lA.toArray(cubeArray);
            if (!p.checkAmmoCube(cubeArray) && !weaponToUse.equals(""))
                return false;
        }
        List<PowerUpCard> lP = new LinkedList<>();
        if(!lPInput.isEmpty()) {
            for (int i = 0; i < lPInput.size(); i++) {
                if (p.getPowerUpCardObject(lPInput.get(i), Colour.valueOf(lPColourInput.get(i))) == null)
                    return false;
                lP.add(p.getPowerUpCardObject(lPInput.get(i), Colour.valueOf(lPColourInput.get(i))));
            }
        }
        for(Integer i : directions) {
            if(i < 0 || i > 4)
                return false;
        }
        return (finalFrenzy && directions.size() <= 2 && this.grid.canGhostMove(p, directions) && this.isValidShootNotAdrenaline(p, weaponToUse, lI, lS, lA, lP));
    }

    public synchronized void finalFrenzyAction4(String nickName, List<Integer> directions, List<String> weaponToReload, String weaponToUse, List<Integer> lI, List<String> lS, List<Colour> lAInput, List<String> lPInput, List<String> lPColourInput) {
        Player p = this.grid.getPlayerObject(nickName);
        List<AmmoCube> lA = new LinkedList<>();
        if(!lAInput.isEmpty()) {
            for (Colour c : lAInput)
                lA.add(new AmmoCube(c));
        }
        List<PowerUpCard> lP = new LinkedList<>();
        if(!lPInput.isEmpty()) {
            for (int i = 0; i < lPInput.size(); i++) {
                lP.add(p.getPowerUpCardObject(lPInput.get(i), Colour.valueOf(lPColourInput.get(i))));
            }
        }
        for(Integer i : directions)
            this.grid.move(p, i);
        if(!weaponToReload.isEmpty()) {
            for(String weapon : weaponToReload)
                this.reloadFrenzy(p, weapon);
        }
        this.shootNotAdrenaline(p, weaponToUse, lI, lS, lA, lP);
        this.gameState = ENDTURN;
    }

    //second action available for player who are the first player, or take their final turn after the first player
    //player can move up to three cells and grab something there

    public boolean isValidFinalFrenzyAction5(String nickName, List<Integer> directions, String wCardInput, String wSlotInput, List<Colour> lAInput, List<String> lPInput, List<String> lPColourInput) {
        Player p = this.grid.getPlayerObject(nickName);
        for(Integer i : directions) {
            if(i < 0 || i > 4)
                return false;
        }
        return(finalFrenzy && directions.size() <= 3 && this.grid.canGhostMove(p, directions) && isValidFrenzyGrab(nickName, wCardInput, wSlotInput, lAInput, lPInput, lPColourInput));
    }

    public synchronized void finalFrenzyAction5(String nickName, List<Integer> directions, String wCardInput, List<Colour> lAInput, List<String> lPInput, List<String> lPColourInput) {
        Player p = this.grid.getPlayerObject(nickName);
        WeaponCard wCard = this.grid.getWeaponCardObject(wCardInput);
        List<AmmoCube> lA = new LinkedList<>();
        if(!lAInput.isEmpty()) {
            for (Colour c : lAInput)
                lA.add(new AmmoCube(c));
        }
        List<PowerUpCard> lP = new LinkedList<>();
        if(!lPInput.isEmpty()) {
            for (int i = 0; i < lPInput.size(); i++) {
                lP.add(p.getPowerUpCardObject(lPInput.get(i), Colour.valueOf(lPColourInput.get(i))));
            }
        }
        for(Integer i : directions)
            this.grid.move(p, i);
        this.frenzyGrab(p, wCard, lA, lP);
        this.gameState = ENDTURN;
    }


    //useful methods for frenzy actions

    private void reloadFrenzy(Player p, String s) {
        if(p.checkAmmoCube(p.getWeaponCardObject(s).getReloadCost())){
            p.getWeaponCardObject(s).reload();
            p.removeArrayAC(p.getWeaponCardObject(s).getReloadCost());
        }
    }

    private boolean isValidFrenzyGrab(String nickName, String wCardInput, String wSlotInput, List<Colour> lAInput, List<String> lPInput, List<String> lPColourInput) {
        if(finalFrenzy) {
        Player p = this.grid.getPlayerObject(nickName);
        WeaponCard wCard = this.grid.getWeaponCardObject(wCardInput);
        List<AmmoCube> lA= new LinkedList<>();
        if(!lAInput.isEmpty()) {
            for (Colour c : lAInput)
                lA.add(new AmmoCube(c));
            AmmoCube[] cubeArray = new AmmoCube[lA.size()];
            lA.toArray(cubeArray);
            if (!p.checkAmmoCube(cubeArray) && wCard != null)
                return false;
        }
        List<PowerUpCard> lP= new LinkedList<>();
        if(!lPInput.isEmpty()) {
            for (int i = 0; i < lPInput.size(); i++) {
                if (p.getPowerUpCardObject(lPInput.get(i), Colour.valueOf(lPColourInput.get(i))) == null)
                    return false;
                lP.add(p.getPowerUpCardObject(lPInput.get(i), Colour.valueOf(lPColourInput.get(i))));
            }
        }
        if(wCardInput.equals("") || (((!wCardInput.equals("") && wCard != null) && (wSlotInput.equals("1") &&p.getCell().getP().getX() == 0 &&p.getCell().getP().getY() == 2)) ||
                (wSlotInput.equals("2") && p.getCell().getP().getX() == 2 && p.getCell().getP().getY() == 3) ||
                (wSlotInput.equals("3") && p.getCell().getP().getX() == 1 && p.getCell().getP().getY() == 0)))
                    return true;
        }
        return false;
    }

    private void frenzyGrab(Player p, WeaponCard wCard, List<AmmoCube> lA, List<PowerUpCard> lP) {
        if(p.getCell().getStatus() == 0)
            giveWhatIsOnAmmoCard(p, p.getCell().getA());
        else if((p.getCell().getStatus() == 1) && wCard != null &&
                (canPay(wCard, choosePayment(lA, lP)))) {
            p.payCard(lA, lP);
            p.getwC().add(wCard);
            p.getwC().get(p.getwC().size() - 1).reload();
        }
        if(p.getwC().size() > 3)
            this.discard = true;                    //View saved the Weapon Slot
    }


    public synchronized void finalFrenzyTurnScoring() {
        int c = 0;
        for(Player p : this.grid.whoIsDead()) {
            this.deadList.add(p.getNickName());
            if(!p.getpB().getDamages().scoreBoard().isEmpty())
                this.grid.scoringByColour(p.getpB().getDamages().getColourPosition(0), 2);
            if(p.getpB().getDamages().scoreBoard().size() >= 2)
                this.grid.scoringByColour(p.getpB().getDamages().getColourPosition(1), 1);
            if(p.getpB().getDamages().scoreBoard().size() >= 3)
                this.grid.scoringByColour(p.getpB().getDamages().getColourPosition(2), 1);
            if(p.getpB().getDamages().scoreBoard().size() >= 4)
                this.grid.scoringByColour(p.getpB().getDamages().getColourPosition(3), 1);
            p.getpB().getDamages().cleanL();
            c++;
            if(c == 2)
                this.grid.scoringByColour(p.getpB().getDamages().getDamageTr()[10].getC(),1);        //Double Kill
            p.changeCell(null);
            p.getpB().getDamages().clean();
        }
        this.gameState = STARTTURN;
    }

    public synchronized void endTurnFinalFrenzy() {
        this.gameState = ENDALLTURN;
    }

    public synchronized void finalScoring() {
        if (this.gameState == ENDALLTURN) {
            for (Player p : this.grid.getPlayers()) {
                if (p.getpB().getDamages().getDamageTr()[0] == null) {
                    if(!p.getpB().getDamages().scoreBoard().isEmpty())
                        this.grid.scoringByColour(p.getpB().getDamages().getColourPosition(0), 2);
                    if(p.getpB().getDamages().scoreBoard().size() >= 2)
                        this.grid.scoringByColour(p.getpB().getDamages().getColourPosition(1), 1);
                    if(p.getpB().getDamages().scoreBoard().size() >= 3)
                        this.grid.scoringByColour(p.getpB().getDamages().getColourPosition(2), 1);
                    if(p.getpB().getDamages().scoreBoard().size() >= 4)
                        this.grid.scoringByColour(p.getpB().getDamages().getColourPosition(3), 1);
                    p.getpB().getDamages().cleanL();
                }
            }
            if(!this.grid.getBoard().getK().scoreBoard().isEmpty())
                this.grid.scoringByColour(this.grid.getBoard().getK().getColourPosition(0), 8);
            if(this.grid.getBoard().getK().scoreBoard().size() >= 2)
                this.grid.scoringByColour(this.grid.getBoard().getK().getColourPosition(1), 6);
            if(this.grid.getBoard().getK().scoreBoard().size() >= 3)
                this.grid.scoringByColour(this.grid.getBoard().getK().getColourPosition(2), 4);
            if(this.grid.getBoard().getK().scoreBoard().size() >= 4)
                this.grid.scoringByColour(this.grid.getBoard().getK().getColourPosition(3), 2);
            if(this.grid.getBoard().getK().scoreBoard().size() >= 5)
                this.grid.scoringByColour(this.grid.getBoard().getK().getColourPosition(4), 1);
            if(this.grid.getBoard().getK().scoreBoard().size() >= 6)
                this.grid.scoringByColour(this.grid.getBoard().getK().getColourPosition(5), 1);
            this.grid.getBoard().getK().cleanL();

            this.gameState = ENDGAME;
        }
    }

}