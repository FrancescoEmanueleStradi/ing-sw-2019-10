package view.gui.socket;

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

public class NPUCPanelSocket extends JPanel implements ActionListener {

    private GUISocket gui;
    private Socket socket;
    private PrintWriter socketOut;
    private Scanner socketIn;
    private int game;
    private String nickName;
    private JFrame parent;
    private JTextField txt1;
    private JTextField txt2;
    private JTextField txt3;
    private JTextField txt4;
    private JButton b;
    private java.util.Timer timer;
    private int turn;
    private String c;
    private List<String> l = new LinkedList<>();

    public NPUCPanelSocket(GUISocket gui, Socket socket, JFrame parent, int game, String nickName, java.util.Timer timer, int turn, String c) throws IOException {
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
        add(new JLabel("Enter the direction(s) in which you want the enemy to go")).doLayout();
        txt2 = new JTextField("", 25);
        add(txt2).doLayout();
        txt3 = new JTextField("", 25);
        add(txt3).doLayout();
        txt4 = new JTextField("", 25);
        add(txt4).doLayout();
        b = new JButton("Confirm");
        b.addActionListener(this);
        add(b);
    }


    public void actionPerformed(ActionEvent e) {
        try {
            this.timer.cancel();
            l.add(txt1.getText());
            if (!txt2.getText().equals(""))
                l.add(txt2.getText());
            if (!txt3.getText().equals(""))
                l.add(txt3.getText());
            if (!txt4.getText().equals(""))
                l.add(txt4.getText());

            socketOut.println("Message Is Valid Use PowerUp Card");
            socketOut.println(game);
            socketOut.println(nickName);
            socketOut.println("Newton");
            socketOut.println(c);
            socketOut.println(l.size());
            for(String s : l)
                socketOut.println(s);
            socketOut.println("null");

            String isValidUsePUC = socketIn.nextLine();

            if (isValidUsePUC.equals("true")) {
                socketOut.println("Message Use PowerUp Card");
                socketOut.println(game);
                socketOut.println(nickName);
                socketOut.println("Newton");
                socketOut.println(c);
                socketOut.println(l.size());
                for(String s : l)
                    socketOut.println(s);
                socketOut.println("null");

                if (turn == 1)
                    gui.action1();
                if (turn == 2)
                    gui.action2();
                if (turn == 3)
                    gui.reload();
            } else {
                gui.usePowerUpCard();
            }
            parent.setVisible(false);
            parent.dispose();

        } catch (RemoteException ex) {

        } catch (InterruptedException exc) {

        }
    }
}