package view;

import controller.Game;
import controller.GameState;
import model.Colour;

import java.rmi.*;
import java.rmi.registry.*;
import java.rmi.server.*;
import java.util.LinkedList;
import java.util.List;


public class Server extends UnicastRemoteObject implements ServerInterface {

    private static List<Game> games;
    private static List<Integer> types;
    private static List<Boolean> canStartList;
    private static List<List<Connection>> connections;
    private static List<LinkedList<Integer>>  suspendedIdentifier;
    private static List<LinkedList<String>> suspendedName;
    private static int frenzyTurn = 0;

    public Server() throws RemoteException {
        super();
    }

    public String echo(String input) throws RemoteException {
        return "From server: " + input;
    }

    public static void main(String[] args) throws RemoteException {
        System.out.println("Generating server...");
        Server centralServer = new Server();

        System.out.println("Binding server to registry...");
        Registry registry = LocateRegistry.createRegistry(5099);
        //registry = LocateRegistry.getRegistry();
        registry.rebind("central_server", centralServer);

        System.out.println("Client may now invoke methods");
        games = new LinkedList<>();
        types = new LinkedList<>();
        connections = new LinkedList<>();
        canStartList = new LinkedList<>();
        suspendedIdentifier = new LinkedList<>();
        suspendedName = new LinkedList<>();

    }


    public synchronized int getGames() throws RemoteException {
        return games.size();
    }

    public synchronized void setGame(int numGame) throws RemoteException {
        if (games.isEmpty() || games.size() <= numGame){
            games.add(numGame, new Game(numGame, this));
            //views.add(numGame, new LinkedList<>());
            connections.add(numGame, new LinkedList<>());
            //playersTakingTheirTurn.add(numGame, 1);
            //players.add(numGame, 0);
            suspendedName.add(numGame, new LinkedList<>());
            suspendedIdentifier.add(numGame, new LinkedList<>());
            canStartList.add(numGame, false);
            connections.get(numGame).add(new Connection());             //this index should be the identifier - 1
            connections.get(numGame).get(connections.get(numGame).size()-1).setMyTurn(true);
        }
        else
            connections.get(numGame).add(new Connection());
    }

    public synchronized void setView(int game, int identifier, View view) throws RemoteException{
        connections.get(game).get(identifier-1).setView(view);
    }

    public synchronized void setNickName(int game, int identifier, String nickName) throws RemoteException{
        connections.get(game).get(identifier-1).setNickName(nickName);
    }

    public synchronized boolean canStart(int game) throws RemoteException{
        return canStartList.get(game);
    }

    public synchronized boolean tooMany(int game) throws RemoteException{               //Even if a Player is suspended, the total number of player can't be more than five
        return (!connections.isEmpty() && (games.size() > game) &&  connections.get(game).size() == 5);
    }

    public synchronized boolean stopGame(int game) throws RemoteException{
        if(suspendedIdentifier.isEmpty())
            return (connections.get(game).size() < 3);
        else
            return (connections.get(game).size()-suspendedIdentifier.get(game).size() < 3);
    }

    public synchronized int receiveIdentifier(int game) throws RemoteException {
        connections.get(game).get(connections.get(game).size()-1).setIdentifier(connections.get(game).size());
        return connections.get(game).size();
    }

    public synchronized void mergeGroup(int game) throws RemoteException, InterruptedException {
        if (connections.get(game).size() == 5) {
            canStartList.add(game, true);
            notifyAll();
        }
        if(connections.get(game).size() == 3) {
            wait(30000);
            while(connections.get(game).size() < 3)
                wait(30000);
            canStartList.add(game, true);
        }
    }

    /*public void setCli(int game, int identifier) throws RemoteException{
        views.get(game).add(identifier, new Cli());
        views.get(game).get(identifier).setGame(games.get(identifier));
    }

    public void setGui(int game, int identifier) throws RemoteException{
        views.get(game).add(identifier, new Gui());
        views.get(game).get(identifier).setGame(games.get(identifier));
    }*/

    public synchronized boolean isMyTurn(int game, int identifier) throws RemoteException {
        if(suspendedIdentifier.get(game).isEmpty())
            return(connections.get(game).get(identifier-1).isMyTurn()  && (connections.get(game).size()-1 != frenzyTurn));
        else
            return(connections.get(game).get(identifier-1).isMyTurn() && (connections.get(game).size()-1-suspendedIdentifier.get(game).size()-1 != frenzyTurn));
    }

    public synchronized boolean isNotFinalFrenzy(int game) throws RemoteException {
        boolean n = !games.get(game).isFinalFrenzy();
        if(!n)
            frenzyTurn++;
        return n;
    }

