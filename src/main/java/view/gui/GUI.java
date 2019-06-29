package view.gui;

import model.Colour;
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
import java.rmi.RemoteException;
import java.util.LinkedList;
import java.util.List;

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
    //private ImageIcon Enjoy;


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
        //Enjoy = new ImageIcon("Images/Enjoy.png");
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

    public void setServer(ServerInterface server) {
        this.server = server;
    }

    @Override
    public void setGame(int game) {
        this.game = game;
    }

    @Override
    public void setIdentifier(int identifier) throws RemoteException {
        this.identifier = identifier;
    }

    @Override
    public String getNickName() {
        return nickName;
    }

    @Override
    public void setInformation(int identifier) throws RemoteException {
        this.nickName = server.getSuspendedName(game, identifier);
        this.colour = server.getSuspendedColour(game, this.nickName);
    }

    @Override
    public void disconnected(int disconnected) throws RemoteException, InterruptedException {
        textArea.append("Player number " + disconnected + " is disconnected");
        this.gameGraphic.revalidate();
    }

    @Override
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


    @Override
    public synchronized void selectSpawnPoint() throws RemoteException, InterruptedException {
        this.server.messageGiveTwoPUCard(game, this.nickName);
        JFrame spawnPoint = new JFrame("Spawn point selection");
        spawnPoint.setLocation(10,10);
        Container c = spawnPoint.getContentPane();
        DiscardPUC d = new DiscardPUC(this, server, game, nickName, this.server.messageGetPowerUpCard(game, this.nickName).get(0), this.server.messageGetPowerUpCard(game, this.nickName).get(1), this.server.messageGetPowerUpCardColour(game, this.nickName).get(0), this.server.messageGetPowerUpCardColour(game, this.nickName).get(1), spawnPoint);
        d.setLayout(new FlowLayout(FlowLayout.LEFT));
        c.add(d);
        spawnPoint.setSize(900,500);
        spawnPoint.setVisible(true);

    }


    @Override
    public synchronized void action1() throws InterruptedException {
        JFrame action = new JFrame(this.nickName + "'s FIRST ACTION");
        action.setLocation(50,50);
        Container c = action.getContentPane();
        Action1 a = new Action1(this, action);
        c.add(a);
        action.setSize(400, 400);
        action.setVisible(true);
    }

    public synchronized void moveFirstAction() throws InterruptedException {
        JFrame move = new JFrame("First action - move");
        move.setLocation(50,50);
        Container c = move.getContentPane();
        Move1 move1 = new Move1(this, server, game, identifier, nickName, move);
        c.add(move1);
        move.setSize(400, 400);
        move.setVisible(true);
    }

    public synchronized void grabFirstAction() throws InterruptedException {
        JFrame grab = new JFrame("First action - grab");
        grab.add(new Grab1(this, server, game, identifier, nickName));
    }

    public synchronized void shootFirstAction() throws RemoteException, InterruptedException {
        JFrame shoot = new JFrame("First action - shoot");
        shoot.add(new Shoot1(this, server, game, identifier, nickName));
    }

    @Override
    public synchronized void action2() throws InterruptedException {
        JFrame action = new JFrame(this.nickName + "'s SECOND ACTION");
        action.setLocation(50,50);
        Container c = action.getContentPane();
        Action2 a = new Action2(this, action);
        c.add(a);
        action.setSize(400, 400);
        action.setVisible(true);
    }

    public synchronized void moveSecondAction() throws InterruptedException {
        JFrame move = new JFrame("First action - move");
        move.setLocation(50,50);
        Container c = move.getContentPane();
        Move2 move2 = new Move2(this, server, game, identifier, nickName, move);
        c.add(move2);
        move.setSize(400, 400);
        move.setVisible(true);
    }

    public synchronized void grabSecondAction() throws InterruptedException {
        JFrame move = new JFrame("Second action - grab");
    }

    public synchronized void shootSecondAction() throws InterruptedException {

    }

    @Override
    public void usePowerUpCard() {
        //TODO
    }

    public void usePowerUpCard2() {
        //TODO
    }

    public void usePowerUpCard3() {
        //TODO
    }

    @Override
    public boolean doYouWantToUsePUC() {
        JFrame jF = new JFrame("Power-Up Card");
        jF.setLocation(50,50);
        Container c = jF.getContentPane();
        WantUsePUCPanel w = new WantUsePUCPanel(this, jF);
        c.add(w);
        jF.setSize(400,400);
        jF.setVisible(true);
        return true;
    }

    public boolean doYouWantToUsePUC2() {
        JFrame jF = new JFrame("Power-Up Card");
        jF.setLocation(50,50);
        Container c = jF.getContentPane();
        WantUsePUCPanel2 w = new WantUsePUCPanel2(this, jF);
        c.add(w);
        jF.setSize(400,400);
        jF.setVisible(true);
        return true;
    }

    public boolean doYouWantToUsePUC3() {
        JFrame jF = new JFrame("Power-Up Card");
        jF.setLocation(50,50);
        Container c = jF.getContentPane();
        WantUsePUCPanel3 w = new WantUsePUCPanel3(this, jF);
        c.add(w);
        jF.setSize(400,400);
        jF.setVisible(true);
        return true;
    }

    @Override
    public void reload() throws RemoteException, InterruptedException {
        CardLinkList l = new CardLinkList();
        JFrame jF = new JFrame();
        ReloadPanel reloadPanel = new ReloadPanel();
        jF.add(new Label("Choose the weapon card you want to reload, or 'end' if you don't need/want to"));
        for(ImageIcon i : l.getImageIconFromName(this.server.messageGetWeaponCardUnloaded(game, this.nickName), new LinkedList<>())) {
            jF.add(new JLabel(i));
            reloadPanel.addButton(l.getNamefromImageIcon(i));
        }
        jF.add(reloadPanel);
        jF.setVisible(true);
        //TODO

















        /*this.server.messageGetWeaponCardUnloaded(game, this.nickName).forEach(System.out::println);
        int i = 0;
        while(i == 0) {
            System.out.println("Choose the weapon card you want to reload, or 'end' if you don't need/want to");
            String s = in.nextLine();
            if(s.equals("end"))
                break;
            System.out.println("Enter 0 if you want to reload another card, otherwise 1");
            i = in.nextInt();
            if(this.server.messageIsValidReload(game, this.nickName, s))
                this.server.messageReload(game, this.nickName, s, i);
            else
                System.out.println("You can't reload now");
        }*/
    }

    @Override
    public void scoring() throws RemoteException {
        if(this.server.messageIsValidScoring(game))
            this.server.messageScoring(game);
    }

    @Override
    public void newSpawnPoint() throws RemoteException {
        //TODO
    }

    @Override
    public void replace() throws RemoteException {
        if(this.server.messageIsValidToReplace(game))
            this.server.messageReplace(game);
    }

    @Override
    public void finalFrenzyTurn() {
        //TODO
    }

    @Override
    public void endFinalFrenzy() throws RemoteException {
        this.server.messageEndTurnFinalFrenzy(game);
        textArea.append("We are calculating the result");
        this.gameGraphic.revalidate();
    }

    @Override
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

    @Override
    public void printPlayer(List<String> information) throws RemoteException {
        players.add(new PlayerName(information.get(0), information.get(1), information.get(2)));
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
    public void printType() throws RemoteException {
        this.gameGraphic.setSize(1400, 1400);
        this.container = gameGraphic.getContentPane();
        if(type == 1) {
            ImageIcon Left14Grid = new ImageIcon("Images/Left14Grid.png");
            ImageIcon Right12Grid = new ImageIcon("Images/Right12Grid.png");
            JLabel L14Grid = new JLabel(Left14Grid);
            JLabel R12Grid = new JLabel(Right12Grid);
            this.container.add(L14Grid);
            this.container.add(R12Grid);
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
