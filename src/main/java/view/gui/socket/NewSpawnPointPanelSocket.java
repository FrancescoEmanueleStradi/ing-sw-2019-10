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

public class NewSpawnPointPanelSocket extends JPanel implements ActionListener {

    private GUISocket gui;
    private Socket socket;
    private PrintWriter socketOut;
    private Scanner socketIn;
    private JFrame parent;
    private int game;
    private String nickName;
    private JButton b;
    private JTextField txt1;
    private JTextField txt2;

    public NewSpawnPointPanelSocket(GUISocket gui, Socket socket, JFrame parent, int game, String nickName) throws IOException {
        super();
        this.gui = gui;
        this.socket = socket;
        this.socketOut = new PrintWriter(socket.getOutputStream(), true);
        this.socketIn = new Scanner(socket.getInputStream());
        this.parent = parent;
        this.game = game;
        this.nickName = nickName;

        socketOut.println("Message Get PowerUp Card Name And Colour");
        socketOut.println(game);
        socketOut.println(nickName);
        int sizePUC = Integer.parseInt(socketIn.nextLine());
        List<String> namePUC = new LinkedList<>();
        List<String> colourPUC = new LinkedList<>();
        for(int i = 0; i < sizePUC; i++) {
            if(i % 2 == 0)
                namePUC.add(socketIn.nextLine());
            else
                colourPUC.add(socketIn.nextLine());
        }

        add(new JLabel("The following are " + this.nickName +"'s starting PowerUpCards")).setBounds(0,0, 5, 5);
        add(new JLabel("Enter the PowerUp card you want to discard; its colour will be your new spawn point:")).doLayout();
        for (int i = 0; i < sizePUC; i++)
            add(new JLabel(namePUC.get(i) + "coloured" + colourPUC.get(i))).doLayout();

        b = new JButton("Confirm");
        txt1 = new JTextField("Write here the name", 25);
        txt2 = new JTextField("Write here the colour", 25);
        b.addActionListener(this);
        add(new JLabel(("Enter the name:"))).doLayout();
        add(txt1).doLayout();
        add(new JLabel("Enter the colour:")).doLayout();
        add(txt2).doLayout();
        add(b);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            socketOut.println("Message Is Valid Discard Card For Spawn Point");
            socketOut.println(game);
            socketOut.println(nickName);
            socketOut.println(txt1.getText());
            socketOut.println(txt2.getText());

            String isValidDiscardCardForSpawnPoint = socketIn.nextLine();

            if(isValidDiscardCardForSpawnPoint.equals("true")) {
                socketOut.println("Message Discard Card For Spawn Point");
                socketOut.println(game);
                socketOut.println(nickName);
                socketOut.println(txt1.getText());
                socketOut.println(txt2.getText());
            }
            else
                gui.newSpawnPoint();

            parent.setVisible(false);
            parent.dispose();
        }

        catch (RemoteException r) {

        }
    }
}