    public synchronized void setFinalTurn(int game, int identifier, String nickName){           //We assert identifier 1 has the first player card
        if(identifier-frenzyTurn > 0)
            games.get(game).changeTurnFinalFrenzy(nickName, 0);
        if(identifier-frenzyTurn < 0 )
            games.get(game).changeTurnFinalFrenzy(nickName, 2);
    }

    public synchronized boolean gameIsFinished(int game) throws RemoteException {
        //if(players.get(game)-suspendedIdentifier.get(game).size() < 3)
            //return true;
        //return games.get(game).getGameState() == GameState.ENDALLTURN;

        return (connections.get(game).size()-suspendedIdentifier.get(game).size() == frenzyTurn ||
                connections.get(game).size()-suspendedIdentifier.get(game).size() < 3);
        /*if(suspendedIdentifier.get(game).isEmpty()) {
            if(connections.get(game).size() == frenzyTurn){
                for(Connection c : connections.get(game)){
                    c.getView().endFinalFrenzy();
                    c.getView().finalScoring();
                }
            }
            return (connections.get(game).size() == frenzyTurn || connections.get(game).size() < 3);
        }
        else
            if(connections.get(game).size()-suspendedIdentifier.get(game).size() == frenzyTurn){
                for(Connection c : connections.get(game)){
                    if(!suspendedIdentifier.get(game).contains(c.getIdentifier())) {
                        c.getView().endFinalFrenzy();
                        c.getView().finalScoring();
                    }
                }
            }
            return(connections.get(game).size()-suspendedIdentifier.get(game).size() == frenzyTurn || connections.get(game).size()-suspendedIdentifier.get(game).size() < 3);
    */
    }

    private int getPlayerTurn(int game) throws RemoteException{
        for(Connection c : connections.get(game)){
            if(c.isMyTurn())
                return c.getIdentifier();
        }
        return -1;
    }

    public synchronized void finishTurn(int game) throws RemoteException {
        if (connections.get(game).size() - suspendedIdentifier.get(game).size() >= 3) {
            do {
                if (games.get(game).getPlayers().size() > getPlayerTurn(game)) {
                    connections.get(game).get(getPlayerTurn(game)).setMyTurn(true);
                    connections.get(game).get(getPlayerTurn(game) - 1).setMyTurn(false);
                } else {
                    connections.get(game).get(getPlayerTurn(game) - 1).setMyTurn(false);
                    connections.get(game).get(0).setMyTurn(true);
                }
            } while (suspendedIdentifier.get(game).contains(getPlayerTurn(game)));
        }
    }

    public synchronized void manageDisconnection(int game, int identifier, String nickName) throws RemoteException, InterruptedException{
        suspendedIdentifier.get(game).add(identifier);
        suspendedName.get(game).add(nickName);
        /*if(connections.get(game).size() - suspendedIdentifier.get(game).size() < 3) {
            for(Connection c : connections.get(game)) {
                if(!c.getView().getNickName().equals(nickName)) {                                     //TODO it must not print in disconnected views
                    c.getView().finalScoring();
                    c.getView().exit();
                }
            }
        }*/
        for(Connection c : connections.get(game)){
            if(!c.getView().getNickName().equals(nickName))
                c.getView().disconnected(identifier);
        }
    }

    public synchronized boolean isASuspendedIdentifier(int game, int identifier) throws RemoteException{
        return suspendedIdentifier.get(game).contains(identifier);
    }

    public synchronized void manageReconnection(int game, int identifier) throws RemoteException{
        suspendedIdentifier.get(game).removeFirstOccurrence(identifier);
    }

    public String getSuspendedName(int game, int identifier) throws RemoteException{
        String s = suspendedName.get(game).get(suspendedIdentifier.get(game).indexOf(identifier));
        suspendedName.get(game).remove(s);
        return s;
    }

    public Colour getSuspendedColour(int game, String nickName) throws RemoteException{
        return games.get(game).getColour(nickName);
    }

    //notify to client

    public synchronized void notifyPlayer(int game, List<String> information) throws RemoteException{
        for (Connection c : connections.get(game)) {
            if(information.get(0).equals(c.getNickName()))
                information.add(Integer.toString(c.getIdentifier()));
        }
        for(Connection c : connections.get(game)){
            if(c.getView()!=null && !suspendedIdentifier.get(game).contains(c.getIdentifier())) {
                c.getView().printPlayer(information);
            }
        }
    }

    public synchronized void notifyScore(int game, List<String> information) throws RemoteException{
        for(Connection c : connections.get(game)){
            if(c.getView()!=null && !suspendedIdentifier.get(game).contains(c.getIdentifier()))
                c.getView().printScore(information);
        }
    }

