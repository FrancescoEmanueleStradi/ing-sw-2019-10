package view;

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
    void messageAskNameAndColour(int game, int identifier) throws RemoteException;
    void messageSelectSpawnPoint(int game, int identifier)throws RemoteException;
    boolean messageDoYouWantToUsePUC(int game, int identifier)throws RemoteException;
    void messageUsePowerUpCard(int game, int identifier)throws RemoteException;
    void messageAction1(int game, int identifier)throws RemoteException;
    void messageAction2(int game, int identifier)throws RemoteException;
    void messageReload(int game, int identifier)throws RemoteException;
    void messageScoring(int game, int identifier)throws RemoteException;
    void messageNewSpawnPoint(int game, int identifier)throws RemoteException;
    void messageReplace(int game, int identifier)throws RemoteException;
    void messageFinalFrenzyTurn(int game, int identifier)throws RemoteException;
    void messageEndFinalFrenzy(int game, int identifier)throws RemoteException;
    void messageFinalScoring(int game, int identifier)throws RemoteException;

}
