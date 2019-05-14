package view;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

 public class TakeInformation extends JPanel implements ActionListener {
     private JButton b;
     private JTextField txt1, txt2;

     public TakeInformation() {
         super();
         b = new JButton("Confirm");
         txt1=new JTextField("Write here", 25);
         txt2 = new JTextField(25);
         txt2.setEditable(false);
         b.addActionListener(this);
         add(txt1);
         add(txt2);
         add(b);
     }

     public void actionPerformed(ActionEvent e){
         txt2.setText( txt1.getText() );
     }

     public String getTxt1() {
         return txt1.getText();
     }
 }
