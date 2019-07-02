package view.gui.socket;

import view.gui.GUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class Move1Socket extends JPanel implements ActionListener {

    private GUISocket gui;
    private Socket socket;
    private PrintWriter socketOut;
    private Scanner socketIn;
    private JFrame parent;
    private int game;
    private int identifier;
    private String nickName;
    private List<Integer> directions = new LinkedList<>();
    private JButton leftArrow;
    private JButton rightArrow;
    private JButton upArrow;
    private JButton downArrow;
    private JButton reset;
    private int dirCount;
    private JButton b;
    /*private JTextField txt1;
    private JTextField txt2;
    private JTextField txt3;*/

    public Move1Socket(GUISocket gui, Socket socket, int game, int identifier, String nickName, JFrame parent) throws IOException {
        super();
        this.gui = gui;
        this.socket = socket;
        this.socketOut = new PrintWriter(socket.getOutputStream(), true);
        this.socketIn = new Scanner(socket.getInputStream());
        this.parent = parent;
        this.game = game;
        this.identifier = identifier;
        this.nickName = nickName;

        /*txt1 = new JTextField("Write here", 25);
        txt2 = new JTextField("Write here", 25);
        txt3 = new JTextField("Write here", 25);
        b.addActionListener(this);
        add(new JLabel(("Enter your first direction:")));
        add(txt1);
        add(new JLabel("Enter your second direction:"));
        add(txt2);
        add(new JLabel("Enter your third direction:"));
        add(txt3);*/

        add(new JLabel("Select the directions you want to move in, up to 3"));
        leftArrow = new JButton("Left");
        rightArrow = new JButton("Right");
        upArrow = new JButton("Up");
        downArrow = new JButton("Down");
        reset = new JButton("Reset");
        add(leftArrow).doLayout();
        add(rightArrow).doLayout();
        add(upArrow).doLayout();
        add(downArrow).doLayout();
        add(reset).doLayout();
        leftArrow.addActionListener(new DirectionSelect());
        rightArrow.addActionListener(new DirectionSelect());
        upArrow.addActionListener(new DirectionSelect());
        downArrow.addActionListener(new DirectionSelect());
        reset.addActionListener(new DirectionSelect());
        b = new JButton("Confirm");
        add(b).doLayout();
        b.addActionListener(this);
        b.setEnabled(false);
    }

    private class DirectionSelect implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton direction = (JButton) e.getSource();
            if(dirCount >= 1 && dirCount <= 3)
                b.setEnabled(true);

            if(direction == reset) {
                dirCount = 0;
                directions.clear();
                leftArrow.setEnabled(true);
                rightArrow.setEnabled(true);
                upArrow.setEnabled(true);
                downArrow.setEnabled(true);
            }
            else {
                if(direction == leftArrow)
                    directions.add(4);
                else if(direction == rightArrow)
                    directions.add(2);
                else if(direction == upArrow)
                    directions.add(1);
                else if(direction == downArrow)
                    directions.add(3);
                dirCount++;

                if(dirCount == 3) {
                    leftArrow.setEnabled(false);
                    rightArrow.setEnabled(false);
                    upArrow.setEnabled(false);
                    downArrow.setEnabled(false);
                }
            }
        }
    }

    public synchronized void actionPerformed(ActionEvent e) {
        /*List<Integer> l = new LinkedList<>();
        l.add(Integer.parseInt(txt1.getText()));
        l.add(Integer.parseInt(txt2.getText()));
        l.add(Integer.parseInt(txt3.getText()));*/

        String validMove;

        do {
            socketOut.println("Message Is Valid First Action Move");
            socketOut.println(game);
            socketOut.println(nickName);
            socketOut.println(directions.size());
            for(Integer i : directions)
                socketOut.println(i);
            validMove = socketIn.nextLine();

        }while(!validMove.equals("true"));

        socketOut.println("Message First Action Move");
        socketOut.println(game);
        socketOut.println(nickName);
        socketOut.println(directions.size());
        for(Integer i : directions)
            socketOut.println(i);

        gui.doYouWantToUsePUC2();
        parent.dispose();
    }
}