    public  synchronized void notifyPosition(int game, List<String> information) throws RemoteException{
        for(Connection c : connections.get(game)){
            if(c.getView()!=null && !suspendedIdentifier.get(game).contains(c.getIdentifier()))
                c.getView().printPosition(information);
        }
    }

    public synchronized void notifyMark(int game, List<String> information) throws RemoteException{
        for(Connection c : connections.get(game)){
            if(c.getView()!=null && !suspendedIdentifier.get(game).contains(c.getIdentifier()))
                c.getView().printMark(information);
        }
    }

    public synchronized void notifyDamage(int game, List<String> information) throws RemoteException{
        for(Connection c : connections.get(game)){
            if(c.getView()!=null && !suspendedIdentifier.get(game).contains(c.getIdentifier()))
                c.getView().printDamage(information);
        }
    }

    public synchronized void notifyType(int game, int type) throws RemoteException{
        types.add(game, type);
    }

    public synchronized int getType(int game) throws RemoteException{
        return types.get(game);
    }


    //message from client to the controller


    public synchronized boolean messageGameIsNotStarted(int game) throws RemoteException {
        return games.get(game).gameIsNotStarted();
    }

    public synchronized void messageGameStart(int game, String nick, Colour c) throws RemoteException {
        games.get(game).gameStart(nick,c);
    }

    public synchronized boolean messageIsValidReceiveType(int game, int type) throws RemoteException {
        return games.get(game).isValidReceiveType(type);
    }

    public synchronized void messageReceiveType(int game, int type) throws RemoteException {
        games.get(game).receiveType(type);
    }

    public synchronized boolean messageIsValidAddPlayer(int game, String nick, Colour c) throws RemoteException {
        return games.get(game).isValidAddPlayer(nick, c);
    }

    public synchronized void messageAddPlayer(int game, String nick, Colour c) throws RemoteException {
        games.get(game).addPlayer(nick, c);
    }

    public synchronized void messageGiveTwoPUCard(int game, String nick) throws RemoteException {
        games.get(game).giveTwoPUCard(nick);
    }

    public synchronized String messageCheckYourStatus(int game, String nick) throws RemoteException {
        return games.get(game).checkYourStatus(nick);
    }

    public synchronized String messageShowCardsOnBoard(int game) throws RemoteException {
        return games.get(game).showCardsOnBoard();
    }

    public synchronized List<String> messageCheckWeaponSlotContents(int game, int n) throws RemoteException {
        return games.get(game).checkWeaponSlotContents(n);
    }

    public synchronized boolean messageIsValidPickAndDiscard(int game, String nick, String p1, String c1) throws RemoteException {
        return games.get(game).isValidPickAndDiscard(nick, p1, c1);
    }

    public synchronized void messagePickAndDiscardCard(int game, String nick, String p1, String c1) throws RemoteException {
        games.get(game).pickAndDiscardCard(nick, p1, c1);
    }

    public synchronized boolean messageIsValidFirstActionShoot(int game, String nick, String wC, List<Integer> lI, List<String> lS, int d, List<Colour> lC, List<String> lP, List<String> lPC) throws RemoteException {
        return games.get(game).isValidFirstActionShoot(nick, wC, lI, lS, d, lC, lP, lPC);
    }

    public synchronized void messageFirstActionShoot(int game, String nick, String wC, List<Integer> lI, List<String> lS, int d, List<Colour> lC, List<String> lP, List<String> lPC) throws RemoteException {
        games.get(game).firstActionShoot(nick, wC, lI, lS, d, lC, lP, lPC);
    }

    public synchronized boolean messageIsValidFirstActionMove(int game, String nick, List<Integer> d) throws RemoteException {
        return games.get(game).isValidFirstActionMove(nick, d);
    }

    public synchronized void messageFirstActionMove(int game, String nick, List<Integer> d) throws RemoteException {
        games.get(game).firstActionMove(nick, d);
    }

    public synchronized List<String> messageGetWeaponCardLoaded(int game, String nick) throws RemoteException {
        return games.get(game).getWeaponCardLoaded(nick);
    }

    public synchronized boolean messageIsValidCard(int game, String nick, String weaponCard) throws RemoteException {
        return games.get(game).isValidCard(nick, weaponCard);
    }

    public synchronized List<Colour> messageGetReloadCost(int game, String s, String nick) throws RemoteException {
        return games.get(game).getReloadCost(s, nick);
    }

