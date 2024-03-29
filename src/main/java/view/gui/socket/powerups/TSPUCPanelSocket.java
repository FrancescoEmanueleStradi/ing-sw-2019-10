package view.gui.socket.powerups;

import model.Colour;
import view.gui.socket.GUISocket;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.rmi.RemoteException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

/**
 * Panel for the Targeting Scope powerup requesting parameters before use.
 */
public class TSPUCPanelSocket extends JPanel implements ActionListener {

    private GUISocket gui;
    private Socket socket;
    private PrintWriter socketOut;
    private Scanner socketIn;
    private int game;
    private String nickName;
    private JFrame parent;
    private JTextField txt1;
    private JTextField txt2;
    private JButton b;
    private JButton button;
    private java.util.Timer timer;
    private int turn;
    private String c;
    private List<String> l = new LinkedList<>();

    /**
     * Creates a new TSPUCPanelSocket.
     *
     * @param gui gui
     * @param socket socket
     * @param parent parent frame
     * @param game game
     * @param nickName nickname
     * @param timer timer
     * @param turn turn
     * @param c ammo cube colour
     * @throws IOException I/O exception of some sort
     */
    public TSPUCPanelSocket(GUISocket gui, Socket socket, JFrame parent , int game, String nickName, java.util.Timer timer, int turn, String c) throws IOException {
        this.gui = gui;
        this.socket = socket;
        this.socketOut = new PrintWriter(socket.getOutputStream(), true);
        this.socketIn = new Scanner(socket.getInputStream());
        this.parent = parent;
        this.game = game;
        this.nickName = nickName;
        this.turn = turn;
        this.c = c;
        this.timer = timer;

        add(new JLabel("Enter the nickname of a player you can see and that gave you damage:")).doLayout();
        txt1 = new JTextField("Write here", 25);
        add(txt1).doLayout();
        add(new JLabel("Enter the colour of the AmmoCube you want to use to pay:")).doLayout();
        txt2 = new JTextField("Write here", 25);
        add(txt2).doLayout();
        b = new JButton("Confirm end end");
        b.addActionListener(this);
        button = new JButton("Confirm and continue to choose another player");
        button.addActionListener(this);
        add(b);
        add(button);
    }

    public void actionPerformed(ActionEvent e) {
        try {
            JButton action = (JButton)e.getSource();
            if(timer != null)
                this.timer.cancel();
            if(action == b) {
                l.add(txt1.getText());

                socketOut.println("Message Is Valid Use PowerUp Card");
                socketOut.println(game);
                socketOut.println(nickName);
                socketOut.println("Targeting Scope");
                socketOut.println(c);
                socketOut.println(l.size());
                for(String s : l)
                    socketOut.println(s);
                socketOut.println(Colour.valueOf(txt2.getText()));

                String isValidUsePUC = socketIn.nextLine();

                if (isValidUsePUC.equals("true")) {
                    socketOut.println("Message Use PowerUp Card");
                    socketOut.println(game);
                    socketOut.println(nickName);
                    socketOut.println("Targeting Scope");
                    socketOut.println(c);
                    socketOut.println(l.size());
                    for(String s : l)
                        socketOut.println(s);
                    socketOut.println(Colour.valueOf(txt2.getText()));

                    if (turn == 1)
                        gui.action1();
                    if (turn == 2)
                        gui.action2();
                    if (turn == 3)
                        gui.reload();
                } else {
                    gui.usePowerUpCard();
                }
            }
            else {
                l.add(txt1.getText());
                socketOut.println("Message Is Valid Use PowerUp Card");
                socketOut.println(game);
                socketOut.println(nickName);
                socketOut.println("Targeting Scope");
                socketOut.println(c);
                socketOut.println(l.size());
                for(String s : l)
                    socketOut.println(s);
                socketOut.println(Colour.valueOf(txt2.getText()));

                String isValidUsePUC = socketIn.nextLine();

                if (isValidUsePUC.equals("true")) {
                    socketOut.println("Message Use PowerUp Card");
                    socketOut.println(game);
                    socketOut.println(nickName);
                    socketOut.println("Targeting Scope");
                    socketOut.println(c);
                    socketOut.println(l.size());
                    for(String s : l)
                        socketOut.println(s);
                    socketOut.println(Colour.valueOf(txt2.getText()));

                    gui.TSPUC(null, turn, c);
                }
                else
                    gui.usePowerUpCard();

            }
            parent.setVisible(false);
            parent.dispose();
        } catch (RemoteException ex) {

        } catch (InterruptedException exc){

        }
    }
}