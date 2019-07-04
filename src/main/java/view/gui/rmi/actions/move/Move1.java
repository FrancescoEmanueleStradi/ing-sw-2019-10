package view.gui.rmi.actions.move;

import network.ServerInterface;
import view.gui.rmi.GUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.LinkedList;
import java.util.List;
import java.util.Timer;

/**
 * Panel prompting move (first action) input parameters.
 */
public class Move1 extends JPanel implements ActionListener {

    private GUI gui;
    private ServerInterface server;
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
    private Timer timer;

    /**
     * Creates a new Move1.
     *
     * @param gui gui
     * @param server server
     * @param game game
     * @param identifier identifier
     * @param nickName nickname
     * @param parent parent frame
     * @param timer timer
     */
    public Move1(GUI gui, ServerInterface server, int game, int identifier, String nickName, JFrame parent, Timer timer) {
        super();
        this.gui = gui;
        this.server = server;
        this.parent = parent;
        this.game = game;
        this.identifier = identifier;
        this.nickName = nickName;
        this.timer = timer;

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
                if(dirCount >= 1 && dirCount <= 3)
                    b.setEnabled(true);

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
        try {
            timer.cancel();
            if(!server.messageIsValidFirstActionMove(game, nickName, directions)) {
                gui.moveFirstAction();
                parent.dispose();
            }
            else {
                server.messageFirstActionMove(game, nickName, directions);
                gui.doYouWantToUsePUC2();
                parent.dispose();
            }
        } catch (RemoteException | InterruptedException ex) {

        }
    }
}


