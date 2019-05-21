package view;

import controller.Game;
import model.Colour;
import java.awt.*;
import javax.swing.*;

import java.rmi.RemoteException;
import java.util.List;

public class GUI implements View{

    private int game;
    private int identifier;
    private int type;
    private ServerInterface server;
    private String nickName;
    private Colour colour;

    @Override
    public View getView() {
        return this;
    }

    @Override
    public void setServer(ServerInterface server) {
        this.server = server;
    }

    @Override
    public void setGame(int game){
        this.game = game;
    }

    @Override
    public void setIdentifier(int identifier) throws RemoteException{
        this.identifier = identifier;
    }

    @Override
    public String getNickName() {
        return nickName;
    }

    @Override
    public void setInformation(int identifier) throws RemoteException{
        this.nickName = server.getSuspendedName(game, identifier);
        this.colour = server.getSuspendedColour(game, this.nickName);
    }

    @Override
    public void disconnected(int disconnected) throws RemoteException, InterruptedException{
        JFrame f = new JFrame("Disconnected");
        Container c = f.getContentPane();
        StandardPanel panel = new StandardPanel(disconnected, server);
        c.add(panel);
        f.show();
    }

    @Override
    public void askNameAndColour() throws RemoteException{
        if (this.server.messageGameIsNotStarted(game)) {
            JFrame f = new JFrame("Enter your name");
            Container c = f.getContentPane();
            TakeInformation p = new TakeInformation();
            c.add(p);
            f.addWindowListener( new Terminator() );
            f.setSize(300,120);
            f.show();


        }
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
    public void scoring() throws RemoteException{
        if(this.server.messageIsValidScoring(game))
            this.server.messageScoring(game);
    }

    @Override
    public void newSpawnPoint() throws RemoteException{
        //TODO
    }

    @Override
    public void replace() throws RemoteException{
        if(this.server.messageIsValidToReplace(game))
            this.server.messageReplace(game);
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


    @Override
    public void printScore(java.util.List<String> information) throws RemoteException{
        //TODO
    }

    @Override
    public void printPosition(java.util.List<String> information) throws RemoteException{
        //TODO
    }

    @Override
    public void printMark(java.util.List<String> information) throws RemoteException{
        //TODO
    }

    @Override
    public void printDamage(List<String> information) throws RemoteException{
        //TODO
    }

    @Override
    public void printType() throws RemoteException{
        //TODO
    }

    @Override
    public void setType(int type) throws RemoteException{
        this.type = type;
    }
}
