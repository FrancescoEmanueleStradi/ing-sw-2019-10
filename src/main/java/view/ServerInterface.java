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
    void setCli(int game, int identifier) throws RemoteException;
    void setGui(int game, int identifier) throws RemoteException;
    boolean isMyTurn(int game, int identifier) throws RemoteException;
    boolean isNotFinalFrenzy(int game) throws RemoteException;
    boolean gameIsFinished(int game) throws RemoteException;
    void finishTurn(int game) throws RemoteException;

    boolean messageGameIsNotStarted();
    void messageGameStart(String nick, Colour c);
    boolean messageIsValidReceiveType(int type);
    void messageReceiveType(int type);
    boolean messageIsValidAddPlayer(String nick, Colour c);
    void messageAddPlayer(String nick, Colour c);
    List<PowerUpCard> messageGiveTwoPUCard(String nick);
    boolean messageIsValidPickAndDiscard(String nick);
    void messagePickAndDiscardCard(String nick, PowerUpCard p1, PowerUpCard p2);
    boolean messageIsValidFirstActionMove(List<Integer> d);
    void messageFirstActionMove(String nick, List<Integer> d);
    List<String> messageGetWeaponCardLoaded(String nick);
    boolean messageIsValidCard(String nick, String weaponCard);
    List<Colour> messageGetReloadCost(String s, String nick);
    String messageGetDescriptionWC(String s, String nick);
    boolean messageIsValidFirstActionGrab(String nick, Integer[] d, String wC, String wS, List<Colour> lA, List<String> lP);
    void messageFirstActionGrab(String nick, Integer[] d, String wC, List<Colour> lC, List<String> lP);
    boolean messageIsDiscard();
    void messageDiscardWeaponCard(String nick, String wS, String wC);
    boolean messageIsValidSecondActionMove(List<Integer> d);
    void messageSecondActionMove(String nick, List<Integer> d);
    boolean messageIsValidSecondActionGrab(String nick, Integer[] d, String wC, String wS, List<Colour> lA, List<String> lP);
    void messageSecondActionGrab(String nick, Integer[] d, String wC, List<Colour> lC, List<String> lP);
    List<String> messageGetPowerUpCard(String nick);
    String messageGetDescriptionPUC(String pC, String nick);
    boolean messageIsValidUsePowerUpCard(String nick, String pC, List<String> l, Colour c);
    void messageUsePowerUpCard(String nick, String pC, List<String> l, Colour c);
    List<String> messageGetWeaponCardUnloaded(String nick);
    boolean messageIsValidReload();
    void messageReload(String nick, String s, int end);
    boolean messageIsValidScoring();
    void messageScoring();
    List<String> messageGetDeadList();
    boolean messageIsValidDiscardCardForSpawnPoint();
    void messageDiscardCardForSpawnPoint(String nick, String s);
    boolean messageIsValidToReplace();
    void messageReplace();
    boolean messageIsValidFinalFrenzyAction(String nick, List<String> l);
    List<String> messageGetWeaponCard(String nick);
    boolean messageIsValidFinalFrenzyAction1(String nick, int d, String wC, List<Integer> lI, List<String> lS, List<Colour> lC, List<String> lP);
    void messageFinalFrenzyAction1(String nick, int d, List<String> lW, String wC, List<Integer> lI, List<String> lS, List<Colour> lC, List<String> lP);
    boolean messageIsValidFinalFrenzyAction2(String nick, List<Integer> d);
    void messageFinalFrenzyAction2(String nick, List<Integer> d);
    boolean messageIsValidFinalFrenzyAction3(String nick, List<Integer> d, String wC, String wS, List<Colour> lC, List<String> lP);
    void messageFinalFrenzyAction3(String nick, List<Integer> d, String wC, List<Colour> lC, List<String> lP);
    boolean messageIsValidFinalFrenzyAction4(String nick, List<Integer> d, String wC, List<Integer> lI, List<String> lS, List<Colour> lC, List<String> lP);
    void messageFinalFrenzyAction4(String nick, List<Integer> d, List<String> lW, String wC, List<Integer> lI, List<String> lS, List<Colour> lC, List<String> lP);
    boolean messageIsValidFinalFrenzyAction5(String nick, List<Integer> d, String wC, String wS, List<Colour> lC, List<String> lS);
    void messageFinalFrenzyAction5(String nick, List<Integer> d, String wC, List<Colour> lC, List<String> lP);
    void messageFinalFrenzyTurnScoring();
    void messageEndTurnFinalFrenzy();
    void messageFinalScoring();
    List<String> messageGetPlayers();
    List<Integer> messageGetScore();
}
