package view;

import javax.swing.*;
import java.util.LinkedList;
import java.util.List;

public class CardLinkList {

    private List<CardLink> cards;

    public CardLinkList(){
        cards = new LinkedList<>();

        //TODO create the connection between every cardName and hi ImageIcon
    }


    public List<ImageIcon> getImageIconFromName(List<String> cardName){
        List<ImageIcon> l = new LinkedList<>();
        for(String s : cardName) {
            for (CardLink c : cards) {
                if (c.getCard().equals(s))
                    l.add(c.getImage());
            }
        }
        return l;
    }

    public List<String> getNamefromImageIcon(List<ImageIcon> cardImages){
        List<String> l = new LinkedList<>();
        for(ImageIcon i : cardImages) {
            for (CardLink c : cards) {
                if (c.getImage().equals(i))
                    l.add(c.getCard());
            }
        }
        return l;
    }



}
