package view;

import model.InvalidColourException;

public abstract class View {

    void askNameAndColour() throws InvalidColourException{}
    void selectSpawnPoint(){};
    void action1(){}
    void action2(){}
    void reload(){}
    void scoring(){}
    void newSpawnPoint(){}
    void replace(){}
}
