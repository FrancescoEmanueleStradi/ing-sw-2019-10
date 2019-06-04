package model;

import model.board.*;
import model.decks.AmmoDeck;
import model.decks.PowerUpDeck;
import model.decks.WeaponDeck;
import model.cards.*;
import model.player.DamageToken;
import model.player.Player;
import network.ServerInterface;

import java.rmi.RemoteException;
import java.util.*;
import java.util.stream.Collectors;

import static java.lang.Math.abs;

public class Grid {

    private int iD;
    private ServerInterface server;
    private ArrayList<Player> players;
    private Board board;
    private WeaponDeck weaponDeck;
    private PowerUpDeck powerUpDeck;
    private AmmoDeck ammoDeck;
    private List<AmmoCard> ammoDiscardPile;
    private List<PowerUpCard> powerUpDiscardPile;

    public Grid(int iD, ServerInterface server)  {
        this.iD = iD;
        this.server = server;
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

    public void setType(int aType) throws RemoteException {
        WeaponSlot ws1 = new WeaponSlot(1, pickWeaponCard(), pickWeaponCard(), pickWeaponCard());
        WeaponSlot ws2 = new WeaponSlot(2, pickWeaponCard(), pickWeaponCard(), pickWeaponCard());
        WeaponSlot ws3 = new WeaponSlot(3, pickWeaponCard(), pickWeaponCard(), pickWeaponCard());
        this.board = new Board(aType, ws1, ws2, ws3);
        server.notifyType(this.iD, aType);
    }

    public void addPlayer(Player p) throws RemoteException{
        this.players.add(p);
        List<String> information = new LinkedList<>();
        information.add(p.getNickName());
        information.add(p.getC().toString());
        server.notifyPlayer(this.iD, information);
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

    public Colour getPlayerColour(String name){
        for(Player p: this.getPlayers()){
            if(p.getNickName().equals(name))
                return p.getC();
        }
        return null;
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

    public Player getPlayerObjectByColour(Colour c) {
        for (Player p : players ){
            if(p.getC().equals(c))
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

    public void damage(Player p, Player p1, int numDamage) throws RemoteException{ //p attacks, p1 is attacked
        removeMarkAndAdd(p1, p);
        p1.getPlayerBoard().getDamage().addDamage(numDamage, p.getC());
        List<String> informationDamage = new LinkedList<>();
        informationDamage.add(p.getNickName());
        informationDamage.add(Integer.toString(numDamage));
        informationDamage.add(p1.getNickName());
        server.notifyDamage(this.iD, informationDamage);
        if(p1.isOverkilled()) {
            p.getPlayerBoard().addMark(new DamageToken(p1.getC()));
            List<String> information = new LinkedList<>();
            information.add(p.getNickName());
            information.add(p1.getNickName());
            server.notifyMark(this.iD, information);
        }
    }

    public void clean(Player p) {
        p.getPlayerBoard().getDamage().clean();
    }

    public void addMark(Player p1, Player p2) throws RemoteException{
        p2.getPlayerBoard().addMark(new DamageToken(p1.getC()));
        List<String> information = new LinkedList<>();
        information.add(p1.getNickName());
        information.add(p2.getNickName());
        server.notifyMark(this.iD, information);
    }

    public void removeMarkAndAdd(Player p1, Player p2) throws RemoteException{
        long x = p1.getPlayerBoard().getMarks().stream().filter(a -> a.getC() == p2.getC()).count();
        if (x > 0) {
            int y = (int) x;
            p1.getPlayerBoard().getDamage().addDamage(y, p2.getC());
            List<String> informationDamage = new LinkedList<>();
            informationDamage.add(p1.getNickName());
            informationDamage.add(Integer.toString(y));
            informationDamage.add(p2.getNickName());
            server.notifyDamage(this.iD, informationDamage);
            p1.getPlayerBoard().clearMark(p2.getC());
        }
    }

    public int distance(Player p1, Player p2) {
        return abs(p1.getCell().getPos().getX()-p2.getCell().getPos().getX()) + abs(p1.getCell().getPos().getY()-p2.getCell().getPos().getY());
    }

    public int distance(Player p, Position pos) {
        return abs(p.getCell().getPos().getX()-pos.getX()) + abs(p.getCell().getPos().getY()-pos.getY());
    }

    public int distance(Position pos1, Position pos2) {
        return abs(pos1.getX()-pos2.getX()) + abs(pos1.getY()-pos2.getY());
    }

    public Cell whereAmI(Player p) {
        return p.getCell();
    }

    public void moveInMyCell(Player p, Player p2) {
        p2.changeCell(p.getCell());
    }

    public void move(Player p, int d) throws RemoteException{                                //1 up, 2 right, 3 down, 4 left
        moveWithoutNotify(p, d);
        List<String> information = new LinkedList<>();
        information.add(p.getNickName());
        information.add(Integer.toString(p.getCell().getPos().getX()));
        information.add(Integer.toString(p.getCell().getPos().getY()));
        server.notifyPosition(this.iD, information);
    }

    public void moveWithoutNotify(Player p, int d) {                                //1 up, 2 right, 3 down, 4 left
        int n = 0;

        for(int i =0; i<p.getCell().getPosWall().length; i++) {
            if (p.getCell().getPosWall()[i] == d) {                     //player can't move
                n=1;
            }
        }

        if(n==0) {
            if((d==1) && (p.getCell().getPos().getX()>0))
                p.changeCell(board.getArena()[p.getCell().getPos().getX()-1][p.getCell().getPos().getY()]);
            else if((d==2) && (p.getCell().getPos().getY()<3))
                p.changeCell(board.getArena()[p.getCell().getPos().getX()][p.getCell().getPos().getY()+1]);
            else if((d==3) && (p.getCell().getPos().getX()<2))
                p.changeCell(board.getArena()[p.getCell().getPos().getX()+1][p.getCell().getPos().getY()]);
            else if((d==4) && (p.getCell().getPos().getY()>0))
                p.changeCell(board.getArena()[p.getCell().getPos().getX()][p.getCell().getPos().getY()-1]);
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


    public void move(Player p, int x, int y) throws RemoteException{
        p.changeCell(board.getArena()[x][y]);
        List<String> information = new LinkedList<>();
        information.add(p.getNickName());
        information.add(Integer.toString(x));
        information.add(Integer.toString(y));
        server.notifyPosition(this.iD, information);
    }

    public boolean isThereAWall (Player p, Position pT) {            //true if there is a wall between p and pT
        int direction = 0;
        if(p.getCell().getPos().getX() > pT.getX())
            direction = 1;
        else if(p.getCell().getPos().getX() < pT.getX())
            direction = 3;
        else if(p.getCell().getPos().getY() > pT.getY())
            direction = 4;
        else if(p.getCell().getPos().getY() < pT.getY())
            direction = 2;
        List<Integer> listPosWalls = new LinkedList<>();        //need to do this because cell.posWalls is int[] and list.contains checks for Integer
        for(int i : this.board.getArena()[p.getCell().getPos().getX()][p.getCell().getPos().getY()].getPosWall())
            listPosWalls.add(i);
        return listPosWalls.contains(direction);
    }

    public boolean isThereAWall (Position pT1, Position pT2) {            //true if there is a wall between pT1 and pT2
        int direction = 0;
        if(pT1.getX() > pT2.getX())
            direction = 1;
        else if(pT1.getX() < pT2.getX())
            direction = 3;
        else if(pT1.getY() > pT2.getY())
            direction = 4;
        else if(pT1.getY() < pT2.getY())
            direction = 2;
        List<Integer> listPosWalls = new LinkedList<>();        //need to do this because cell.posWalls is int[] and list.contains checks for Integer
        for(int i : this.board.getArena()[pT1.getX()][pT1.getY()].getPosWall())
            listPosWalls.add(i);
        return listPosWalls.contains(direction);
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

    public boolean isInViewZone(Player p, Player p2) {
        boolean b = false;
        for(int i=0; i<p.getCell().getPosDoor().length; i++) {
            if ((!b) && ((p.getCell().getPosDoor()[i] == 1 && p.getCell().getPos().getX() - 1 >= 0 && p2.getCell().getC().equals(this.board.getArena()[p.getCell().getPos().getX() - 1][p.getCell().getPos().getY()].getC())) ||
                    (p.getCell().getPosDoor()[i] == 2 && p.getCell().getPos().getY() + 1 <= 3 && p2.getCell().getC().equals(this.board.getArena()[p.getCell().getPos().getX()][p.getCell().getPos().getY() + 1].getC())) ||
                    (p.getCell().getPosDoor()[i] == 3 && p.getCell().getPos().getX() + 1 <= 2 && p2.getCell().getC().equals(this.board.getArena()[p.getCell().getPos().getX() + 1][p.getCell().getPos().getY()].getC())) ||
                    p.getCell().getPosDoor()[i] == 4 && p.getCell().getPos().getY() - 1 >= 0 && p2.getCell().getC().equals(this.board.getArena()[p.getCell().getPos().getX()][p.getCell().getPos().getY() - 1].getC())))
                b = true;
            }
        return (isInTheRoom(p, p2) || ((p.getCell().getPosDoor()!=null) && b));
    }

    public boolean isInViewZone(Player p, Position pos) {
        if(this.board.getArena()[pos.getX()][pos.getY()].getC().equals(p.getCell().getC()))
            return true;
        for(int i=0; i<p.getCell().getPosDoor().length; i++)
            if ((p.getCell().getPosDoor()[i] == 1 && p.getCell().getPos().getX() - 1 >= 0 &&
                    this.board.getArena()[p.getCell().getPos().getX() - 1][p.getCell().getPos().getY()].getC().equals(this.board.getArena()[pos.getX()][pos.getY()].getC())) ||
                    (p.getCell().getPosDoor()[i] == 2 && p.getCell().getPos().getY() + 1 <= 3 &&
                            this.board.getArena()[p.getCell().getPos().getX()][p.getCell().getPos().getY() + 1].getC().equals(this.board.getArena()[pos.getX()][pos.getY()].getC())) ||
                    (p.getCell().getPosDoor()[i] == 3 && p.getCell().getPos().getX() + 1 <= 2 &&
                            this.board.getArena()[p.getCell().getPos().getX() + 1][p.getCell().getPos().getY()].getC().equals(this.board.getArena()[pos.getX()][pos.getY()].getC())) ||
                    (p.getCell().getPosDoor()[i] == 4 && p.getCell().getPos().getY() - 1 >= 0 &&
                            this.board.getArena()[p.getCell().getPos().getX()][p.getCell().getPos().getY() - 1].getC().equals(this.board.getArena()[pos.getX()][pos.getY()].getC())))
                    return true;
        return false;
    }

    //returns a list of the colours of the rooms which are in the view zone of player p,
    //except for the colour of the room player p is in
    public List<Colour> colourOfOtherViewZone(Player p){
        List<Colour> l = new LinkedList<>();
        for(int i=0; i<p.getCell().getPosDoor().length; i++){
            if (p.getCell().getPosDoor()[i] == 1)
                l.add(this.board.getArena()[p.getCell().getPos().getX() - 1][p.getCell().getPos().getY()].getC());
            else if (p.getCell().getPosDoor()[i] == 2)
                l.add(this.board.getArena()[p.getCell().getPos().getX()][p.getCell().getPos().getY() + 1].getC());
            else if (p.getCell().getPosDoor()[i] == 3)
                l.add(this.board.getArena()[p.getCell().getPos().getX() + 1][p.getCell().getPos().getY()].getC());
            else if (p.getCell().getPosDoor()[i] == 4)
                l.add(this.board.getArena()[p.getCell().getPos().getX()][p.getCell().getPos().getY() - 1].getC());
        }
        return l.stream().distinct().collect(Collectors.toList());
    }

    public List<Player> whoIsInTheRoom(Player p) {
        ArrayList<Player> pRoom = new ArrayList<>();
        for(Player px : players) {
            if(isInTheRoom(p, px) && p!=px)
                pRoom.add(px);
        }
        return pRoom;
    }

    public List<Player> whoIsInTheViewZone(Player p) {
        ArrayList<Player> pViewZone = new ArrayList<>();
        for (Player px : players) {
            if (isInViewZone(p, px) && p != px)
                pViewZone.add(px);
        }
        return pViewZone;
    }

    public WeaponCard getWeaponCardObject(String wCardName){
        if(board.getW1().getCard1() != null && board.getW1().getCard1().getCardName().equals(wCardName))
            return board.getW1().getCard1();
        else if(board.getW1().getCard2() != null && board.getW1().getCard2().getCardName().equals(wCardName))
            return board.getW1().getCard2();
        else if(board.getW1().getCard3() != null && board.getW1().getCard3().getCardName().equals(wCardName))
            return board.getW1().getCard3();
        else if(board.getW2().getCard1() != null && board.getW2().getCard1().getCardName().equals(wCardName))
            return board.getW2().getCard1();
        else if(board.getW2().getCard2() != null && board.getW2().getCard2().getCardName().equals(wCardName))
            return board.getW2().getCard2();
        else if(board.getW2().getCard3() != null && board.getW2().getCard3().getCardName().equals(wCardName))
            return board.getW2().getCard3();
        else if(board.getW3().getCard1() != null && board.getW3().getCard1().getCardName().equals(wCardName))
            return board.getW3().getCard1();
        else if(board.getW3().getCard2() != null && board.getW3().getCard2().getCardName().equals(wCardName))
            return board.getW3().getCard2();
        else if(board.getW3().getCard3() != null && board.getW3().getCard3().getCardName().equals(wCardName))
            return board.getW3().getCard3();
        return null;
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
        if(p.getPowerUpCards().size() < 3) {
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
                if(this.board.getArena()[i][j].getStatus() == 0 && this.board.getArena()[i][j].getA() == null)              //check that when we pick up the card from the cell it will be null
                    this.board.getArena()[i][j].setA(this.pickAmmoCard());
            }
        }
    }

    public List<AmmoCard> getAmmoDiscardPile() {
        return ammoDiscardPile;
    }

    public PowerUpDeck getPowerUpDeck() {
        return powerUpDeck;
    }

    public List<Player> whoIsDead() {
        LinkedList<Player> l = new LinkedList<>();
        for(Player p: this.players) {
            if(p.isDead())
                l.add(p);

        }
        return l;
    }

    public Player ghostMove(Player p, List<Integer> directions) {
        Player ghost = new Player("?gHoSt!", p.getC(), p.isFirstPlayerCard());
        ghost.changeCell(p.getCell());

        for(Integer i : directions)
            this.moveWithoutNotify(ghost, i);

        return ghost;
    }

    public boolean canGhostMove(Player p, List<Integer> directions) {
        Player ghost = new Player("?gHoSt!", p.getC(), p.isFirstPlayerCard());
        ghost.changeCell(p.getCell());
        Position initialPos = ghost.getCell().getPos();

        for(Integer i : directions) {
            this.moveWithoutNotify(ghost, i);
            if(i != 0 && ghost.getCell().getPos().equals(initialPos))
                return false;
            else
                initialPos = ghost.getCell().getPos();
        }
        return true;
    }

    public WeaponSlot getWeaponSlotObject(String s) {
        if(s.equals("1"))
            return this.board.getW1();
        if(s.equals("2"))
            return this.board.getW2();
        if(s.equals("3"))
            return this.board.getW3();
        return null;
    }
}