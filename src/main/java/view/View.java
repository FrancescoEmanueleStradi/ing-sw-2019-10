package view;

import java.rmi.RemoteException;

public interface View  {

    void setServer(ServerInterface server)throws RemoteException;
    void setGame(int game)throws RemoteException;
    String getNickName() throws RemoteException;
    void setInformation(int identifier) throws RemoteException;
    void disconnected() throws RemoteException, InterruptedException;
    void askNameAndColour()throws RemoteException;
    void selectSpawnPoint()throws RemoteException;
    void action1()throws RemoteException;
    void action2()throws RemoteException;
    boolean doYouWantToUsePUC();
    void usePowerUpCard()throws RemoteException;
    void reload()throws RemoteException;
    void scoring()throws RemoteException;
    void newSpawnPoint()throws RemoteException;
    void replace()throws RemoteException;
    void finalFrenzyTurn()throws RemoteException;
    void endFinalFrenzy()throws RemoteException;
    void finalScoring()throws RemoteException;
}
