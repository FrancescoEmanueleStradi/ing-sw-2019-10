package view.gui.socket;

import model.Colour;
import view.gui.CardLinkList;
import network.ServerInterface;
import view.gui.GUI;

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

public class Grab1Socket extends JPanel {

    private GUISocket gui;
    private Socket socket;
    private PrintWriter socketOut;
    private Scanner socketIn;
    private int game;
    private int identifier;
    private String nickName;
    private List<Integer> directions = new LinkedList<>();
    private List<Colour> lC = new LinkedList<>();
    private List<String> lP = new LinkedList<>();
    private List<String> lPC = new LinkedList<>();
    private CardLinkList cardLink = new CardLinkList();
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
    private JButton finalConfirm;
    private JComboBox slotList;
    private JComboBox slot1List, slot2List, slot3List;

    public Grab1Socket(GUISocket gui, Socket socket, int game, int identifier, String nickName) throws IOException {
        super();
        this.gui = gui;
        this.socket = socket;
        this.socketOut = new PrintWriter(socket.getOutputStream(), true);
        this.socketIn = new Scanner(socket.getInputStream());
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
                try {
                    ammoGrab();
                }catch (IOException ex) {

                }
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

                //maximum direction count is 2, but isValid will check whether player is or isn't in Adrenaline
                if(dirCount == 2) {
                    leftArrow.setEnabled(false);
                    rightArrow.setEnabled(false);
                    upArrow.setEnabled(false);
                    downArrow.setEnabled(false);
                }
            }
        }
    }

    private void weaponGrab() {
        JPanel wGrab = new JPanel();
        wGrab.add(new JLabel("Grab a Weapon"));
        this.add(wGrab);
        Object[] slots = {1, 2, 3};
        slotList = new JComboBox(slots);

        List<String> lWS1 = new LinkedList<>();

        socketOut.println("Message Check Weapon Slot Contents Reduced");
        socketOut.println(game);
        socketOut.println(1);
        int size1 = Integer.parseInt(socketIn.nextLine());
        for(int i = 0; i < size1; i++)
            lWS1.add(socketIn.nextLine());

        Object[] weapons1 = {lWS1.get(0), lWS1.get(1), lWS1.get(2)};

        List<String> lWS2 = new LinkedList<>();

        socketOut.println("Message Check Weapon Slot Contents Reduced");
        socketOut.println(game);
        socketOut.println(1);
        int size2 = Integer.parseInt(socketIn.nextLine());
        for(int i = 0; i < size2; i++)
            lWS2.add(socketIn.nextLine());

        Object[] weapons2 = {lWS2.get(0), lWS2.get(1), lWS2.get(2)};

        List<String> lWS3 = new LinkedList<>();

        socketOut.println("Message Check Weapon Slot Contents Reduced");
        socketOut.println(game);
        socketOut.println(1);
        int size3 = Integer.parseInt(socketIn.nextLine());
        for(int i = 0; i < size3; i++)
            lWS3.add(socketIn.nextLine());

        Object[] weapons3 = {lWS3.get(0), lWS3.get(1), lWS3.get(2)};

        slot1List = new JComboBox(weapons1);
        slot2List = new JComboBox(weapons2);
        slot3List = new JComboBox(weapons3);

        wGrab.add(new JLabel("Pick a WeaponSlot to grab a card from, and the card you want from it")).doLayout();
        wGrab.add(slotList).doLayout();
        slotList.addActionListener(new SlotSelect());
        wGrab.add(slot1List).doLayout();
        slot1List.setVisible(false);
        wGrab.add(slot2List).doLayout();
        slot2List.setVisible(false);
        wGrab.add(slot3List).doLayout();
        slot3List.setVisible(false);

        socketOut.println("Message Get PowerUp Card Name And Colour");
        socketOut.println(game);
        socketOut.println(nickName);
        int sizePowerUpNameAndColour = Integer.parseInt(socketIn.nextLine());

        List<String> powerUps = new LinkedList<>();
        List<String> powerUpColours = new LinkedList<>();

        for(int i = 0; i < sizePowerUpNameAndColour; i++) {
            if (i % 2 == 0)
                powerUps.add(socketIn.nextLine());
            else
                powerUpColours.add(socketIn.nextLine());
        }

        List<JCheckBox> powerUpBoxes = new LinkedList<>();
        if(!powerUps.isEmpty()) {
            add(new JLabel("Choose a PowerUpCard/s to pay with if necessary")).doLayout();
            for(int i = 0; i < powerUps.size(); i++) {
                powerUpBoxes.add(new JCheckBox(cardLink.getImageIconFromName(powerUps.get(i), powerUpColours.get(i))));
                wGrab.add(powerUpBoxes.get(i));
                powerUpBoxes.get(i).addActionListener(new PowerUpSelect());
            }
        } else wGrab.add(new JLabel("You have no PowerUpCards with which to pay"));

        finalConfirm = new JButton("Confirm grab");
         wGrab.add(finalConfirm);
         finalConfirm.setEnabled(false);

    }

    private class SlotSelect implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JComboBox slot = (JComboBox) e.getSource();
            Integer slotNum = (Integer)slot.getSelectedItem();

            if(slotNum == 1) {
                slot1List.setVisible(true);
                slot2List.setVisible(false);
                slot3List.setVisible(false);
                socketOut.println("Message Get Reload Cost Reduced");
                socketOut.println(game);
                socketOut.println((String)slot1List.getSelectedItem());
                int sizeReloadCost = Integer.parseInt(socketIn.nextLine());
                for(int i = 0; i < sizeReloadCost; i++)
                    lC.add(Colour.valueOf(socketIn.nextLine()));
            }
            else if(slotNum == 2) {
                slot1List.setVisible(false);
                slot2List.setVisible(true);
                slot3List.setVisible(false);
                socketOut.println("Message Get Reload Cost Reduced");
                socketOut.println(game);
                socketOut.println((String)slot2List.getSelectedItem());
                int sizeReloadCost = Integer.parseInt(socketIn.nextLine());
                for(int i = 0; i < sizeReloadCost; i++)
                    lC.add(Colour.valueOf(socketIn.nextLine()));
            }
            else if(slotNum == 3) {
                slot1List.setVisible(false);
                slot2List.setVisible(false);
                slot3List.setVisible(true);
                socketOut.println("Message Get Reload Cost Reduced");
                socketOut.println(game);
                socketOut.println((String)slot3List.getSelectedItem());
                int sizeReloadCost = Integer.parseInt(socketIn.nextLine());
                for(int i = 0; i < sizeReloadCost; i++)
                    lC.add(Colour.valueOf(socketIn.nextLine()));
            }
        }
    }

    private class PowerUpSelect implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JCheckBox checked = (JCheckBox)e.getSource();
            ImageIcon power = (ImageIcon)checked.getIcon();
            if(!checked.isSelected()) {
                checked.setSelected(false);
                lP.remove(cardLink.getNamefromImageIcon(power));
                lPC.remove(cardLink.getColourfromImageIcon(power));
            }
            else if(checked.isSelected()) {
                checked.setSelected(true);
                lP.add(cardLink.getNamefromImageIcon(power));
                lPC.add(cardLink.getColourfromImageIcon(power));
            }
            if(!finalConfirm.isEnabled())
                finalConfirm.setEnabled(true);
        }
    }

    private void ammoGrab() throws IOException {
        socketOut.println("Message Is Valid First Action Grab");
        socketOut.println(game);
        socketOut.println(nickName);
        socketOut.println(directions.size());
        for(Integer i : directions)
            socketOut.println(i);
        socketOut.println(wCard);
        socketOut.println(weaponSlot);
        socketOut.println(lC.size());
        for(Colour c : lC)
            socketOut.println(c.getColourId());
        socketOut.println(lP.size());
        for(String s : lP)
            socketOut.println(s);
        socketOut.println(lPC.size());
        for(String s : lPC)
            socketOut.println(s);

        String isValidFirstActionGrab = socketIn.nextLine();

        if(isValidFirstActionGrab.equals("true"))
            gui.grabFirstAction();

        socketOut.println("Message First Action Grab");
        socketOut.println(game);
        socketOut.println(nickName);
        socketOut.println(directions.size());
        for(Integer i : directions)
            socketOut.println(i);
        socketOut.println(wCard);
        socketOut.println(lC.size());
        for(Colour c : lC)
            socketOut.println(c.getColourId());
        socketOut.println(lP.size());
        for(String s : lP)
            socketOut.println(s);
        socketOut.println(lPC.size());
        for(String s : lPC)
            socketOut.println(s);
    }
}