package model;

public class InvalidColourException extends Exception{

    public InvalidColourException(){

        super("model.Colour is not blue, yellow or red");

    }


}
