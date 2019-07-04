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
import java.util.Timer;

public class FFPanelSocket extends JPanel implements ActionListener {

    private GUISocket gui;
    private Socket socket;
    private PrintWriter socketOut;
    private Scanner socketIn;
    private JFrame parent;
    private int game;
    private String nickName;
    private List<String> l = new LinkedList<>();
    private JButton b;
    private JButton b2;
    private JButton b3;
    private JButton b4;
    private JButton b5;
    private Timer timer;
    private int count = 0;

    public FFPanelSocket(GUISocket gui, Socket socket, JFrame parent, int game, String nickName, Timer timer) throws IOException {
        super();
        this.gui = gui;
        this.socket = socket;
        this.socketOut = new PrintWriter(socket.getOutputStream(), true);
        this.socketIn = new Scanner(socket.getInputStream());
        this.parent = parent;
        this.game = game;
        this.nickName = nickName;
        this.timer = timer;


        add(new JLabel("This is the final turn. Final frenzy mode activated.\n" +
                "Choose the action(s) you want to do according to the fact you are before or after the player who started the game.\n" +
                "click exit if you have finished"));

        b = new JButton("1");
        b.addActionListener(this);
        add(b);
        b2 = new JButton("2");
        b2.addActionListener(this);
        add(b2);
        b3 = new JButton("3");
        b3.addActionListener(this);
        add(b3);
        b4 = new JButton("4");
        b4.addActionListener(this);
        add(b4);
        b5 = new JButton("5");
        b5.addActionListener(this);
        add(b5);


        add(new JButton("exit"));
    }



    public void actionPerformed(ActionEvent e) {
        this.timer.cancel();
        JButton action = (JButton) e.getSource();
        if (!action.getText().equals("exit"))
            l.add((action.getText()));
        else {
            socketOut.println("Message Is Valid Final Frenzy Action");
            socketOut.println(game);
            socketOut.println(nickName);
            socketOut.println(l.size());
            for(String s : l)
                socketOut.println(s);

            String isValidFFAction = socketIn.nextLine();

            if (isValidFFAction.equals("true")) {
                if (l.contains("1")) {
                    if(count == l.size())
                        gui.FirstFFAction(true);
                    else
                        gui.FirstFFAction(false);
                    count++;
                }
                if (l.contains("2")) {
                    if(count == l.size())
                        gui.SecondFFAction(true);
                    else
                        gui.SecondFFAction(false);
                    count++;
                }
                if (l.contains("3")) {
                    if(count == l.size())
                        gui.ThirdFFAction(true);
                    else
                        gui.ThirdFFAction(false);
                    count++;
                }
                if (l.contains("4")) {
                    if(count == l.size())
                        gui.FourthFFAction(true);
                    else
                        gui.FourthFFAction(false);
                    count++;
                }
                if (l.contains("5")) {
                    if(count == l.size())
                        gui.FifthFFAction(true);
                    else
                        gui.FifthFFAction(false);
                    count++;
                }

                this.parent.setVisible(false);
                this.parent.dispose();
            } else {
                gui.finalFrenzyTurn();
                this.parent.setVisible(false);
                this.parent.dispose();
            }
        }
    }
}