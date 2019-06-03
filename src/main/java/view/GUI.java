package view;

import model.Colour;
import java.awt.*;
import javax.swing.*;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.LinkedList;
import java.util.List;

public class GUI implements View, Serializable {

    private int game;
    private int identifier;
    private int type;
    private boolean block = false;
    private ServerInterface server;
    private String nickName;
    private Colour colour;
    private JFrame gameGraphic;
    private Window gridGraphic;
    private JScrollPane scrollPane;
    private TextArea textArea;
    private JPanel players;
    //private ImageIcon Enjoy;


    public GUI(int game, ServerInterface server) throws RemoteException {
        super();
        this.game = game;
        this.server = server;
        this.gameGraphic = new JFrame();
        //Enjoy = new ImageIcon("Images/Enjoy.png");
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public void setColour(Colour colour) {
        this.colour = colour;
    }

    public void setBlock(boolean block) {
        this.block = block;
    }

    public boolean isBlock() {
        return block;
    }

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
        textArea.append("Player number " + disconnected + " is disconnected");
        this.gameGraphic.revalidate();
    }

    @Override
    public synchronized void askNameAndColour() throws RemoteException, InterruptedException{
        if (this.server.messageGameIsNotStarted(game) && this.identifier == 1) {
            JFrame f = new JFrame("Name, colour and type");
            f.setLocation(10,10);
            Container c = f.getContentPane();
            TakeInformation p = new TakeInformation(this, this.server, this.game, this.identifier);
            p.setLayout(new FlowLayout(FlowLayout.LEFT));
            c.add(p);
            //f.addWindowListener( new Terminator());
            f.setSize(500,500);
            f.setVisible(true);
            wait();
        }
        else{
            JFrame f = new JFrame("Name and colour");
            f.setLocation(10,10);
            Container c = f.getContentPane();
            TakeInformation p = new TakeInformation(this, this.server, this.game, this.identifier);
            p.setLayout(new FlowLayout(FlowLayout.LEFT));
            c.add(p);
            //f.addWindowListener( new Terminator());
            f.setSize(500,500);
            f.setVisible(true);
            wait();
        }
    }


    @Override
    public void selectSpawnPoint() throws RemoteException, InterruptedException{
        CardLinkList l = new CardLinkList();
        this.server.messageGiveTwoPUCard(game, this.nickName);
        Container spawnPoint = new Container();
        spawnPoint.add(new JLabel("The following are " + this.nickName +"'s starting PowerUpCards"));
        spawnPoint.add(new JLabel(l.getImageIconFromName(this.server.messageGetPowerUpCard(game, this.nickName).get(0), this.server.messageGetPowerUpCardColour(game, this.nickName).get(0))));
        spawnPoint.add(new JLabel(l.getImageIconFromName(this.server.messageGetPowerUpCard(game, this.nickName).get(1), this.server.messageGetPowerUpCardColour(game, this.nickName).get(1))));
        spawnPoint.add(new JLabel("\n---------SPAWN POINT SELECT---------\n"));
        spawnPoint.add(new DiscardPUC(this, server, game, nickName, this.server.messageGetPowerUpCard(game, this.nickName).get(0), this.server.messageGetPowerUpCard(game, this.nickName).get(1), this.server.messageGetPowerUpCardColour(game, this.nickName).get(0), this.server.messageGetPowerUpCardColour(game, this.nickName).get(1)));
        spawnPoint.setVisible(true);
        wait();
    }


    @Override
    public synchronized void action1() throws InterruptedException{
        JFrame action = new JFrame(this.nickName + "'s FIRST ACTION");
        action.add(new Action1(this));
        action.setVisible(true);
        wait();
    }

    public synchronized void moveFirstAction() throws InterruptedException{
        JFrame move = new JFrame("First action - move");
        move.add(new Move1(this, server, game, identifier, nickName));
        move.setVisible(true);
        wait();
    }

    public synchronized void grabFirstAction() throws InterruptedException{
        JFrame grab = new JFrame("First action - grab");
        grab.add(new Grab1(this, server, game, identifier, nickName));
    }

    public synchronized void shootFirstAction() throws InterruptedException{

    }

    @Override
    public void action2() throws InterruptedException{
        JFrame action = new JFrame(this.nickName + "'s SECOND ACTION");
        action.add(new Action1(this));
        action.setVisible(true);
        wait();
    }

    public synchronized void moveSecondAction() throws InterruptedException{
        JFrame move = new JFrame("Second action - move");                     //TODO
        move.add(new Move2(this, server, game, identifier, nickName));
        move.setVisible(true);
        wait();
    }

    public synchronized void grabSecondAction() throws InterruptedException{
        JFrame move = new JFrame("Second action - grab");
    }

    public synchronized void shootSecondAction() throws InterruptedException{

    }

    @Override
    public void usePowerUpCard(){
        //TODO
    }

    @Override
    public boolean doYouWantToUsePUC(){
        JFrame jF = new JFrame();
        jF.add(new Label("Do you want to use Power-Up Card)"));

        return true;
    }

