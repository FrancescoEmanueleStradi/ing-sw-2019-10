package model;

import model.board.*;
import model.decks.AmmoDeck;
import model.decks.PowerUpDeck;
import model.decks.WeaponDeck;
import model.cards.*;
import model.player.DamageToken;
import model.player.Player;

import java.util.*;
import java.util.stream.Collectors;

import static java.lang.Math.abs;

public class Grid {
    private ArrayList<Player> players;
    private Board board;
    private WeaponDeck weaponDeck;
    private PowerUpDeck powerUpDeck;
    private AmmoDeck ammoDeck;
    private List<AmmoCard> ammoDiscardPile;
    private List<PowerUpCard> powerUpDiscardPile;

    public Grid()  {
        this.players = new ArrayList<>();
        this.weaponDeck = new WeaponDeck();
        this.weaponDeck.startingShuffle();
        this.powerUpDeck = new PowerUpDeck();
        this.powerUpDeck.startingShuffle();
        this.ammoDeck = new AmmoDeck();
        this.ammoDeck.startingShuffle();
        this.ammoDiscardPile = new LinkedList<>();
        this.powerUpDiscardPile = new LinkedList<>();
    }

    public void setType(int aType){
        this.board = new Board(aType, pickWeaponCard(), pickWeaponCard(), pickWeaponCard(), pickWeaponCard(), pickWeaponCard(), pickWeaponCard(), pickWeaponCard(), pickWeaponCard(), pickWeaponCard());
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

    public List<String> getPlayersNickName() {
        LinkedList<String> l = new LinkedList<>();
        for(Player p: this.getPlayers())
            l.add(p.getNickName());
        return l;
    }

    public List<Colour> getPlayersColour() {
        LinkedList<Colour> l = new LinkedList<>();
        for(Player p: this.getPlayers())
            l.add(p.getC());
        return l;
    }

    public Player getPlayerObject(String name) {
        for (Player p : players ){
            if(p.getNickName().equals(name))
                return p;
        }
        return null;
    }

    public int getNumPlayers() {
        return this.players.size();
    }

    public void scoringByColour(Colour c, int n) {
        for(Player p : this.players) {
            if(c.equals(p.getC()))
                p.addScore(n);
        }
    }

    public Board getBoard() {
        return board;
    }

    public void damage(Player p, Player p1, int numDamage) { //p attacks, p1 is attacked
        p1.getpB().getDamages().addDamage(numDamage, p.getC());
    }

    public void clean(Player p) {
        p.getpB().getDamages().clean();
    }

    public void addMark(Player p1, Player p2) {
        p2.getpB().addMark(new DamageToken(p1.getC()));
    }

    public void removeMarkAndAdd(Player p1, Player p2 ) {
        long x = p1.getpB().getMarks().stream().filter(a -> a.getC() == p2.getC()).count();
        int y = (int)x;
        this.damage(p2, p1, y);
        p1.getpB().clearMark(p2.getC());

    }

    public int distance(Player p1, Player p2) {
        return abs(p1.getCell().getP().getX()-p2.getCell().getP().getX()) + abs(p1.getCell().getP().getY()-p2.getCell().getP().getY());
    }

    public int distance(Player p, Position pos) {
        return abs(p.getCell().getP().getX()-pos.getX()) + abs(p.getCell().getP().getY()-pos.getY());
    }

    public int distance(Position pos1, Position pos2) {
        return abs(pos1.getX()-pos2.getX()) + abs(pos1.getY()-pos2.getY());
    }

    public Cell whereAmI(Player p) {
        return p.getCell();
    }

    public void moveInMyCell(Player p, Player p2) {
        p2.setCell(p.getCell());
    }

    public void move(Player p, int d) {                                //1 up, 2 right, 3 down, 4 left
        int n = 0;

        for(int i =0; i<p.getCell().getPosWall().length; i++) {
            if (p.getCell().getPosWall()[i] == d) {
                //System.out.println("you can't move");
                n=1;
            }
        }

        if(n==0) {
            if((d==1) && (p.getCell().getP().getX()>0))
                p.changeCell(board.getArena()[p.getCell().getP().getX()-1][p.getCell().getP().getY()]);
            if((d==2) && (p.getCell().getP().getY()<3))
                p.changeCell(board.getArena()[p.getCell().getP().getX()][p.getCell().getP().getY()+1]);
            if((d==3) && (p.getCell().getP().getX()<2))
                p.changeCell(board.getArena()[p.getCell().getP().getX()+1][p.getCell().getP().getY()]);
            if((d==4) && (p.getCell().getP().getY()>0))
                p.changeCell(board.getArena()[p.getCell().getP().getX()][p.getCell().getP().getY()-1]);
        }
    }

    public boolean canMove(Player p, int d) {                                //1 up, 2 right, 3 down, 4 left

        for(int i =0; i<p.getCell().getPosWall().length; i++) {
            if (p.getCell().getPosWall()[i] == d) {
                return false;
            }
        }
        return true;
    }

    public void move(Player p, Position pt) {
        p.changeCell(board.getArena()[pt.getX()][pt.getY()]);           //TODO control where we used
    }

    public void move(Player p, int x, int y) {
        p.changeCell(board.getArena()[x][y]);
    }

    public boolean isThereAWall (Player p, Position pT){            //1 if there is a wall
        int direction = 0;
        if(p.getCell().getP().getX() > pT.getX())
            direction = 1;
        if(p.getCell().getP().getX() < pT.getX())
            direction = 3;
        if(p.getCell().getP().getY() > pT.getY())
            direction = 4;
        if(p.getCell().getP().getY() < pT.getY())
            direction = 2;
        return Arrays.asList(this.board.getArena()[p.getCell().getP().getX()][p.getCell().getP().getY()].getPosWall()).contains(direction);
    }


    
    public void collectCard(Player p) {
          if(p.getCell().getA().ispC())
              pickPowerUpCard(p);
          for(int i = 0; i < p.getCell().getA().getaC().size(); i++)
              p.addNewAC(p.getCell().getA().getaC().get(i));
    }

    public boolean isInTheRoom(Player p, Player p2) {
        return (p.getCell().getC().equals(p2.getCell().getC()));
    }

    public boolean isInTheRoom(Player p, Colour c) {
        return (p.getCell().getC().equals(c));
    }

    public boolean isInViewZone(Player p, Player p2) {
        boolean b = false;
        for(int i=0; i<p.getCell().getPosDoor().length; i++) {
            if(!b) {
                if (p.getCell().getPosDoor()[i] == 1 && p2.getCell().getP().getX() - 1 >= 0 && p2.getCell().getC().equals(this.board.getArena()[p.getCell().getP().getX() - 1][p.getCell().getP().getY()].getC()))
                    b = true;
                if (p.getCell().getPosDoor()[i] == 2 && p2.getCell().getP().getY() + 1 <= 3 && p2.getCell().getC().equals(this.board.getArena()[p.getCell().getP().getX()][p.getCell().getP().getY() + 1].getC()))
                    b = true;
                if (p.getCell().getPosDoor()[i] == 3 && p2.getCell().getP().getX() + 1 <= 2 && p2.getCell().getC().equals(this.board.getArena()[p.getCell().getP().getX() + 1][p.getCell().getP().getY()].getC()))
                    b = true;
                if (p.getCell().getPosDoor()[i] == 4 && p2.getCell().getP().getY() - 1 >= 0 && p2.getCell().getC().equals(this.board.getArena()[p.getCell().getP().getX()][p.getCell().getP().getY() - 1].getC()))
                    b = true;
            }
        }
        return (isInTheRoom(p, p2) || ((p.getCell().getPosDoor()!=null) && b));
    }

    public boolean isInViewZone(Player p, Position pos){
        if(this.board.getArena()[pos.getX()][pos.getY()].getC().equals(p.getCell().getC()))
            return true;
        for(int i=0; i<p.getCell().getPosDoor().length; i++){
            if (p.getCell().getPosDoor()[i] == 1 && p.getCell().getP().getX() - 1 >= 0) {
                if (this.board.getArena()[p.getCell().getP().getX() - 1][p.getCell().getP().getY()].getC().equals(this.board.getArena()[pos.getX()][pos.getY()].getC()))
                    return true;
            }
            else if (p.getCell().getPosDoor()[i] == 2 && p.getCell().getP().getY() + 1 <= 3) {
                if (this.board.getArena()[p.getCell().getP().getX()][p.getCell().getP().getY() + 1].getC().equals(this.board.getArena()[pos.getX()][pos.getY()].getC()))
                    return true;
            }
            else if (p.getCell().getPosDoor()[i] == 3 && p.getCell().getP().getX() + 1 <= 2) {
                if (this.board.getArena()[p.getCell().getP().getX() + 1][p.getCell().getP().getY()].getC().equals(this.board.getArena()[pos.getX()][pos.getY()].getC()))
                    return true;
            }
            else if (p.getCell().getPosDoor()[i] == 4 && p.getCell().getP().getY() - 1 >= 0) {
                if (this.board.getArena()[p.getCell().getP().getX()][p.getCell().getP().getY() - 1].getC().equals(this.board.getArena()[pos.getX()][pos.getY()].getC()))
                    return true;
            }
        }
        return false;
    }

    public List<Colour> colourOfOtherViewZone(Player p){
        List<Colour> l = new LinkedList<>();
        for(int i=0; i<p.getCell().getPosDoor().length; i++){
            if (p.getCell().getPosDoor()[i] == 1)
                l.add(this.board.getArena()[p.getCell().getP().getX() - 1][p.getCell().getP().getY()].getC());
            if (p.getCell().getPosDoor()[i] == 2)
                l.add(this.board.getArena()[p.getCell().getP().getX()][p.getCell().getP().getY() + 1].getC());
            if (p.getCell().getPosDoor()[i] == 3)
                l.add(this.board.getArena()[p.getCell().getP().getX() + 1][p.getCell().getP().getY()].getC());
            if (p.getCell().getPosDoor()[i] == 4)
                l.add(this.board.getArena()[p.getCell().getP().getX()][p.getCell().getP().getY() - 1].getC());
        }
        return l.stream().distinct().collect(Collectors.toList());
    }

    public List<Player> whoIsInTheRoom(Player p) {
        ArrayList<Player> pRoom = new ArrayList<>();
        for(Player px : players) {
            if(isInTheRoom(p, px) && p!=px)         //Does it work?
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

    public WeaponCard getWeaponCardObject(String wCardName){
        if(board.getW1().getCard1() != null && board.getW1().getCard1().getCardName().equals(wCardName))
            return board.getW1().getCard1();
        if(board.getW1().getCard2() != null && board.getW1().getCard2().getCardName().equals(wCardName))
            return board.getW1().getCard2();
        if(board.getW1().getCard3() != null && board.getW1().getCard3().getCardName().equals(wCardName))
            return board.getW1().getCard3();
        if(board.getW2().getCard1() != null && board.getW2().getCard1().getCardName().equals(wCardName))
            return board.getW2().getCard1();
        if(board.getW2().getCard2() != null && board.getW2().getCard2().getCardName().equals(wCardName))
            return board.getW2().getCard2();
        if(board.getW2().getCard3() != null && board.getW2().getCard3().getCardName().equals(wCardName))
            return board.getW2().getCard3();
        if(board.getW3().getCard1() != null && board.getW3().getCard1().getCardName().equals(wCardName))
            return board.getW3().getCard1();
        if(board.getW3().getCard2() != null && board.getW3().getCard2().getCardName().equals(wCardName))
            return board.getW3().getCard2();
        if(board.getW3().getCard3() != null && board.getW3().getCard3().getCardName().equals(wCardName))
            return board.getW3().getCard3();
        return null;
    }

    public void pickWeaponCard(Player p) {
            p.addWeaponCard(this.weaponDeck.getTopOfDeck());
    }

    public WeaponCard pickWeaponCard() {
        return this.weaponDeck.getTopOfDeck();
    }

    public void replaceWeaponCard() {
       if(this.board.getW1().getCard1() == null)
           this.board.getW1().setCard1(pickWeaponCard());
        if(this.board.getW1().getCard2() == null)
            this.board.getW1().setCard2(pickWeaponCard());
        if(this.board.getW1().getCard3() == null)
            this.board.getW1().setCard3(pickWeaponCard());
        if(this.board.getW2().getCard1() == null)
            this.board.getW2().setCard1(pickWeaponCard());
        if(this.board.getW2().getCard2() == null)
            this.board.getW2().setCard2(pickWeaponCard());
        if(this.board.getW2().getCard3() == null)
            this.board.getW2().setCard3(pickWeaponCard());
        if(this.board.getW3().getCard1() == null)
            this.board.getW3().setCard1(pickWeaponCard());
        if(this.board.getW3().getCard2() == null)
            this.board.getW3().setCard2(pickWeaponCard());
        if(this.board.getW3().getCard3() == null)
            this.board.getW3().setCard3(pickWeaponCard());
    }


    public void pickPowerUpCard(Player p) {     //use this every time a player wants a PowerUpCard, except for the final "pick and discard" when he is dead
        if(p.getpC().size() < 3) {
            if(powerUpDeck.getDeck().isEmpty()) {
                Collections.shuffle(powerUpDiscardPile);
                powerUpDeck.getDeck().addAll(powerUpDiscardPile);
                powerUpDiscardPile.clear();
            }
            p.addPowerUpCard(this.powerUpDeck.getTopOfDeck());
        }
    }

    public PowerUpCard pickPowerUpCard() {      //use this for the final "pick and discard" when the player is dead
        if(powerUpDeck.getDeck().isEmpty()) {
            Collections.shuffle(powerUpDiscardPile);
            powerUpDeck.getDeck().addAll(powerUpDiscardPile);
            powerUpDiscardPile.clear();
        }
        return this.powerUpDeck.getTopOfDeck();
    }

    public List<PowerUpCard> getPowerUpDiscardPile() {
        return powerUpDiscardPile;
    }

    public AmmoCard pickAmmoCard() {
        if(ammoDeck.getDeck().isEmpty()) {
            Collections.shuffle(ammoDiscardPile);
            ammoDeck.getDeck().addAll(ammoDiscardPile);
            ammoDiscardPile.clear();
        }
        return this.ammoDeck.getTopOfDeck();
    }

    public void setUpAmmoCard() {
        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 4; j++) {
               this.board.getArena()[i][j].setA(this.pickAmmoCard());
            }
        }
    }

    public void replaceAmmoCard() {
        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 4; j++) {
                if(this.board.getArena()[i][j].getA() == null)              //check that when we pick up the card from the cell it will be null
                    this.board.getArena()[i][j].setA(this.pickAmmoCard());
            }
        }
    }

    public void changeAmmoCard(Position p) {
        this.board.getArena()[p.getX()][p.getY()].setA(this.pickAmmoCard());
    }


    public List<AmmoCard> getAmmoDiscardPile() {
        return ammoDiscardPile;
    }

    public void discardShuffle() {
        Collections.shuffle(ammoDiscardPile);
    }

    public List<Player> whoIsDead() {
        LinkedList<Player> l = new LinkedList<>();
        for(Player p: this.players) {
            if(p.isDead())
                l.add(p);

        }
        return l;
    }


    //--------------------------------------------------------------------------------------

    public Player ghostMove(Player p, List<Integer> directions) {
        Player ghost = new Player("?gHoSt!", p.getC(), p.isFirstPlayerCard());
        ghost.setCell(p.getCell());

        for(Integer i : directions)
            this.move(ghost, i);

        return ghost;
    }

    public boolean canGhostMove(Player p, List<Integer> directions)  {
        Player ghost = new Player("?gHoSt!", p.getC(), p.isFirstPlayerCard());
        ghost.setCell(p.getCell());
        Position initialPos = ghost.getCell().getP();

        for(Integer i : directions) {
            this.move(ghost, i);
            if(i != 0 && ghost.getCell().getP().equals(initialPos))
                return false;
        }
        return true;
    }
}