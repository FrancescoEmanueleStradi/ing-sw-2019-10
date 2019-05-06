package view;

import controller.Game;

import java.rmi.RemoteException;

public interface View  {

    void setServer(ServerInterface server);
    void setGame(Game game);
    void askNameAndColour();
    void selectSpawnPoint();
    void action1();
    void action2();
    boolean doYouWantToUsePUC();
    void usePowerUpCard();
    void reload();
    void scoring();
    void newSpawnPoint();
    void replace();
    void finalFrenzyTurn();
    void endFinalFrenzy();
    void finalScoring();
}
