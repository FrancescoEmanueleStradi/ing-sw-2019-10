package view.gui;

import view.View;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WantUsePUCPanel extends JPanel implements ActionListener {

    private View view;

    public WantUsePUCPanel(View view) {
        this.view = view;
        add(new JLabel("Do you want to use Power Up card?"));
        add(new JButton("Yes"));
        add(new JButton("No"));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //if(e.paramString().equals("yes")

            //TODO
    }
}
