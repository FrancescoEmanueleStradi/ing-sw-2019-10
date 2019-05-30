package view;

import model.Colour;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.LinkedList;
import java.util.List;

public class Grab1 extends JPanel {

    private GUI gui;
    private ServerInterface server;
    private int game;
    private int identifier;
    private String nickName;
    private List<Integer> directions = new LinkedList<>();
    private List<Colour> lC = new LinkedList<>();
    private List<String> lP = new LinkedList<>();
    private List<String> lPC = new LinkedList<>();
    private String wCard = "";
    private String weaponSlot = "";
    private JButton leftArrow;
    private JButton rightArrow;
    private JButton upArrow;
    private JButton downArrow;
    private JButton reset;
    private int dirCount;
    private JButton weaponConfirm;
    private JButton ammoConfirm;
    private JFrame weaponFrame;
    private JFrame ammoFrame;

    public Grab1(GUI gui, ServerInterface server, int game, int identifier, String nickName) {
        super();
        this.gui = gui;
        this.server = server;
        this.game = game;
        this.identifier = identifier;
        this.nickName = nickName;
        //directions = new LinkedList<>();

        add(new JLabel("Select the directions you want to move in, if you like; one if you haven't unlocked the adrenaline" +
                "move, up to two otherwise"));
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

        add(new JLabel("Grab AmmoCard or WeaponCard?"));
        weaponConfirm = new JButton("WeaponCard");
        add(weaponConfirm).doLayout();
        weaponConfirm.addActionListener(new CardSelect());
        ammoConfirm = new JButton("AmmoCard");
        add(ammoConfirm).doLayout();
        ammoConfirm.addActionListener(new CardSelect());
    }

    private class CardSelect implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton cardConfirm = (JButton)e.getSource();
            //weaponConfirm.setEnabled(false);
            //ammoConfirm.setEnabled(false);
            if(cardConfirm == weaponConfirm)
                weaponGrab();
            else if(cardConfirm == ammoConfirm)
                ammoGrab();
        }
    }

    private class DirectionSelect implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton direction = (JButton)e.getSource();
            if(direction == reset) {
                dirCount = 0;
                directions.clear();
                leftArrow.setEnabled(true);
                rightArrow.setEnabled(true);
                upArrow.setEnabled(true);
                downArrow.setEnabled(false);
            }

            if(direction == leftArrow)
                directions.add(4);
            else if(direction == rightArrow)
                directions.add(2);
            else if(direction == upArrow)
                directions.add(1);
            else if(direction == downArrow)
                directions.add(3);
            dirCount++;

            if(dirCount == 2) {             //maximum direction count is 2, but isValid will check whether player is or isn't in Adrenaline
                leftArrow.setEnabled(false);
                rightArrow.setEnabled(false);
                upArrow.setEnabled(false);
                downArrow.setEnabled(false);
            }
        }
    }

    private void weaponGrab() {

    }

    private void ammoGrab() {
        try {
            while (!this.server.messageIsValidFirstActionGrab(game, nickName, directions, wCard, weaponSlot, lC, lP, lPC))
                gui.grabFirstAction();
            server.messageFirstActionGrab(game, nickName, directions, wCard, lC, lP, lPC);
        } catch (RemoteException | InterruptedException e) {

        }
    }
}
