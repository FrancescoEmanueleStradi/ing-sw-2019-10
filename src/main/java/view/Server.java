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
    static private List<List<View>> views;
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
        views = new LinkedList<>();
        playersTakingTheirTurn = new LinkedList<>();            //TODO methods in the controller to notify the server
    }

    public int getGames(){
        return games.size();
    }

    public int setGame(int numGame) throws RemoteException{
        if (games.size() < numGame)
            return numGame;
        else{
            games.add(numGame, new Game());             //TODO add a game even if it shouldn't
            views.add(numGame, new LinkedList<>());
            playersTakingTheirTurn.add(numGame, 1);
            return numGame;
        }
    }

    public int receiveIdentifier(int game) throws RemoteException{
        return views.get(game).size();                  //It should be correct
    }

    public void setCli(int game, int identifier) throws RemoteException{
        views.get(game).add(identifier, new Cli());
        views.get(game).get(identifier).setGame(games.get(identifier));         //TODO this could be a problem!!!!!!!
    }

    public void setGui(int game, int identifier) throws RemoteException{
        views.get(game).add(identifier, new Gui());
        views.get(game).get(identifier).setGame(games.get(identifier));         //TODO this could be a problem!!!!!!!
    }

    public boolean isMyTurn(int game, int identifier) throws RemoteException {
        return(playersTakingTheirTurn.get(game) == identifier);
    }

    public boolean isNotFinalFrenzy(int game) throws RemoteException {
        return !games.get(game).isFinalFrenzy();
    }

    public boolean gameIsFinished(int game) throws RemoteException {
        return games.get(game).getGameState() == GameState.ENDALLTURN;
    }

    public void finishTurn(int game) throws RemoteException {
        if(games.get(game).getPlayers().size() < playersTakingTheirTurn.get(game))
            playersTakingTheirTurn.add(game, playersTakingTheirTurn.get(game)+1);
        else playersTakingTheirTurn.add(game, 1);
    }


    public boolean messageGameIsNotStarted() {

    }

    public void messageGameStart(String nick, Colour c) {

    }

    public boolean messageIsValidReceiveType(int type) {

    }

    public void messageReceiveType(int type) {

    }
    public boolean messageIsValidAddPlayer(String nick, Colour c) {

    }
    public void messageAddPlayer(String nick, Colour c) {

    }
    public List<PowerUpCard> messageGiveTwoPUCard(String nick) {

    }
    public boolean messageIsValidPickAndDiscard(String nick) {

    }
    public void messagePickAndDiscardCard(String nick, PowerUpCard p1, PowerUpCard p2) {

    }
    public boolean messageIsValidFirstActionMove(List<Integer> d) {

    }
    public void messageFirstActionMove(String nick, List<Integer> d) {

    }
    public List<String> messageGetWeaponCardLoaded(String nick) {


    }
    public boolean messageIsValidCard(String nick, String weaponCard) {

    }
    public List<Colour> messageGetReloadCost(String s, String nick) {

    }
    public String messageGetDescriptionWC(String s, String nick) {

    }
    public boolean messageIsValidFirstActionGrab(String nick, Integer[] d, String wC, String wS, List<Colour> lA, List<String> lP) {

    }
    public void messageFirstActionGrab(String nick, Integer[] d, String wC, List<Colour> lC, List<String> lP) {

    }
    public boolean messageIsDiscard() {

    }
    public void messageDiscardWeaponCard(String nick, String wS, String wC) {

    }
    public boolean messageIsValidSecondActionMove(List<Integer> d) {

    }
    public void messageSecondActionMove(String nick, List<Integer> d) {

    }
    public boolean messageIsValidSecondActionGrab(String nick, Integer[] d, String wC, String wS, List<Colour> lA, List<String> lP) {

    }
    public void messageSecondActionGrab(String nick, Integer[] d, String wC, List<Colour> lC, List<String> lP) {

    }
    public List<String> messageGetPowerUpCard(String nick) {

    }
    public String messageGetDescriptionPUC(String pC, String nick) {

    }
    public boolean messageIsValidUsePowerUpCard(String nick, String pC, List<String> l, Colour c) {

    }
    public void messageUsePowerUpCard(String nick, String pC, List<String> l, Colour c) {

    }
    public List<String> messageGetWeaponCardUnloaded(String nick) {

    }
    public boolean messageIsValidReload() {

    }
    public void messageReload(String nick, String s, int end) {

    }
    public boolean messageIsValidScoring() {

    }
    public void messageScoring() {

    }
    public List<String> messageGetDeadList() {

    }
    public boolean messageIsValidDiscardCardForSpawnPoint() {

    }
    public void messageDiscardCardForSpawnPoint(String nick, String s) {

    }
    public boolean messageIsValidToReplace() {

    }
    public void messageReplace() {

    }
    public boolean messageIsValidFinalFrenzyAction(String nick, List<String> l) {

    }
    public List<String> messageGetWeaponCard(String nick) {

    }
    public boolean messageIsValidFinalFrenzyAction1(String nick, int d, String wC, List<Integer> lI, List<String> lS, List<Colour> lC, List<String> lP) {

    }
    public void messageFinalFrenzyAction1(String nick, int d, List<String> lW, String wC, List<Integer> lI, List<String> lS, List<Colour> lC, List<String> lP) {

    }
    public boolean messageIsValidFinalFrenzyAction2(String nick, List<Integer> d) {

    }
    public void messageFinalFrenzyAction2(String nick, List<Integer> d) {

    }
    public boolean messageIsValidFinalFrenzyAction3(String nick, List<Integer> d, String wC, String wS, List<Colour> lC, List<String> lP) {

    }
    public void messageFinalFrenzyAction3(String nick, List<Integer> d, String wC, List<Colour> lC, List<String> lP) {

    }
    public boolean messageIsValidFinalFrenzyAction4(String nick, List<Integer> d, String wC, List<Integer> lI, List<String> lS, List<Colour> lC, List<String> lP) {

    }
    public void messageFinalFrenzyAction4(String nick, List<Integer> d, List<String> lW, String wC, List<Integer> lI, List<String> lS, List<Colour> lC, List<String> lP) {

    }
    public boolean messageIsValidFinalFrenzyAction5(String nick, List<Integer> d, String wC, String wS, List<Colour> lC, List<String> lS) {

    }
    public void messageFinalFrenzyAction5(String nick, List<Integer> d, String wC, List<Colour> lC, List<String> lP) {

    }
    public void messageFinalFrenzyTurnScoring() {

    }
    public void messageEndTurnFinalFrenzy() {

    }
    public void messageFinalScoring() {

    }
    public  List<String> messageGetPlayers() {

    }
    public List<Integer> messageGetScore() {

    }

    /* We should insert methods who take parameters from the view end give them to the controller, returning the public boolean (only for the
    IsValidMethod) to the Cli/Gui
     */
}
