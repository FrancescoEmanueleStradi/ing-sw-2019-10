package view;

import model.Colour;
import model.cards.PowerUpCard;
import model.cards.WeaponCard;

import java.rmi.*;
import java.util.List;

/*Notes:
  stub: "client side gateway for client side objects all outgoing requests to server
         side objects"
  skeleton: "gateway for server side objects and all incoming clients requests are routed
             through it"
  The stub calls a method and marshalls parameters, while the skeleton receives said call and
  unmarshalls the parameters. The skeleton invokes the method proper, then marshalls the return
  value, and finally the stub unmarshalls the skeleton's response.
  The RMI registry provides the client with the necessary stub.
 */

public interface ServerInterface extends Remote {

    String echo(String input) throws RemoteException;
    int getGames() throws RemoteException;
    int setGame(int game) throws RemoteException;
    boolean canStart(int game) throws RemoteException;
    boolean tooMany(int game) throws RemoteException;
    boolean stopGame(int game) throws RemoteException;
    int receiveIdentifier(int game) throws RemoteException;
    void mergeGroup(int game) throws RemoteException, InterruptedException;
    //void setCli(int game, int identifier) throws RemoteException;
    //void setGui(int game, int identifier) throws RemoteException;
    boolean isMyTurn(int game, int identifier) throws RemoteException;
    boolean isNotFinalFrenzy(int game) throws RemoteException;
    boolean gameIsFinished(int game) throws RemoteException;
    void finishTurn(int game) throws RemoteException;

