package controller;

import model.cards.PowerUpCard;
import model.cards.WeaponCard;

public class Cli {

    public void askPlayerDamage(){
        System.out.println("Write the Nickname of the player you want to damage");
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

    public void printCardDetails(PowerUpCard p){
        System.out.println("here are the details:"+p.getDescription());
    }
}
