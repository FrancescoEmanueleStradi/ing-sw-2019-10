package Model;

public class InvalidColourException extends Exception{

    public InvalidColourException(){

        super("Model.Colour is not blue, yellow or red");

    }


}
