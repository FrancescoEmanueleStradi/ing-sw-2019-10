package view.gui.rmi.actions.grab;

import model.Colour;
import network.ServerInterface;
import view.gui.common.CardLinkList;
import view.gui.rmi.GUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.LinkedList;
import java.util.List;
import java.util.Timer;

public class Grab2 extends JPanel {

    private GUI gui;
    private ServerInterface server;
    private int game;
    private int identifier;
    private String nickName;
    private List<Integer> directions = new LinkedList<>();
    private List<Colour> lC = new LinkedList<>();
    private List<String> lP = new LinkedList<>();
    private List<String> lPC = new LinkedList<>();
    private List<String> powerUps;
    private List<String> powerUpColours;
    private List<Colour> reducedReload;
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
    private JFrame parent;
    private java.util.Timer timer;

    public Grab2(GUI gui, ServerInterface server, int game, int identifier, String nickName, JFrame parent, Timer timer) throws RemoteException {
        super();
        this.gui = gui;
        this.server = server;
        this.game = game;
        this.identifier = identifier;
        this.nickName = nickName;
        this.parent = parent;
        this.timer = timer;
        this.powerUps = server.messageGetPlayerPowerUpCard(game, nickName);
        this.powerUpColours = server.messageGetPlayerPowerUpCardColour(game, nickName);

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
            timer.cancel();
            JButton cardConfirm = (JButton)e.getSource();
            weaponConfirm.setEnabled(false);
            ammoConfirm.setEnabled(false);
            if(cardConfirm == weaponConfirm)
                weaponGrab();
            else if(cardConfirm == ammoConfirm)
                finalGrab();
            weaponConfirm.setEnabled(false);
            ammoConfirm.setEnabled(false);
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

        try {
            Object[] weapons1 = {server.messageCheckWeaponSlotContentsReduced(game, 1).get(0), server.messageCheckWeaponSlotContentsReduced(game, 1).get(1),
                    server.messageCheckWeaponSlotContentsReduced(game, 1).get(2)};
            Object[] weapons2 = {server.messageCheckWeaponSlotContentsReduced(game, 2).get(0), server.messageCheckWeaponSlotContentsReduced(game, 2).get(1),
                    server.messageCheckWeaponSlotContentsReduced(game, 2).get(2)};
            Object[] weapons3 = {server.messageCheckWeaponSlotContentsReduced(game, 3).get(0), server.messageCheckWeaponSlotContentsReduced(game, 3).get(1),
                    server.messageCheckWeaponSlotContentsReduced(game, 3).get(2)};
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

            List<JCheckBox> ammoBoxes = new LinkedList<>();
            List<JCheckBox> powerUpBoxes = new LinkedList<>();

            if(reducedReload.isEmpty()) {
                add(new JLabel("Choose an AmmoCube/s to pay with if necessary")).doLayout();
                for(int i = 0; i < reducedReload.size(); i++) {
                    ammoBoxes.add(new JCheckBox(reducedReload.get(i).getColourId()));
                    wGrab.add(ammoBoxes.get(i));
                    ammoBoxes.get(i).addActionListener(new AmmoSelect());
                }
            } else wGrab.add(new JLabel("You have no AmmoCubes with which to pay"));

            if(!powerUps.isEmpty()) {
                add(new JLabel("Choose a PowerUpCard/s to pay with if necessary")).doLayout();
                for(int i = 0; i < powerUps.size(); i++) {
                    powerUpBoxes.add(new JCheckBox(cardLink.getImageIconFromName(powerUps.get(i), powerUpColours.get(i))));
                    wGrab.add(new JLabel("Powerup " + powerUps.get(i) + " of colour " + powerUpColours.get(i)));
                    wGrab.add(powerUpBoxes.get(i)).setVisible(true);
                    powerUpBoxes.get(i).addActionListener(new PowerUpSelect());
                }
            } else wGrab.add(new JLabel("You have no PowerUpCards with which to pay"));
            finalConfirm = new JButton("Confirm grab");
            wGrab.add(finalConfirm);
            finalConfirm.addActionListener(new GrabFinal());
            finalConfirm.setEnabled(true);

        } catch(RemoteException /*| InterruptedException*/ e){

        }
    }

    private class SlotSelect implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JComboBox slot = (JComboBox) e.getSource();
            Integer slotNum = (Integer)slot.getSelectedItem();

            try {
                if(slotNum == 1) {
                    slot1List.setVisible(true);
                    slot2List.setVisible(false);
                    slot3List.setVisible(false);
                    setReducedReload(server.messageGetPlayerReloadCost(game,(String)slot1List.getSelectedItem(), nickName));
                }
                else if(slotNum == 2) {
                    slot1List.setVisible(false);
                    slot2List.setVisible(true);
                    slot3List.setVisible(false);
                    setReducedReload(server.messageGetPlayerReloadCost(game,(String)slot2List.getSelectedItem(), nickName));
                }
                else if(slotNum == 3) {
                    slot1List.setVisible(false);
                    slot2List.setVisible(false);
                    slot3List.setVisible(true);
                    setReducedReload(server.messageGetPlayerReloadCost(game,(String)slot3List.getSelectedItem(), nickName));
                }

            } catch (RemoteException ex) {

            }
        }
    }

    private class AmmoSelect implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JCheckBox checked = (JCheckBox)e.getSource();
            Colour ammoColour = Colour.valueOf(checked.getName());
            if(checked.isSelected()) {
                lC.add(ammoColour);
            }
            else {
                lC.remove(ammoColour);
            }
        }
    }

    private class PowerUpSelect implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JCheckBox checked = (JCheckBox)e.getSource();
            ImageIcon power = (ImageIcon)checked.getIcon();
            if(checked.isSelected()) {
                lP.add(cardLink.getNameFromImageIcon(power));
                lPC.add(cardLink.getColourfromImageIcon(power));
            }
            else {
                lP.remove(cardLink.getNameFromImageIcon(power));
                lPC.remove(cardLink.getColourfromImageIcon(power));
            }
            if(!finalConfirm.isEnabled())
                finalConfirm.setEnabled(true);
        }
    }

    private void finalGrab() {
        try {
            if(!this.server.messageIsValidSecondActionGrab(game, nickName, directions, wCard, weaponSlot, lC, lP, lPC)) {
                gui.grabSecondAction();
            }
            server.messageSecondActionGrab(game, nickName, directions, wCard, lC, lP, lPC);
            gui.doYouWantToUsePUC3();
            parent.setVisible(false);
            parent.dispose();
        } catch (RemoteException | InterruptedException e) {

        }
    }

    private class GrabFinal implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            finalGrab();
        }
    }

    public List<Colour> getReducedReload() {
        return reducedReload;
    }

    public void setReducedReload(List<Colour> reload) {
        this.reducedReload = reload;
    }
}

