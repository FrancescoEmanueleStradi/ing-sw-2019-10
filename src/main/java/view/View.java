package view;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public interface View extends Remote {

    void askNameAndColour() throws RemoteException;
    void selectSpawnPoint()throws RemoteException;
    void action1()throws RemoteException;
    void action2()throws RemoteException;
    boolean doYouWantToUsePUC();                                            //TODO is it correct?
    void usePowerUpCard()throws RemoteException;
    void reload()throws RemoteException;
    void scoring()throws RemoteException;
    void newSpawnPoint()throws RemoteException;
    void replace()throws RemoteException;
    boolean isFinalFrenzy() throws RemoteException;
    void finalFrenzyTurn()throws RemoteException;
    void endFinalFrenzy()throws RemoteException;
    void finalScoring()throws RemoteException;
}
