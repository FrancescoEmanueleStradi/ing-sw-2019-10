package view;

import controller.Game;
import controller.GameState;
import model.Colour;
import model.cards.PowerUpCard;

import java.rmi.*;
import java.rmi.registry.*;
import java.rmi.server.*;
import java.util.LinkedList;
import java.util.List;

public class Server extends UnicastRemoteObject implements ServerInterface {

    static private List<Game> games;
    static private List<Integer> players;           //for each game the number of players
    //static private List<List<View>> views;
    static private List<Integer> playersTakingTheirTurn;        //position n --> game n

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
        players = new LinkedList<>();
        //views = new LinkedList<>();
        playersTakingTheirTurn = new LinkedList<>();            //TODO methods in the controller to notify the server
    }

    public synchronized int getGames() throws RemoteException {
        return games.size();
    }

    public synchronized int setGame(int numGame) throws RemoteException {
        if (games.isEmpty() || games.size() < numGame){
            games.add(numGame, new Game());                  //TODO add a game even if it shouldn't
            //views.add(numGame, new LinkedList<>());
            playersTakingTheirTurn.add(numGame, 1);
        }
        return numGame;
    }

    public synchronized int receiveIdentifier(int game) throws RemoteException{
        players.add(game, players.get(game)+1);
        return players.get(game);
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
        return(playersTakingTheirTurn.get(game) == identifier);
    }

    public synchronized boolean isNotFinalFrenzy(int game) throws RemoteException {
        return !games.get(game).isFinalFrenzy();
    }

    public synchronized boolean gameIsFinished(int game) throws RemoteException {
        return games.get(game).getGameState() == GameState.ENDALLTURN;
    }

    public synchronized void finishTurn(int game) throws RemoteException {
        if(games.get(game).getPlayers().size() < playersTakingTheirTurn.get(game))
            playersTakingTheirTurn.add(game, playersTakingTheirTurn.get(game)+1);
        else playersTakingTheirTurn.add(game, 1);
    }


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
    public synchronized List<PowerUpCard> messageGiveTwoPUCard(int game, String nick) throws RemoteException {
        return games.get(game).giveTwoPUCard(nick);
    }

    public synchronized boolean messageIsValidPickAndDiscard(int game, String nick) throws RemoteException {
        return games.get(game).isValidPickAndDiscard(nick);
    }
    public synchronized void messagePickAndDiscardCard(int game, String nick, PowerUpCard p1, PowerUpCard p2) throws RemoteException {
        games.get(game).pickAndDiscardCard(nick, p1, p2);
    }

    public synchronized boolean messageIsValidFirstActionShoot(int game, String nick, String wC, List<Integer> lI, List<String> lS, int d, List<Colour> lC, List<String> lP) throws RemoteException {
        return games.get(game).isValidFirstActionShoot(nick, wC, lI, lS, d, lC, lP);
    }

    public synchronized void messageFirstActionShoot(int game, String nick, String wC, List<Integer> lI, List<String> lS, int d, List<Colour> lC, List<String> lP) throws RemoteException {
        games.get(game).firstActionShoot(nick, wC, lI, lS, d, lC, lP);
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

    public synchronized boolean messageIsValidFirstActionGrab(int game, String nick, Integer[] d, String wC, String wS, List<Colour> lA, List<String> lP) throws RemoteException {
        return games.get(game).isValidFirstActionGrab(nick, d, wC, wS,lA, lP);
    }
    public synchronized void messageFirstActionGrab(int game, String nick, Integer[] d, String wC, List<Colour> lC, List<String> lP) throws RemoteException {
        games.get(game).firstActionGrab(nick, d, wC, lC, lP);
    }

    public synchronized boolean messageIsDiscard(int game) throws RemoteException {
        return games.get(game).isDiscard();
    }

    public synchronized void messageDiscardWeaponCard(int game, String nick, String wS, String wC) throws RemoteException {
        games.get(game).discardWeaponCard(nick, wS, wC);
    }

    public synchronized boolean messageIsValidSecondActionShoot(int game, String nick, String wC, List<Integer> lI, List<String> lS, int d, List<Colour> lC, List<String> lP) throws RemoteException {
        return games.get(game).isValidSecondActionShoot(nick, wC, lI, lS, d, lC, lP);
    }

    public synchronized void messageSecondActionShoot(int game, String nick, String wC, List<Integer> lI, List<String> lS, int d, List<Colour> lC, List<String> lP) throws RemoteException {
        games.get(game).secondActionShoot(nick, wC, lI, lS, d, lC, lP);
    }

    public synchronized boolean messageIsValidSecondActionMove(int game, List<Integer> d) throws RemoteException {
        return games.get(game).isValidSecondActionMove(d);
    }

    public synchronized void messageSecondActionMove(int game, String nick, List<Integer> d) throws RemoteException {
        games.get(game).secondActionMove(nick, d);
    }

    public synchronized boolean messageIsValidSecondActionGrab(int game, String nick, Integer[] d, String wC, String wS, List<Colour> lA, List<String> lP) throws RemoteException {
        return games.get(game).isValidSecondActionGrab(nick, d, wC, wS, lA, lP);
    }

    public synchronized void messageSecondActionGrab(int game, String nick, Integer[] d, String wC, List<Colour> lC, List<String> lP) throws RemoteException {
        games.get(game).secondActionGrab(nick, d, wC, lC, lP);

    }
    public synchronized List<String> messageGetPowerUpCard(int game, String nick) throws RemoteException {
        return games.get(game).getPowerUpCard(nick);
    }

    public synchronized String messageGetDescriptionPUC(int game, String pC, String nick) throws RemoteException {
        return games.get(game).getDescriptionPUC(pC, nick);
    }

    public synchronized boolean messageIsValidUsePowerUpCard(int game, String nick, String pC, List<String> l, Colour c) throws RemoteException {
        return games.get(game).isValidUsePowerUpCard(nick, pC, l, c);
    }


    public synchronized void messageUsePowerUpCard(int game, String nick, String pC, List<String> l, Colour c) throws RemoteException {
        games.get(game).usePowerUpCard(nick, pC, l, c);
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

    public synchronized boolean messageIsValidDiscardCardForSpawnPoint(int game, String nick, String s) throws RemoteException {
        return games.get(game).isValidDiscardCardForSpawnPoint(nick, s);
    }

    public synchronized void messageDiscardCardForSpawnPoint(int game, String nick, String s) throws RemoteException {
        games.get(game).discardCardForSpawnPoint(nick, s);
    }

    public synchronized boolean messageIsValidToReplace(int game) throws RemoteException {
        return games.get(game).isValidToReplace();
    }

    public synchronized void messageReplace(int game) throws RemoteException {
        games.get(game).replace();;

    }
    public synchronized boolean messageIsValidFinalFrenzyAction(int game, String nick, List<String> l) throws RemoteException {
        return games.get(game).isValidFinalFrenzyAction(nick, l);
    }

    public synchronized List<String> messageGetWeaponCard(int game, String nick) throws RemoteException {
        return games.get(game).getWeaponCard(nick);
    }

    public synchronized boolean messageIsValidFinalFrenzyAction1(int game, String nick, int d, String wC, List<Integer> lI, List<String> lS, List<Colour> lC, List<String> lP) throws RemoteException {
        return games.get(game).isValidFinalFrenzyAction1(nick, d, wC, lI, lS, lC, lP);
    }

    public synchronized void messageFinalFrenzyAction1(int game, String nick, int d, List<String> lW, String wC, List<Integer> lI, List<String> lS, List<Colour> lC, List<String> lP) throws RemoteException {
        games.get(game).finalFrenzyAction1(nick, d, lW, wC, lI, lS, lC, lP);
    }

    public synchronized boolean messageIsValidFinalFrenzyAction2(int game, String nick, List<Integer> d) throws RemoteException {
        return games.get(game).isValidFinalFrenzyAction2(nick, d);
    }

    public synchronized void messageFinalFrenzyAction2(int game, String nick, List<Integer> d) throws RemoteException {
        games.get(game).finalFrenzyAction2(nick, d);
    }
    public synchronized boolean messageIsValidFinalFrenzyAction3(int game, String nick, List<Integer> d, String wC, String wS, List<Colour> lC, List<String> lP) throws RemoteException {
       return games.get(game).isValidFinalFrenzyAction3(nick, d, wC, wS, lC, lP);
    }

    public synchronized void messageFinalFrenzyAction3(int game, String nick, List<Integer> d, String wC, List<Colour> lC, List<String> lP) throws RemoteException {
        games.get(game).finalFrenzyAction3(nick, d, wC, lC, lP);

    }
    public synchronized boolean messageIsValidFinalFrenzyAction4(int game, String nick, List<Integer> d, String wC, List<Integer> lI, List<String> lS, List<Colour> lC, List<String> lP) throws RemoteException {
        return games.get(game).isValidFinalFrenzyAction4(nick, d, wC, lI, lS, lC, lP);
    }

    public synchronized void messageFinalFrenzyAction4(int game, String nick, List<Integer> d, List<String> lW, String wC, List<Integer> lI, List<String> lS, List<Colour> lC, List<String> lP) throws RemoteException {
        games.get(game).finalFrenzyAction4(nick, d, lW, wC, lI, lS, lC, lP);

    }
    public synchronized boolean messageIsValidFinalFrenzyAction5(int game, String nick, List<Integer> d, String wC, String wS, List<Colour> lC, List<String> lS) throws RemoteException {
        return games.get(game).isValidFinalFrenzyAction5(nick, d, wC, wS, lC, lS);
    }

    public synchronized void messageFinalFrenzyAction5(int game, String nick, List<Integer> d, String wC, List<Colour> lC, List<String> lP) throws RemoteException {
        games.get(game).finalFrenzyAction5(nick, d, wC, lC, lP);

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
