package view.gui.socket;

import model.Colour;
import network.MyTask;
import view.*;
import view.gui.actions.Action1;
import view.gui.actions.Action2;
import view.gui.actions.grab.Grab1;
import view.gui.actions.move.Move1;
import view.gui.actions.move.Move2;
import view.gui.actions.shoot.Shoot1;

import java.awt.*;
import javax.swing.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.net.Socket;
import java.rmi.RemoteException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.Timer;

public class GUISocket implements View, Serializable {

    private int game;
    private int identifier;
    private int type;
    private Socket socket;
    private PrintWriter socketOut;
    private Scanner socketIn;
    private String nickName;
    private Colour colour;
    private Container container;
    private JFrame gameGraphic;
    private JPanel gridGraphic;
    private JScrollPane scrollPane;
    private TextArea textArea;
    private JPanel players;


    public GUISocket(int game, Socket socket) throws IOException {
        super();
        this.game = game;
        this.socket = socket;
        this.socketOut = new PrintWriter(socket.getOutputStream(), true);
        this.socketIn = new Scanner(socket.getInputStream());
        this.container = new Container();
        this.gameGraphic = new JFrame();
        this.gridGraphic = new JPanel();
        this.textArea = new TextArea();
        this.scrollPane = new JScrollPane (textArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        this.players = new JPanel();
    }

    @Override
    public int getGame() {
        return game;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public void setColour(Colour colour) {
        this.colour = colour;
    }


    @Override
    public View getView() {
        return this;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void setGame(int game) {
        this.game = game;
    }

    @Override
    public void setIdentifier(int identifier) {
        this.identifier = identifier;
    }

    @Override
    public String getNickName() {
        return nickName;
    }

    @Override
    public void setInformation(int identifier) {
        socketOut.println("Get Suspended Name");
        socketOut.println(game);
        socketOut.println(identifier);
        this.nickName = socketIn.nextLine();
        this.nickName = socketIn.nextLine();

        socketOut.println("Get Suspended Colour");
        socketOut.println(game);
        socketOut.println(this.nickName);
        this.colour = Colour.valueOf(socketIn.nextLine());
        this.identifier = identifier;
    }

    @Override
    public void disconnected(int disconnected) {
        textArea.append("Player number " + disconnected + " is disconnected");
        this.gameGraphic.revalidate();
    }

    @Override
    public synchronized void askNameAndColour() {
        socketOut.println("Message Game Is Not Started");
        socketOut.println(game);
        String gameIsNotStarted = socketIn.nextLine();

        try {
            if (gameIsNotStarted.equals("true") && this.identifier == 1) {
                JFrame f = new JFrame("Name, colour and type");
                f.setLocation(10, 10);
                Container c = f.getContentPane();
                TakeInformationSocket p = new TakeInformationSocket(this, this.socket, this.game, this.identifier, f);
                p.setLayout(new FlowLayout(FlowLayout.LEFT));
                c.add(p);
                f.setSize(500, 500);
                f.setVisible(true);
            } else {
                JFrame f = new JFrame("Name and colour");
                f.setLocation(10, 10);
                Container c = f.getContentPane();
                TakeInformationSocket p = new TakeInformationSocket(this, this.socket, this.game, this.identifier, f);
                p.setLayout(new FlowLayout(FlowLayout.LEFT));
                c.add(p);
                f.setSize(500, 500);
                f.setVisible(true);
            }
        }catch (IOException ex) {

        }
    }


    @Override
    public synchronized void selectSpawnPoint() throws RemoteException, InterruptedException {
        socketOut.println("Message Give Two PU Card");
        socketOut.println(game);
        socketOut.println(nickName);

        socketOut.println("Message Get Initial PowerUp Card");
        socketOut.println(game);
        socketOut.println(nickName);
        String pUCard1 = socketIn.nextLine();
        String pUCard2 = socketIn.nextLine();

        socketOut.println("Message Get Initial PowerUp Card Colour");
        socketOut.println(game);
        socketOut.println(nickName);
        String pUCard1Colour = socketIn.nextLine();
        String pUCard2Colour = socketIn.nextLine();

        try {
            JFrame spawnPoint = new JFrame("Spawn point selection");
            spawnPoint.setLocation(10, 10);
            Container c = spawnPoint.getContentPane();                  //TODO image
            DiscardPUCSocket d = new DiscardPUCSocket(this, socket, game, nickName, pUCard1, pUCard2, pUCard1Colour, pUCard2Colour, spawnPoint);
            d.setLayout(new FlowLayout(FlowLayout.LEFT));
            c.add(d);
            spawnPoint.setSize(900, 500);
            spawnPoint.setVisible(true);
        }catch (IOException ex) {

        }
    }


    @Override
    public synchronized void action1() throws InterruptedException {
        JFrame action = new JFrame(this.nickName + "'s FIRST ACTION");
        action.setLocation(50,50);
        Container c = action.getContentPane();
        Action1 a = new Action1(null, this, action);
        c.add(a);
        action.setSize(400, 400);
        action.setVisible(true);
    }

    public synchronized void moveFirstAction() throws IOException {
        JFrame move = new JFrame("First action - move");
        move.setLocation(50,50);
        Container c = move.getContentPane();
        Move1Socket move1 = new Move1Socket(this, socket, game, identifier, nickName, move);
        c.add(move1);
        move.setSize(400, 400);
        move.setVisible(true);
    }

    public synchronized void grabFirstAction() throws IOException {
        JFrame grab = new JFrame("First action - grab");
        grab.add(new Grab1Socket(this, socket, game, identifier, nickName));
    }

    public synchronized void shootFirstAction() throws RemoteException, InterruptedException {
        JFrame shoot = new JFrame("First action - shoot");
        //shoot.add(new Shoot1(this, socket, game, identifier, nickName));
    }

    @Override
    public synchronized void action2() throws InterruptedException {
        JFrame action = new JFrame(this.nickName + "'s SECOND ACTION");
        action.setLocation(50,50);
        Container c = action.getContentPane();
        Action2 a = new Action2(null, this, action);
        c.add(a);
        action.setSize(400, 400);
        action.setVisible(true);
    }

    public synchronized void moveSecondAction() throws InterruptedException {
        JFrame move = new JFrame("First action - move");
        move.setLocation(50,50);
        Container c = move.getContentPane();
        /*Move2 move2 = new Move2(this, socket, game, identifier, nickName, move);
        c.add(move2);*/
        move.setSize(400, 400);
        move.setVisible(true);
    }

    public synchronized void grabSecondAction() throws InterruptedException {
        JFrame move = new JFrame("Second action - grab");
    }

    public synchronized void shootSecondAction() throws InterruptedException {

    }

    @Override
    public void usePowerUpCard() throws RemoteException{                    //TODO image
        /*MyTask task = new MyTask(game, identifier, this.getNickName(), socket);
        Timer timer = new Timer();
        timer.schedule(task, 150000);
        JFrame jF = new JFrame("Use Power-Up Card");
        jF.setLocation(150,150);
        Container c = jF.getContentPane();
        UsePUCPanel u = new UsePUCPanel(this, socket, jF , game, nickName, timer, 1);
        c.add(u);
        jF.setSize(400,400);
        jF.setVisible(true);*/
    }

    public void usePowerUpCard2() throws RemoteException{
        /*MyTask task = new MyTask(game, identifier, this.getNickName(), socket);
        Timer timer = new Timer();
        timer.schedule(task, 150000);
        JFrame jF = new JFrame("Use Power-Up Card");
        jF.setLocation(150,150);
        Container c = jF.getContentPane();
        UsePUCPanel u = new UsePUCPanel(this, socket, jF , game, nickName, timer, 2);
        c.add(u);
        jF.setSize(400,400);
        jF.setVisible(true);*/
    }

    public void usePowerUpCard3() throws RemoteException {
        /*MyTask task = new MyTask(game, identifier, this.getNickName(), socket);
        Timer timer = new Timer();
        timer.schedule(task, 150000);
        JFrame jF = new JFrame("Use Power-Up Card");
        jF.setLocation(150,150);
        Container c = jF.getContentPane();
        UsePUCPanel u = new UsePUCPanel(this, socket, jF , game, nickName, timer, 3);
        c.add(u);
        jF.setSize(400,400);
        jF.setVisible(true);*/
    }

    public void TGPUC(Timer timer, int turn) throws RemoteException, InterruptedException{

    }

    public void TSPUC(Timer timer, int turn) throws RemoteException, InterruptedException{

    }

    public void NPUC(Timer timer, int turn) throws RemoteException, InterruptedException{

    }

    public void TPUC(Timer timer, int turn) throws RemoteException, InterruptedException{

    }

    @Override
    public boolean doYouWantToUsePUC() throws RemoteException{
        socketOut.println("Stop Game");
        socketOut.println(game);
        String stopGame = socketIn.nextLine();

        if (stopGame.equals("true"))
            this.endFinalFrenzy();

        JFrame jF = new JFrame("Power-Up Card");
        jF.setLocation(50,50);
        Container c = jF.getContentPane();
        /*WantUsePUCPanel w = new WantUsePUCPanel(this, jF);
        c.add(w);*/
        jF.setSize(400,400);
        jF.setVisible(true);
        return true;
    }

    public void doYouWantToUsePUC2() {
        JFrame jF = new JFrame("Power-Up Card");
        jF.setLocation(50,50);
        Container c = jF.getContentPane();
        /*WantUsePUCPanel2 w = new WantUsePUCPanel2(this, jF);
        c.add(w);*/
        jF.setSize(400,400);
        jF.setVisible(true);
    }

    public void doYouWantToUsePUC3() {
        JFrame jF = new JFrame("Power-Up Card");
        jF.setLocation(50,50);
        Container c = jF.getContentPane();
        /*WantUsePUCPanel3 w = new WantUsePUCPanel3(this, jF);
        c.add(w);*/
        jF.setSize(400,400);
        jF.setVisible(true);
    }

    @Override
    public void reload() throws RemoteException, InterruptedException {
        JFrame jF = new JFrame("Reload");
        jF.setLocation(50,50);
        Container c = jF.getContentPane();
        /*ReloadPanel reloadPanel = new ReloadPanel(this, socket, jF, game, nickName);
        c.add(reloadPanel);*/
        jF.setSize(400,400);
        jF.setVisible(true);
    }

    @Override
    public void scoring() throws RemoteException {
        socketOut.println("Message Is Valid Scoring");
        socketOut.println(game);

        String isValidScoring = socketIn.nextLine();

        if (isValidScoring.equals("true")) {
            socketOut.println("Message Scoring");
            socketOut.println(game);
        }

        this.replace();
    }

    @Override
    public void newSpawnPoint() throws RemoteException {
            List<String> deadList = new LinkedList<>();
            socketOut.println("Message Get Dead List");
            socketOut.println(game);
            int size = Integer.parseInt(socketIn.nextLine());
            for(int i = 0; i < size; i++)
                deadList.add(socketIn.nextLine());

            if(deadList.contains(this.nickName)) {
            JFrame jF = new JFrame("Reload");
            jF.setLocation(50,50);
            Container c = jF.getContentPane();
            /*NewSpawnPointPanel newSpawnPointPanel =  new NewSpawnPointPanel(this, socket, jF, game, nickName);
            c.add(newSpawnPointPanel);*/
            jF.setSize(400,400);
            jF.setVisible(true);
        }

        while(true){
            socketOut.println("Stop Game");
            socketOut.println(game);
            String stopGame = socketIn.nextLine();

            if (stopGame.equals("true"))
                this.endFinalFrenzy();

            socketOut.println("Is My Turn");
            socketOut.println(game);
            socketOut.println(identifier);
            String isMyTurn = socketIn.nextLine();

            if (isMyTurn.equals("true"))
                break;
        }
        socketOut.println("Is Not Final Frenzy");
        socketOut.println(game);
        String isNotFF = socketIn.nextLine();

        if (isNotFF.equals("true"))
            this.doYouWantToUsePUC();
        else
            this.finalFrenzyTurn();
    }

    @Override
    public void replace() throws RemoteException {
        socketOut.println("Message Replace");
        socketOut.println(game);

        socketOut.println("Finish Turn");
        socketOut.println(game);

        socketOut.println("Stop Game");
        socketOut.println(game);
        String stopGame = socketIn.nextLine();

        if (stopGame.equals("true"))
            this.endFinalFrenzy();
        this.newSpawnPoint();
    }

    @Override
    public void finalFrenzyTurn() {
        //TODO
    }

    @Override
    public void endFinalFrenzy() throws RemoteException {
        socketOut.println("Message End Turn Final Frenzy");
        socketOut.println(game);
        textArea.append("We are calculating the result");
        this.gameGraphic.revalidate();
        this.finalScoring();
    }

    @Override
    public void finalScoring() throws RemoteException {
        socketOut.println("Message Final Scoring");
        socketOut.println(game);

        textArea.append("FINAL SCORE");

        socketOut.println("Message Get Players");
        socketOut.println(game);
        int size = Integer.parseInt(socketIn.nextLine());
        List<String> players = new LinkedList<>();
        for(int i = 0; i < size; i++)
            players.add(socketIn.nextLine());
        players.forEach(textArea::append);

        textArea.append("");

        socketOut.println("Message Get Score");
        List<Integer> score = new LinkedList<>();
        int size1 = Integer.parseInt(socketIn.nextLine());
        for(int i = 0; i < size1; i++)
            score.add(Integer.parseInt(socketIn.nextLine()));

        score.stream().map(a -> Integer.toString(a)).forEach(textArea::append);

        textArea.append("");
        textArea.append("END GAME");
        this.gameGraphic.revalidate();
    }

    @Override
    public void printPlayer(List<String> information) throws RemoteException {
        //players.add(new PlayerName(information.get(0), information.get(1), information.get(2)));
        this.gameGraphic.add(players);
        textArea.append("Player " + information.get(0) + " (identifier " + information.get(2)+ ") whose colour is " + information.get(1) + " is now a player of this game.");
        gameGraphic.revalidate();
    }

    @Override
    public void printScore(List<String> information) throws RemoteException {
        textArea.append("Player: " + information.get(0) + " has now this score: " + information.get(1));
        this.gameGraphic.revalidate();
    }

    @Override
    public void printPosition(List<String> information) throws RemoteException {
        textArea.append("Now Player: " + information.get(0) + " is in the cell " + information.get(1) + " " + information.get(2));
        this.gameGraphic.revalidate();
    }

    @Override
    public void printMark(List<String> information) throws RemoteException {
        textArea.append("Player: " + information.get(0) + "give a new Mark to Player" + information.get(1));
        this.gameGraphic.revalidate();
    }

    @Override
    public void printDamage(List<String> information) throws RemoteException {
        textArea.append("Player: " + information.get(0) + " give " + information.get(1) + " damages to Player: " + information.get(2));
        this.gameGraphic.revalidate();
    }

    @Override
    public void printType() throws RemoteException {                            //TODO image
        this.gameGraphic.setSize(1400, 1400);
        this.container = gameGraphic.getContentPane();
        if(type == 1) {
            ImageIcon Left14Grid = new ImageIcon("Images/Left14Grid.png");
            ImageIcon Right12Grid = new ImageIcon("Images/Right12Grid.png");
            JLabel L14Grid = new JLabel(Left14Grid);
            L14Grid.setSize(300, 300);
            JLabel R12Grid = new JLabel(Right12Grid);
            R12Grid.setSize(300, 300);
            this.container.add(L14Grid).doLayout();
            this.container.add(R12Grid).doLayout();
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

    @Override
    public void setType(int type) throws RemoteException {
        this.type = type;
    }

    @Override
    public void exit() {
        System.exit(0);
    }
}