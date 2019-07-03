package view.gui;

import model.Colour;
import network.MyTask;
import network.ServerInterface;
import view.*;
import view.gui.actions.Action1;
import view.gui.actions.Action2;
import view.gui.actions.grab.Grab1;
import view.gui.actions.move.Move1;
import view.gui.actions.move.Move2;
import view.gui.actions.shoot.Shoot1;

import java.awt.*;
import javax.swing.*;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.LinkedList;
import java.util.List;
import java.util.Timer;

import static javax.swing.ScrollPaneConstants.LOWER_LEFT_CORNER;

public class GUI implements View, Serializable {

    private int game;
    private int identifier;
    private int type;
    private ServerInterface server;
    private String nickName;
    private Colour colour;
    private Container container;
    private JFrame gameGraphic;
    private JPanel gridGraphic;
    private JScrollPane scrollPane;
    private TextArea textArea;
    private JPanel players;


    public GUI(int game, ServerInterface server) throws RemoteException {
        super();
        this.game = game;
        this.server = server;
        this.container = new Container();
        this.gameGraphic = new JFrame();
        this.gridGraphic = new JPanel();
        this.textArea = new TextArea();
        this.scrollPane = new JScrollPane (textArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        this.players = new JPanel();
    }

    public int getGame() {
        return game;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public void setColour(Colour colour) {
        this.colour = colour;
    }

    public View getView() {
        return this;
    }

    public void setServer(ServerInterface server) {
        this.server = server;
    }

    public void setGame(int game) {
        this.game = game;
    }

    public void setIdentifier(int identifier) throws RemoteException {
        this.identifier = identifier;
    }

    public String getNickName() {
        return nickName;
    }

    public void setInformation(int identifier) throws RemoteException {
        this.nickName = server.getSuspendedName(game, identifier);
        this.colour = server.getSuspendedColour(game, this.nickName);
        this.identifier = identifier;
    }

    public void disconnected(int disconnected) throws RemoteException, InterruptedException {
        textArea.append("Player number " + disconnected + " is disconnected");
        this.gameGraphic.revalidate();
    }

    public synchronized void askNameAndColour() throws RemoteException, InterruptedException {
        if(this.server.messageGameIsNotStarted(game) && this.identifier == 1) {
            JFrame f = new JFrame("Name, colour and type");
            f.setLocation(10,10);
            Container c = f.getContentPane();
            TakeInformation p = new TakeInformation(this, this.server, this.game, this.identifier, f);
            p.setLayout(new FlowLayout(FlowLayout.LEFT));
            c.add(p);
            f.setSize(500,500);
            f.setVisible(true);
        }
        else {
            JFrame f = new JFrame("Name and colour");
            f.setLocation(10,10);
            Container c = f.getContentPane();
            TakeInformation p = new TakeInformation(this, this.server, this.game, this.identifier, f);
            p.setLayout(new FlowLayout(FlowLayout.LEFT));
            c.add(p);
            f.setSize(500,500);
            f.setVisible(true);

        }
    }

    public synchronized void selectSpawnPoint() throws RemoteException, InterruptedException {
        this.server.messageGiveTwoPUCard(game, this.nickName);
        JFrame spawnPoint = new JFrame("Spawn point selection");
        spawnPoint.setLocation(10,10);
        Container c = spawnPoint.getContentPane();                  //TODO image
        DiscardPUC d = new DiscardPUC(this, server, game, nickName, this.server.messageGetPowerUpCard(game, this.nickName).get(0), this.server.messageGetPowerUpCard(game, this.nickName).get(1), this.server.messageGetPowerUpCardColour(game, this.nickName).get(0), this.server.messageGetPowerUpCardColour(game, this.nickName).get(1), spawnPoint);
        d.setLayout(new FlowLayout(FlowLayout.LEFT));
        c.add(d);
        spawnPoint.setSize(900,500);
        spawnPoint.setVisible(true);

    }

    public synchronized void action1() throws InterruptedException {
        JFrame action = new JFrame(this.nickName + "'s FIRST ACTION");
        action.setLocation(50,50);
        Container c = action.getContentPane();
        Action1 a = new Action1(this, null, action);
        c.add(a);
        action.setSize(400, 400);
        action.setVisible(true);
    }

    public synchronized void moveFirstAction() throws InterruptedException {
        MyTask task = new MyTask(game, identifier, this.getNickName(), server);
        Timer timer = new Timer();
        timer.schedule(task, 150000);
        JFrame move = new JFrame("First action - move");
        move.setLocation(50,50);
        Container c = move.getContentPane();
        Move1 move1 = new Move1(this, server, game, identifier, nickName, move, timer);
        c.add(move1);
        move.setSize(400, 400);
        move.setVisible(true);
    }

    public synchronized void grabFirstAction() throws InterruptedException {
        MyTask task = new MyTask(game, identifier, this.getNickName(), server);
        Timer timer = new Timer();
        timer.schedule(task, 150000);
        JFrame grab = new JFrame("First action - grab");
        grab.setLocation(50,50);
        Container c = grab.getContentPane();
        Grab1 grab1 = new Grab1(this, server, game, identifier, nickName, grab, timer);
        c.add(grab1);
        grab.setSize(500,500);
        grab.setVisible(true);
    }

    public synchronized void shootFirstAction() throws RemoteException, InterruptedException {
        JFrame shoot = new JFrame("First action - shoot");
        shoot.setLocation(50,50);
        Container c = shoot.getContentPane();
        Shoot1 shoot1 = new Shoot1(this, server, game, identifier, nickName);
        c.add(shoot1);
        shoot.setSize(500,500);
        shoot.setVisible(true);
    }

    public synchronized void action2() throws InterruptedException {
        JFrame action = new JFrame(this.nickName + "'s SECOND ACTION");
        action.setLocation(50,50);
        Container c = action.getContentPane();
        Action2 a = new Action2(this, null, action);
        c.add(a);
        action.setSize(400, 400);
        action.setVisible(true);
    }

    public synchronized void moveSecondAction() throws InterruptedException {
        MyTask task = new MyTask(game, identifier, this.getNickName(), server);
        Timer timer = new Timer();
        timer.schedule(task, 150000);
        JFrame move = new JFrame("First action - move");
        move.setLocation(50,50);
        Container c = move.getContentPane();
        Move2 move2 = new Move2(this, server, game, identifier, nickName, move, timer);
        c.add(move2);
        move.setSize(400, 400);
        move.setVisible(true);
    }

    public synchronized void grabSecondAction() throws InterruptedException {
        MyTask task = new MyTask(game, identifier, this.getNickName(), server);
        Timer timer = new Timer();
        timer.schedule(task, 150000);
        JFrame grab = new JFrame("Second action - grab");
        grab.setLocation(50,50);
        Container c = grab.getContentPane();
        //Grab2 grab2 = new Grab2(this, server, game, identifier, nickName, grab, timer);
        //c.add(grab2);
        grab.setSize(500,500);
        grab.setVisible(true);
    }

    public synchronized void shootSecondAction() throws InterruptedException {
        JFrame shoot = new JFrame("Second action - shoot");
        shoot.setLocation(50,50);
        Container c = shoot.getContentPane();
        //Shoot2 shoot2 = new Shoot2(this, server, game, identifier, nickName);
        //c.add(shoot2);
        shoot.setSize(500,500);
        shoot.setVisible(true);
    }

    //TODO image
    public void usePowerUpCard() throws RemoteException {
        MyTask task = new MyTask(game, identifier, this.getNickName(), server);
        Timer timer = new Timer();
        timer.schedule(task, 150000);
        JFrame jF = new JFrame("Use Power-Up Card");
        jF.setLocation(150,150);
        Container c = jF.getContentPane();
        UsePUCPanel u = new UsePUCPanel(this, server, jF , game, nickName, timer, 1);
        c.add(u);
        jF.setSize(400,400);
        jF.setVisible(true);
    }

    public void usePowerUpCard2() throws RemoteException {
        MyTask task = new MyTask(game, identifier, this.getNickName(), server);
        Timer timer = new Timer();
        timer.schedule(task, 150000);
        JFrame jF = new JFrame("Use Power-Up Card");
        jF.setLocation(150,150);
        Container c = jF.getContentPane();
        UsePUCPanel u = new UsePUCPanel(this, server, jF , game, nickName, timer, 2);
        c.add(u);
        jF.setSize(400,400);
        jF.setVisible(true);
    }

    public void usePowerUpCard3() throws RemoteException {
        MyTask task = new MyTask(game, identifier, this.getNickName(), server);
        Timer timer = new Timer();
        timer.schedule(task, 150000);
        JFrame jF = new JFrame("Use Power-Up Card");
        jF.setLocation(150,150);
        Container c = jF.getContentPane();
        UsePUCPanel u = new UsePUCPanel(this, server, jF , game, nickName, timer, 3);
        c.add(u);
        jF.setSize(400,400);
        jF.setVisible(true);
    }

    public void TGPUC(Timer timer, int turn, String col) throws RemoteException, InterruptedException{
        JFrame jF = new JFrame("Effect");
        jF.setLocation(150,150);
        Container c = jF.getContentPane();
        TGPUCPanel u = new TGPUCPanel(this, server, jF , game, nickName, timer, turn, col);
        c.add(u);
        jF.setSize(400,400);
        jF.setVisible(true);
    }

    public void TSPUC(Timer timer, int turn, String col) throws RemoteException, InterruptedException{
        JFrame jF = new JFrame("Effect");
        jF.setLocation(150,150);
        Container c = jF.getContentPane();
        TSPUCPanel u = new TSPUCPanel(this, server, jF , game, nickName, timer, turn, col);
        c.add(u);
        jF.setSize(400,400);
        jF.setVisible(true);
    }

    public void NPUC(Timer timer, int turn, String col) throws RemoteException, InterruptedException{
        JFrame jF = new JFrame("Effect");
        jF.setLocation(150,150);
        Container c = jF.getContentPane();
        NPUCPanel u = new NPUCPanel(this, server, jF , game, nickName, timer, turn, col);
        c.add(u);
        jF.setSize(400,400);
        jF.setVisible(true);
    }

    public void TPUC(Timer timer, int turn, String col) throws RemoteException, InterruptedException{
        JFrame jF = new JFrame("Effect");
        jF.setLocation(150,150);
        Container c = jF.getContentPane();
        TPUCPanel u = new TPUCPanel(this, server, jF , game, nickName, timer, turn, col);
        c.add(u);
        jF.setSize(400,400);
        jF.setVisible(true);
    }

    public boolean doYouWantToUsePUC() throws RemoteException{
        if(server.stopGame(game))
            this.endFinalFrenzy();
        JFrame jF = new JFrame("Power-Up Card");
        jF.setLocation(50,50);
        Container c = jF.getContentPane();
        WantUsePUCPanel w = new WantUsePUCPanel(this, jF);
        c.add(w);
        jF.setSize(400,400);
        jF.setVisible(true);
        return true;
    }

    public void doYouWantToUsePUC2() {
        JFrame jF = new JFrame("Power-Up Card");
        jF.setLocation(50,50);
        Container c = jF.getContentPane();
        WantUsePUCPanel2 w = new WantUsePUCPanel2(this, jF);
        c.add(w);
        jF.setSize(400,400);
        jF.setVisible(true);
    }

    public void doYouWantToUsePUC3() {
        JFrame jF = new JFrame("Power-Up Card");
        jF.setLocation(50,50);
        Container c = jF.getContentPane();
        WantUsePUCPanel3 w = new WantUsePUCPanel3(this, jF);
        c.add(w);
        jF.setSize(400,400);
        jF.setVisible(true);
    }

    public void reload() throws RemoteException, InterruptedException {
        JFrame jF = new JFrame("Reload");
        jF.setLocation(50,50);
        Container c = jF.getContentPane();
        ReloadPanel reloadPanel = new ReloadPanel(this, server, jF, game, nickName);
        c.add(reloadPanel);
        jF.setSize(400,400);
        jF.setVisible(true);
    }

    public void scoring() throws RemoteException {
        if(this.server.messageIsValidScoring(game))
            this.server.messageScoring(game);
        this.replace();
    }

    public void newSpawnPoint() throws RemoteException {
        if(this.server.messageGetDeadList(game).contains(this.nickName)){
            JFrame jF = new JFrame("Reload");
            jF.setLocation(50,50);
            Container c = jF.getContentPane();
            NewSpawnPointPanel newSpawnPointPanel =  new NewSpawnPointPanel(this, server, jF, game, nickName);
            c.add(newSpawnPointPanel);
            jF.setSize(400,400);
            jF.setVisible(true);
        }

        while(true){
            if(server.stopGame(game))
                this.endFinalFrenzy();
            if(server.isMyTurn(game, identifier))
                break;
        }
        if(server.isNotFinalFrenzy(game))
            this.doYouWantToUsePUC();
        else
            this.finalFrenzyTurn();
    }

    public void replace() throws RemoteException {
        this.server.messageReplace(game);
        server.finishTurn(game);
        if(server.stopGame(game))
            this.endFinalFrenzy();
        this.newSpawnPoint();
    }

    public void finalFrenzyTurn() {
        //TODO
    }

    public void endFinalFrenzy() throws RemoteException {
        this.server.messageEndTurnFinalFrenzy(game);
        textArea.append("We are calculating the result");
        this.gameGraphic.revalidate();
        this.finalScoring();
    }

    public void finalScoring() throws RemoteException {
        this.server.messageFinalScoring(game);
        textArea.append("FINAL SCORE");
        this.server.messageGetPlayers(game).forEach(textArea::append);
        textArea.append("");
        this.server.messageGetScore(game).stream().map(a -> Integer.toString(a)).forEach(textArea::append);
        textArea.append("");
        textArea.append("END GAME");
        this.gameGraphic.revalidate();
    }

    public void printPlayer(List<String> information) throws RemoteException {
        players.add(new PlayerName(information.get(0), information.get(1), information.get(2)));
        this.gameGraphic.add(players);
        textArea.append("Player " + information.get(0) + " (identifier " + information.get(2)+ ") whose colour is " + information.get(1) + " is now a player of this game.");
        gameGraphic.revalidate();
    }

    public void printScore(List<String> information) throws RemoteException {
        textArea.append("Player: " + information.get(0) + " has now this score: " + information.get(1));
        this.gameGraphic.revalidate();
    }

    public void printPosition(List<String> information) throws RemoteException {
        textArea.append("Now Player: " + information.get(0) + " is in the cell " + information.get(1) + " " + information.get(2));
        this.gameGraphic.revalidate();
    }

    public void printMark(List<String> information) throws RemoteException {
        textArea.append("Player: " + information.get(0) + "give a new Mark to Player" + information.get(1));
        this.gameGraphic.revalidate();
    }

    public void printDamage(List<String> information) throws RemoteException {
        textArea.append("Player: " + information.get(0) + " give " + information.get(1) + " damages to Player: " + information.get(2));
        this.gameGraphic.revalidate();
    }

    //TODO image
    public void printType() throws RemoteException {
        this.gameGraphic.setSize(1400, 1400);
        //this.container = gameGraphic.getContentPane();
        if(type == 1) {
            ImageIcon Left14Grid = new ImageIcon("Images/Left14Grid.png");
            ImageIcon Right12Grid = new ImageIcon("Images/Right12Grid.png");
            JLabel L14Grid = new JLabel(Left14Grid);
            //L14Grid.doLayout();
            L14Grid.setHorizontalAlignment(SwingConstants.CENTER);
            JLabel R12Grid = new JLabel(Right12Grid);
            R12Grid.setHorizontalAlignment(SwingConstants.RIGHT);
            //R12Grid.doLayout();
            //R12Grid.setSize(10, 10);
            gameGraphic.add(L14Grid).setBounds(700, 700, 400, 400);
            gameGraphic.add(R12Grid).setBounds(1000, 700, 400, 400);
            gameGraphic.pack();
            //this.container.add(L14Grid).setSize(10,10);
            //this.container.add(R12Grid).setSize(10,10);
        }
        if(type == 2) {
            ImageIcon Left23Grid = new ImageIcon("Images/Left23Grid.png");
            ImageIcon Right12Grid = new ImageIcon("Images/Right12Grid.png");
            JLabel L23Grid = new JLabel(Left23Grid);
            JLabel R12Grid = new JLabel(Right12Grid);
            L23Grid.setMaximumSize(new Dimension(300, 300));
            R12Grid.setMaximumSize(new Dimension(300, 300));
            L23Grid.setDisplayedMnemonic(SwingConstants.CENTER);
            R12Grid.doLayout();
            this.gridGraphic.add(L23Grid);
            this.gridGraphic.add(R12Grid);
        }
        if(type == 3) {
            ImageIcon Left23Grid = new ImageIcon("Images/Left23Grid.png");
            ImageIcon Right34Grid = new ImageIcon("Images/Right34Grid.png");
            JLabel L23Grid = new JLabel(Left23Grid);
            JLabel R34Grid = new JLabel(Right34Grid);
            this.gridGraphic.add(L23Grid).setBounds(350, 600, 125, 0);
            this.gridGraphic.add(R34Grid).setBounds(475, 600, 350, 0);
        }
        if(type == 4) {
            ImageIcon Left14Grid = new ImageIcon("Images/Left14Grid.png");
            ImageIcon Right34Grid = new ImageIcon("Images/Right34Grid.png");
            JLabel L14Grid = new JLabel(Left14Grid);
            JLabel R34Grid = new JLabel(Right34Grid);
            this.gridGraphic.add(L14Grid).setBounds(350, 600, 125, 0);
            this.gridGraphic.add(R34Grid).setBounds(475, 600, 350, 0);
        }
        //scrollPane.setCorner(LOWER_LEFT_CORNER, new JPanel());
        //players.setLayout(new FlowLayout(FlowLayout.LEFT));
        //container.add(players);
        //container.add(scrollPane);
        gameGraphic.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameGraphic.setVisible(true);
    }

    public void setType(int type) throws RemoteException {
        this.type = type;
    }

    public void exit() {
        System.exit(0);
    }
}