    boolean messageGameIsNotStarted(int game) throws RemoteException;
    void messageGameStart(int game, String nick, Colour c) throws RemoteException;
    boolean messageIsValidReceiveType(int game, int type) throws RemoteException;
    void messageReceiveType(int game, int type) throws RemoteException;
    boolean messageIsValidAddPlayer(int game, String nick, Colour c) throws RemoteException;
    void messageAddPlayer(int game, String nick, Colour c) throws RemoteException;
    List<String> messageGiveTwoPUCard(int game, String nick) throws RemoteException;       //TODO Problem
    boolean messageIsValidPickAndDiscard(int game, String nick, String p1, String c1) throws RemoteException;
    void messagePickAndDiscardCard(int game, String nick, String p1, String c1) throws RemoteException;
    boolean messageIsValidFirstActionShoot(int game, String nick, String wC, List<Integer> lI, List<String> lS, int d, List<Colour> lC, List<String> lP, List<String> lPC) throws RemoteException;
    void messageFirstActionShoot(int game, String nick, String wC, List<Integer> lI, List<String> lS, int d, List<Colour> lC, List<String> lP, List<String> lPC) throws RemoteException;
    boolean messageIsValidFirstActionMove(int game, String nick, List<Integer> d) throws RemoteException;
    void messageFirstActionMove(int game, String nick, List<Integer> d) throws RemoteException;
    List<String> messageGetWeaponCardLoaded(int game, String nick) throws RemoteException;
    boolean messageIsValidCard(int game, String nick, String weaponCard) throws RemoteException;
    List<Colour> messageGetReloadCost(int game, String s, String nick) throws RemoteException;      //TODO Problem
    String messageGetDescriptionWC(int game, String s, String nick) throws RemoteException;
    boolean messageIsValidFirstActionGrab(int game, String nick, Integer[] d, String wC, String wS, List<Colour> lA, List<String> lP, List<String> lPC) throws RemoteException;
    void messageFirstActionGrab(int game, String nick, Integer[] d, String wC, List<Colour> lC, List<String> lP, List<String> lPC) throws RemoteException;
    boolean messageIsDiscard(int game) throws RemoteException;
    void messageDiscardWeaponCard(int game, String nick, String wS, String wC) throws RemoteException;
    boolean messageIsValidSecondActionShoot(int game, String nick, String wC, List<Integer> lI, List<String> lS, int d, List<Colour> lC, List<String> lP, List<String> lPC) throws RemoteException;
    void messageSecondActionShoot(int game, String nick, String wC, List<Integer> lI, List<String> lS, int d, List<Colour> lC, List<String> lP, List<String> lPC) throws RemoteException;
    boolean messageIsValidSecondActionMove(int game, String nick, List<Integer> d) throws RemoteException;
    void messageSecondActionMove(int game, String nick, List<Integer> d) throws RemoteException;
    boolean messageIsValidSecondActionGrab(int game, String nick, Integer[] d, String wC, String wS, List<Colour> lA, List<String> lP, List<String> lPC) throws RemoteException;
    void messageSecondActionGrab(int game, String nick, Integer[] d, String wC, List<Colour> lC, List<String> lP, List<String> lPC) throws RemoteException;
    List<String> messageGetPowerUpCard(int game, String nick) throws RemoteException;
    String messageGetDescriptionPUC(int game, String pC, String col, String nick) throws RemoteException;
    boolean messageIsValidUsePowerUpCard(int game, String nick, String pC, String col, List<String> l,Colour c) throws RemoteException;
    void messageUsePowerUpCard(int game, String nick, String pC, String col, List<String> l, Colour c) throws RemoteException;
    List<String> messageGetWeaponCardUnloaded(int game, String nick) throws RemoteException;
    boolean messageIsValidReload(int game, String nick, String s) throws RemoteException;
    void messageReload(int game, String nick, String s, int end) throws RemoteException;
    boolean messageIsValidScoring(int game) throws RemoteException;
    void messageScoring(int game) throws RemoteException;
    List<String> messageGetDeadList(int game) throws RemoteException;
    boolean messageIsValidDiscardCardForSpawnPoint(int game, String nick, String s, String c) throws RemoteException;
    void messageDiscardCardForSpawnPoint(int game, String nick, String s, String c) throws RemoteException;
    boolean messageIsValidToReplace(int game) throws RemoteException;
    void messageReplace(int game) throws RemoteException;
    boolean messageIsValidFinalFrenzyAction(int game, String nick, List<String> l) throws RemoteException;
    List<String> messageGetWeaponCard(int game, String nick) throws RemoteException;
    boolean messageIsValidFinalFrenzyAction1(int game, String nick, int d, String wC, List<Integer> lI, List<String> lS, List<Colour> lC, List<String> lP, List<String> lPC) throws RemoteException;
    void messageFinalFrenzyAction1(int game, String nick, int d, List<String> lW, String wC, List<Integer> lI, List<String> lS, List<Colour> lC, List<String> lP, List<String> lPC) throws RemoteException;
    boolean messageIsValidFinalFrenzyAction2(int game, String nick, List<Integer> d) throws RemoteException;
    void messageFinalFrenzyAction2(int game, String nick, List<Integer> d) throws RemoteException;
    boolean messageIsValidFinalFrenzyAction3(int game, String nick, List<Integer> d, String wC, String wS, List<Colour> lC, List<String> lP, List<String> lPC) throws RemoteException;
    void messageFinalFrenzyAction3(int game, String nick, List<Integer> d, String wC, List<Colour> lC, List<String> lP, List<String> lPC) throws RemoteException;
    boolean messageIsValidFinalFrenzyAction4(int game, String nick, List<Integer> d, String wC, List<Integer> lI, List<String> lS, List<Colour> lC, List<String> lP, List<String> lPC) throws RemoteException;
    void messageFinalFrenzyAction4(int game, String nick, List<Integer> d, List<String> lW, String wC, List<Integer> lI, List<String> lS, List<Colour> lC, List<String> lP, List<String> lPC) throws RemoteException;
    boolean messageIsValidFinalFrenzyAction5(int game, String nick, List<Integer> d, String wC, String wS, List<Colour> lC, List<String> lS, List<String> lPC) throws RemoteException;
    void messageFinalFrenzyAction5(int game, String nick, List<Integer> d, String wC, List<Colour> lC, List<String> lP, List<String> lPC) throws RemoteException;
    void messageFinalFrenzyTurnScoring(int game) throws RemoteException;
    void messageEndTurnFinalFrenzy(int game) throws RemoteException;
    void messageFinalScoring(int game) throws RemoteException;
    List<String> messageGetPlayers(int game) throws RemoteException;        
    List<Integer> messageGetScore(int game) throws RemoteException;
}
