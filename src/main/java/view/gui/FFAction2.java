package view.gui;

import network.ServerInterface;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.LinkedList;
import java.util.List;
import java.util.Timer;

/**
 * Second Final Frenzy action panel.
 */
public class FFAction2 extends JPanel implements ActionListener {

    private GUI gui;
    private ServerInterface server;
    private JFrame parent;
    private int game;
    private String nickName;
    private Timer timer;

    private List<Integer> directions = new LinkedList<>();
    private JButton leftArrow;
    private JButton rightArrow;
    private JButton upArrow;
    private JButton downArrow;
    private JButton reset;
    private int dirCount;
    private JButton b;

    /**
     * Creates a new FFAction2.
     *
     * @param gui gui
     * @param server server
     * @param parent parent
     * @param game game
     * @param nickName nickname
     */
    public FFAction2(GUI gui, ServerInterface server, JFrame parent, int game, String nickName) {
        super();
        this.gui = gui;
        this.server = server;
        this.parent = parent;
        this.game = game;
        this.nickName = nickName;

        add(new JLabel("Final Frenzy action 2.\n" +
                "You may move up to 4 squares."));

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
            if(dirCount >= 1 && dirCount <= 4)
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

                if(dirCount == 4) {
                    leftArrow.setEnabled(false);
                    rightArrow.setEnabled(false);
                    upArrow.setEnabled(false);
                    downArrow.setEnabled(false);
                }
            }
        }
    }

    public synchronized void actionPerformed(ActionEvent e) {
        try {
            if(!server.messageIsValidFinalFrenzyAction2(game, nickName, directions)) {
                gui.secondFFAction(false);
                parent.dispose();
            }
            else {
                server.messageFinalFrenzyAction2(game, nickName, directions);
                //gui.doYouWantToUsePUC3();
                parent.setVisible(false);
                parent.dispose();
            }
        } catch (RemoteException ex) {

        }
    }
}