    public synchronized String messageGetDescriptionWC(int game, String s, String nick) throws RemoteException {
        return games.get(game).getDescriptionWC(s, nick);
    }

    public synchronized boolean messageIsValidFirstActionGrab(int game, String nick, List<Integer> d, String wC, String wS, List<Colour> lA, List<String> lP, List<String> lPC) throws RemoteException {
        return games.get(game).isValidFirstActionGrab(nick, d, wC, wS,lA, lP, lPC);
    }

    public synchronized void messageFirstActionGrab(int game, String nick, List<Integer> d, String wC, List<Colour> lC, List<String> lP, List<String> lPC) throws RemoteException {
        games.get(game).firstActionGrab(nick, d, wC, lC, lP, lPC);
    }

    public synchronized boolean messageIsDiscard(int game) throws RemoteException {
        return games.get(game).isDiscard();
    }

    public synchronized void messageDiscardWeaponCard(int game, String nick, String wS, String wC) throws RemoteException {
        games.get(game).discardWeaponCard(nick, wS, wC);
    }

    public synchronized boolean messageIsValidSecondActionShoot(int game, String nick, String wC, List<Integer> lI, List<String> lS, int d, List<Colour> lC, List<String> lP, List<String> lPC) throws RemoteException {
        return games.get(game).isValidSecondActionShoot(nick, wC, lI, lS, d, lC, lP, lPC);
    }

    public synchronized void messageSecondActionShoot(int game, String nick, String wC, List<Integer> lI, List<String> lS, int d, List<Colour> lC, List<String> lP, List<String> lPC) throws RemoteException {
        games.get(game).secondActionShoot(nick, wC, lI, lS, d, lC, lP, lPC);
    }

    public synchronized boolean messageIsValidSecondActionMove(int game, String nick, List<Integer> d) throws RemoteException {
        return games.get(game).isValidSecondActionMove(nick, d);
    }

    public synchronized void messageSecondActionMove(int game, String nick, List<Integer> d) throws RemoteException {
        games.get(game).secondActionMove(nick, d);
    }

    public synchronized boolean messageIsValidSecondActionGrab(int game, String nick, List<Integer> d, String wC, String wS, List<Colour> lA, List<String> lP, List<String> lPC) throws RemoteException {
        return games.get(game).isValidSecondActionGrab(nick, d, wC, wS, lA, lP, lPC);
    }

    public synchronized void messageSecondActionGrab(int game, String nick, List<Integer> d, String wC, List<Colour> lC, List<String> lP, List<String> lPC) throws RemoteException {
        games.get(game).secondActionGrab(nick, d, wC, lC, lP, lPC);
    }

    public synchronized List<String> messageGetPowerUpCard(int game, String nick) throws RemoteException {
        return games.get(game).getPowerUpCard(nick);
    }

    public synchronized List<String> messageGetPowerUpCardColour(int game, String nickName) throws RemoteException {
        return games.get(game).getPowerUpCardColour(nickName);
    }

    public synchronized String messageGetDescriptionPUC(int game, String pC, String col, String nick) throws RemoteException {
        return games.get(game).getDescriptionPUC(pC, col, nick);
    }

    public synchronized boolean messageIsValidUsePowerUpCard(int game, String nick, String pC, String col, List<String> l, Colour c) throws RemoteException {
        return games.get(game).isValidUsePowerUpCard(nick, pC, col, l, c);
    }

    public synchronized void messageUsePowerUpCard(int game, String nick, String pC, String col, List<String> l, Colour c) throws RemoteException {
        games.get(game).usePowerUpCard(nick, pC, col, l, c);
    }

    public synchronized List<String> messageGetWeaponCardUnloaded(int game, String nick) throws RemoteException {
        return games.get(game).getWeaponCardUnloaded(nick);
    }

    public synchronized boolean messageIsValidReload(int game, String nick, String s) throws RemoteException {
        return games.get(game).isValidReload(nick, s);
    }

    public synchronized void messageReload(int game, String nick, String s, int end) throws RemoteException {
        games.get(game).reload(nick, s, end);
    }

    public synchronized boolean messageIsValidScoring(int game) throws RemoteException {
        return games.get(game).isValidScoring();
    }

    public synchronized void messageScoring(int game) throws RemoteException {
        games.get(game).scoring();
    }

    public synchronized List<String> messageGetDeadList(int game) throws RemoteException {
        return games.get(game).getDeadList();
    }

    public synchronized boolean messageIsValidDiscardCardForSpawnPoint(int game, String nick, String s, String c) throws RemoteException {
        return games.get(game).isValidDiscardCardForSpawnPoint(nick, s, c);
    }

