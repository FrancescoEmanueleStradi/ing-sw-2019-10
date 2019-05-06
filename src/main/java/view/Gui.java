package view;

import controller.Game;

import java.rmi.RemoteException;

public class Gui implements View{

    private Game game;
    private ServerInterface server;

    @Override
    public void setServer(ServerInterface server) {
        this.server = server;
    }

    @Override
    public void setGame(Game game){
        //TODO
    }

    @Override
    public void askNameAndColour() {
        //TODO
    }

    @Override
    public void selectSpawnPoint(){
        //TODO
    }

    @Override
    public void action1(){
        //TODO
    }

    @Override
    public void action2(){
        //TODO
    }

    @Override
    public void usePowerUpCard(){
        //TODO
    }

    @Override
    public boolean doYouWantToUsePUC(){
        return true;
        //TODO
    }

    @Override
    public void reload(){
        //TODO
    }

    @Override
    public void scoring(){
        //TODO
    }

    @Override
    public void newSpawnPoint(){
        //TODO
    }

    @Override
    public void replace(){
        //TODO
    }

    @Override
    public void finalFrenzyTurn() {
        //TODO
    }

    @Override
    public void endFinalFrenzy(){
        //TODO
    }

    @Override
    public void finalScoring(){
        //TODO
    }
}
