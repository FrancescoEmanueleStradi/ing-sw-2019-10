package controller;

import model.cards.*;
import model.*;

public class Game {

    private Grid grid;

    public void askPlayerDamage(){
        System.out.println("Write the Nickname of player you want to damage");
    }

    public void errorInvalidPlayer(){
        System.out.println("You wrote an Invalid Nickname");
    }

    public void enterYourNickname(){
        System.out.println("Enter your Nickname:");
    }

    public void effectType(){
        System.out.println("Which type of effect you desire to use?");
    }

    public void printCardDetails(WeaponCard w){
        System.out.println("here are the details:"+w.getDescription());
    }

    

}
