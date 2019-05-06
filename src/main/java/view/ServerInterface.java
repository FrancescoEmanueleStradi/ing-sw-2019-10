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

    //String getSomeMessage();
    String echo(String input) throws RemoteException;
    int getGames() throws RemoteException;
    int setGame(int game) throws RemoteException;
    int receiveIdentifier(int game) throws RemoteException;
    //void setCli(int game, int identifier) throws RemoteException;
    //void setGui(int game, int identifier) throws RemoteException;
    boolean isMyTurn(int game, int identifier) throws RemoteException;
    boolean isNotFinalFrenzy(int game) throws RemoteException;
    boolean gameIsFinished(int game) throws RemoteException;
    void finishTurn(int game) throws RemoteException;

    boolean messageGameIsNotStarted(int game);
    void messageGameStart(int game, String nick, Colour c);
    boolean messageIsValidReceiveType(int game, int type);
    void messageReceiveType(int game, int type);
    boolean messageIsValidAddPlayer(int game, String nick, Colour c);
    void messageAddPlayer(int game, String nick, Colour c);
    List<PowerUpCard> messageGiveTwoPUCard(int game, String nick);
    boolean messageIsValidPickAndDiscard(int game, String nick);
    void messagePickAndDiscardCard(int game, String nick, PowerUpCard p1, PowerUpCard p2);
    boolean messageIsValidFirstActionMove(int game, List<Integer> d);
    void messageFirstActionMove(int game, String nick, List<Integer> d);
    List<String> messageGetWeaponCardLoaded(int game, String nick);
    boolean messageIsValidCard(int game, String nick, String weaponCard);
    List<Colour> messageGetReloadCost(int game, String s, String nick);
    String messageGetDescriptionWC(int game, String s, String nick);
    boolean messageIsValidFirstActionGrab(int game, String nick, Integer[] d, String wC, String wS, List<Colour> lA, List<String> lP);
    void messageFirstActionGrab(int game, String nick, Integer[] d, String wC, List<Colour> lC, List<String> lP);
    boolean messageIsDiscard(int game);
    void messageDiscardWeaponCard(int game, String nick, String wS, String wC);
    boolean messageIsValidSecondActionMove(int game, List<Integer> d);
    void messageSecondActionMove(int game, String nick, List<Integer> d);
    boolean messageIsValidSecondActionGrab(int game, String nick, Integer[] d, String wC, String wS, List<Colour> lA, List<String> lP);
    void messageSecondActionGrab(int game, String nick, Integer[] d, String wC, List<Colour> lC, List<String> lP);
    List<String> messageGetPowerUpCard(int game, String nick);
    String messageGetDescriptionPUC(int game, String pC, String nick);
    boolean messageIsValidUsePowerUpCard(int game, String nick, String pC, List<String> l, Colour c);
    void messageUsePowerUpCard(int game, String nick, String pC, List<String> l, Colour c);
    List<String> messageGetWeaponCardUnloaded(int game, String nick);
    boolean messageIsValidReload(int game);
    void messageReload(int game, String nick, String s, int end);
    boolean messageIsValidScoring(int game);
    void messageScoring(int game);
    List<String> messageGetDeadList(int game);
    boolean messageIsValidDiscardCardForSpawnPoint(int game);
    void messageDiscardCardForSpawnPoint(int game, String nick, String s);
    boolean messageIsValidToReplace(int game);
    void messageReplace(int game);
    boolean messageIsValidFinalFrenzyAction(int game, String nick, List<String> l);
    List<String> messageGetWeaponCard(int game, String nick);
    boolean messageIsValidFinalFrenzyAction1(int game, String nick, int d, String wC, List<Integer> lI, List<String> lS, List<Colour> lC, List<String> lP);
    void messageFinalFrenzyAction1(int game, String nick, int d, List<String> lW, String wC, List<Integer> lI, List<String> lS, List<Colour> lC, List<String> lP);
    boolean messageIsValidFinalFrenzyAction2(int game, String nick, List<Integer> d);
    void messageFinalFrenzyAction2(int game, String nick, List<Integer> d);
    boolean messageIsValidFinalFrenzyAction3(int game, String nick, List<Integer> d, String wC, String wS, List<Colour> lC, List<String> lP);
    void messageFinalFrenzyAction3(int game, String nick, List<Integer> d, String wC, List<Colour> lC, List<String> lP);
    boolean messageIsValidFinalFrenzyAction4(int game, String nick, List<Integer> d, String wC, List<Integer> lI, List<String> lS, List<Colour> lC, List<String> lP);
    void messageFinalFrenzyAction4(int game, String nick, List<Integer> d, List<String> lW, String wC, List<Integer> lI, List<String> lS, List<Colour> lC, List<String> lP);
    boolean messageIsValidFinalFrenzyAction5(int game, String nick, List<Integer> d, String wC, String wS, List<Colour> lC, List<String> lS);
    void messageFinalFrenzyAction5(int game, String nick, List<Integer> d, String wC, List<Colour> lC, List<String> lP);
    void messageFinalFrenzyTurnScoring(int game);
    void messageEndTurnFinalFrenzy(int game);
    void messageFinalScoring(int game);
    List<String> messageGetPlayers(int game);
    List<Integer> messageGetScore(int game);
}