    public synchronized void messageDiscardCardForSpawnPoint(int game, String nick, String s, String c) throws RemoteException {
        games.get(game).discardCardForSpawnPoint(nick, s, c);
    }

    public synchronized boolean messageIsValidToReplace(int game) throws RemoteException {
        return games.get(game).isValidToReplace();
    }

    public synchronized void messageReplace(int game) throws RemoteException {
        games.get(game).replace();
    }

    public synchronized boolean messageIsValidFinalFrenzyAction(int game, String nick, List<String> l) throws RemoteException {
        return games.get(game).isValidFinalFrenzyAction(nick, l);
    }

    public synchronized List<String> messageGetWeaponCard(int game, String nick) throws RemoteException {
        return games.get(game).getWeaponCard(nick);
    }

    public synchronized boolean messageIsValidFinalFrenzyAction1(int game, String nick, int d, String wC, List<Integer> lI, List<String> lS, List<Colour> lC, List<String> lP, List<String> lPC) throws RemoteException {
        return games.get(game).isValidFinalFrenzyAction1(nick, d, wC, lI, lS, lC, lP, lPC);
    }

    public synchronized void messageFinalFrenzyAction1(int game, String nick, int d, List<String> lW, String wC, List<Integer> lI, List<String> lS, List<Colour> lC, List<String> lP, List<String> lPC) throws RemoteException {
        games.get(game).finalFrenzyAction1(nick, d, lW, wC, lI, lS, lC, lP, lPC);
    }

    public synchronized boolean messageIsValidFinalFrenzyAction2(int game, String nick, List<Integer> d) throws RemoteException {
        return games.get(game).isValidFinalFrenzyAction2(nick, d);
    }

    public synchronized void messageFinalFrenzyAction2(int game, String nick, List<Integer> d) throws RemoteException {
        games.get(game).finalFrenzyAction2(nick, d);
    }

    public synchronized boolean messageIsValidFinalFrenzyAction3(int game, String nick, List<Integer> d, String wC, String wS, List<Colour> lC, List<String> lP, List<String> lPC) throws RemoteException {
       return games.get(game).isValidFinalFrenzyAction3(nick, d, wC, wS, lC, lP, lPC);
    }

    public synchronized void messageFinalFrenzyAction3(int game, String nick, List<Integer> d, String wC, List<Colour> lC, List<String> lP, List<String> lPC) throws RemoteException {
        games.get(game).finalFrenzyAction3(nick, d, wC, lC, lP, lPC);
    }

    public synchronized boolean messageIsValidFinalFrenzyAction4(int game, String nick, List<Integer> d, String wC, List<Integer> lI, List<String> lS, List<Colour> lC, List<String> lP, List<String> lPC) throws RemoteException {
        return games.get(game).isValidFinalFrenzyAction4(nick, d, wC, lI, lS, lC, lP, lPC);
    }

    public synchronized void messageFinalFrenzyAction4(int game, String nick, List<Integer> d, List<String> lW, String wC, List<Integer> lI, List<String> lS, List<Colour> lC, List<String> lP, List<String> lPC) throws RemoteException {
        games.get(game).finalFrenzyAction4(nick, d, lW, wC, lI, lS, lC, lP, lPC);
    }

    public synchronized boolean messageIsValidFinalFrenzyAction5(int game, String nick, List<Integer> d, String wC, String wS, List<Colour> lC, List<String> lS, List<String> lPC) throws RemoteException {
        return games.get(game).isValidFinalFrenzyAction5(nick, d, wC, wS, lC, lS, lPC);
    }

    public synchronized void messageFinalFrenzyAction5(int game, String nick, List<Integer> d, String wC, List<Colour> lC, List<String> lP, List<String> lPC) throws RemoteException {
        games.get(game).finalFrenzyAction5(nick, d, wC, lC, lP, lPC);
    }

    public synchronized void messageFinalFrenzyTurnScoring(int game) throws RemoteException {
        games.get(game).finalFrenzyTurnScoring();
    }

    public synchronized void messageEndTurnFinalFrenzy(int game) throws RemoteException {
        games.get(game).endTurnFinalFrenzy();

    }
    public synchronized void messageFinalScoring(int game) throws RemoteException {
        games.get(game).finalScoring();
    }

    public  synchronized List<String> messageGetPlayers(int game) throws RemoteException {
        return games.get(game).getPlayers();
    }

    public synchronized List<Integer> messageGetScore(int game) throws RemoteException {
        return games.get(game).getScore();
    }

    /* We should insert methods who take parameters from the view end give them to the controller, returning the public boolean (only for the
    IsValidMethod) to the Cli/Gui
     */
}
