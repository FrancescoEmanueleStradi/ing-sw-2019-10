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
    private List<Colour> lC;
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
    private JButton finalConfirm;
    private JComboBox slotList;
    private JComboBox slot1List, slot2List, slot3List;

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
                downArrow.setEnabled(true);
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

            add(new JLabel("Pick a WeaponSlot to grab a card from, and the card you want from it")).doLayout();
            add(slotList).doLayout();
            slotList.addActionListener(new SlotSelect());
            add(slot1List).doLayout();
            slot1List.setVisible(false);
            add(slot2List).doLayout();
            slot2List.setVisible(false);
            add(slot3List).doLayout();
            slot3List.setVisible(false);

            List<String> powerUps = server.messageGetPowerUpCard(game, nickName);
            List<String> powerUpColours = server.messageGetPowerUpCardColour(game, nickName);
            int i = 0;
            List<JCheckBox> powerUpBoxes = new LinkedList<>();
            if (!powerUps.isEmpty()) {
                add(new JLabel("Choose a PowerUpCard/s to pay with if necessary")).doLayout();
                for(String pC : powerUps) {
                    powerUpBoxes.add(new JCheckBox(pC/* + " " + powerUpColours.get(i)*/, false));
                    powerUpBoxes.get(powerUpBoxes.size() - 1).addActionListener(new PowerUpSelect());
                    // will *probably* not go out of bounds as the two lists must be the same size
                    i++;
                }
            } else add(new JLabel("You have no PowerUpCards with which to pay"));

            finalConfirm = new JButton("Confirm grab");
            finalConfirm.setEnabled(false);

        } catch (RemoteException /*| InterruptedException*/ e) {

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
                    lC = server.messageGetReloadCostReduced(game, (String)slot1List.getSelectedItem());
                }
                else if(slotNum == 2) {
                    slot1List.setVisible(false);
                    slot2List.setVisible(true);
                    slot3List.setVisible(false);
                    lC = server.messageGetReloadCostReduced(game, (String)slot2List.getSelectedItem());
                }
                else if(slotNum == 3) {
                    slot1List.setVisible(false);
                    slot2List.setVisible(false);
                    slot3List.setVisible(true);
                    lC = server.messageGetReloadCostReduced(game, (String)slot3List.getSelectedItem());
            }

            } catch (RemoteException ex) {

            }
        }
    }

    private class PowerUpSelect implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JCheckBox checked = (JCheckBox)e.getSource();
            String power = checked.getText();
            if (checked.isSelected()) {
                checked.setSelected(false);
                lP.remove(power);
            }
            else if (!checked.isSelected()) {
                checked.setSelected(true);
                lP.add(power);
            }
            if (!finalConfirm.isEnabled())
                finalConfirm.setEnabled(true);
        }
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