    @Override
    public void reload() throws RemoteException, InterruptedException{
        CardLinkList l = new CardLinkList();
        JFrame jF = new JFrame();
        ReloadPanel reloadPanel = new ReloadPanel();
        jF.add(new Label("Choose the weapon card you want to reload, or 'end' if you don't need/want to"));
        for(ImageIcon i : l.getImageIconFromName(this.server.messageGetWeaponCardUnloaded(game, this.nickName), new LinkedList<>())){
            jF.add(new JLabel(i));
            reloadPanel.addButton(l.getNamefromImageIcon(i));
        }
        jF.add(reloadPanel);
        jF.setVisible(true);
        wait();
        //TODO

















        /*this.server.messageGetWeaponCardUnloaded(game, this.nickName).forEach(System.out::println);
        int i = 0;
        while (i == 0) {
            System.out.println("Choose the weapon card you want to reload, or 'end' if you don't need/want to");
            String s = in.nextLine();
            if (s.equals("end"))
                break;
            System.out.println("Enter 0 if you want to reload another card, otherwise 1");
            i = in.nextInt();
            if (this.server.messageIsValidReload(game, this.nickName, s))
                this.server.messageReload(game, this.nickName, s, i);
            else
                System.out.println("You can't reload now");
        }*/
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
    public void endFinalFrenzy() throws RemoteException{
        this.server.messageEndTurnFinalFrenzy(game);
        textArea.append("We are calculating the result");
        this.gameGraphic.revalidate();
    }

    @Override
    public void finalScoring() throws RemoteException{
        this.server.messageFinalScoring(game);
        textArea.append("FINAL SCORE");
        this.server.messageGetPlayers(game).forEach(textArea::append);
        textArea.append("");
        this.server.messageGetScore(game).stream().map(a -> Integer.toString(a)).forEach(textArea::append);
        textArea.append("");
        textArea.append("END GAME");
        this.gameGraphic.revalidate();
    }

    @Override
    public void printPlayer(List<String> information) throws RemoteException{
        this.players = new JPanel();
        players.add(new PlayerName(information.get(0), information.get(1), information.get(2)));
        players.setBounds(0,350, 50, 0);
        this.gameGraphic.add(players);
        //TODO
        gameGraphic.revalidate();
    }

    @Override
    public void printScore(List<String> information) throws RemoteException{
        textArea.append("Player: " + information.get(0) + " has now this score: " + information.get(1));
        this.gameGraphic.revalidate();
    }

    @Override
    public void printPosition(List<String> information) throws RemoteException{
        textArea.append("Now Player: " + information.get(0) + " is in the cell " + information.get(1) + " " + information.get(2));
        //TODO
        this.gameGraphic.revalidate();
    }

    @Override
    public void printMark(List<String> information) throws RemoteException{
        textArea.append("Player: " + information.get(0) + "give a new Mark to Player" + information.get(1));
        this.gameGraphic.revalidate();
    }

    @Override
    public void printDamage(List<String> information) throws RemoteException{
        textArea.append("Player: " + information.get(0) + " give " + information.get(1) + " damages to Player: " + information.get(2));
        //TODO
        this.gameGraphic.revalidate();
    }

    @Override
    public void printType() throws RemoteException{
        this.gameGraphic.setSize(700, 700);
        if(type == 1){
            ImageIcon Left14Grid = new ImageIcon("Images/Left14Grid.png");
            ImageIcon Right12Grid = new ImageIcon("Images/Right12Grid.png");
            JLabel L14Grid = new JLabel(Left14Grid);
            JLabel R12Grid = new JLabel(Right12Grid);
            this.gameGraphic.add(L14Grid).setBounds(350, 600, 125, 0);
            this.gameGraphic.add(R12Grid).setBounds(475, 600, 350, 0);
        }
        if(type == 2){
            ImageIcon Left23Grid = new ImageIcon("Images/Left23Grid.png");
            ImageIcon Right12Grid = new ImageIcon("Images/Right12Grid.png");
            JLabel L23Grid = new JLabel(Left23Grid);
            JLabel R12Grid = new JLabel(Right12Grid);
            this.gameGraphic.add(L23Grid).setBounds(350, 600, 125, 0);
            this.gameGraphic.add(R12Grid).setBounds(475, 600, 350, 0);
        }
        if(type == 3){
            ImageIcon Left23Grid = new ImageIcon("Images/Left23Grid.png");
            ImageIcon Right34Grid = new ImageIcon("Images/Right34Grid.png");
            JLabel L23Grid = new JLabel(Left23Grid);
            JLabel R34Grid = new JLabel(Right34Grid);
            this.gameGraphic.add(L23Grid).setBounds(350, 600, 125, 0);
            this.gameGraphic.add(R34Grid).setBounds(475, 600, 350, 0);
        }
        if(type == 4){
            ImageIcon Left14Grid = new ImageIcon("Images/Left14Grid.png");
            ImageIcon Right34Grid = new ImageIcon("Images/Right34Grid.png");
            JLabel L14Grid = new JLabel(Left14Grid);
            JLabel R34Grid = new JLabel(Right34Grid);
            this.gameGraphic.add(L14Grid).setBounds(350, 600, 125, 0);
            this.gameGraphic.add(R34Grid).setBounds(475, 600, 350, 0);
        }
        textArea = new TextArea();
        scrollPane = new JScrollPane (textArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setBounds(0, 100, 100, 0);
        gameGraphic.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameGraphic.add(scrollPane);
        gameGraphic.setVisible(true);
    }

    @Override
    public void setType(int type) throws RemoteException{
        this.type = type;
    }

    @Override
    public void exit() {
        System.exit(0);
    }
}
