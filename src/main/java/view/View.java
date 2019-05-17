package view;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface View extends Remote {

    View getView() throws RemoteException;
    void setServer(ServerInterface server)throws RemoteException;
    void setGame(int game)throws RemoteException;
    void setIdentifier(int identifier) throws RemoteException;
    String getNickName() throws RemoteException;
    void setInformation(int identifier) throws RemoteException;
    void disconnected(int disconnected) throws RemoteException, InterruptedException;
    void askNameAndColour()throws RemoteException;
    void selectSpawnPoint()throws RemoteException;
    void action1()throws RemoteException;
    void action2()throws RemoteException;
    boolean doYouWantToUsePUC()throws RemoteException;
    void usePowerUpCard()throws RemoteException;
    void reload()throws RemoteException;
    void scoring()throws RemoteException;
    void newSpawnPoint()throws RemoteException;
    void replace()throws RemoteException;
    void finalFrenzyTurn()throws RemoteException;
    void endFinalFrenzy()throws RemoteException;
    void finalScoring()throws RemoteException;

    void printScore(List<String> information) throws RemoteException;
    void printPosition(List<String> information) throws RemoteException;
    void printMark(List<String> information) throws RemoteException;
    void printDamage(List<String> information) throws RemoteException;
    void printType(int type) throws RemoteException;
}
